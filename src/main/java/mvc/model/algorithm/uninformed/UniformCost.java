package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.problem.Node;
import mvc.model.problem.NodeType;
import mvc.model.problem.Problem;

import java.util.*;

public class UniformCost extends SearchAlgorithm {


    public UniformCost(Problem problem, int source, int target) {
        super(problem, source, target);
    }

    public void calculate(){


        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.getgCoast(), o2.getgCoast());
            }
        });


        priorityQueue.add(this.getSource());
        Node current;

        boolean run = true;
        while(!priorityQueue.isEmpty() && run){
            current = priorityQueue.poll();

            if(current.equals(this.getTarget())) {
                System.out.println("Ziel gefunden: " + current.toString());
                this.setPath(this.tracePath(current));
                run = false;
                System.out.println("target gCoast: " + current.getgCoast());
                break;
            }

            getCloseList().add(current);
            List<Node> childs = getProblem().expandNode(current);

            // TODO Lambdas
            // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

            for(Node child: childs){
                int currentPathCoastToChild = child.getgCoast();
                int newPathCoast= current.getgCoast()+child.getStepCoast();

                if(!priorityQueue.contains(child) && !getCloseList().contains(child)){
                    child.setgCoast(newPathCoast);
                    priorityQueue.add(child);
                    child.setType(NodeType.OPENLIST);
                } else if(priorityQueue.contains(child) && currentPathCoastToChild > newPathCoast){
                    child.setgCoast(newPathCoast);
                }
            }
        }

        this.getOpenList().addAll(priorityQueue);

    }


}
