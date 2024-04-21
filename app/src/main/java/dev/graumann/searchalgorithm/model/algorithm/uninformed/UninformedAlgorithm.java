package dev.graumann.searchalgorithm.model.algorithm.uninformed;

import dev.graumann.searchalgorithm.model.algorithm.SearchAlgorithm;
import dev.graumann.searchalgorithm.model.field.Field;

/**
 * Diese Klasse stellt einen uninformierten Algorithmus da.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public abstract class UninformedAlgorithm extends SearchAlgorithm {

    public UninformedAlgorithm(Field field, int source, int target) {
        super(field, source, target);
    }

}
