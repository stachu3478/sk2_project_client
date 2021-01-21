package pl.put.poznan.sk2_project_client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GameCanvas extends JPanel {

    public GameCanvas() {
        super();
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                // TODO: resize camera
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {
                // TODO: start rendering
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO: stop rendering
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;

        graphics.setColor(Color.GREEN);
        graphics.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 7));
        graphics.drawLine(40, 10, 10, 40);

        graphics.translate(20, 0);
        graphics.translate(-20, 0);
    }
}
