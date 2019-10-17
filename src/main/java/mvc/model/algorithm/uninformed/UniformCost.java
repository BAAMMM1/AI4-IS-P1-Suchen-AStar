package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.field.Node;
import mvc.model.field.NodeType;
import mvc.model.field.Field;

import java.util.*;

public class UniformCost extends UninformedAlgorithm {


    public UniformCost(Field field, int source, int target) {
        super(field, source, target);
    }

    public void execute(){

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getgCost));

        priorityQueue.add(source);
        Node current;

        boolean run = true;
        while(!priorityQueue.isEmpty() && run){
            current = priorityQueue.poll();

            if(current.equals(target)) {
                System.out.println("Ziel gefunden: " + current.toString());
                path = tracePath(current);
                run = false;
                System.out.println("target gCoast: " + current.getgCost());
                break;
            }

            closeList.add(current);
            List<Node> childs = field.expandNode(current);

            for(Node child: childs){

                int pathCostToChildOverCurrent= current.getgCost() + child.getStepCoast();

                if(!priorityQueue.contains(child) && !closeList.contains(child)){

                    child.setParent(current);
                    child.setgCost(pathCostToChildOverCurrent);

                    priorityQueue.add(child);
                    child.setType(NodeType.OPENLIST);

                } else if(priorityQueue.contains(child) && child.getgCost() > pathCostToChildOverCurrent){ // Gibt es bereits einen Weg zum Child, aber ist der Weg über diesen Knoten günstiger zum Kind als vorher?
                    child.setParent(current);
                    child.setgCost(pathCostToChildOverCurrent);
                }
            }
        }

        openList.addAll(priorityQueue);

    }


}
