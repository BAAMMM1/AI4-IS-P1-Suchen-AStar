package mvc.model.algorithm.informed;

import mvc.model.field.Node;

public class EuclideanDistance extends AStarHeuristic{



    public EuclideanDistance(Node target, int columns) {
        super(target, columns);
    }


    /*
    function heuristic(node) =
    dx = abs(node.x - goal.x)
    dy = abs(node.y - goal.y)
    return D * sqrt(dx * dx + dy * dy)
     */
    @Override
    public int hCost(Node node) {

        int d = 1; // TODO d?

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return (int)(d * Math.sqrt(dx * dx + dy * dy)); // TODO (int) nicht double?
    }

}
