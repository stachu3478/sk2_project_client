package pl.put.poznan.sk2_project_client.game;

import java.io.IOException;

public class UICallback {
    private final Player player;

    public UICallback(Player player) {
        this.player = player;
    }

    public void play(String nickname) {
        try {
            player.play(nickname);
        } catch (IOException e) {
            System.out.println("Cannot connect xd");
        }
    }
}
