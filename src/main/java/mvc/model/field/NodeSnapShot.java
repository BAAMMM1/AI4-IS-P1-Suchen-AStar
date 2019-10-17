package mvc.model.field;

public class NodeSnapShot {

    private Node node;
    private NodeType snapShotTyp;

    public NodeSnapShot(Node node, NodeType type) {
        this.node = node;
        this.snapShotTyp = type;
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
                "node=" + node +
                ", snapShotTyp=" + snapShotTyp +
                '}';
    }
}
