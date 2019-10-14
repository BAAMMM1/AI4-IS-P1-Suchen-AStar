package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.field.Node;
import mvc.model.field.NodeType;
import mvc.model.field.Field;

import java.util.LinkedList;
import java.util.List;

public class BreadthFirst extends SearchAlgorithm {


    public BreadthFirst(Field field, int source, int target) {
        super(field, source, target);
    }

    public void calculate(){

        if(this.getSource().equals(target)){

            System.out.println("Ziel gefunden: Source ist Target");
            path.add(source);

        } else {

            LinkedList<Node> queue = new LinkedList<>();

            queue.add(source);
            Node current;

            boolean run = true;
            while(!queue.isEmpty() && run){

                current = queue.get(0);
                queue.remove(0);

                closeList.add(current);
                List<Node> childs = field.expandNode(current);

                for(Node child: childs){

                    if(!queue.contains(child) && !closeList.contains(child)){
                        child.setParent(current);

                        if(child.equals(target)) {
                            System.out.println("Ziel gefunden: " + child.toString() + " bei Tiefe: " + child.getDepth());
                            path = this.tracePath(child);
                            run = false;
                            break;
                        }

                        queue.add(child);
                        child.setType(NodeType.OPENLIST);

                    }
                }
            }

            openList.addAll(queue);

        }

    }



}
