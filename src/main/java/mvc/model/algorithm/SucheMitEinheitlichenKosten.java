package mvc.model.algorithm;

import mvc.model.problem.Node;
import mvc.model.problem.NodeType;
import mvc.model.problem.Problem;

import java.util.*;

public class SucheMitEinheitlichenKosten {

    private Node source;
    private Node target;

    private PriorityQueue<Node> openList;
    private List<Node> closeList;
    private List<Node> path;

    public SucheMitEinheitlichenKosten() {

    }

    public List<Node> calculate(Problem problem, int source, int target){

        this.source = problem.getField()[source];
        this.target = problem.getField()[target];
        this.openList = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.getgCoast(), o2.getgCoast());
            }
        });
        this.closeList = new ArrayList<Node>();

        this.path = new ArrayList<Node>();

        openList.add(this.source);
        Node current;

        boolean run = true;
        while(!openList.isEmpty() && run){
            current = openList.poll();

            if(current.equals(this.target)) {
                System.out.println("Ziel gefunden: " + current.toString());
                path = this.tracePath(current);
                run = false;
                System.out.println("target gCoast: " + current.getgCoast());
                break;
            }

            closeList.add(current);
            List<Node> childs = problem.expandNode(current);

            // TODO Lambdas
            // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

            for(Node child: childs){
                int currentPathCoastToChild = child.getgCoast();
                int newPathCoast= current.getgCoast()+child.getStepCoast();

                if(!openList.contains(child) && !closeList.contains(child)){
                    child.setgCoast(newPathCoast);
                    openList.add(child);
                    child.setType(NodeType.OPENLIST);
                } else if(openList.contains(child) && currentPathCoastToChild > newPathCoast){
                    child.setgCoast(newPathCoast);
                }
            }
        }

        // TODO Path anhand der partens berechnen


        return path;
    }



    private List<Node> tracePath(Node node){

        List<Node> result = new ArrayList<>();
        Node currenNode = node;

        result.add(node);

        while(currenNode.getParent() != null){
            result.add(currenNode.getParent());
            currenNode = currenNode.getParent();
        }

        Collections.reverse(result);

        return result;

    }



    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public List<Node> getCloseList() {
        return closeList;
    }

    public List<Node> getPath() {
        return path;
    }

}
