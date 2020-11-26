/*
 * Copyright (c) 2019. by Dennis Eickholt
 * All rights reserved.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Startpunkt für das Programm
 * @author Dennis Eickholt
 * @created 10/2019
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Main.fxml"));
        Parent root = fxmlLoader.load();
        // Controller mFxmlDocumentController = fxmlLoader.getController();
        primaryStage.setTitle("AI-IS-P1-Suchen");
        //primaryStage.sizeToScene();;
        primaryStage.setScene(new Scene(root));

        GUIDecorator guiDecorator = new GUIDecorator();
        guiDecorator.decorate(primaryStage);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);


    }
}
