package mvc.model.algorithm.informed;

import mvc.model.algorithm.informed.heurisitc.Heuristic;
import mvc.model.field.Field;

public class IDAStar extends InformedAlgorithm {

    public IDAStar(Field field, int source, int target, Heuristic heuristic) {
        super(field, source, target, heuristic);
    }

    @Override
    public void execute() {

    }

}
