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

        if(this.getSource().equals(this.getTarget())){
            System.out.println("Ziel gefunden: Source ist Target");
            this.getPath().add(this.getSource());

        } else {

            Stack<Node> stack = new Stack<>();

            stack.add(this.getSource());
            Node current;

            boolean run = true;
            while(!stack.isEmpty() && run){
                current = stack.pop();

                getCloseList().add(current);
                List<Node> childs = getProblem().expandNode(current);

                // TODO Die einzelenen Step-Listen der Open-/Closelist in jeweils einer Liste von Listen speichern, damit man die Verlauf visualisieren kann.

                for(Node child: childs){
                    if(!stack.contains(child) && !getCloseList().contains(child)){

                        if(child.equals(this.getTarget())) {
                            System.out.println("Ziel gefunden: " + child.toString());
                            this.setPath(this.tracePath(child));
                            run = false;
                            break;
                        }
                        stack.push(child);
                        child.setType(NodeType.OPENLIST);

                    }
                }
            }

            this.getOpenList().addAll(stack);


        };

        // TODO Tiefe
        // TODO könnte man auch rekursiv machen

    }

}
