package mvc.model.algorithm;

import mvc.model.problem.Node;

import java.util.List;
import java.util.PriorityQueue;

public abstract class Algorithm {

    private Node source;
    private Node target;

    private PriorityQueue<Node> openList;
    private List<Node> closeList;
    private List<Node> path;

    public abstract void calculate();


    public Node getSource() {
        return source;
    }

    public Node getTarget() {
        return target;
    }

    public PriorityQueue<Node> getOpenList() {
        return openList;
    }

    public List<Node> getCloseList() {
        return closeList;
    }

    public List<Node> getPath() {
        return path;
    }


}
