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

    public void iterateVisibleTiles(TileCallback callback) {
        int tileX = scrollX / TILE_SIZE;
        for (int screenX = scrollX % TILE_SIZE - TILE_SIZE; screenX < screenWidth; screenX += TILE_SIZE, tileX++ ) {
            int tileY = scrollY / TILE_SIZE;
            for (int screenY = scrollY % TILE_SIZE - TILE_SIZE; screenX < screenHeight; screenY += TILE_SIZE, tileY++) {
                callback.call(tileX, tileY, screenX, screenY);
            }
        }
    }

    private void fitIntoMap() {
        if (scrollX + screenWidth > TILE_SIZE * mapWidth) scrollX = TILE_SIZE * mapWidth - screenWidth;
        if (scrollX < 0) scrollX = 0;
        if (scrollY + screenHeight > TILE_SIZE * mapHeight) scrollY = TILE_SIZE * mapHeight - screenHeight;
        if (scrollY < 0) scrollY = 0;
    }

    public interface TileCallback {
        void call(int tileX, int tileY, int screenX, int screenY);
    }
}
