package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.field.Node;
import mvc.model.field.NodeType;
import mvc.model.field.Field;

import java.util.List;
import java.util.Stack;

public class DepthFirst extends UninformedAlgorithm {


    public DepthFirst(Field field, int source, int target) {
        super(field, source, target);
    }

    public void execute(){

        if(source.equals(target)){
            System.out.println("Ziel gefunden: Source ist Target");
            path.add(source);

        } else {

            Stack<Node> stack = new Stack<>();

            stack.push(source);
            snapShotsAdd(source);

            Node current;

            boolean run = true;
            while(!stack.isEmpty() && run){
                current = stack.pop();

                closeListAdd(current);
                List<Node> childs = field.expandNode(current);


                for(Node child: childs){
                    if(!stack.contains(child) && !closeList.contains(child)){
                        child.setParent(current);

                        if(child.equals(target)) {
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

            openList.addAll(stack);

        }

    }

}
