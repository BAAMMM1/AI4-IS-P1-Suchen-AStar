package mvc.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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
            if (choiceBox_Heuristik.getValue() == null && pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
                System.out.println("Es fehlt die Heuristik bei dem Informierten Algorithmus");
            } else {
                if (choiceBox_Heuristik.getValue() == null && pathFinding.getUninformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
                    pathFinding.uninformedCalc(gridsize, block, choiceBox_Algorithm.getValue().toString(), source, target);

                } else if (pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString()) && choiceBox_Heuristik.getValue() != null) {
                    pathFinding.informedCalc(gridsize, block, choiceBox_Algorithm.getValue().toString(), choiceBox_Heuristik.getValue(), source, target);
                } else {
                    throw new Exception();
                }

                this.historyline = pathFinding.getSnapShots();
                this.historylineforThread = pathFinding.getSnapShots();

                drawHistorylineTimeLine(historyline);
            }
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
            gridpane.add(drawRectangle(gridfieldsize, gridfieldsize, "#AFEEEE"), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
            addTextfields(nodeSnapShot);
        }
    }

    private void drawOpenList(NodeSnapShot nodeSnapShot) {
        if (historylineforThread.get(0).getFieldNumber() != source && historylineforThread.get(0).getFieldNumber() != target) {
            gridpane.add(drawRectangle(gridfieldsize, gridfieldsize, "#98FB98"), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
            addTextfields(nodeSnapShot);
        }
    }


    private void drawPath(NodeSnapShot nodeSnapShot) {
        if (nodeSnapShot.getNode().getZustand() != source && nodeSnapShot.getNode().getZustand() != target) {
            int tmpcolumn = nodeSnapShot.getNode().getZustand() % gridsize;
            int tmprow = nodeSnapShot.getNode().getZustand() / gridsize;
            drawPath(tmpcolumn, tmprow, nodeSnapShot);
        }

    }

    private void drawPath(int column, int row, NodeSnapShot nodeSnapShot) {
        //Zeichnen der Nodes
        Circle cicic = drawCircle(gridfieldsize / 3.5);
        GridPane.setValignment(cicic, VPos.BOTTOM);
        System.out.println("Valignment ist " + GridPane.getValignment(cicic));
        gridpane.add(cicic, column, row);

        //Beschriften der Nodes
        String text;
        if (choiceBox_Heuristik.getValue() != null && pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            text = "" + nodeSnapShot.getfCost();
        } else {
            text = "" + nodeSnapShot.getNode().getDepth();
        }
        gridpane.add(addPathTextfield(text), column, row);
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
        if (block != null) {
            // foreach (Wert, über den gegangen werden soll : Set/Liste, in der der Wert vorhanden ist) -> intellij macht aus foreach -> for
            for (Integer next : block) {
                drawBlock(getcolumn(next), getrow(next));
            }
        }
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
            drawSource(getcolumn(source), getrow(source));
        }
    }

    private void drawTarget() {
        if (target != null) {
            drawTarget(getcolumn(target), getrow(target));
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
                            blankCell(getcolumn(source), getrow(source));
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
                            blankCell(getcolumn(target), getrow(target));
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
        gridpane.add(drawRectangle(gridfieldsize - 1, gridfieldsize - 1, "#808080"), column, row);
    }

    private void drawSource(int column, int row) {
        gridpane.add(drawRectangle(gridfieldsize - 1, gridfieldsize - 1, "#00DD00"), column, row);
    }

    private void drawTarget(int column, int row) {
        gridpane.add(drawRectangle(gridfieldsize - 1, gridfieldsize - 1, "#EE4400"), column, row);
    }

    private void blankCell(int column, int row) {
        Rectangle rectangle = drawRectangle(gridfieldsize, gridfieldsize, "#FFFFFF");
        rectangle.setStroke(Paint.valueOf("grey"));
        rectangle.setStrokeWidth(0.5);
        gridpane.add(rectangle, column, row);
    }

    private void addTextfields(NodeSnapShot nodeSnapShot) {
        //Informierte Suche
        if (pathFinding.getInformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString()) && choiceBox_Heuristik.getValue() != null) {
            //Oben links        -> G
            gridpane.add(addTextfieldToNode(Pos.TOP_LEFT, "" + nodeSnapShot.getgCost(), gridfieldsize / 4), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
            //Oben rechts       -> H
            gridpane.add(addTextfieldToNode(Pos.TOP_RIGHT, "" + nodeSnapShot.gethCost(), gridfieldsize / 4), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
            //Mitte unten       -> F
            gridpane.add(addTextfieldToNode(Pos.BOTTOM_CENTER, "" + (nodeSnapShot.getfCost()), gridfieldsize * 3 / 10), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
        }
        //Uninformierte Suche
        else if (pathFinding.getUninformedAlgorithm().contains(choiceBox_Algorithm.getValue().toString())) {
            //Oben links        -> Fieldnumber
            gridpane.add(addTextfieldToNode(Pos.TOP_LEFT, "" + nodeSnapShot.getFieldNumber(), gridfieldsize / 4), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
            //Mitte unten       -> Gesammtkosten
            gridpane.add(addTextfieldToNode(Pos.BOTTOM_CENTER, "" + (nodeSnapShot.getNode().getDepth()), gridfieldsize * 3 / 10), getcolumn(nodeSnapShot), getrow(nodeSnapShot));
        }
    }

    /*******************************************
     ***           Helper Section            ***
     *******************************************/

    private Rectangle drawRectangle(double width, double height, String colorstring) {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setFill(Color.web(colorstring));
        GridPane.setHalignment(rectangle, HPos.CENTER);
        return rectangle;
    }

    private Circle drawCircle(double radius) {
        Circle circle = new Circle(radius);
        circle.setFill(Color.web("#FFFF00"));
        GridPane.setHalignment(circle, HPos.CENTER);
        return circle;
    }

    private TextField addPathTextfield(String text) {
        TextField tfpath = new TextField();
        tfpath.setAlignment(Pos.BOTTOM_CENTER);
        tfpath.setBackground(Background.EMPTY);
        tfpath.setPrefSize(gridfieldsize, gridfieldsize);
        tfpath.setText(text);
        return tfpath;
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

    private int getcolumn(int fieldnumber) {
        return fieldnumber % gridsize;
    }

    private int getcolumn(NodeSnapShot nodeSnapShot) {
        return nodeSnapShot.getFieldNumber() % gridsize;
    }

    private int getrow(int fieldnumber) {
        return fieldnumber / gridsize;
    }

    private int getrow(NodeSnapShot nodeSnapShot) {
        return nodeSnapShot.getFieldNumber() / gridsize;
    }
}


// TODO JPS https://www.youtube.com/watch?v=jB1IOR5roUM ablauf
// TODO Clearance-based Pathfinding  -> man geht von allen hindernissen aus und versucht möglichst große quadrate zu bilden https://harablog.wordpress.com/2009/01/29/clearance-based-pathfinding/

// https://stackoverflow.com/questions/39235545/add-delay-after-platform-runlater-runnable
// drawPath(int column, int row, NodeSnapShot nodeSnapShot) + v^ both, for Thread and Timeline

//  Erkenntnis: Gridpane bzw View ist nicht darauf ausgelegt, das man felder ausliest um deren Attribute vergleichen zu könne
//  https://stackoverflow.com/questions/20655024/javafx-gridpane-retrieve-specific-cell-content
