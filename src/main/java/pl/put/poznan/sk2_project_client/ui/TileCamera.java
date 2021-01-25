package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.logic.Marker;

import java.awt.*;

public class TileCamera {
    private final static int TILE_SIZE = 32; // tile size in pixels
    private Marker marker = new Marker();
    private int scrollX; // upper left corner position
    private int scrollY;
    private int screenWidth;
    private int screenHeight;
    private int mapWidth;
    private int mapHeight;

    public TileCamera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        marker.setTileSize(TILE_SIZE);
    }

    public void scrollBy(int x, int y) {
        this.scrollX += x;
        this.scrollY += y;
        fitIntoMap();
    }

    public void setCenter(int tileX, int tileY) {
        int x = tileX * TILE_SIZE - TILE_SIZE / 2;
        int y = tileY * TILE_SIZE - TILE_SIZE / 2;
        this.scrollX = x - screenWidth / 2;
        this.scrollY = y - screenHeight / 2;
        fitIntoMap();
    }

    public void setSize(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        fitIntoMap();
    }

    public void setMapSize(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public void iterateVisibleTiles(TileCallback callback) {
        int tileX = scrollX / TILE_SIZE;
        for (int screenX = -scrollX % TILE_SIZE - TILE_SIZE; screenX < screenWidth && tileX < mapWidth; screenX += TILE_SIZE, tileX++ ) {
            int tileY = scrollY / TILE_SIZE;
            for (int screenY = -scrollY % TILE_SIZE - TILE_SIZE; screenY < screenHeight && tileY < mapHeight; screenY += TILE_SIZE, tileY++) {
                callback.call(tileX, tileY, screenX, screenY);
            }
        }
    }

    private void fitIntoMap() {
        if (screenWidth == 0 || screenHeight == 0) return;
        if (scrollX + screenWidth > TILE_SIZE * mapWidth - TILE_SIZE) scrollX = TILE_SIZE * mapWidth - screenWidth - TILE_SIZE;
        if (scrollX < 0) scrollX = 0;
        if (scrollY + screenHeight > TILE_SIZE * mapHeight - TILE_SIZE) scrollY = TILE_SIZE * mapHeight - screenHeight - TILE_SIZE;
        if (scrollY < 0) scrollY = 0;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public interface TileCallback {
        int T_SIZE = TILE_SIZE;
        void call(int tileX, int tileY, int screenX, int screenY);
    }

    public Marker getMarker() {
        return marker;
    }

    public Point toTilePosition(Point screenPosition) {
        return new Point((screenPosition.x + scrollX) / TILE_SIZE + 1, (screenPosition.y + scrollY) / TILE_SIZE + 1);
    }

    public void setMarkPosition(int x, int y, boolean initial) {
        if (initial) marker.setMarkStart(x + scrollX, y + scrollY);
        else marker.setMarkEnd(x + scrollX, y + scrollY);
    }

    public Rectangle getMarkRectangle() {
        Rectangle r = marker.getMarkRectangle();
        return new Rectangle(r.x - scrollX, r.y - scrollY, r.width, r.height);
    }

    public Rectangle getMarkRectangle(int endX, int endY) {
        Rectangle r = marker.getMarkRectangle(endX + scrollX, endY + scrollY);
        return new Rectangle(r.x - scrollX, r.y - scrollY, r.width, r.height);
    }
}
