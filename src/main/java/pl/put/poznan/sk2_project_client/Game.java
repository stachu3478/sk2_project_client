package pl.put.poznan.sk2_project_client;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.put.poznan.sk2_project_client.ui.GameCanvas;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SK2 Game 0.1-BETA");
        Group root = new Group();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        GameCanvas gameCanvas = new GameCanvas();
        gameCanvas.appendTo(root);
        gameCanvas.draw();
    }
}
