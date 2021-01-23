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
            Color color = map.getColor(tileX, tileY);
            if (color == null) {
                graphics2D.setColor(new Color(230 + 20 * (tileX % 2), 170 + 20 * (tileY % 2), 170));
            } else {
                graphics2D.setColor(color);
            }
            graphics2D.fillRect(screenX, screenY, T_SIZE, T_SIZE);
            if (map.getStroke(tileX, tileY)) {
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawRect(screenX, screenY, T_SIZE, T_SIZE);
            }
        }
    }
}
