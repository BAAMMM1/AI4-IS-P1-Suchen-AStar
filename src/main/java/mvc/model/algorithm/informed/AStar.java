package mvc.model.algorithm.informed;

import mvc.model.algorithm.informed.heurisitc.Heuristic;
import mvc.model.field.Node;
import mvc.model.field.NodeType;
import mvc.model.field.Field;

import java.util.*;


/**
 * Diese Klasse stellt den AStar da.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class AStar extends InformedAlgorithm {


    public AStar(Field field, int source, int target, Heuristic heuristic) {
        super(field, source, target, heuristic);
        this.heuristic = heuristic;
    }


    public void execute(){

        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getfcost));

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
                    child.sethCost(heuristic.hCost(child));
                    child.setFcost(child.getgCost()+ heuristic.hCost(child));

                    child.setType(NodeType.OPENLIST);
                    priorityQueue.add(child);
                    snapShotsAdd(child);


                } else if(priorityQueue.contains(child) && child.getgCost() > pathCostToChildOverCurrent){ // Gibt es bereits einen Weg zum Child, aber ist der Weg über diesen Knoten günstiger zum Kind als vorher?
                    child.setParent(current);
                    child.setgCost(pathCostToChildOverCurrent);
                    child.sethCost(heuristic.hCost(child));
                    child.setFcost(child.getgCost()+ heuristic.hCost(child));

                    child.setType(NodeType.OPENLIST);
                    priorityQueue.remove(child);
                    priorityQueue.add(child);
                    snapShotsAdd(child);

                }
            }
        }

        openList.addAll(priorityQueue);
    }



}
