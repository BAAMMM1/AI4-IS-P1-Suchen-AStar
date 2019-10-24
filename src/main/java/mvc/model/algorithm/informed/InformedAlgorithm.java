package mvc.model.algorithm.informed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.algorithm.informed.heurisitc.Heuristic;
import mvc.model.field.Field;

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
