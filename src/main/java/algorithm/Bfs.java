package algorithm;

import org.omg.CORBA.NO_IMPLEMENT;
import problem.Node;
import problem.NodeType;
import problem.Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Bfs {

    private Node source;
    private Node target;

    private List<Node> openList;
    private List<Node> closeList;
    private List<Node> path;

    // TODO getOpenlist, getCloselist für das Einzeichnen?

    public Bfs() {

    }

    public List<Node> calculate(Problem problem, int source, int target){

        this.source = problem.getField()[source];
        this.target = problem.getField()[target];
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();

        this.path = new ArrayList<Node>();

        if(this.source.equals(this.target)){
            System.out.println("Ziel gefunden: Source ist Target");
            path.add(this.source);
            return path;
        };

        // TODO blocked Node berücksichtigen



        openList.add(this.source);
        Node current;

        boolean run = true;
        while(!openList.isEmpty() && run){
            current = openList.get(0);
            openList.remove(0);

            closeList.add(current);
            List<Node> childs = problem.expandNode(current);

            // TODO Lambdas
            // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

            for(Node child: childs){
                if(!openList.contains(child) && !closeList.contains(child)){

                    if(child.equals(this.target)) {
                        System.out.println("Ziel gefunden: " + child.toString());
                        path = this.tracePath(child);
                        run = false;
                        break;
                    }
                    openList.add(child);
                    child.setType(NodeType.OPENLIST);

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

    public List<Node> getOpenList() {
        return openList;
    }

    public List<Node> getCloseList() {
        return closeList;
    }

    public List<Node> getPath() {
        return path;
    }
}
