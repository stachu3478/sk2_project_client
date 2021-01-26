package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.ui.rotary_menu.MenuItem;
import pl.put.poznan.sk2_project_client.ui.rotary_menu.RotaryMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ConnectedPanel extends SwappablePanel {
    private final JLabel nicknameLabel;
    private final RotaryMenu menu = new RotaryMenu();

    private final static int RENDERING_PERIOD_MILLIS = 17; // 60 fps
    private final Timer renderingTimer = new Timer(RENDERING_PERIOD_MILLIS, (e) -> panel.repaint());

    public ConnectedPanel(Me me) {
        super();
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentY(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        nicknameLabel = new JLabel("Your nickname (or just press play):");
        nicknameLabel.setForeground(Color.LIGHT_GRAY);
        nicknameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField nicknameField = new JTextField();
        Color transparent = new Color(0, 0, 0, 0);
        JPanel nicknameFieldContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 100));
        nicknameFieldContainer.setBackground(transparent);
        nicknameField.setBackground(Color.BLACK);
        nicknameField.setForeground(Color.WHITE);
        nicknameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        nicknameField.setPreferredSize(new Dimension(100, 20));
        nicknameFieldContainer.add(nicknameField);
        panel.add(nicknameLabel);
        panel.add(nicknameFieldContainer);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                super.mouseClicked(evt);
                MenuItem item = menu.getItemAtPosition(new Point(evt.getX(), evt.getY()), 34);
                if (item == null) return;
                if (item.getName().equals("Play")) {
                    try {
                        me.play(nicknameField.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (item.getName().equals("Exit")) {
                    try {
                        me.disconnect();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.exit(0);
                }
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                menu.selectItemAtPosition(new Point(e.getX(), e.getY()), 34);
            }
        });

        menu.addItem(new MenuItem("Play", "play"));
        menu.addItem(new MenuItem("Exit", "exit"));
        menu.setShown(true);
    }

    @Override
    public void draw(Graphics2D g) {
        menu.setPosition(new Point(panel.getWidth() / 2, panel.getHeight() / 2));
        menu.render(g);
    }

    @Override
    public void mounted() {
        renderingTimer.start();
    }

    @Override
    public void dismounted() {
        renderingTimer.stop();
    }
}
