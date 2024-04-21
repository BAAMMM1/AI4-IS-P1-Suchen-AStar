package dev.graumann.searchalgorithm.model.algorithm.informed.heurisitc;

import java.util.Random;

import dev.graumann.searchalgorithm.model.field.Node;

/**
 * Diese Klasse stellt eine Heuristik da, die die Entfernung zum Ziel überschätzt.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class Overestimate extends Heuristic{

    public Overestimate(Node target, int columns) {
        super(target, columns);
    }

    @Override
    public int hCost(Node node) {
        int xNode = node.getZustand() % columns;
        int yNode = node.getZustand() / columns;

        int dx = Math.abs(xNode - xTarget);
        int dy = Math.abs(yNode - yTarget);

        return d * (dx + dy)*new Random().nextInt(100);
    }
}
