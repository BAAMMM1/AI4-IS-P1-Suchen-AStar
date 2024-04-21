package dev.graumann.searchalgorithm.model.algorithm.uninformed;

import java.util.ArrayList;
import java.util.List;

import dev.graumann.searchalgorithm.model.field.Field;
import dev.graumann.searchalgorithm.model.field.Node;
import dev.graumann.searchalgorithm.model.field.NodeType;

/**
 * WIP: Diese Klasse stellt die iterative Tiefensuche da.
 *
 * @author Christian Graumann
 * @created 10.2019
 */
public class IterativDeepeningDepthFirstRekursive extends UninformedAlgorithm {

    private static final int MAX_DEPTH = 200;
    private boolean solution = false;

    private List<Node> openListTemp;
    private List<Node> closeListTemp;

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


    @Override
    public void execute() {

        for (int i = 0; i < MAX_DEPTH; i++) {

            System.out.println("Depthlimit: " + i);
            openListTemp = new ArrayList<>();
            closeListTemp = new ArrayList<>();

            if (depthLimitedSearch(source, i)) {
                System.out.println("Lösung gefunden auf Tiefe: " + i);
                break;
            }
            clearParents();

        }

        openList.addAll(openListTemp);
        closeList.addAll(closeListTemp);

        tracePath(target);
        System.out.println(path);

    }

    private void clearParents() {
        for(Node node: field.getField()){
            node.setParent(null);
        }
    }


    public boolean depthLimitedSearch(Node current, int depthLimit) {


        current.setType(NodeType.CLOSELIST);
        closeListTemp.add(current);
        snapShotsAdd(current);

        boolean solution = false;
        if (depthLimit == 0 && current.equals(target)){
            return true;
        }

        List<Node> childs = field.expandNode(current);

        if (depthLimit > 0) {
            // Der for Blick ist für die snapShot einzeichnung
            for (Node child : childs) {

                if (!openListTemp.contains(child) && !closeListTemp.contains(child)) {
                    if (current.getParent() == null || !current.getParent().equals(child) && !child.equals(source)) {
                        child.setType(NodeType.OPENLIST);
                        snapShotsAdd(child);
                    }
                }
            }

            for (Node child : childs) {

                if(!openListTemp.contains(child) && !closeListTemp.contains(child)) {

                    if (current.getParent() == null || !current.getParent().equals(child) && !child.equals(source)) {
                        child.setType(NodeType.OPENLIST);
                        openListTemp.add(child);
                        child.setParent(current);
                        //snapShotsAdd(child);
                        if (depthLimitedSearch(child, depthLimit - 1)) {
                            return true;
                        }
                    }


                }
            }
        }

        return solution;
    }
}
