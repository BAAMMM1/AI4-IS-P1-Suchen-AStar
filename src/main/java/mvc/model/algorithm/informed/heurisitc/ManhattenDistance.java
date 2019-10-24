package mvc.model.algorithm.informed.heurisitc;

import mvc.model.field.Node;


/**
 * Diese Klasse stellt die Manhatten Heuristik da.
 *
 *     function heuristic(node) =
 *      dx = abs(node.x - goal.x)
 *      dy = abs(node.y - goal.y)
 *      return D * (dx + dy) *
 *
 * Quelle: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class ManhattenDistance extends Heuristic {

    public ManhattenDistance(Node target, int columns) {
        super(target, columns);
    }


    @Override
    public int hCost(Node node) {

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return d * (dx + dy);
    }

}
