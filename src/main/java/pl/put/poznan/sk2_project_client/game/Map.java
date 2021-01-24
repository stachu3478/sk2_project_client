package pl.put.poznan.sk2_project_client.game;

import java.awt.*;

public class Map {
    private int width;
    private int height;
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

    public Color getColor(int x, int y) {
        Tile tile = tiles[x][y];
        if (tile == null) return null;
        Unit unit = tile.getUnit();
        if (unit == null) return null;
        return unit.getColor();
    }

    public boolean getStroke(int x, int y) {
        Tile tile = tiles[x][y];
        if (tile == null) return false;
        Unit unit = tile.getUnit();
        if (unit == null) return false;
        return unit.isSelected();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
