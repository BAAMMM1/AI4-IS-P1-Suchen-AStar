package mvc.model.algorithm.informed.heurisitc;

import mvc.model.field.Node;

public class Underestimate extends Heuristic{

    public Underestimate(Node target, int columns) {
        super(target, columns);
    }

    @Override
    public int hCost(Node node) {
        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return d * (dx + dy)/2;
    }
}
