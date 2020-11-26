package model.algorithm.informed.heurisitc;

import model.field.Node;

/**
 * Diese Klasse stellt die Diagonale-Distanz Heuristik da.
 *
 *     function heuristic(node) =
 *      dx = abs(node.x - goal.x)
 *      dy = abs(node.y - goal.y)
 *      return D * (dx + dy) + (D2 - 2 * D) * min(dx, dy)
 *
 * Quelle: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class DiagonalDistance extends Heuristic {

    public DiagonalDistance(Node target, int columns) {
        super(target, columns);
    }

    @Override
    public int hCost(Node node) {

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return d * (dx + dy) + (d2 - 2 * d) * Math.max(dx, dy);

    }

}
