package model.algorithm.uninformed;

import model.algorithm.SearchAlgorithm;
import model.field.Field;

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
