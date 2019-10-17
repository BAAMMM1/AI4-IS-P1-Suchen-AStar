package mvc.model.algorithm.uninformed;

import mvc.model.field.Node;
import mvc.model.field.NodeSnapShot;
import mvc.model.field.NodeType;
import mvc.model.field.Field;

import java.util.LinkedList;
import java.util.List;

public class BreadthFirst extends UninformedAlgorithm {


    public BreadthFirst(Field field, int source, int target) {
        super(field, source, target);
    }

    public void execute(){
        System.out.println("Source: " + source);

        if(this.getSource().equals(target)){

            System.out.println("Ziel gefunden: Source ist Target");
            addPath(source);

        } else {

            LinkedList<Node> queue = new LinkedList<>();

            addOpenList(queue, source);
            //queue.add(source);
            //snapShots.add(new NodeSnapShot(source, NodeType.OPENLIST));;

            Node current;

            boolean run = true;
            while(!queue.isEmpty() && run){

                current = queue.get(0);

                removeOpenList(queue, current);
                //queue.remove(0);

                addCloseList(queue, current);
                //closeList.add(current);
                //snapShots.add(new NodeSnapShot(current, NodeType.CLOSELIST));

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

                        addOpenList(queue, child);
                        //queue.add(child);
                        //snapShots.add(new NodeSnapShot(child, NodeType.OPENLIST));

                        child.setType(NodeType.OPENLIST);

                    }
                }
            }

            //openList.addAll(queue);


        }


    }



}
