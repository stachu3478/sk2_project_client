package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Map;
import pl.put.poznan.sk2_project_client.game.Unit;

import java.awt.*;
import java.awt.geom.Dimension2D;

public class MapRenderer {
    private final Map map;
    private TileCamera camera;
    private final DrawTileCallback tileCallback = new DrawTileCallback();
    private Dimension markStart;
    private Dimension markEnd;

    public MapRenderer(Map map) {
        this.map = map;
    }

    public void setCamera(TileCamera camera) {
        this.camera = camera;
        camera.setMapSize(map.getWidth(), map.getHeight());
    }

    public void render(Graphics2D graphics2D) {
        tileCallback.setGraphics2D(graphics2D);
        camera.iterateVisibleTiles(tileCallback);
    }

    private class DrawTileCallback implements TileCamera.TileCallback {
        private Graphics2D graphics2D;

        public void setGraphics2D(Graphics2D graphics2D) {
            this.graphics2D = graphics2D;
        }

        @Override
        public void call(int tileX, int tileY, int screenX, int screenY) {
            Unit unit = map.getUnit(new Point(tileX, tileY));
            if (unit == null) {
                graphics2D.setColor(new Color(230 + 20 * (tileX % 2), 170 + 20 * (tileY % 2), 170));
            } else {
                graphics2D.setColor(unit.getColor());
            }
            graphics2D.fillRect(screenX, screenY, T_SIZE, T_SIZE);
            if (unit != null) {
                if (unit.isSelected()) {
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawRect(screenX, screenY, T_SIZE, T_SIZE);
                }
                int attackFrames = unit.getAttackingFrames();
                if (attackFrames > 0) {
                    int v = Math.min(attackFrames * 20, 255);
                    int y = Math.min(attackFrames * 80, 255);
                    graphics2D.setColor(new Color(y, y, y, v));
                    graphics2D.fillArc(screenX, screenY, T_SIZE, T_SIZE, 0, 360);
                }
                int attackedFrames = unit.getAttackedFrames();
                if (attackedFrames > 0) {
                    int v = Math.min(attackedFrames * 5, 255);
                    int y = Math.min(attackedFrames * 20, 255);
                    graphics2D.setColor(new Color(y, y, y, v));
                    graphics2D.fillRect(screenX, screenY, T_SIZE, T_SIZE);
                }
            }
        }
    }
}
