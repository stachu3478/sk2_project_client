package pl.put.poznan.sk2_project_client.game;

import java.awt.*;

public class Map {
    private final int width;
    private final int height;
    private final Tile[][] tiles;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public void setUnit(Unit unit) {
        setUnit(unit, unit.getXPos(), unit.getYPos());
    }

    public void setUnit(Unit unit, int x, int y) {
        tiles[x][y] = new Tile(unit);
        if (unit != null) unit.setPos(x, y);
    }

    public void moveUnit(Unit unit, int x, int y) {
        Tile tile = tiles[unit.getXPos()][unit.getYPos()];
        tile.setUnit(null);
        setUnit(unit, x, y);
    }

    public void clearUnit(int x, int y) {
        Tile tile = tiles[x][y];
        if (tile != null) tile.setUnit(null);
    }

    public boolean isDanger(Point pos, Player me) {
        if (!isBound(pos)) return false;
        Unit unit = getUnit(pos);
        if (unit == null) return false;
        return unit.getOwnerId() != me.getOwnerId();
    }

    public Unit getUnit(Point pos) {
        Tile tile = tiles[pos.x][pos.y];
        if (tile == null) return null;
        return tile.getUnit();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private boolean isBound(Point pos) {
        return pos.x > 0 && pos.y > 0 && pos.x < width && pos.y < height;
    }
}
