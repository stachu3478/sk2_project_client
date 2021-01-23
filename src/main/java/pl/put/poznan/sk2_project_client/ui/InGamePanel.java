package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Game;
import pl.put.poznan.sk2_project_client.game.Me;

import javax.swing.*;
import java.awt.*;

public class InGamePanel extends SwappablePanel {
    private GameCanvas gameCanvas;
    private Game game;

    public InGamePanel(Me me) {
        super();
        gameCanvas = new GameCanvas(me);
        this.game = me.getGame();
        JLabel gameText = new JLabel("You are in the game, somehow");
        gameText.setForeground(Color.LIGHT_GRAY);
        panel.setLayout(new BorderLayout());
        panel.add(gameCanvas);
    }
}
