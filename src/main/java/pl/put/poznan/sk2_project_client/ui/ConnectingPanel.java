package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class ConnectingPanel {
    private final JPanel panel;

    public ConnectingPanel() {
        panel = new JPanel();
        JLabel connectingText = new JLabel("Connecting...");
        panel.add(connectingText);
    }

    public void setIn(JFrame frame) {
        frame.add(panel);
    }
}
