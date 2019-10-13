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

    // TODO schrittkosten

    public void calculate(){

        if(this.getSource().equals(this.getTarget())){
            System.out.println("Ziel gefunden: Source ist Target");
            this.getPath().add(this.getSource());

        } else {

            LinkedList<Node> queue = new LinkedList<>();

            queue.add(this.getSource());
            Node current;

            boolean run = true;
            while(!queue.isEmpty() && run){
                current = queue.get(0);
                queue.remove(0);

                this.getCloseList().add(current);
                List<Node> childs = this.getProblem().expandNode(current);

                // TODO Lambdas
                // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

                for(Node child: childs){
                    if(!queue.contains(child) && !this.getCloseList().contains(child)){

                        if(child.equals(this.getTarget())) {
                            System.out.println("Ziel gefunden: " + child.toString());
                            this.setPath(this.tracePath(child));
                            run = false;
                            break;
                        }
                        queue.add(child);
                        child.setType(NodeType.OPENLIST);

                    }
                }
            }

            this.getOpenList().addAll(queue);

        }

    }



}
