package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/mainInterface.fxml"));
            primaryStage.setTitle("GraphCreator MVS");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.setMinHeight(600);
            primaryStage.setMinWidth(800);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Pane pane = new Pane();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
