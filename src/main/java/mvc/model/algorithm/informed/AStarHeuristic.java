package mvc.model.algorithm.informed;

import mvc.model.field.Node;

public abstract class AStarHeuristic {

    protected int columns;
    protected int xTarget;
    protected int yTarget;

    public AStarHeuristic(Node target, int columns) {
        this.columns = columns;
        this.xTarget = target.getZustand() % columns;
        this.yTarget = target.getZustand() / columns;
    }

    public abstract int hCost(Node node);

}
