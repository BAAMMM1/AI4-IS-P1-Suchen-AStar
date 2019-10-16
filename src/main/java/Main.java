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
        URL url = Paths.get("./src/main/java/mvc/view/sample.fxml").toUri().toURL();
        Parent root = fxmlLoader.load(url);

        primaryStage.setTitle("Title");

        primaryStage.sizeToScene();
        //primaryStage.setScene(new Scene(root, 800, 640));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);


    }
}
