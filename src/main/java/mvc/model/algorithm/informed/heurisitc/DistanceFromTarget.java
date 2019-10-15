package mvc.model.algorithm.informed.heurisitc;

import mvc.model.field.Node;

public class DistanceFromTarget extends Heuristic {


    public DistanceFromTarget(Node target, int columns) {
        super(target, columns);
    }


    public int hCost(Node node){

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return (dx + dy);
    }


}
