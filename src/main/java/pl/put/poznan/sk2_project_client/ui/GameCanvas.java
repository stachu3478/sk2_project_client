package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;

public class GameCanvas extends JPanel {
    private MapRenderer renderer;
    private TileCamera camera;
    private int scrollingX = 0;
    private int scrollingY = 0;
    private Instant lastScrollTime = Instant.now();
    private final static int SCROLLING_PIXELS_PER_SECOND = 200;
    private final static int RENDERING_PERIOD_MILLIS = 17; // 60 fps
    private final Timer renderingTimer = new Timer(RENDERING_PERIOD_MILLIS, (e) -> repaint());

    public GameCanvas(Map map) {
        super();
        renderer = new MapRenderer(map);
        camera = new TileCamera(getWidth(), getHeight());
        renderer.setCamera(camera);
        renderingTimer.start(); // TODO: stop timer when left the game (ComponentListener not working)
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Character.toLowerCase(e.getKeyChar()) == 'a') scrollingX = -1;
                if (Character.toLowerCase(e.getKeyChar()) == 'd') scrollingX = 1;
                if (Character.toLowerCase(e.getKeyChar()) == 'w') scrollingY = -1;
                if (Character.toLowerCase(e.getKeyChar()) == 's') scrollingY = 1;
                processScrolling();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (Character.toLowerCase(e.getKeyChar()) == 'a') scrollingX = 0;
                if (Character.toLowerCase(e.getKeyChar()) == 'd') scrollingX = 0;
                if (Character.toLowerCase(e.getKeyChar()) == 'w') scrollingY = 0;
                if (Character.toLowerCase(e.getKeyChar()) == 's') scrollingY = 0;
                processScrolling();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        processScrolling();
        Graphics2D graphics = (Graphics2D) g;
        camera.setSize(getSize().width, getSize().height); // FIXME: resized event does not work
        renderer.render(graphics);
    }

    private void processScrolling() {
        Instant now = Instant.now();
        int millisBetweenLastScroll = (int)Duration.between(lastScrollTime, now).toMillis();
        int pixelsToScroll = millisBetweenLastScroll * SCROLLING_PIXELS_PER_SECOND / 1000;
        camera.scrollBy(pixelsToScroll * scrollingX, pixelsToScroll * scrollingY);
        lastScrollTime = now;
    }
}
