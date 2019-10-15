package mvc.model.algorithm.informed;

import mvc.model.field.Node;

public class ManhattenDistance extends AStarHeuristic{

    public ManhattenDistance(Node target, int columns) {
        super(target, columns);
    }

    /*
    function heuristic(node) =
    dx = abs(node.x - goal.x)
    dy = abs(node.y - goal.y)
    return D * (dx + dy)
     */
    @Override
    public int hCost(Node node) {

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return d * (dx + dy);
    }

}
