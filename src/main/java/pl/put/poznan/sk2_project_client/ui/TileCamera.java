package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Tile;

public class TileCamera {
    private final static int TILE_SIZE = 32; // tile size in pixels
    private int scrollX; // upper left corner position
    private int scrollY;
    private int screenWidth;
    private int screenHeight;
    private int mapWidth;
    private int mapHeight;

    public TileCamera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void scrollBy(int x, int y) {
        this.scrollX += x;
        this.scrollY += y;
        fitIntoMap();
    }

    public void setCenter(int x, int y) {
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
        if (scrollX + screenWidth > TILE_SIZE * mapWidth - TILE_SIZE) scrollX = TILE_SIZE * mapWidth - screenWidth - TILE_SIZE;
        if (scrollX < 0) scrollX = 0;
        if (scrollY + screenHeight > TILE_SIZE * mapHeight - TILE_SIZE) scrollY = TILE_SIZE * mapHeight - screenHeight - TILE_SIZE;
        if (scrollY < 0) scrollY = 0;
    }

    public interface TileCallback {
        int T_SIZE = TILE_SIZE;
        void call(int tileX, int tileY, int screenX, int screenY);
    }
}
