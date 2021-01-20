package pl.put.poznan.sk2_project_client.game;

import java.awt.*;

public class Unit {
    private byte percentHp;
    private int xPos;
    private int yPos;
    private final Color color;

    public Unit(Color color) {
        this.color = color;
        this.percentHp = 100;
    }

    public byte getPercentHp() {
        return percentHp;
    }

    public void setPercentHp(byte percentHp) {
        this.percentHp = percentHp;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public Color getColor() {
        return color;
    }
}
