package mvc.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import mvc.model.PathFinding;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


public class Controller {
    Integer gridsize = 15;
    int gridfieldsize = 40;
    int circleradius = 10;
    Set<Integer> block = new HashSet<>();
    Integer source;
    Integer target;
    Integer mode;
    List<Integer> openList;
    List<Integer> closedList;
    List<Integer> pathList;
    PathFinding pathFinding;

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

    @FXML
    TextField grid_resize_textfield;

    @FXML
    TextField grid_fieldsize_textfield;

    public Controller() {
    }

    //TODO: Commit and Push nicht Standardgemäß auf "Master" sondern auf "gui" Branch
    //TODO: LOAD/SAVE
    //TODO: Heurisitk anzeigen
    //TODO: Ausführen Heuristik supi

    @FXML
    private void initialize() {
        System.out.println("starte Initialize");
        openList=new ArrayList<>();
        closedList=new ArrayList<>();
        pathList=new ArrayList<>();
        block=new HashSet<>();
        target=null;
        source=null;
        pathFinding = new PathFinding();


        // Setzen der Standardeinstellung im Algorithm Bereich zum Bearbeiten
        // hier: Mode 1: Block setzen
        mode = 0;
        block_Button.setSelected(false);

        // Setzen der verfügbaren Such-Algorithm

        choiceBox_Algorithm.setItems(FXCollections.observableArrayList(pathFinding.avaiableAlgortihm()));

        // Aufbau der Spielfläche
        gridpane.getRowConstraints().clear();
        gridpane.getColumnConstraints().clear();
        gridpane.getChildren().clear();
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

        pathFinding.calc(gridsize, block, choiceBox_Algorithm.getValue().toString(),source, target);

        this.openList = pathFinding.getOpenList();
        this.closedList = pathFinding.getCloseList();
        this.pathList = pathFinding.getPath();

        draw_openList (openList);
        draw_closedList(closedList);
        draw_path(pathList);

//        Andere Möglichkeit:
//        ArrayList<List> solution[] = new ArrayList[3];
//        solution[] = deinemethode(source, target, block);
//        this.openList = solution[0];
//        this.closedList = solution[1];
//        this.pathList = solution[2];


    }

    private void draw_openList(List<Integer> openList) {
        for (int i= 0; i < openList.size(); i++ ){
            int tmpcolumn = openList.get(i) % gridsize;
            int tmprow = openList.get(i)/gridsize;
            if (openList.get(i)==source) continue;
            addOpenList(tmpcolumn, tmprow);
        }
    }

    private void draw_closedList(List<Integer> openList) {
        for (int i= 0; i < openList.size(); i++ ){
            int tmpcolumn = openList.get(i) % gridsize;
            int tmprow = openList.get(i)/gridsize;
            if (openList.get(i)==source) continue;
            addClosedList(tmpcolumn, tmprow);
        }
    }

    private void draw_path(List<Integer> openList) {
        for (int i= 0; i < openList.size()-2; i++ ){
            int tmpcolumn = openList.get(i+1) % gridsize;
            int tmprow = openList.get(i+1)/gridsize;
            addPath(tmpcolumn, tmprow);
        }
    }

    public void grid_resize_input(){
        Integer a = Integer.parseInt(grid_resize_textfield.getText());
        gridsize = a;
        System.out.println("gridsize ist nun: "+ gridsize);
        initialize();
    }

    public void grid_fieldresize_input(){
        Integer a = Integer.parseInt(grid_fieldsize_textfield.getText());
        gridfieldsize = a;
        System.out.println("gridfieldsize ist nun: "+ a);
        initialize();
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
                case 2: if  ((target == null || target != colIndex%gridsize + rowIndex*gridsize)
                              && (source != null && source != colIndex%gridsize + rowIndex*gridsize)){
                            tmpcolIndex = source%gridsize;
                            tmprowIndex = source/gridsize;
                            blankCell(tmpcolIndex, tmprowIndex);
                            System.out.print("Source war " + source + " ");
                         }
                        if ((source == null
                                || (source != colIndex%gridsize + rowIndex*gridsize))
                            && (target == null
                             || colIndex%gridsize + rowIndex*gridsize != target)){
                            source = (colIndex%gridsize + rowIndex*gridsize);
                            addSource(colIndex, rowIndex);
                            System.out.println("Source nun Feld: "+ source);
                        }
                        break;
                case 3: if ((source == null || source != colIndex%gridsize + rowIndex*gridsize)
                            && (target != null && target != colIndex%gridsize + rowIndex*gridsize)){
                            tmpcolIndex = target%gridsize;
                            tmprowIndex = target/gridsize;
                            blankCell(tmpcolIndex, tmprowIndex);
                            System.out.print("Target war: "+ target + " ");
                            }
                        if ((target == null
                                || target != colIndex%gridsize + rowIndex*gridsize)
                            && (source == null
                                    || source != colIndex%gridsize + rowIndex*gridsize)){
                            target = (colIndex%gridsize + rowIndex*gridsize);
                            addTarget(colIndex, rowIndex);
                            System.out.println("Target nun Feld: "+ target);
                            }
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
       // Circle circle = new Circle(circleradius);
       // circle.setFill(Paint.valueOf("green"));
       // gridpane.setHalignment(circle, HPos.CENTER);
       // gridpane.add(circle, column,row);
        Rectangle green_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        green_rechtangle.setFill(Color.web("#98FB98"));
        gridpane.add(green_rechtangle, column, row);

    }

    private void addClosedList(int column, int row){
//        Circle circle = new Circle(circleradius);
//        circle.setFill(Paint.valueOf("cyan"));
//        gridpane.setHalignment(circle, HPos.CENTER);
//        gridpane.add(circle, column,row);
        Rectangle cyan_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        //cyan_rechtangle.setFill(Paint.valueOf("lightblue"));
        cyan_rechtangle.setFill(Color.web("#AFEEEE"));
        gridpane.add(cyan_rechtangle, column, row);
    }

    private void addPath(int column, int row){
//      Add Cicle, guter Kompromiss bis Linie gezeichnet werden kann
         Circle circle = new Circle(circleradius);
         circle.setFill(Color.web("#FFFF00"));
         gridpane.setHalignment(circle, HPos.CENTER);
        gridpane.add(circle, column,row);

//      Add Line, muss aber Fallunterscheidung gemacht werden um set Start X/Y Koordinate und End X/Y Koordiante zu setzen
//        Line line = new Line();
//        line.setFill(Color.web("#FFFF00"));
//        gridpane.add(line, column, row);

//      Add Rectangle ist kpl ausgefüllt, sieht sehr aggressiv derzeit aus
//        Rectangle purple_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
//        purple_rechtangle.setFill(Color.web("#FFFF00"));
//        gridpane.add(purple_rechtangle, column, row);
    }

    private void addBlock(int column, int row){
        Rectangle black_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        black_rechtangle.setFill(Color.web("#808080"));
        gridpane.add(black_rechtangle, column, row);
    }

    private void blankCell(int column, int row){
        Rectangle blank_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        blank_rechtangle.setStroke(Paint.valueOf("grey"));
        blank_rechtangle.setStrokeWidth(1.0);
        blank_rechtangle.setFill(Paint.valueOf("white"));
        gridpane.add(blank_rechtangle, column, row);
        Rectangle another_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
        another_rechtangle.setStroke(Paint.valueOf("grey"));
        another_rechtangle.setFill(Paint.valueOf("white"));
        another_rechtangle.setStrokeWidth(0.01);
        gridpane.add(another_rechtangle, column, row);
    }

    private void addSource(int column, int row) {
        Rectangle green_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        green_rechtangle.setFill(Color.web("#00DD00"));
        System.out.println("paint of green : " + Paint.valueOf("green").toString());

        gridpane.add(green_rechtangle, column, row);
    }

    private void addTarget(int column, int row) {
        Rectangle red_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        red_rechtangle.setFill(Color.web("#EE4400"));
        gridpane.add(red_rechtangle, column, row);
    }




    // -> ich weiß das das rendundant ist und man das auch auslagern könnte in add rectanglerype (string object, int x, int y) -> object = source target way oder block
}