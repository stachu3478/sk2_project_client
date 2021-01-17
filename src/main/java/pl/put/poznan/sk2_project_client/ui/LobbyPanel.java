package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;
import javax.swing.event.ListDataListener;

public class LobbyPanel extends SwappablePanel {
    private int countdown = 10;
    private int players = 0;
    private ListModel<String> model;
    private JLabel startInfo;

    public LobbyPanel() {
        super();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel waitingText = new JLabel("Waiting for players...");
        panel.add(waitingText);

        startInfo = new JLabel("Not enough players to start");
        panel.add(startInfo);
    }

    public void setMinPlayersToStart(int minPlayersToStart) {
        startInfo.setText("Minimum players to start: " + minPlayersToStart);
    }

    public void addPlayer(String nickname) {
        players++;
        JLabel nickText = new JLabel(players + ". " + nickname);
        panel.add(nickText);
    }
}
