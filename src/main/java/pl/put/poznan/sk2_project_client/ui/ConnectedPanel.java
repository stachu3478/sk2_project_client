package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectedPanel {
    private final JPanel panel;

    public ConnectedPanel(Player player) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nicknameLabel = new JLabel("Your nickname (or just press play):");
        JTextField nicknameField = new JTextField();
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    player.play(nicknameField.getText());
                    while (!player.isInLobby()) {
                        player.selectFor(50);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    // if (!player.isConnected()) connecting();
                }
            }
        });
        panel.add(nicknameLabel);
        panel.add(nicknameField);
        panel.add(playButton);
    }

    public void setIn(JFrame frame) {
        frame.add(panel);
    }
}
