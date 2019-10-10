package mvc.model.problem;

public enum NodeType {

    // TODO Type Openlist, Closelist usw.
    START("Start"), TARGET("Target"), BLOCKED("Blocked"), FREE_UNDISCOVERED("Free_Undisovered"), OPENLIST("Openlist"), DISCOVERED_CLOSELIST("Discovered_Closelist"), PATH("Path") ;

    private String name;

    private NodeType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
