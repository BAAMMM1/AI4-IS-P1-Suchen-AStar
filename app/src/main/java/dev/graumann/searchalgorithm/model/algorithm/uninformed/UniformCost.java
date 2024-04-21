package dev.graumann.searchalgorithm.model.algorithm.uninformed;

import java.util.*;

import dev.graumann.searchalgorithm.model.field.Field;
import dev.graumann.searchalgorithm.model.field.Node;
import dev.graumann.searchalgorithm.model.field.NodeType;

/**
 * Diese Klasse stellt die uninformierte Cost Suche da.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class UniformCost extends UninformedAlgorithm {


    public UniformCost(Field field, int source, int target) {
        super(field, source, target);
    }

    public void execute(){

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getgCost));

        priorityQueue.add(source);
        snapShotsAdd(source);
        Node current;

        boolean run = true;
        while(!priorityQueue.isEmpty() && run){
            current = priorityQueue.poll();

            if(current.equals(target)) {
                System.out.println("Ziel gefunden: " + current.toString());
                tracePath(current);
                run = false;
                System.out.println("target gCoast: " + current.getgCost());
                break;
            }

            closeListAdd(current);
            List<Node> childs = field.expandNode(current);

            for(Node child: childs){

                int pathCostToChildOverCurrent= current.getgCost() + child.getStepCost();

                if(!priorityQueue.contains(child) && !closeList.contains(child)){

                    child.setParent(current);
                    child.setgCost(pathCostToChildOverCurrent);

                    child.setType(NodeType.OPENLIST);
                    priorityQueue.add(child);
                    snapShotsAdd(child);

                } else if(priorityQueue.contains(child) && child.getgCost() > pathCostToChildOverCurrent){ // Gibt es bereits einen Weg zum Child, aber ist der Weg über diesen Knoten günstiger zum Kind als vorher?
                    child.setParent(current);
                    child.setgCost(pathCostToChildOverCurrent);
                    priorityQueue.add(child);

                }
            }
        }

        openList.addAll(priorityQueue);

    }


}
