package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class SwappablePanel {
    protected JPanel panel = new JPanel();

    void setIn(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
}
