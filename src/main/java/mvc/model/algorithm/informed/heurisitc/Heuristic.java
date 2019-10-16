package mvc.model.algorithm.informed.heurisitc;

import mvc.model.field.Node;

public abstract class Heuristic {

    protected int columns;
    protected int xTarget;
    protected int yTarget;
    protected int d;
    protected int d2;

    public Heuristic(Node target, int columns) {
        this.columns = columns;
        this.xTarget = target.getZustand() % columns;
        this.yTarget = target.getZustand() / columns;
        this.d = 1;
        this.d2 = 1;
    }

    public abstract int hCost(Node node);

}
