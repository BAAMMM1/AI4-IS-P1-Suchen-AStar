package mvc.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvc.model.PathFinding;
import mvc.model.field.NodeType;
import mvc.model.io.CheckPointDTO;
import mvc.model.io.IO;
import mvc.model.field.NodeSnapShot;

import java.net.URI;
import java.util.*;


public class Controller {
    Integer gridsize = 15;
    int gridfieldsize = 40;
    Set<Integer> block;
    Integer source;
    Integer target;
    Integer mode;
    Stage stage;
    IO io;
    URI uri;
    List<NodeSnapShot> historyline;
    List<Integer> openList;
    List<Integer> closedList;
    List<Integer> pathList;
    PathFinding pathFinding;

    @FXML
    GridPane gridpane;

//    @FXML
//    GridPane gridpane2;

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

    @FXML
    Label label_Heuristik;

    @FXML
    ChoiceBox choiceBox_Heuristik;

    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem menuitem_load;

    @FXML
    MenuItem menuitem_save;


    public Controller() {
    }

    @FXML
    private void initialize() {
        System.out.println("starte Initialize");
        //Initialisierung der Variablen
        openList = new ArrayList<>();
        closedList = new ArrayList<>();
        pathList = new ArrayList<>();
        block = new HashSet<>();
        target = null;
        source = null;
        pathFinding = new PathFinding();
        io = new IO();
        stage = new Stage();

        // Setzen der Standardeinstellung im Algorithm Bereich zum Bearbeiten
        // hier: Mode 1: Block setzen
        mode = 0;
        block_Button.setSelected(false);

        // Setzen der verfügbaren Such-Algorithm
        List<Object> allAlgortihm = new ArrayList<>();
        allAlgortihm.addAll(pathFinding.getUninformedAlgorithm());
        allAlgortihm.add(new Separator());
        allAlgortihm.addAll(pathFinding.getInformedAlgorithm());
        choiceBox_Algorithm.setItems(FXCollections.observableArrayList(allAlgortihm));

        // Setzen der Heuristik
        List<String> allHeuristik = new ArrayList<>();
        allHeuristik.addAll(pathFinding.getHeuristic());
        choiceBox_Heuristik.setItems(FXCollections.observableArrayList(allHeuristik));

        // Aufbau der Spielfläche
        cleargrid(gridpane);

        System.out.println("beende Initialize");
    }
//  Erkenntnis: Gridpane bzw View ist nicht darauf ausgelegt, das man felder ausliest um deren Attribute vergleichen zu könne
//  https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content


    /*******************************************
     ***            File Section             ***
     *******************************************/

    public void menuitem_load_action() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Field");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(("Field File(*.json"), "*.json"));
        String uristring = new String();
        String absolutepath = new String();
        uri = fileChooser.showOpenDialog(stage).getAbsoluteFile().toURI();

        CheckPointDTO checkPointDTO = io.load(uri);

        this.gridsize = checkPointDTO.getGridSize();
        this.source = checkPointDTO.getSource();
        this.target = checkPointDTO.getTarget();
        this.block = checkPointDTO.getBlockSet();
        clickButtonResetGrid();
    }

    public void menuitem_save_action() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Field");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(("Field File(*.json"), "*.json"));
        URI url = null;

        url = fileChooser.showSaveDialog(stage).getAbsoluteFile().toURI();

        System.out.println("URL der ausgewählten Datei zum Speichern ist: " + url.toString());

        System.out.println("dabei Gridsize: " + gridsize + " und Block: " + block.toString() + " und Source: " + source + " und Target: " + target);

        io.save(url, gridsize, block, source, target);
    }

    public void start() throws Exception {
        // hier müssen übergabe der Listen an model/Algorithm entstehen

        if (choiceBox_Heuristik.getValue() == null && pathFinding.getUninformedAlgorithm().contains(choiceBox_Algorithm.getValue())) {
            pathFinding.uninformedCalc(gridsize, block, choiceBox_Algorithm.getValue().toString(), source, target);
        } else if (choiceBox_Heuristik.getValue() != null) {
            pathFinding.informedCalc(gridsize, block, choiceBox_Algorithm.getValue().toString(), choiceBox_Heuristik.getValue().toString(), source, target);
        } else {
            throw new Exception();
        }

        this.openList = pathFinding.getOpenList();
        this.closedList = pathFinding.getCloseList();
        this.pathList = pathFinding.getPath();

        this.historyline = pathFinding.getSnapShots();
//        historyline.addAll(pathFinding.getSnapShots()); -> funktioniert aber nur, wenn sie vorher initialisiert wurde -> new Arraylist, da an null nicht geaddet werden kann

        drawHistoryline(historyline);
//        drawOpenList(openList);
//        drawClosedList(closedList);
//        drawPath(pathList);


//        Andere Möglichkeit:
//        ArrayList<List> solution[] = new ArrayList[3];
//        solution[] = deinemethode(source, target, block);
//        this.openList = solution[0];
//        this.closedList = solution[1];
//        this.pathList = solution[2];


    }

    // TODO Darstellung nicht erst nachdem fertiggezeichnet wurde sondern sofortige Darstellung -> er wartet mit Zeit aber erst nach Abschluss ist sichtbar
    private void drawHistoryline(List<NodeSnapShot> historyline) {
        if (historyline != null) {
            for (int i = 0; i < historyline.size(); i++) {
                if (historyline.get(i).getSnapShotTyp().equals(NodeType.OPENLIST)) {
                    drawOpenList(historyline.get(i).getNode().getZustand());
                }
                if (historyline.get(i).getSnapShotTyp().equals(NodeType.CLOSELIST)) {
                    drawClosedList(historyline.get(i).getNode().getZustand());
                }
                if (historyline.get(i).getSnapShotTyp().equals(NodeType.PATH)) {
                    drawPath(historyline.get(i).getNode().getZustand());
                }
                try {
                    Thread.sleep(new Long(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void visibility_heuristic() {
        System.out.println(choiceBox_Algorithm.getValue());
        if (pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            label_Heuristik.setVisible(true);
            choiceBox_Heuristik.setVisible(true);
        } else {
            label_Heuristik.setVisible(false);
            choiceBox_Heuristik.setVisible(false);
            choiceBox_Heuristik.setValue(null);
        }

    }

    ;


    private void drawBlock() {
        if (block != null) {
            Iterator<Integer> it = block.iterator();
            while (it.hasNext()) {
                Integer next = it.next();
                int tmpcolumn = next % gridsize;
                int tmprow = next / gridsize;
                drawBlock(tmpcolumn, tmprow);
            }
        }
        /*
        Stream Exercise
        List<Integer> blockList = block.stream().collect(Collectors.toList());
        for (int i= 0; i < blockList.size(); i++ ){
            int tmpcolumn = blockList.get(i) % gridsize;
            int tmprow = blockList.get(i)/gridsize;
            addBlock(tmpcolumn, tmprow);
        }*/


    }

    private void cleargrid(GridPane gp) {
        gp.getRowConstraints().clear();
        gp.getColumnConstraints().clear();
        gp.getChildren().clear();
        for (int a = 0; a < gridsize; a++) {
            gp.getRowConstraints().add(new RowConstraints());
            gp.getColumnConstraints().add(new ColumnConstraints());
        }
        for (int i = 0; i < gridsize; i++) {
            for (int j = 0; j < gridsize; j++) {
                blankCell(i, j);
            }
        }
    }

    private void drawSource() {
        if (source != null) {
            int tmpcolumn = source % gridsize;
            int tmprow = source / gridsize;
            drawSource(tmpcolumn, tmprow);
        }
    }

    private void drawTarget() {
        if (target != null) {
            int tmpcolumn = target % gridsize;
            int tmprow = target / gridsize;
            drawTarget(tmpcolumn, tmprow);
        }
    }

    private void drawOpenList(List<Integer> openList) {
        for (int i = 0; i < openList.size(); i++) {
            int tmpcolumn = openList.get(i) % gridsize;
            int tmprow = openList.get(i) / gridsize;
            if (openList.get(i) == source) continue;
            drawOpenList(tmpcolumn, tmprow);
        }
    }

    private void drawOpenList(int fieldnumber) {
        if (fieldnumber != source && fieldnumber != target) {
            int tmpcolumn = fieldnumber % gridsize;
            int tmprow = fieldnumber / gridsize;
            drawOpenList(tmpcolumn, tmprow);
        }
    }

    private void drawClosedList(List<Integer> openList) {
        for (int i = 0; i < openList.size(); i++) {
            int tmpcolumn = openList.get(i) % gridsize;
            int tmprow = openList.get(i) / gridsize;
            if (openList.get(i) == source) continue;
            drawClosedList(tmpcolumn, tmprow);
        }
    }

    private void drawClosedList(int fieldnumber) {
        if (fieldnumber != source && fieldnumber != target) {
            int tmpcolumn = fieldnumber % gridsize;
            int tmprow = fieldnumber / gridsize;
            drawClosedList(tmpcolumn, tmprow);
        }
    }


    private void drawPath(List<Integer> openList) {
        for (int i = 0; i < openList.size() - 2; i++) {
            int tmpcolumn = openList.get(i + 1) % gridsize;
            int tmprow = openList.get(i + 1) / gridsize;
            drawPath(tmpcolumn, tmprow);
        }
    }

    private void drawPath(int fieldnumber) {
        if (fieldnumber != source && fieldnumber != target) {
            int tmpcolumn = fieldnumber % gridsize;
            int tmprow = fieldnumber / gridsize;
            drawPath(tmpcolumn, tmprow);
        }
    }


    /*******************************************
     ***        GUI-Action Section           ***
     *******************************************/

    public void actionGridResizeInput() {
        Integer a = Integer.parseInt(grid_resize_textfield.getText());
        gridsize = a;
        System.out.println("gridsize ist nun: " + gridsize);
        initialize();
    }

    public void actionGridFieldresizeInput() {
        Integer a = Integer.parseInt(grid_fieldsize_textfield.getText());
        gridfieldsize = a;
        System.out.println("gridfieldsize ist nun: " + a);
        initialize();
    }

    @FXML
    private void clickButtonResetGrid() {
        cleargrid(gridpane);
        drawTarget();
        drawSource();
        drawBlock();
    }

    public void clickBlockButton() {
        mode = 1;
        block_Button.setSelected(true);
        clear_Button.setSelected(false);
        target_Button.setSelected(false);
        source_Button.setSelected(false);
    }

    public void clickSourceButton() {
        mode = 2;
        block_Button.setSelected(false);
        clear_Button.setSelected(false);
        target_Button.setSelected(false);
        source_Button.setSelected(true);
    }

    public void clickTargetButton() {
        mode = 3;
        block_Button.setSelected(false);
        clear_Button.setSelected(false);
        target_Button.setSelected(true);
        source_Button.setSelected(false);
    }

    public void clickClearButton() {
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

            if (colIndex != null && rowIndex != null) {

                int tmpcolIndex;
                int tmprowIndex;

                switch (mode) {
                    case 0:
                        break;
                    case 1:
                        drawBlock(colIndex, rowIndex);
                        block.add(colIndex % gridsize + rowIndex * gridsize);
                        System.out.println("gespeichert in Blocklist: " + block.toString());
                        break;
                    case 2:
                        if ((target == null || target != colIndex % gridsize + rowIndex * gridsize)
                                && (source != null && source != colIndex % gridsize + rowIndex * gridsize)) {
                            tmpcolIndex = source % gridsize;
                            tmprowIndex = source / gridsize;
                            blankCell(tmpcolIndex, tmprowIndex);
                            System.out.print("Source war " + source + " ");
                        }
                        if ((source == null
                                || (source != colIndex % gridsize + rowIndex * gridsize))
                                && (target == null
                                || colIndex % gridsize + rowIndex * gridsize != target)) {
                            source = (colIndex % gridsize + rowIndex * gridsize);
                            drawSource(colIndex, rowIndex);
                            System.out.println("Source nun Feld: " + source);
                        }
                        break;
                    case 3:
                        if ((source == null || source != colIndex % gridsize + rowIndex * gridsize)
                                && (target != null && target != colIndex % gridsize + rowIndex * gridsize)) {
                            tmpcolIndex = target % gridsize;
                            tmprowIndex = target / gridsize;
                            blankCell(tmpcolIndex, tmprowIndex);
                            System.out.print("Target war: " + target + " ");
                        }
                        if ((target == null
                                || target != colIndex % gridsize + rowIndex * gridsize)
                                && (source == null
                                || source != colIndex % gridsize + rowIndex * gridsize)) {
                            target = (colIndex % gridsize + rowIndex * gridsize);
                            drawTarget(colIndex, rowIndex);
                            System.out.println("Target nun Feld: " + target);
                        }
                        break;
                    case 4:
                        if (block.contains(colIndex % gridsize + rowIndex * gridsize)) {
                            block.remove(colIndex % gridsize + rowIndex * gridsize);
                            blankCell(colIndex, rowIndex);
                        }
                        break;
                }

            }
        }
    }


    /*******************************************
     ***            Draw Section             ***
     *******************************************/


    private void drawBlock(int column, int row) {
        Rectangle black_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        black_rechtangle.setFill(Color.web("#808080"));
        gridpane.add(black_rechtangle, column, row);
    }

    private void drawSource(int column, int row) {
        Rectangle green_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        green_rechtangle.setFill(Color.web("#00DD00"));
        System.out.println("paint of green : " + Paint.valueOf("green").toString());

        gridpane.add(green_rechtangle, column, row);
    }

    private void drawTarget(int column, int row) {
        Rectangle red_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        red_rechtangle.setFill(Color.web("#EE4400"));
        gridpane.add(red_rechtangle, column, row);
    }

    private void blankCell(int column, int row) {
        Rectangle blank_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        blank_rechtangle.setStroke(Paint.valueOf("grey"));
        blank_rechtangle.setStrokeWidth(1.0);
        blank_rechtangle.setFill(Paint.valueOf("white"));
        gridpane.add(blank_rechtangle, column, row);
        Rectangle another_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        another_rechtangle.setStroke(Paint.valueOf("grey"));
        another_rechtangle.setFill(Paint.valueOf("white"));
        another_rechtangle.setStrokeWidth(0.01);
        gridpane.add(another_rechtangle, column, row);
    }

    private void drawOpenList(int column, int row) {
        // Circle circle = new Circle(circleradius);
        // circle.setFill(Paint.valueOf("green"));
        // gridpane.setHalignment(circle, HPos.CENTER);
        // gridpane.add(circle, column,row);
        Rectangle green_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        green_rechtangle.setFill(Color.web("#98FB98"));
        gridpane.add(green_rechtangle, column, row);

    }

    private void drawClosedList(int column, int row) {
//        Circle circle = new Circle(circleradius);
//        circle.setFill(Paint.valueOf("cyan"));
//        gridpane.setHalignment(circle, HPos.CENTER);
//        gridpane.add(circle, column,row);
        Rectangle cyan_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        //cyan_rechtangle.setFill(Paint.valueOf("lightblue"));
        cyan_rechtangle.setFill(Color.web("#AFEEEE"));
        gridpane.add(cyan_rechtangle, column, row);
    }

    private void drawPath(int column, int row) {
//      Add Cicle, guter Kompromiss bis Linie gezeichnet werden kann
        Circle circle = new Circle(gridfieldsize / 3);
        circle.setFill(Color.web("#FFFF00"));
        gridpane.setHalignment(circle, HPos.CENTER);
        gridpane.add(circle, column, row);

//      Add Line, muss aber Fallunterscheidung gemacht werden um set Start X/Y Koordinate und End X/Y Koordiante zu setzen
//        Line line = new Line();
//        line.setFill(Color.web("#FFFF00"));
//        gridpane.add(line, column, row);

//      Add Rectangle ist kpl ausgefüllt, sieht sehr aggressiv derzeit aus
//        Rectangle purple_rechtangle = new Rectangle(gridfieldsize,gridfieldsize);
//        purple_rechtangle.setFill(Color.web("#FFFF00"));
//        gridpane.add(purple_rechtangle, column, row);
    }

}