package mvc.model.field;

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
