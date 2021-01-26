package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;
import java.awt.*;

public class SwappablePanel {
    protected JPanel panel = new DrawingPanel();
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
        panel.setVisible(true);
        mounted();
    }

    void unset() {
        frame.remove(panel);
        panel.setVisible(false);
        dismounted();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void update() {}

    public void draw(Graphics2D g) {}

    /**
     * Function fires when the panel is dismounted and mounted
     */
    public void dismounted() {}
    public void mounted() {}

    private class DrawingPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            draw((Graphics2D) g);
        }
    }
}
