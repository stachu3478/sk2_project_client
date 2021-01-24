package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Unit;

import java.awt.*;

public class Marker {
    private int markStartX;
    private int markStartY;
    private int markEndX;
    private int markEndY;
    private int tileSize;
    private boolean inProgress = false;

    public void setMarkStart(int x, int y) {
        inProgress = true;
        this.markStartX = x;
        this.markStartY = y;
    }

    public void setMarkEnd(int x, int y) {
        inProgress = false;
        this.markEndX = x;
        this.markEndY = y;
    }

    public boolean checkUnitSelection(Unit unit) {
        return unit.setSelected(  Math.min(markStartX, markEndX) < unit.getXPos() * tileSize
                        && Math.max(markStartX, markEndX) > unit.getXPos() * tileSize - tileSize
                        && Math.min(markStartY, markEndY) < unit.getYPos() * tileSize
                        && Math.max(markStartY, markEndY) > unit.getYPos() * tileSize - tileSize);
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public Rectangle getMarkRectangle() {
        return getMarkRectangle(markEndX, markEndY);
    }

    public Rectangle getMarkRectangle(int currentX, int currentY) {
        return new Rectangle(Math.min(markStartX, currentX), Math.min(markStartY, currentY),
                Math.abs(markStartX - currentX), Math.abs(markStartY - currentY));
    }

    public boolean isInProgress() {
        return inProgress;
    }
}
