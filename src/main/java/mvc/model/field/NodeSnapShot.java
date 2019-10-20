package mvc.model.field;

public class NodeSnapShot {

    private Node node;
    private NodeType snapShotTyp;
    private int gCost = 0;
    private int hCost = 0;
    private int fCost = 0;
    private int stepCost = 0;

    public NodeSnapShot(Node node, NodeType type) {
        this.node = node;
        this.snapShotTyp = type;
        this.gCost = node.getgCost();
        this.hCost = node.gethCost();
        this.fCost = node.getfcost();
        this.stepCost = node.getStepCost();
    }

    public Node getNode() {
        return node;
    }

    public int getgCost() {
        return gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public int getfCost() {
        return fCost;
    }

    public int getStepCost() {
        return stepCost;
    }

    public NodeType getSnapShotTyp() {
        return snapShotTyp;
    }

    @Override
    public String toString() {
        return "NodeSnapShot{" +
                "snapShotTyp=" + snapShotTyp +
                ", stepCost=" + stepCost +
                ", gCost=" + gCost +
                ", hCost=" + hCost +
                ", fCost=" + fCost +
                '}';
    }
}
