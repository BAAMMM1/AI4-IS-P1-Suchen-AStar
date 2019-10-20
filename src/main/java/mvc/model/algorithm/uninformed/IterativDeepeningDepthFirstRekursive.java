package mvc.model.algorithm.uninformed;

import mvc.model.field.Field;
import mvc.model.field.Node;
import mvc.model.field.NodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class IterativDeepeningDepthFirstRekursive extends UninformedAlgorithm {

    private static final int MAX_DEPTH = 200;
    private boolean solution = false;

    public IterativDeepeningDepthFirstRekursive(Field field, int source, int target) {
        super(field, source, target);
    }

    /*
function IDDFS(root)
    for depth from 0 to ∞
        found <- DLS(root, depth)
        if found ≠ null
            return found

function DLS(node, depth)
    if depth = 0 and node is a goal
        return node
    if depth > 0
        foreach child of node
            found <- DLS(child, depth−1)
            if found ≠ null
                return found
    return null
     */

/*    @Override
    public void execute(){
        
        for(int i = 0; i < 9999; i++){
            System.out.println("Depth: " + i);
            if(dls(source, i)){
                System.out.println("Lösung gefunden auf Tiefe: " + i);
                break;
            }
        }

    }

    private boolean dls(Node current, int depth) {
        if(depth == 0 && current.equals(target)) return true;

        if(depth > 0){
            List<Node> childs = field.expandNode(current);
            for(Node child: childs){
                boolean found = dls(child, depth-1);
                if(found) return true;
            }
        }
        return false;
    }*/

    @Override
    public void execute() {

        for (int i = 0; i < MAX_DEPTH; i++) {
            System.out.println("Depthlimit: " + i);
            openList = new ArrayList<>();
            closeList = new ArrayList<>();
            if (depthLimitedSearch(source, i)) {
                System.out.println("Lösung gefunden auf Tiefe: " + i);
                break;
            }
        }


        tracePath(target);
        System.out.println(path);

    }


    public boolean depthLimitedSearch(Node current, int depthLimit) {


        current.setType(NodeType.CLOSELIST);
        closeList.add(current);
        snapShotsAdd(current);

        boolean solution = false;
        if (current.equals(target)){
            return true;
        }

        List<Node> childs = field.expandNode(current);

        if (depthLimit > 0) {

            for (Node child : childs) {

                if(!openList.contains(child) && !closeList.contains(child)) {

                    if (current.getParent() == null || !current.getParent().equals(child) && !child.equals(source)) {
                        child.setType(NodeType.OPENLIST);
                        openList.add(child);
                        child.setParent(current);
                        snapShotsAdd(current);
                    }

                    if (depthLimitedSearch(child, depthLimit - 1)) {
                        return true;
                    }
                }
            }
        }

        return solution;
    }
}
