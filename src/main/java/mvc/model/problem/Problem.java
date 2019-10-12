package mvc.model.problem;

import mvc.model.algorithm.AStar;
import mvc.model.algorithm.Bfs;
import mvc.model.algorithm.SucheMitEinheitlichenKosten;
import mvc.model.algorithm.Tfs;

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
                childUp = new Node(node.getZustand() - columns);
                //Parent setzen
                childUp.setParent(node);
                childs.add(childUp);
            }

        }

        return childUp;

    }

    public Node expandDown(List<Node> childs, Node node){

        Node childDown = null;

        if(node.getZustand() + columns < this.field.length){
            if(!(this.field[node.getZustand() + columns].getType() == NodeType.BLOCKED)) {
                childDown = new Node(node.getZustand() + columns);
                //Parent setzen
                childDown.setParent(node);
                childs.add(childDown);
            }


        }

        return childDown;

    }

    public Node expandRight(List<Node> childs, Node node){

        Node childRight = null;

        if(node.getZustand() % columns < columns - 1){
            if(!(this.field[node.getZustand() + 1].getType() == NodeType.BLOCKED)) {
                childRight = new Node(node.getZustand() + 1);
                //Parent setzen
                childRight.setParent(node);
                childs.add(childRight);
            }


        }

        return node;

    }

    public Node expandLeft(List<Node> childs, Node node){

        Node childleft = null;

        if(node.getZustand() % columns > 0){
            if(!(this.field[node.getZustand() - 1].getType() == NodeType.BLOCKED)) {
                childleft = new Node(node.getZustand() - 1);
                //Parent setzen
                childleft.setParent(node);
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
    public void paintAlgorithmInToField(Bfs bfs){

        for(Node node: bfs.getCloseList()){
            this.field[node.getZustand()].setType(NodeType.DISCOVERED_CLOSELIST);
        }

        for(Node node: bfs.getOpenList()){
            this.field[node.getZustand()].setType(NodeType.OPENLIST);
        }

        // TODO Path einzeichnen
        for(Node node: bfs.getPath()){
            this.field[node.getZustand()].setType(NodeType.PATH);
        }

        this.field[bfs.getSource().getZustand()].setType(NodeType.START);
        this.field[bfs.getTarget().getZustand()].setType(NodeType.TARGET);

    }

    public void paintAlgorithmInToField(Tfs tfs){

        for(Node node: tfs.getCloseList()){
            this.field[node.getZustand()].setType(NodeType.DISCOVERED_CLOSELIST);
        }

        for(Node node: tfs.getOpenList()){
            this.field[node.getZustand()].setType(NodeType.OPENLIST);
        }

        // TODO Path einzeichnen
        for(Node node: tfs.getPath()){
            this.field[node.getZustand()].setType(NodeType.PATH);
        }

        this.field[tfs.getSource().getZustand()].setType(NodeType.START);
        this.field[tfs.getTarget().getZustand()].setType(NodeType.TARGET);

    }

    public void paintAlgorithmInToField(SucheMitEinheitlichenKosten smek){

        for(Node node: smek.getCloseList()){
            this.field[node.getZustand()].setType(NodeType.DISCOVERED_CLOSELIST);
        }

        for(Node node: smek.getOpenList()){
            this.field[node.getZustand()].setType(NodeType.OPENLIST);
        }

        // TODO Path einzeichnen
        for(Node node: smek.getPath()){
            this.field[node.getZustand()].setType(NodeType.PATH);
        }

        this.field[smek.getSource().getZustand()].setType(NodeType.START);
        this.field[smek.getTarget().getZustand()].setType(NodeType.TARGET);

    }

    public void paintAlgorithmInToField(AStar astar){

        for(Node node: astar.getCloseList()){
            this.field[node.getZustand()].setType(NodeType.DISCOVERED_CLOSELIST);
        }

        for(Node node: astar.getOpenList()){
            this.field[node.getZustand()].setType(NodeType.OPENLIST);
        }

        // TODO Path einzeichnen
        for(Node node: astar.getPath()){
            this.field[node.getZustand()].setType(NodeType.PATH);
        }

        this.field[astar.getSource().getZustand()].setType(NodeType.START);
        this.field[astar.getTarget().getZustand()].setType(NodeType.TARGET);

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
