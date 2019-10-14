package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();

       URL url = Paths.get("./src/main/java/mvc/view/AI-IS-P1-Suchen.fxml").toUri().toURL();
        System.out.println("das hindert mich: " + fxmlLoader.getLocation());
        System.out.println("das hindert mich nicht : " + url);

        Parent root = fxmlLoader.load(url);
        primaryStage.setTitle("Title");

        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);


    }
}
