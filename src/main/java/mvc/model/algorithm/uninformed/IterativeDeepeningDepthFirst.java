package mvc.model.algorithm.uninformed;

import mvc.model.field.Field;
import mvc.model.field.Node;
import mvc.model.field.NodeType;

import java.util.List;
import java.util.Stack;

public class IterativeDeepeningDepthFirst extends UninformedAlgorithm {


    public IterativeDeepeningDepthFirst(Field field, int source, int target) {
        super(field, source, target);
    }

    public void execute(){

        if(source.equals(target)){
            System.out.println("Ziel gefunden: Source ist Target");
            path.add(source);

        } else {

            Stack<Node> stack = new Stack<>();

            stack.add(source);
            Node current;

            boolean run = true;
            while(!stack.isEmpty() && run){
                current = stack.pop();

                closeList.add(current);
                List<Node> childs = field.expandNode(current);


                for(Node child: childs){
                    if(!stack.contains(child) && !closeList.contains(child)){
                        child.setParent(current);

                        if(child.equals(target)) {
                            System.out.println("Ziel gefunden: " + child.toString());
                            path = this.tracePath(child);
                            run = false;
                            break;
                        }
                        stack.push(child);
                        child.setType(NodeType.OPENLIST);

                    }
                }
            }

            openList.addAll(stack);

        }

    }

}
