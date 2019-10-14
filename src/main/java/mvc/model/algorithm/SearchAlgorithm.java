package mvc.model.algorithm;

import mvc.model.field.Node;
import mvc.model.field.Field;

import java.util.*;

public abstract class SearchAlgorithm {

    protected Field field;

    protected Node source;
    protected Node target;

    protected List<Node> openList;
    protected List<Node> closeList;
    protected List<Node> path;

    public SearchAlgorithm(Field field, int source, int target) {
        this.field = field;
        this.source = field.getField()[source];
        this.target = field.getField()[target];
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
    }

    public abstract void calculate();

    protected List<Node> tracePath(Node node){

//        node.setDepth(node.getParent().getDepth() + 1);

        List<Node> result = new ArrayList<>();
        Node currenNode = node;

        result.add(node);

        while(currenNode.getParent() != null){
            result.add(currenNode.getParent());
            currenNode = currenNode.getParent();
        }

        Collections.reverse(result);

        return result;

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

}
