package mvc.model.algorithm.uninformed;

import mvc.model.algorithm.SearchAlgorithm;
import mvc.model.problem.Node;
import mvc.model.problem.NodeType;
import mvc.model.problem.Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

// TODO Schrittkosten
public class DepthFirst extends SearchAlgorithm {


    public DepthFirst(Problem problem, int source, int target) {
        super(problem, source, target);
    }

    public void calculate(){

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
                List<Node> childs = problem.expandNode(current);

                // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

                for(Node child: childs){
                    if(!stack.contains(child) && !closeList.contains(child)){

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


        };

        // TODO könnte man auch rekursiv machen

    }

}
