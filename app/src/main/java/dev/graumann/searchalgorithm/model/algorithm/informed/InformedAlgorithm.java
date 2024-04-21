package dev.graumann.searchalgorithm.model.algorithm.informed;

import dev.graumann.searchalgorithm.model.algorithm.SearchAlgorithm;
import dev.graumann.searchalgorithm.model.algorithm.informed.heurisitc.Heuristic;
import dev.graumann.searchalgorithm.model.field.Field;

/**
 * Diese Klasse stellt einen informierten Algorithmus da.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public abstract class InformedAlgorithm extends SearchAlgorithm {

    protected Heuristic heuristic;

    public InformedAlgorithm(Field field, int source, int target, Heuristic heuristic) {
        super(field, source, target);
        this.heuristic = heuristic;
    }

}
