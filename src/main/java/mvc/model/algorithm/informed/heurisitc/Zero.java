package mvc.model.algorithm.informed.heurisitc;

import mvc.model.field.Node;


/**
 * Diese Klasse stellt eine Heuristik da, die die Entfernung zum Ziel immer auf 0 schätzt.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class Zero extends Heuristic{

    public Zero(Node target, int columns) {
        super(target, columns);
    }

    @Override
    public int hCost(Node node) {
        return 0;
    }
}
