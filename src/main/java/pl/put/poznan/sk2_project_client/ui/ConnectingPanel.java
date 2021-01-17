package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class ConnectingPanel extends SwappablePanel {
    private JLabel connectingText;

    public ConnectingPanel() {
        panel = new JPanel();
        connectingText = new JLabel("Connecting...");
        panel.add(connectingText);
    }

    public void dlaczegoToKurwaNieDziala() {
        connectingText.setText("Dlaczego to kurwa nie działa");
    }
}
