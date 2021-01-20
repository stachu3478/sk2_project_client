package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;
import java.awt.*;

public class SwappablePanel {
    protected JPanel panel = new JPanel();
    protected JFrame frame;

    public SwappablePanel() {
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    void setIn(JFrame frame) {
        this.frame = frame;
        frame.getContentPane().removeAll();
        panel.setBackground(Color.BLACK);
        panel.setForeground(Color.LIGHT_GRAY);
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void update() {};
}
