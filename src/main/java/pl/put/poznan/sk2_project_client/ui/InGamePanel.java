package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Game;

import javax.swing.*;
import java.awt.*;

public class InGamePanel extends SwappablePanel {
    private GameCanvas gameCanvas = new GameCanvas();
    private Game game;

    public InGamePanel(Game game) {
        super();
        this.game = game;
        JLabel gameText = new JLabel("You are in the game, somehow");
        gameText.setForeground(Color.LIGHT_GRAY);
        panel.setLayout(new BorderLayout());
        panel.add(gameCanvas);
    }
}
