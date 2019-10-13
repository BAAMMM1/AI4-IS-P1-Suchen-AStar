package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.problem.Node;
import mvc.model.problem.NodeType;
import mvc.model.problem.Problem;

import java.util.LinkedList;
import java.util.List;

public class BreadthFirst extends SearchAlgorithm {


    public BreadthFirst(Problem problem, int source, int target) {
        super(problem, source, target);
    }

    public void calculate(){

        if(this.getSource().equals(target)){

            System.out.println("Ziel gefunden: Source ist Target");
            path.add(source);

        } else {

            LinkedList<Node> queue = new LinkedList<>();

            queue.add(source);
            Node current;

            boolean run = true;
            while(!queue.isEmpty() && run){

                current = queue.get(0);
                queue.remove(0);

                closeList.add(current);
                List<Node> childs = problem.expandNode(current);

                // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

                for(Node child: childs){
                    if(!queue.contains(child) && !closeList.contains(child)){

                        if(child.equals(target)) {
                            System.out.println("Ziel gefunden: " + child.toString());
                            path = this.tracePath(child);
                            run = false;
                            break;
                        }
                        queue.add(child);
                        child.setType(NodeType.OPENLIST);

                    }
                }
            }

            openList.addAll(queue);

        }

    }



}
