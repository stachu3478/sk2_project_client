package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Map;
import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.game.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;

public class GameCanvas extends JPanel {
    private MapRenderer renderer;
    private TileCamera camera;
    private int scrollingX = 0;
    private int scrollingY = 0;
    private Instant lastScrollTime = Instant.now();
    private final static int SCROLLING_PIXELS_PER_SECOND = 200;
    private final static int RENDERING_PERIOD_MILLIS = 17; // 60 fps
    private final Timer renderingTimer = new Timer(RENDERING_PERIOD_MILLIS, (e) -> repaint());
    private Marker marker;
    private Point mousePositionOffset;

    public GameCanvas(Me me) {
        super();
        renderer = new MapRenderer(me.getGame().getMap());
        camera = new TileCamera(getWidth(), getHeight());
        Unit myUnit = me.getUnits().iterator().next();
        camera.setCenter(myUnit.getXPos(), myUnit.getYPos());
        marker = camera.getMarker();
        renderer.setCamera(camera);
        renderingTimer.start(); // TODO: stop timer when left the game (ComponentListener not working)
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point absoluteMousePosition = MouseInfo.getPointerInfo().getLocation();
                mousePositionOffset = new Point(absoluteMousePosition.x - e.getX(),absoluteMousePosition.y - e.getY());
                camera.setMarkPosition(e.getX(), e.getY(), true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                camera.setMarkPosition(e.getX(), e.getY(), false);
                for (Iterator<Unit> it = me.getUnits().iterator(); it.hasNext(); ) {
                    Unit unit = it.next();
                    marker.checkUnitSelection(unit);
                }
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
        if (marker.isInProgress()) {
            graphics.setStroke(new BasicStroke(3));
            graphics.setColor(new Color(64, 250, 64, 40));
            Point mousePosition = MouseInfo.getPointerInfo().getLocation();
            Rectangle r = camera.getMarkRectangle(mousePosition.x - mousePositionOffset.x, mousePosition.y - mousePositionOffset.y);
            graphics.drawRect(r.x, r.y, r.width, r.height);
            graphics.fillRect(r.x, r.y, r.width, r.height);
        }
    }

    private void processScrolling() {
        Instant now = Instant.now();
        int millisBetweenLastScroll = (int)Duration.between(lastScrollTime, now).toMillis();
        int pixelsToScroll = millisBetweenLastScroll * SCROLLING_PIXELS_PER_SECOND / 1000;
        camera.scrollBy(pixelsToScroll * scrollingX, pixelsToScroll * scrollingY);
        lastScrollTime = now;
    }
}
