package mvc.model.algorithm.informed.heurisitc;

import mvc.model.field.Node;

public class Zero extends Heuristic{

    public Zero(Node target, int columns) {
        super(target, columns);
    }

    @Override
    public int hCost(Node node) {
        return 0;
    }
}
