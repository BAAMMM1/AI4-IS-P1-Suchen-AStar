package mvc.model.algorithm.informed;

import mvc.model.problem.Node;

import java.util.Random;

public class DistanceFromTarget extends AStarHeuristic{

    private int hCoast = 1;

    private Node target;
    private int columns;
    private int xTarget;
    private int yTarget;

    public DistanceFromTarget(Node target, int columns) {
        this.target = target;
        this.columns = columns;

        this.xTarget = target.getZustand() % columns;
        this.yTarget = target.getZustand() / columns; // Integer-Division

        System.out.println("x= " + xTarget +" y= " + yTarget);


    }


    public int calcHVonN(Node node){

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int xdistance = Math.abs(this.xTarget - xNode);
        int ydistance = Math.abs(this.yTarget - yNode);

        return xdistance + ydistance;
    }


}
