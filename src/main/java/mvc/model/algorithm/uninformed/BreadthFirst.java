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

        if(this.getSource().equals(target)){

            System.out.println("Ziel gefunden: Source ist Target");
            pathAdd(source);

        } else {

            LinkedList<Node> queue = new LinkedList<>();

            queue.add(source);
            snapShotsAdd(source);

            Node current;

            boolean run = true;
            while(!queue.isEmpty() && run){

                current = queue.get(0);
                queue.remove(current);

                closeListAdd(current);

                List<Node> childs = field.expandNode(current);

                for(Node child: childs){

                    if(!queue.contains(child) && !closeList.contains(child)){
                        child.setParent(current);

                        if(child.equals(target)) {
                            System.out.println("Ziel gefunden: " + child.toString() + " bei Tiefe: " + child.getDepth());
                            tracePath(child);
                            run = false;
                            break;
                        }

                        child.setType(NodeType.OPENLIST);
                        queue.add(child);
                        snapShotsAdd(child);

                    }
                }
            }

            openList.addAll(queue); // Hier addAll, weil wir auf einer lokalen Queue arbeiten und anschlißened alle die noch in der Queue sind abspeichern wollen
        }
    }

}
