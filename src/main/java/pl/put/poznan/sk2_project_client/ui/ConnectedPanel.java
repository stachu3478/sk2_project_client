package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Me;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectedPanel extends SwappablePanel {
    private final JLabel nicknameLabel;

    public ConnectedPanel(Me me) {
        super();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        nicknameLabel = new JLabel("Your nickname (or just press play):");
        nicknameLabel.setForeground(Color.LIGHT_GRAY);
        nicknameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nicknameField = new JTextField();
        Color transparent = new Color(0, 0, 0, 0);
        JPanel nicknameFieldContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 100));
        nicknameFieldContainer.setBackground(transparent);
        nicknameField.setBackground(Color.BLACK);
        nicknameField.setForeground(Color.WHITE);
        nicknameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nicknameField.setPreferredSize(new Dimension(100, 20));
        nicknameFieldContainer.add(nicknameField);
        JButton playButton = new JButton("Play");
        playButton.setForeground(Color.LIGHT_GRAY);
        playButton.setBackground(transparent);
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    me.play(nicknameField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        panel.add(nicknameLabel);
        panel.add(nicknameFieldContainer);
        panel.add(playButton);
    }
}
