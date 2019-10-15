package mvc.model.field;

import mvc.model.algorithm.*;

import java.util.*;

public class Field {

    /**
     * Die Spaltengröße des Felds
     */
    private int columns;

    private Node[] field;

    private Set<Integer> blockSet;

    public Field(int columns) {
        this.columns = columns;
        this.field = new Node[columns*columns];

        this.blockSet = new HashSet<>();

        for(int i = 0; i < this.field.length; i++) {
            this.field[i] = new Node(i);
        }
    }

    public void blockNode(int i){
        this.field[i].setType(NodeType.BLOCKED);
        blockSet.add(i);
    }

    public void blockNode(Set<Integer> blockSet){

        if(blockSet != null) {
            this.blockSet.addAll(blockSet);
            for (Integer value : blockSet) {
                blockNode(value.intValue());
            }
        }

    }



    public List<Node> expandNode(Node node){

        List<Node> childs = new ArrayList<>();

        this.expandUp(childs, node);
        this.expandRight(childs, node);
        this.expandDown(childs, node);
        this.expandLeft(childs, node);

        return childs;
    }

    public void expandUp(List<Node> childs, Node node){

        if(node.getZustand() - columns >= 0){

            if(!(this.field[node.getZustand() - columns].getType() == NodeType.BLOCKED)) {

                childs.add(this.field[node.getZustand() - columns]);

            }
        }

    }

    public void expandDown(List<Node> childs, Node node){

        if(node.getZustand() + columns < this.field.length){
            if(!(this.field[node.getZustand() + columns].getType() == NodeType.BLOCKED)) {

                childs.add(this.field[node.getZustand() + columns]);

            }

        }

    }

    public void expandRight(List<Node> childs, Node node){

        if(node.getZustand() % columns < columns - 1){
            if(!(this.field[node.getZustand() + 1].getType() == NodeType.BLOCKED)) {

                childs.add(this.field[node.getZustand() + 1]);
            }

        }

    }

    public void expandLeft(List<Node> childs, Node node){

        if(node.getZustand() % columns > 0){
            if(!(this.field[node.getZustand() - 1].getType() == NodeType.BLOCKED)) {

                childs.add(this.field[node.getZustand() - 1]);
            }

        }

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
        }

        this.field[algorithm.getSource().getZustand()].setType(NodeType.START);
        this.field[algorithm.getSource().getZustand()].setDraw("" + this.field[algorithm.getSource().getZustand()].getDepth());

        this.field[algorithm.getTarget().getZustand()].setType(NodeType.TARGET);
        this.field[algorithm.getTarget().getZustand()].setDraw("" + this.field[algorithm.getTarget().getZustand()].getDepth());

    }

    public Set<Integer> getBlockSet() {
        return blockSet;
    }

    @Override
    public String toString() {
        return "Field{" +
                "columns=" + columns +
                ", field=" + Arrays.toString(field) +
                '}';
    }

    public void clearFieldFromAlgorithm(){
        for(int i = 0; i < this.field.length; i++){
            if(this.field[i].getType() != NodeType.BLOCKED){
                this.field[i].setType(NodeType.FREE_UNDISCOVERED);
            }
        }
    }


}
