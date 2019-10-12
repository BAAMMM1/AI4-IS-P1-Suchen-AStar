package mvc.model.algorithm;

import mvc.model.problem.Node;

import java.util.Random;

public class Heuristic {

    private int hCoast = 1;

    private Node target;
    private int columns;
    private int xTarget;
    private int yTarget;

    public Heuristic(Node target, int columns) {
        this.target = target;
        this.columns = columns;

        this.xTarget = target.getZustand() % columns;
        this.yTarget = target.getZustand() / columns; // Integer-

        System.out.println("x= " + xTarget +" y= " + yTarget);


    }

    // h(node) = geschätze Kosten für billigsten Pfad vom Zustand an Knoten n zu einem Zielzustand
    public int heuristic() {
        return hCoast;
    }

    /*
    public int calc(Node node){
        return new Random().nextInt(100);
    }
    */

    public int calc(Node node){

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int xdistance = Math.abs(this.xTarget - xNode);
        int ydistance = Math.abs(this.yTarget - yNode);

        return xdistance + ydistance;
    }

}
