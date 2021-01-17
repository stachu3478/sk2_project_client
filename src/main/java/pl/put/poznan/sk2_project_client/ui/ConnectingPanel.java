package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class ConnectingPanel extends SwappablePanel {
    private JLabel connectingText;

    public ConnectingPanel() {
        super();
        connectingText = new JLabel("Connecting...");
        panel.add(connectingText);
    }
}
