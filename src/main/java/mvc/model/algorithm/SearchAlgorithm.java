package mvc.model.algorithm;

import mvc.model.problem.Node;
import mvc.model.problem.Problem;

import java.util.*;

public abstract class SearchAlgorithm {

    private Problem problem;

    private Node source;
    private Node target;

    private List<Node> openList;
    private List<Node> closeList;
    private List<Node> path;

    public SearchAlgorithm(Problem problem, int source, int target) {
        this.problem = problem;
        this.source = problem.getField()[source];
        this.target = problem.getField()[target];
        this.openList = new ArrayList<Node>();
        this.closeList = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
    }

    public abstract void calculate();

    public List<Node> tracePath(Node node){

        node.setDepth(node.getParent().getDepth() + 1);

        List<Node> result = new ArrayList<>();
        Node currenNode = node;

        result.add(node);

        while(currenNode.getParent() != null){
            result.add(currenNode.getParent());
            currenNode = currenNode.getParent();
        }

        System.out.println(result.get(0).getDepth());
        Collections.reverse(result);

        return result;

    }

    public Problem getProblem() {
        return problem;
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

    public void setPath(List<Node> path) {
        this.path = path;
    }
}
