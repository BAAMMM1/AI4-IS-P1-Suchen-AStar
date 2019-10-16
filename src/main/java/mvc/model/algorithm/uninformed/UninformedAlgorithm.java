package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.field.Field;

public abstract class UninformedAlgorithm extends SearchAlgorithm {

    public UninformedAlgorithm(Field field, int source, int target) {
        super(field, source, target);
    }

}
