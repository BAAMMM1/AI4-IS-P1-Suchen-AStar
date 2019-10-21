package mvc.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import mvc.model.PathFinding;
import mvc.model.field.NodeType;
import mvc.model.io.CheckPointDTO;
import mvc.model.io.IO;
import mvc.model.field.NodeSnapShot;

import java.net.URI;
import java.util.*;


public class Controller {
    private Integer gridsize = 15;
    private double gridfieldsize = 40;
    private double sleeptime = 50d;
    private Set<Integer> block;
    private Integer source;
    private Integer target;
    private Integer mode;
    private Stage stage;
    private IO io;
    private List<NodeSnapShot> historyline;
    private List<NodeSnapShot> historylineforThread;

    private PathFinding pathFinding;

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
    ChoiceBox<Object> choiceBox_Algorithm;

    @FXML
    TextField grid_resize_textfield;

    @FXML
    TextField grid_fieldsize_textfield;

    @FXML
    TextField animation_speed_textfield;

    @FXML
    Label label_Heuristik;

    @FXML
    ChoiceBox<String> choiceBox_Heuristik;

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
        List<Object> allAlgortihm = new ArrayList<>(pathFinding.getUninformedAlgorithm());
        allAlgortihm.add(new Separator());
        allAlgortihm.addAll(pathFinding.getInformedAlgorithm());
        choiceBox_Algorithm.setItems(FXCollections.observableArrayList(allAlgortihm));

        // Setzen der Heuristik
        List<String> allHeuristik = new ArrayList<>(pathFinding.getHeuristic());
        choiceBox_Heuristik.setItems(FXCollections.observableArrayList(allHeuristik));

        // Aufbau der Spielfläche
        cleargrid(gridpane);

        System.out.println("beende Initialize");
    }


    /*******************************************
     ***            File Section             ***
     *******************************************/

    public void menuitem_load_action() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Field");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(("Field File(*.json"), "*.json"));
        URI uri = fileChooser.showOpenDialog(stage).getAbsoluteFile().toURI();

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
        URI url;
        url = fileChooser.showSaveDialog(stage).getAbsoluteFile().toURI();
        System.out.println("URL der ausgewählten Datei zum Speichern ist: " + url.toString());
        System.out.println("dabei Gridsize: " + gridsize + " und Block: " + block.toString() + " und Source: " + source + " und Target: " + target);
        io.save(url, gridsize, block, source, target);
    }

    public void start() throws Exception {
        clickButtonResetGrid();
        // hier müssen übergabe der Listen an model/Algorithm entstehen
        if (choiceBox_Algorithm.getValue() != null) {
            if (choiceBox_Heuristik.getValue() == null && pathFinding.getUninformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
                pathFinding.uninformedCalc(gridsize, block, choiceBox_Algorithm.getValue().toString(), source, target);
            } else if (choiceBox_Heuristik.getValue() != null) {
                pathFinding.informedCalc(gridsize, block, choiceBox_Algorithm.getValue().toString(), choiceBox_Heuristik.getValue(), source, target);
            } else {
                throw new Exception();
            }

            this.historyline = pathFinding.getSnapShots();
            this.historylineforThread = pathFinding.getSnapShots();

            drawHistorylineTimeLine(historyline);
        }
    }


    private void drawHistorylineTimeLine(List<NodeSnapShot> historyline) {
        if (historyline != null) {
            // Setzen der Keysframes, der Zustände, die erreicht werden wollen
            ArrayList<KeyFrame> keyFrameArrayList = new ArrayList<>();
            for (int a = 0; a < historyline.size(); a++) {
                keyFrameArrayList.add(new KeyFrame(Duration.millis(a * sleeptime), e -> forkeyframeaction()));
            }
            // Nacheinander abspielen aller Keyframes/Zustände
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(keyFrameArrayList);
            Platform.runLater(timeline::play);
        }
    }

    private void forkeyframeaction() {
        if (historylineforThread.get(0).getSnapShotTyp().equals(NodeType.OPENLIST)) {
            drawOpenList(historylineforThread.get(0));
        }
        if (historylineforThread.get(0).getSnapShotTyp().equals(NodeType.CLOSELIST)) {
            drawClosedList(historyline.get(0));
        }
        if (historylineforThread.get(0).getSnapShotTyp().equals(NodeType.PATH)) {
            drawPath(historyline.get(0));
        }
        historylineforThread.remove(0);
    }

    private void drawClosedList(NodeSnapShot nodeSnapShot) {
        if (nodeSnapShot.getNode().getZustand() != source && nodeSnapShot.getNode().getZustand() != target) {
            int tmpcolumn = nodeSnapShot.getNode().getZustand() % gridsize;
            int tmprow = nodeSnapShot.getNode().getZustand() / gridsize;
            drawClosedList(tmpcolumn, tmprow, nodeSnapShot);
        }
    }

    private void drawClosedList(int column, int row, NodeSnapShot nodeSnapShot) {
        Rectangle cyan_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        cyan_rechtangle.setFill(Color.web("#AFEEEE"));
        gridpane.add(cyan_rechtangle, column, row);
        if (choiceBox_Heuristik.getValue() != null && pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            addTextfields(column, row, nodeSnapShot.getgCost(), nodeSnapShot.gethCost());
        }

    }

    private void drawOpenList(NodeSnapShot nodeSnapShot) {
        if (historylineforThread.get(0).getNode().getZustand() != source && historylineforThread.get(0).getNode().getZustand() != target) {
            int tmpcolumn = historylineforThread.get(0).getNode().getZustand() % gridsize;
            int tmprow = historylineforThread.get(0).getNode().getZustand() / gridsize;
            drawOpenList(tmpcolumn, tmprow, nodeSnapShot);
        }
    }

    private void drawOpenList(int column, int row, NodeSnapShot nodeSnapShot) {
        Rectangle green_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        green_rechtangle.setFill(Color.web("#98FB98"));
        gridpane.add(green_rechtangle, column, row);
        //if (nodeSnapShot instanceof InformedAlgorithm){;
        if (choiceBox_Heuristik.getValue() != null && pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            addTextfields(column, row, nodeSnapShot.getgCost(), nodeSnapShot.gethCost());
        }
    }

    private void drawPath(NodeSnapShot nodeSnapShot) {
        if (choiceBox_Heuristik.getValue() != null && pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            if (nodeSnapShot.getNode().getZustand() != source && nodeSnapShot.getNode().getZustand() != target) {
                int tmpcolumn = nodeSnapShot.getNode().getZustand() % gridsize;
                int tmprow = nodeSnapShot.getNode().getZustand() / gridsize;
                drawPath(tmpcolumn, tmprow, nodeSnapShot);
            }
        }
    }

    private void drawPath(int column, int row, NodeSnapShot nodeSnapShot) {
        Circle circle = new Circle(gridfieldsize / 3);
        circle.setFill(Color.web("#FFFF00"));
        GridPane.setHalignment(circle, HPos.CENTER);
        gridpane.add(circle, column, row);
        TextField tfpath = new TextField();
        tfpath.setAlignment(Pos.CENTER);
        tfpath.setText("" + nodeSnapShot.getfCost());
        tfpath.setBackground(Background.EMPTY);
        tfpath.setPrefSize(gridfieldsize, gridfieldsize);
        gridpane.add(tfpath, column, row);
    }


    @FXML
    private void visibility_heuristic() {
        System.out.println(choiceBox_Algorithm.getValue());
        if (choiceBox_Algorithm != null && pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            label_Heuristik.setVisible(true);
            choiceBox_Heuristik.setVisible(true);
        } else {
            label_Heuristik.setVisible(false);
            choiceBox_Heuristik.setVisible(false);
            choiceBox_Heuristik.setValue(null);
        }

    }

    private void drawBlock() {
/*
        if (block != null) {
            Iterator<Integer> it = block.iterator();
            while (it.hasNext()) {
                Integer next = it.next();
                int tmpcolumn = next % gridsize;
                int tmprow = next / gridsize;
                drawBlock(tmpcolumn, tmprow);
            }
        }
*/

        if (block != null) {
            // foreach (Wert, über den gegangen werden soll : Set/Liste, in der der Wert vorhanden ist) -> intellij macht aus foreach -> for
            for (Integer next : block) {
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

    /*******************************************
     ***        GUI-Action Section           ***
     *******************************************/

    public void actionGridResizeInput() {
        gridsize = Integer.parseInt(grid_resize_textfield.getText());
        System.out.println("gridsize ist nun: " + gridsize);
        initialize();
    }

    public void actionGridFieldresizeInput() {
        Integer a = Integer.parseInt(grid_fieldsize_textfield.getText());
        gridfieldsize = a;
        System.out.println("gridfieldsize ist nun: " + a);
        initialize();
    }

    public void actionAnimationSpeedInput() {
        if (!animation_speed_textfield.getText().equals("")) {
            sleeptime = Double.parseDouble(this.animation_speed_textfield.getText());
        }
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

    public void clickGrid(MouseEvent event) {
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

    // Um das Grid wieder zu reseten -> Entfernen von OpenList, ClosedList und Path
    private void drawBlock(int column, int row) {
        Rectangle black_rechtangle = new Rectangle(gridfieldsize - 1, gridfieldsize - 1);
        black_rechtangle.setFill(Color.web("#808080"));
        GridPane.setHalignment(black_rechtangle, HPos.CENTER);
        gridpane.add(black_rechtangle, column, row);
    }

    private void drawSource(int column, int row) {
        Rectangle green_rechtangle = new Rectangle(gridfieldsize - 1, gridfieldsize - 1);
        green_rechtangle.setFill(Color.web("#00DD00"));
        System.out.println("paint of green : " + Paint.valueOf("green").toString());
        GridPane.setHalignment(green_rechtangle, HPos.CENTER);
        gridpane.add(green_rechtangle, column, row);
    }

    private void drawTarget(int column, int row) {
        Rectangle red_rechtangle = new Rectangle(gridfieldsize - 1, gridfieldsize - 1);
        red_rechtangle.setFill(Color.web("#EE4400"));
        GridPane.setHalignment(red_rechtangle, HPos.CENTER);
        GridPane.setHalignment(red_rechtangle, HPos.CENTER);
        gridpane.add(red_rechtangle, column, row);
    }

    private void blankCell(int column, int row) {
/*
        Rectangle blank_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        blank_rechtangle.setStroke(Paint.valueOf("grey"));
        blank_rechtangle.setStrokeWidth(1.0);
        blank_rechtangle.setFill(Paint.valueOf("white"));
        gridpane.setHalignment(blank_rechtangle, HPos.CENTER);
        gridpane.add(blank_rechtangle, column, row);
*/
        Rectangle another_rechtangle = new Rectangle(gridfieldsize, gridfieldsize);
        another_rechtangle.setStroke(Paint.valueOf("grey"));
        another_rechtangle.setFill(Paint.valueOf("white"));
        another_rechtangle.setStrokeWidth(0.5);
        gridpane.setAlignment(Pos.CENTER);
        gridpane.add(another_rechtangle, column, row);
    }

    // Um OpenList und ClosedList die CostFelder anzeigen zu lassen
    private void addTextfields(int column, int row, int gMoveCost, int hHeuristicDistanceToTarget) {
        // Textfield nach https://www.youtube.com/watch?v=KNXfSOx4eEE
        // bzw nach https://www.youtube.com/watch?v=-L-WgKMFuhE&t=143s

        //Oben links        -> G
        gridpane.add(addTextfieldToNode(Pos.TOP_LEFT, "" + gMoveCost, gridfieldsize / 4), column, row);
        //Oben rechts       -> H
        gridpane.add(addTextfieldToNode(Pos.TOP_RIGHT, "" + hHeuristicDistanceToTarget, gridfieldsize / 4), column, row);
        //Mitte unten       -> F
        gridpane.add(addTextfieldToNode(Pos.BOTTOM_CENTER, "" + (gMoveCost + hHeuristicDistanceToTarget), gridfieldsize * 3 / 10), column, row);
    }

    private TextField addTextfieldToNode(Pos Position, String text, double fontsize) {
        TextField textfield = new TextField();
        textfield.setAlignment(Position);
        textfield.setText(text);
        textfield.setBackground(Background.EMPTY);
        textfield.setPrefSize(gridfieldsize, gridfieldsize);
        textfield.setFont(Font.font(fontsize));
        return textfield;
    }
}


// TODO JPS https://www.youtube.com/watch?v=jB1IOR5roUM ablauf
// TODO Clearance-based Pathfinding  -> man geht von allen hindernissen aus und versucht möglichst große quadrate zu bilden https://harablog.wordpress.com/2009/01/29/clearance-based-pathfinding/

// https://stackoverflow.com/questions/39235545/add-delay-after-platform-runlater-runnable
// drawPath(int column, int row, NodeSnapShot nodeSnapShot) + v^ both, for Thread and Timeline

//  Erkenntnis: Gridpane bzw View ist nicht darauf ausgelegt, das man felder ausliest um deren Attribute vergleichen zu könne
//  https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content
