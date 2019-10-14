package mvc.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


public class Controller {
    int gridsize = 15;
    int gridfieldsize = 40;
    int circleradius = 10;
    Set<Integer> block = new HashSet<>();
    int source;
    int target;
    int mode;
    ArrayList<List> openList;
    ArrayList<List> closedList;
    ArrayList<List> pathList;

    @FXML
    GridPane gridpane;

    @FXML
    ToggleButton block_Button;

    @FXML
    ToggleButton source_Button;

    @FXML
    ToggleButton target_Button;

    @FXML
    ToggleButton clear_Button;

    @FXML
    ChoiceBox choiceBox_Algorithm;

    public Controller() {
    }


    @FXML
    private void initialize() {
        System.out.println("starte Initialize");

        // Setzen der Standardeinstellung im Algorithm Bereich zum Bearbeiten
        // hier: Mode 1: Block setzen
        mode = 0;
        block_Button.setSelected(false);

        // Setzen der verfügbaren Such-Algorithm
        List<String> choiceBox_Algorithm_Names_Placeholder = new ArrayList<>(); // TODO Wenn Pathfinding da, dann hier Mehtodenaufruf
        choiceBox_Algorithm_Names_Placeholder.add("Test");
        choiceBox_Algorithm.setItems(FXCollections.observableArrayList(choiceBox_Algorithm_Names_Placeholder));

        // Aufbau der Spielfläche
        gridpane.getRowConstraints().clear();
        gridpane.getColumnConstraints().clear();
        for (int a = 0; a < gridsize; a++) {
            gridpane.getRowConstraints().add(new RowConstraints());
            gridpane.getColumnConstraints().add(new ColumnConstraints());

            for (int i = 0; i < gridsize; i++) {
                for (int j = 0; j < gridsize; j++) {
                    blankCell(i,j);
                }
            }
        }
        System.out.println("beende Initialize");
    }
//  Erkenntnis: Gridpane bzw View ist nicht darauf ausgelegt, das man felder ausliest um deren Attribute vergleichen zu könne
//  https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content


/*    @FXML
    private void mouseEntered(MouseEvent e) {
        Node source = (Node)e.getSource() ;
        Integer colIndex = gridpane.getColumnIndex(source);
        Integer rowIndex = gridpane.getRowIndex(source);
        System.out.println("Mouse entered cell "+ colIndex + "/"+ rowIndex);
    }*/

    public void start(){
        // hier müssen übergabe der Listen an model/Algorithm entstehen



//        Andere Möglichkeit:
//        ArrayList<List> solution[] = new ArrayList[3];
//        solution[] = deinemethode(source, target, block);
//        this.openList = solution[0];
//        this.closedList = solution[1];
//        this.pathList = solution[2];


    }

    public void click_Block_Button() {
        mode = 1;
        block_Button.setSelected(true);
        clear_Button.setSelected(false);
        target_Button.setSelected(false);
        source_Button.setSelected(false);
    }

    public void click_source_Button(){
        mode = 2;
        block_Button.setSelected(false);
        clear_Button.setSelected(false);
        target_Button.setSelected(false);
        source_Button.setSelected(true);
    }

    public void click_target_Button(){
        mode = 3;
        block_Button.setSelected(false);
        clear_Button.setSelected(false);
        target_Button.setSelected(true);
        source_Button.setSelected(false);
    }

    public void click_clear_Button(){
        mode = 4;
        block_Button.setSelected(false);
        clear_Button.setSelected(true);
        target_Button.setSelected(false);
        source_Button.setSelected(false);

    }

    public void clickGrid(javafx.scene.input.MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (clickedNode != gridpane) {
            // click on descendant node
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);

            int tmpcolIndex;
            int tmprowIndex;

            switch (mode){
                case 0: break;
                case 1: addBlock(colIndex, rowIndex);
                        block.add(colIndex%gridsize + rowIndex*gridsize);
                        System.out.println("gespeichert in Blocklist: " + block.toString());
                        break;
                case 2: //if (source != null || (colIndex%gridsize + rowIndex*gridsize) != target){
                            tmpcolIndex = source%gridsize;
                            tmprowIndex = source/gridsize;
                            blankCell(tmpcolIndex, tmprowIndex);
                            System.out.print("Source war " + source + " ");
                          //  }
                        //if (target == null || (colIndex%gridsize + rowIndex*gridsize) != target){
                            source = (colIndex%gridsize + rowIndex*gridsize);
                            addSource(colIndex, rowIndex);
                            System.out.println("Source nun Feld: "+ source);
                        //}
                        break;
                case 3: //if (target!= null || (colIndex%gridsize + rowIndex*gridsize) != source){
                            tmpcolIndex = target%gridsize;
                            tmprowIndex = target/gridsize;
                            blankCell(tmpcolIndex, tmprowIndex);
                            System.out.print("Target war: "+ target + " ");
                        //    }
                        //if (source ==null || (colIndex%gridsize + rowIndex*gridsize) != source){
                            target = (colIndex%gridsize + rowIndex*gridsize);
                            addTarget(colIndex, rowIndex);
                            System.out.println("Target nun Feld: "+ target);
                        //    }
                        break;
                case 4: if (block.contains(colIndex%gridsize + rowIndex*gridsize)){
                            block.remove(colIndex%gridsize + rowIndex*gridsize);
                            blankCell(colIndex, rowIndex);
                        }
                        break;
            }

        }
    }

    private Node getNodeFromGridPane(GridPane griPa, int col, int row) {
        for (Node node : griPa.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
        // Wenn 10er grid, dann auch möglich mit gridpane.getChildren.get(col*10+row)
        // sprich 2/2 auf 10er wäre der gleiche Node wie getChildren.get(22)
        //oder 1column/3row im 5er grid == getChildren.get(14) -> 0. row 0-4; 1. row 5-8, 3. row 13,*14*,15,16 ->
        /*

        Quelle https://stackoverflow.com/questions/50012463/how-can-i-click-a-gridpane-cell-and-have-it-perform-an-action
        if (clickedNode != gridmane) {
    // click on descendant node
    Node parent = clickedNode.getParent();
    while (parent != gridmane) {
        clickedNode = parent;
        parent = clickedNode.getParent();
    }
    Integer colIndex = GridPane.getColumnIndex(clickedNode);
    Integer rowIndex = GridPane.getRowIndex(clickedNode);
    System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
}
         */


    }

    private void addOpenList(int column, int row){
        Circle circle = new Circle(circleradius);
        circle.setFill(Paint.valueOf("green"));
        gridpane.setHalignment(circle, HPos.CENTER);
        gridpane.add(circle, column,row);
    }

    private void addClosedList(int column, int row){
        Circle circle = new Circle(circleradius);
        circle.setFill(Paint.valueOf("cyan"));
        gridpane.setHalignment(circle, HPos.CENTER);
        gridpane.add(circle, column,row);
    }

    private void addPath(int column, int row){
        Rectangle purple_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        purple_rechtangle.setFill(Paint.valueOf("purple"));
        gridpane.add(purple_rechtangle, column, row);
    }

    private void addBlock(int column, int row){
        Rectangle black_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        black_rechtangle.setFill(Paint.valueOf("black"));
        gridpane.add(black_rechtangle, column, row);
    }

    private void blankCell(int column, int row){
        Rectangle blank_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        blank_rechtangle.setStroke(Paint.valueOf("black"));
        blank_rechtangle.setFill(Paint.valueOf("white"));
        gridpane.add(blank_rechtangle, column, row);
    }

    private void addSource(int column, int row) {
        Rectangle green_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        green_rechtangle.setFill(Paint.valueOf("green"));
        gridpane.add(green_rechtangle, column, row);
    }

    private void addTarget(int column, int row) {
        Rectangle red_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        red_rechtangle.setFill(Paint.valueOf("red"));
        gridpane.add(red_rechtangle, column, row);
    }

    // -> ich weiß das das rendundant ist und man das auch auslagern könnte in add rectanglerype (string object, int x, int y) -> object = source target way oder block
}