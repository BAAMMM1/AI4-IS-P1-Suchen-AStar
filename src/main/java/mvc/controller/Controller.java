package mvc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import mvc.model.problem.Problem;

public class Controller {

    @FXML
    GridPane gridpane;

    @FXML
    Button button;

    Problem suche;

    public Controller() {

    }


    @FXML
    private void initialize() {
       
        button.setText("Test");

        for (int i = 0; i < 10; i++) {
            gridpane.add(new Button(i + " "), 0, i);
        }
    }


    @FXML
    public void mouseClick(){
        System.out.println("hello");
    }

}
