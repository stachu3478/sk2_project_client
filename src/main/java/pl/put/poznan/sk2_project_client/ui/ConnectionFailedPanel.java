package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class ConnectionFailedPanel extends SwappablePanel {
    public ConnectionFailedPanel() {
        JLabel connectingText = new JLabel("Failed to connect, check your address and try again.");
        panel.add(connectingText);
    }
}
