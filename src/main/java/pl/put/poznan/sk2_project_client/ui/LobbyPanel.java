package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class LobbyPanel {
    private final JPanel panel;
    private int minPlayersToStart;
    private int countdown = 10;
    private JList<JLabel> playersInfo;
    private JLabel startInfo;

    public LobbyPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel waitingText = new JLabel("Waiting for players...");
        panel.add(waitingText);

        startInfo = new JLabel("Not enough players to start");
        panel.add(startInfo);

        playersInfo = new JList<>();
        panel.add(playersInfo);
    }

    public void setMinPlayersToStart(int minPlayersToStart) {
        this.minPlayersToStart = minPlayersToStart;
        startInfo.setText("Minimum players to start: " + minPlayersToStart);
    }

    public void addPlayer(String nickname) {
        JLabel nickText = new JLabel(nickname);
        playersInfo.add(nickText);
    }

    public void setIn(JFrame frame) {
        frame.add(panel);
    }
}
