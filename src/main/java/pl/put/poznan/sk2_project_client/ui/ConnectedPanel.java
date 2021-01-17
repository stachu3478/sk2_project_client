package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectedPanel extends SwappablePanel {
    private final JLabel nicknameLabel;

    public ConnectedPanel(Player player) {
        super();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        nicknameLabel = new JLabel("Your nickname (or just press play):");
        JTextField nicknameField = new JTextField();
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    player.play(nicknameField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        panel.add(nicknameLabel);
        panel.add(nicknameField);
        panel.add(playButton);
    }
}
