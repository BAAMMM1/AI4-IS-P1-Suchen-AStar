package mvc.model.field;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node {

    private int zustand;

    private NodeType type;

    private Node parent;

    private String draw;

    private int depth;

    // Schrittkosten zum Knoten (Kosten an der Kante)
    private int stepCoast = 1;

    // Die addieren StepCoast zu dem Knoten, d.h. die Pfadkosten zum Knoten
    private int gCost;

    // f(n) = g(n) + (hn)
    private int fcost;

    public Node(int zustand) {
        this.type = NodeType.FREE_UNDISCOVERED;
        this.zustand = zustand;
        //this.type = type;
        this.depth = 0;
        //this.draw = "" + zustand;
        this.draw = "X";
        this.gCost = 0;
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
        this.depth = parent.getDepth() + 1;
    }

    public NodeType getType() {
        return type;
    }

    public int getgCost() {
        return gCost;
    }

    public int getStepCoast() {
        return stepCoast;
    }

    public int getFcost() {
        return fcost;
    }

    public int getDepth() {
        return depth;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public void setStepCoast(int stepCoast) {
        this.stepCoast = stepCoast;
    }

    public void setFcost(int fcost) {
        this.fcost = fcost;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String draw(){

        if(this.type == NodeType.FREE_UNDISCOVERED){
            return "\033[0;37m X" + "\033[0;30m";

        } else if(this.type == NodeType.START){
            return "\033[41m " + this.draw + "\033[0;30m";

        } else if(this.type == NodeType.TARGET){
            return "\033[41m " + this.draw + "\033[0;30m";

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
