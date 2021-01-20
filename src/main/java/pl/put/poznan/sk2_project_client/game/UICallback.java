package pl.put.poznan.sk2_project_client.game;

import java.io.IOException;

public class UICallback {
    private final Me me;

    public UICallback(Me me) {
        this.me = me;
    }

    public void play(String nickname) {
        try {
            me.play(nickname);
        } catch (IOException e) {
            System.out.println("Cannot connect xd");
        }
    }
}
