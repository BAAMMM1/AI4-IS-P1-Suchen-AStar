package mvc.model.algorithm.uninformed;

import mvc.model.field.Field;
import mvc.model.field.Node;
import mvc.model.field.NodeSnapShot;
import mvc.model.field.NodeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class IterativeDeepeningDepthFirst extends UninformedAlgorithm {

    private static final int MAX_DEPTH = 9999;


    public IterativeDeepeningDepthFirst(Field field, int source, int target) {
        super(field, source, target);
    }

    public void execute() {

        if (source.equals(target)) {
            System.out.println("Ziel gefunden: Source ist Target");
            path.add(source);

        } else {

            Stack<Node> stack = new Stack<>();
            int depth = 0;

            Node current;

            boolean run = true;

            for (int i = 0; i <= MAX_DEPTH; i++) {
                if (!run) break;
                stack = new Stack<>();
                stack.push(source);
                setCloseList(new ArrayList<>());
                clearSnapShots();
                snapShotsAdd(source);


                while (!stack.isEmpty() && run) {


                    current = stack.pop();
                    if (current.getDepth() > depth) break;

                    closeListAdd(current);
                    List<Node> childs = field.expandNode(current);


                    for (Node child : childs) {
                        if (!stack.contains(child) && !closeList.contains(child)) {
                            child.setParent(current);

                            if (child.equals(target)) {
                                System.out.println("Ziel gefunden: " + child.toString());
                                tracePath(child);
                                run = false;
                                break;
                            }

                            child.setType(NodeType.OPENLIST);
                            stack.push(child);
                            snapShotsAdd(child);

                        }
                    }

                }

                depth++;
            }

            openList.addAll(stack);

        }

    }

    private void clearSnapShots() {



    }

}
