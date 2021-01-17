package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;

public class SwappablePanel {
    protected JPanel panel = new JPanel();

    public SwappablePanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

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
