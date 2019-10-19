package mvc.model.algorithm;

import mvc.model.field.Node;
import mvc.model.field.Field;
import mvc.model.field.NodeSnapShot;
import mvc.model.field.NodeType;

import java.util.*;

public abstract class SearchAlgorithm {

    protected Field field;

    protected Node source;
    protected Node target;

    protected List<Node> openList;
    protected List<Node> closeList;
    protected List<Node> path;
    protected List<NodeSnapShot> snapShots;

    private long startTime;
    private long endTime;

    // TODO Methoden add open, close ...., remove open, close, path für die anderen Algorithmen
    // TODO openList zur Collection, dann hat jeder Algorithmus eine openList, in die er seine Collection-Art setzen kann. Dann bei addPath() usw. keine übergabe mehr
    // TODO Pathfinding, alles für die Perfomance übergeben. (Anzahl an Knoten, Anzahl an geblockten Knoten, Anzahl an freien Knoten)

    public SearchAlgorithm(Field field, int source, int target) {
        this.field = field;
        this.source = field.getField()[source];
        this.target = field.getField()[target];
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
        this.snapShots = new ArrayList<>();
    }

    public void calculate() {
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
        this.snapShots = new ArrayList<>();

        snapShots.add(new NodeSnapShot(source, NodeType.SOURCE));
        snapShots.add(new NodeSnapShot(target, NodeType.TARGET));

        startTime();
        execute();
        endTime();

    }

    protected abstract void execute();

    protected List<Node> tracePath(Node node) {

//        node.setDepth(node.getParent().getDepth() + 1);

        List<Node> result = new ArrayList<>();
        Node currenNode = node;

        result.add(node);

        while (currenNode.getParent() != null) {
            //System.out.println(currenNode);
            result.add(currenNode.getParent());
            currenNode = currenNode.getParent();
            snapShots.add(new NodeSnapShot(currenNode, NodeType.PATH));
        }

        Collections.reverse(result);

        return result;

    }


    protected void addOpenList(Collection<Node> collection, Node node) {
        snapShots.add(new NodeSnapShot(node, NodeType.OPENLIST));

        collection.add(node);
        openList.add(node);
    }

    protected void removeOpenList(Collection<Node> collection, Node node) {
        collection.remove(node);
        openList.remove(node);
    }

    protected void addCloseList(Collection<Node> collection, Node node) {
        snapShots.add(new NodeSnapShot(node, NodeType.CLOSELIST));

        collection.add(node);
        closeList.add(node);
    }

    protected void removeCloseList(Collection<Node> collection, Node node) {
        collection.remove(node);
        closeList.remove(node);
    }

    protected void addPath(Node node) {
        path.add(node);
        snapShots.add(new NodeSnapShot(node, NodeType.PATH));
    }

    public Field getField() {
        return field;
    }

    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public List<Node> getOpenList() {
        return openList;
    }

    public List<Node> getCloseList() {
        return closeList;
    }

    public List<Node> getPath() {
        return path;
    }

    public int getStorageComplexity() {
        return openList.size() + closeList.size();
    }

    protected void startTime() {
        startTime = System.currentTimeMillis();
    }

    protected void endTime() {
        endTime = System.currentTimeMillis();
    }

    public long getTime() {
        return endTime - startTime;
    }

    public List<NodeSnapShot> getSnapShots() {
        return snapShots;
    }
}
