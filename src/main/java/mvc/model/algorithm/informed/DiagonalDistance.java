package mvc.model.algorithm.informed;

import mvc.model.field.Node;

public class DiagonalDistance extends AStarHeuristic {


    public DiagonalDistance(Node target, int columns) {
        super(target, columns);
    }

    /*
        function heuristic(node) =
        dx = abs(node.x - goal.x)
        dy = abs(node.y - goal.y)
        return D * (dx + dy) + (D2 - 2 * D) * min(dx, dy)
         */
    @Override
    public int hCost(Node node) {

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return 1 * (dx + dy) + (d2 - 2 * d) * Math.max(dx, dy);

    }

}
