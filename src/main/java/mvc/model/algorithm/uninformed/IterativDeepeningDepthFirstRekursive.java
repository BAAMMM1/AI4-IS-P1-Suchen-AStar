package mvc.model.algorithm.uninformed;

import mvc.model.field.Field;
import mvc.model.field.Node;

import java.util.List;
import java.util.Stack;

public class IterativDeepeningDepthFirstRekursive extends UninformedAlgorithm {

    private static final int MAX_DEPTH = 200;
    private boolean solution = false;

    public IterativDeepeningDepthFirstRekursive(Field field, int source, int target) {
        super(field, source, target);
    }

    @Override
    public void execute() {

        for (int i = 0; i < MAX_DEPTH; i++) {
            if (depthLimitedSearch(source, i)) {
                System.out.println("Lösung gefunden auf Tiefe: " + i);
                break;
            }
        }

        tracePath(target);
        System.out.println(path);

    }


    public boolean depthLimitedSearch(Node current, int depthLimit) {
        boolean solution = false;
        if (current.equals(target)) return true;

        Stack<Node> stack = new Stack<>();
        stack.add(current);

        List<Node> childs = field.expandNode(current);

        if (depthLimit > 0) {

            for (Node child : childs) {
                // TODO
                if(current.getParent() != child) child.setParent(current);
                if(depthLimitedSearch(child, depthLimit - 1)) {
                    return true;
                }

            }
        }

        return solution;
    }
}
