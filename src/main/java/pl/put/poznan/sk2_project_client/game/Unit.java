package pl.put.poznan.sk2_project_client.game;

import java.awt.*;

public class Unit {
    private byte percentHp;
    private int xPos;
    private int yPos;
    private byte ownerId;
    private int id;

    public Unit(byte ownerId) {
        this.ownerId = ownerId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOwnerId(byte ownerId) {
        this.ownerId = ownerId;
    }

    public Color getColor() {
        switch (ownerId % 18) {
            case 0: return Color.RED;
            case 1: return Color.GREEN;
            case 2: return Color.BLUE;
            case 3: return Color.YELLOW;
            case 4: return Color.MAGENTA;
            case 5: return Color.CYAN;
            case 6: return new Color(150, 150, 100);
            case 7: return new Color(150, 100, 150);
            case 8: return new Color(100, 150, 150);
            case 9: return new Color(150, 250, 0);
            case 10: return new Color(250, 0, 150);
            case 11: return new Color(0, 150, 250);
            case 12: return new Color(250, 150, 0);
            case 13: return new Color(150, 0, 250);   // PINK PINK PINK   PINK   PINK          PINK   PINK     PINK
            case 14: return new Color(0, 250, 150);   // PINK      PINK   PINK   PINK PINK     PINK   PINK   PINK
            case 15: return new Color(250, 150, 250); // PINK PINK PINK   PINK   PINK   PINK   PINK   PINK PINK
            case 16: return new Color(150, 250, 250); // PINK             PINK   PINK     PINK PINK   PINK   PINK
            case 17: return new Color(250, 250, 150); // PINK             PINK   PINK          PINK   PINK     PINK
        }
        return Color.BLACK;
    }
}
