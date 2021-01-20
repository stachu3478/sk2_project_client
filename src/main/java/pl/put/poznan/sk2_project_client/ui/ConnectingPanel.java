package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;
import java.awt.*;

public class ConnectingPanel extends SwappablePanel {
    private JLabel connectingText;

    public ConnectingPanel() {
        super();
        connectingText = new JLabel("Connecting...");
        connectingText.setForeground(Color.LIGHT_GRAY);
        panel.add(connectingText);
    }
}
