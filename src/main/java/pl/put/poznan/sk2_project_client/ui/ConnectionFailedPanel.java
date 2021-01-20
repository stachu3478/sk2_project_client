package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;
import java.awt.*;

public class ConnectionFailedPanel extends SwappablePanel {
    public ConnectionFailedPanel() {
        super();
        JLabel connectingText = new JLabel("Failed to connect, check your address and try again.");
        connectingText.setForeground(Color.LIGHT_GRAY);
        panel.add(connectingText);
    }
}
