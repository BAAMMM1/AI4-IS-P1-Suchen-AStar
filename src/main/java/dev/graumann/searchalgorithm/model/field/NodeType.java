package dev.graumann.searchalgorithm.model.field;

/**
 * Diese Klasse stellt da, welchen Typ ein Knoten annehmen kann.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public enum NodeType {

    SOURCE("Source"), TARGET("Target"), BLOCKED("Blocked"), UNDISCOVERED("Undisovered"), OPENLIST("Openlist"), CLOSELIST("Closelist"), PATH("Path") ;

    private String name;

    private NodeType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
