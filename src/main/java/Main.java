/*
 * Copyright (c) 2019. by Dennis Eickholt
 * All rights reserved.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        guiDecorator.setTitelbarSVGIconContent("M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z");
        guiDecorator.setRepositoryURL("https://git.haw-hamburg.de/abr227/ai4-is-p1-suchen");

        primaryStage.getIcons().add(new Image(getClass().getResource("images/icon.png").toExternalForm()));

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);


    }
}
