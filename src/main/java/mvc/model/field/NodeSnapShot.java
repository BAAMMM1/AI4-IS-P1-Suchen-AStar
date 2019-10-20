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
        this.stepCost = node.getStepCoast();
    }

    public Node getNode() {
        return node;
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
