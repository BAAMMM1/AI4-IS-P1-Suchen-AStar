package mvc.model.problem;

import mvc.model.algorithm.*;
import mvc.model.algorithm.informed.AStar;
import mvc.model.algorithm.uninformed.BreadthFirst;
import mvc.model.algorithm.uninformed.DepthFirst;
import mvc.model.algorithm.uninformed.UniformCost;

import java.util.ArrayList;
import java.util.List;

public class Problem {

    /**
     * Die Spaltengröße des Felds
     */
    private int columns;

    private Node[] field;

    public Problem(int columns) {
        this.columns = columns;
        this.field = new Node[columns*columns];

        for(int i = 0; i < this.field.length; i++) {
            this.field[i] = new Node(i);
        }
    }

    public void blockNode(int i){
        this.field[i].setType(NodeType.BLOCKED);
    }



    public List<Node> expandNode(Node node){

        List<Node> childs = new ArrayList<>();

        this.expandUp(childs, node);
        this.expandRight(childs, node);
        this.expandDown(childs, node);
        this.expandLeft(childs, node);

        /*
        for (Node nodeChild: childs) {
            System.out.println(nodeChild.getZustand());
        }
        */

        return childs;
    }

    public Node expandUp(List<Node> childs, Node node){

        Node childUp = null;

        if(node.getZustand() - columns >= 0){
            if(!(this.field[node.getZustand() - columns].getType() == NodeType.BLOCKED)) {
                // TODO bei 3x3 ist 8 max, Node mit 9 dürfte es nicht geben. Abprüfen ob Node nicht über maximum geht

                //childUp = new Node(node.getZustand() - columns);
                childUp = this.field[node.getZustand() - columns];
                //Parent setzen
                //childUp.setParent(node);
                childs.add(childUp);
            }

        }

        return childUp;

    }

    public Node expandDown(List<Node> childs, Node node){

        Node childDown = null;

        if(node.getZustand() + columns < this.field.length){
            if(!(this.field[node.getZustand() + columns].getType() == NodeType.BLOCKED)) {

                //childDown = new Node();
                childDown = this.field[node.getZustand() + columns];
                //Parent setzen
                //childDown.setParent(node);
                childs.add(childDown);
            }


        }

        return childDown;

    }

    public Node expandRight(List<Node> childs, Node node){

        Node childRight = null;

        if(node.getZustand() % columns < columns - 1){
            if(!(this.field[node.getZustand() + 1].getType() == NodeType.BLOCKED)) {

                //childRight = new Node();
                childRight = this.field[node.getZustand() + 1];
                //Parent setzen
                //childRight.setParent(node);
                childs.add(childRight);
            }


        }

        return node;

    }

    public Node expandLeft(List<Node> childs, Node node){

        Node childleft = null;

        if(node.getZustand() % columns > 0){
            if(!(this.field[node.getZustand() - 1].getType() == NodeType.BLOCKED)) {

                //childleft = new Node(node.getZustand() - 1);
                childleft = this.field[node.getZustand() - 1];
                //Parent setzen
                //childleft.setParent(node);
                childs.add(childleft);
            }



        }

        return childleft;

    }

    // Für die optische Ausgabe
    public void drawField() {

        int index = 0;

        for(int x = 0; x < columns; x++){

            for(int y = 0; y < columns; y++){
                System.out.print(this.field[index++].draw() + "\t");
            }
            System.out.print("\n");

        }

    }

    public int getColumns() {
        return columns;
    }

    public Node[] getField() {
        return field;
    }

    // Für die Indexe
    public void printField(){

        int index = 0;

        for(int x = 0; x < columns; x++){

            for(int y = 0; y < columns; y++){
                System.out.print(this.field[index++] + "\t");
            }
            System.out.print("\n");

        }
    }

    // Allgemeingültig güt Algortihmen machen
    public void paintAlgorithmInToField(SearchAlgorithm algorithm){

        for(Node node: algorithm.getCloseList()){
            this.field[node.getZustand()].setType(NodeType.DISCOVERED_CLOSELIST);
        }

        for(Node node: algorithm.getOpenList()){
            this.field[node.getZustand()].setType(NodeType.OPENLIST);
        }

        for(Node node: algorithm.getPath()){
            this.field[node.getZustand()].setType(NodeType.PATH);
        }

        this.field[algorithm.getSource().getZustand()].setType(NodeType.START);
        this.field[algorithm.getTarget().getZustand()].setType(NodeType.TARGET);

    }

    public void paintAlgorithmInToFieldWithDepth(SearchAlgorithm algorithm){

        for(Node node: algorithm.getCloseList()){
            this.field[node.getZustand()].setType(NodeType.DISCOVERED_CLOSELIST);
            this.field[node.getZustand()].setDraw("" + node.getDepth());
        }

        for(Node node: algorithm.getOpenList()){
            this.field[node.getZustand()].setType(NodeType.OPENLIST);
            this.field[node.getZustand()].setDraw("" + node.getDepth());
        }

        for(Node node: algorithm.getPath()){
            this.field[node.getZustand()].setType(NodeType.PATH);
            this.field[node.getZustand()].setDraw("" + node.getDepth());
            //System.out.println("depth: " + this.field[node.getZustand()].getDepth() + " " + this.field[node.getZustand()].draw());
        }

        this.field[algorithm.getSource().getZustand()].setType(NodeType.START);
        this.field[algorithm.getSource().getZustand()].setDraw("" + this.field[algorithm.getSource().getZustand()].getDepth());

        this.field[algorithm.getTarget().getZustand()].setType(NodeType.TARGET);
        this.field[algorithm.getTarget().getZustand()].setDraw("" + this.field[algorithm.getTarget().getZustand()].getDepth());

    }



    public void clearFieldFromAlgorithm(){
        for(int i = 0; i < this.field.length; i++){
            if(this.field[i].getType() != NodeType.BLOCKED){
                this.field[i].setType(NodeType.FREE_UNDISCOVERED);
            }
        }
    }

    public static void main(String[] args) {
        Problem problem = new Problem(4);
        Node node = new Node(0);

        problem.expandNode(node);
    }


}
