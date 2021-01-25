package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Map;
import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.game.Unit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class GameCanvas extends JPanel {
    private MapRenderer renderer;
    private TileCamera camera;
    private final static int RENDERING_PERIOD_MILLIS = 17; // 60 fps
    private final Timer renderingTimer = new Timer(RENDERING_PERIOD_MILLIS, (e) -> repaint());
    private Marker marker;
    private Point mousePositionOffset = new Point(0, 0);
    private ArrayList<Unit> selectedUnits = new ArrayList<>();
    private Map map;
    private Me me;
    private boolean scrolledToMyDroids = false;
    private Scroller scroller;

    public GameCanvas(Me me) {
        super();
        this.me = me;
        renderer = new MapRenderer(map = me.getGame().getMap());
        camera = new TileCamera(getWidth(), getHeight());
        scroller = new Scroller(camera);
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
                if (selectedUnits.isEmpty()) { // select units
                    for (Unit unit : me.getUnits()) {
                        if (marker.checkUnitSelection(unit)) {
                            selectedUnits.add(unit);
                        }
                    }
                } else { // move or attack units
                    try {
                        Point pos = camera.toTilePosition(new Point(e.getX(), e.getY()));
                        if (map.isDanger(pos, me)) {
                            me.attackUnits(selectedUnits, map.getUnit(pos));
                        } else {
                            me.moveUnits(selectedUnits, pos);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        try {
                            me.disconnect();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                    for (Unit unit : selectedUnits) unit.setSelected(false);
                    selectedUnits.clear();
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Character.toLowerCase(e.getKeyChar()) == 'a') scroller.setX(-1);
                if (Character.toLowerCase(e.getKeyChar()) == 'd') scroller.setX(1);
                if (Character.toLowerCase(e.getKeyChar()) == 'w') scroller.setY(-1);
                if (Character.toLowerCase(e.getKeyChar()) == 's') scroller.setY(1);
                if (Character.toLowerCase(e.getKeyChar()) == ' ') {
                    for (Unit unit : selectedUnits) unit.setSelected(false);
                    selectedUnits.clear();
                }
                scroller.process(getRelativeMousePosition());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (Character.toLowerCase(e.getKeyChar()) == 'a') scroller.setX(0);
                if (Character.toLowerCase(e.getKeyChar()) == 'd') scroller.setX(0);
                if (Character.toLowerCase(e.getKeyChar()) == 'w') scroller.setY(0);
                if (Character.toLowerCase(e.getKeyChar()) == 's') scroller.setY(0);
                scroller.process(getRelativeMousePosition());
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        scroller.process(getRelativeMousePosition());
        Graphics2D graphics = (Graphics2D) g;
        camera.setSize(getSize().width, getSize().height); // FIXME: resized event does not work
        if (!scrolledToMyDroids && getSize().width > 0) {
            Unit myUnit = me.getUnits().iterator().next();
            camera.setCenter(myUnit.getXPos(), myUnit.getYPos());
            scrolledToMyDroids = true;
        }
        renderer.render(graphics);
        if (marker.isInProgress()) { // zaznaczamy zaznaczanie zaznaczenia
            graphics.setStroke(new BasicStroke(3));
            graphics.setColor(new Color(64, 250, 64, 40));
            Point mousePosition = MouseInfo.getPointerInfo().getLocation();
            Rectangle r = camera.getMarkRectangle(mousePosition.x - mousePositionOffset.x, mousePosition.y - mousePositionOffset.y);
            graphics.drawRect(r.x, r.y, r.width, r.height);
            graphics.fillRect(r.x, r.y, r.width, r.height);
        } else if (!selectedUnits.isEmpty()) { // celowniczek ;d
            Point m = getRelativeMousePosition();
            Point tilePosition = camera.toTilePosition(m);
            if (map.isDanger(tilePosition, me)) {
                graphics.setColor(new Color(250, 0, 0, 200));
            } else graphics.setColor(new Color(200, 250, 250, 200));
            graphics.fillRect(m.x - 16, m.y - 2, 8, 4);
            graphics.fillRect(m.x + 8, m.y - 2, 8, 4);
            graphics.fillRect(m.x - 2, m.y - 16, 4, 8);
            graphics.fillRect(m.x - 2, m.y + 8, 4, 8);
        }
    }

    private Point getRelativeMousePosition() {
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        return new Point(mousePosition.x - mousePositionOffset.x, mousePosition.y - mousePositionOffset.y);
    }
}
