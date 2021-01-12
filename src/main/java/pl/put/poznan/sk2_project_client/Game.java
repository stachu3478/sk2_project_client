package pl.put.poznan.sk2_project_client;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.put.poznan.sk2_project_client.game.GameMessageIdentifier;
import pl.put.poznan.sk2_project_client.game.Player;
import pl.put.poznan.sk2_project_client.ui.GameUI;

import java.io.IOException;

public class Game extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Player player = new Player();
        GameUI ui = new GameUI(primaryStage, player);

        int attempts = 3;
        while (!player.isConnected() && attempts > 0) {
            try {
                player.connect();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                attempts--;
            }
        }

        if (player.isConnected()) {
            player.getClient().setMessageIdentifier(new GameMessageIdentifier(player));
        }
    }
}
