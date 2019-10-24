package mvc.model.field;

/**
 * Diese Klasse stellt einen Knoten als Verwaltungseinheit für einen aktuellen Zustand (Fieldnumber) da.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class Node {

    /*
    Stellt die aktuelle Feldnummer im Feld da
     */
    private int zustand;

    private NodeType type;

    private Node parent;

    private String draw;

    private int depth;

    // Schrittkosten zum Knoten (Kosten an der Kante)
    private int stepCost = 1;

    // Die addierten StepCoast zu dem Knoten, d.h. die Pfadkosten zum Knoten
    private int gCost;

    private int hCost;

    // f(n) = g(n) + (hn)
    private int fcost;

    public Node(int zustand) {
        this.type = NodeType.UNDISCOVERED;
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
        if(parent != null){
            this.depth = parent.getDepth() + 1;
        } else {
            this.depth = 0;
        }

    }

    public NodeType getType() {
        return type;
    }

    public int getgCost() {
        return gCost;
    }

    public int getStepCost() {
        return stepCost;
    }

    public int getfcost() {
        return fcost;
    }

    public int getDepth() {
        return depth;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public void setStepCost(int stepCost) {
        this.stepCost = stepCost;
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

        if(this.type == NodeType.UNDISCOVERED){
            return "\033[0;37m X" + "\033[0;30m";

        } else if(this.type == NodeType.SOURCE){
            return "\033[41m " + this.draw + "\033[0;30m";

        } else if(this.type == NodeType.TARGET){
            return "\033[41m " + this.draw + "\033[0;30m";

        } else if(this.type == NodeType.BLOCKED){
            return "\033[40m X" + "\033[0;30m";

        } else if(this.type == NodeType.CLOSELIST){
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
