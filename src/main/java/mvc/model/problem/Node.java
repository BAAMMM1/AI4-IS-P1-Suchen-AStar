package mvc.model.problem;

public class Node {

    private int zustand;

    private NodeType type;
    private Node parent;

    private String draw;

    public Node(int zustand) {
        this.type = NodeType.FREE_UNDISCOVERED;
        this.zustand = zustand;
        this.type = type;
        this.draw = "" + zustand;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public int getZustand() {
        return zustand;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public NodeType getType() {
        return type;
    }

    public String draw(){

        if(this.type == NodeType.FREE_UNDISCOVERED){
            return "\033[0;37m X" + "\033[0;30m";

        } else if(this.type == NodeType.START){
            return "\033[42m S" + "\033[0;30m";

        } else if(this.type == NodeType.TARGET){
            return "\033[41m Z" + "\033[0;30m";

        } else if(this.type == NodeType.BLOCKED){
            return "\033[40m X" + "\033[0;30m";

        } else if(this.type == NodeType.DISCOVERED_CLOSELIST){
            return "\033[46m " + this.draw + "\033[0;30m";

        } else if(this.type == NodeType.OPENLIST){
            return "\033[42m " + this.draw  + "\033[0;30m";

        } else if(this.type == NodeType.PATH){
            return "\033[1;35m " + this.draw + "\033[0;30m";

        } else {
            return "";
        }


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return zustand == node.zustand;
    }

    @Override
    public int hashCode() {
        return zustand;
    }

    @Override
    public String toString() {
        return "" + this.zustand;
    }

}
