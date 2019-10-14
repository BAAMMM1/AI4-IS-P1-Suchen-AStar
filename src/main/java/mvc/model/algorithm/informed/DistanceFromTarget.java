package mvc.model.algorithm.informed;

import mvc.model.field.Node;

public class DistanceFromTarget extends AStarHeuristic{

    private int columns;
    private int xTarget;
    private int yTarget;

    public DistanceFromTarget(Node target, int columns) {

        this.columns = columns;
        this.xTarget = target.getZustand() % columns;
        this.yTarget = target.getZustand() / columns;

    }


    public int hCost(Node node){

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int xdistance = Math.abs(this.xTarget - xNode);
        int ydistance = Math.abs(this.yTarget - yNode);

        return xdistance + ydistance;
    }


}
