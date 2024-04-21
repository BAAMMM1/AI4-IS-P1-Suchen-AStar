package dev.graumann.searchalgorithm.model.algorithm.informed.heurisitc;

import dev.graumann.searchalgorithm.model.field.Node;


/**
 * Diese Klasse stellt die Euclidean-Distanz Heuristik da.
 *
 *     function heuristic(node) =
 *      dx = abs(node.x - goal.x)
 *      dy = abs(node.y - goal.y)
 *      return D * sqrt(dx * dx + dy * dy)
 *
 * Quelle: http://theory.stanford.edu/~amitp/GameProgramming/Heuristics.html
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class EuclideanDistance extends Heuristic {



    public EuclideanDistance(Node target, int columns) {
        super(target, columns);
    }


    /*

     */
    @Override
    public int hCost(Node node) {

        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return (int)(d * Math.sqrt(dx * dx + dy * dy)); // TODO (int) nicht double?
    }

}
