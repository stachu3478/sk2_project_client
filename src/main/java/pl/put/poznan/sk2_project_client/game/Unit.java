package pl.put.poznan.sk2_project_client.game;

import java.awt.*;

public class Unit {
    private byte percentHp;
    private int xPos;
    private int yPos;
    private byte ownerId;
    private int id;
    private boolean selected;
    private int attackedFrames = 0;
    private int attackingFrames = 0;

    public Unit(byte ownerId) {
        this.ownerId = ownerId;
        this.percentHp = 100;
    }

    public void attack(Unit unit, byte newPercentHp) {
        if (unit != null) unit.setPercentHp(newPercentHp);
        attackingFrames = 10;
    }

    public byte getPercentHp() {
        return percentHp;
    }

    public void setPercentHp(byte percentHp) {
        this.percentHp = percentHp; attackedFrames = 30;
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

    public byte getOwnerId() {
        return ownerId;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean setSelected(boolean selected) {
        return this.selected = selected;
    }

    public int getAttackedFrames() {
        return attackedFrames--;
    }

    public int getAttackingFrames() {
        return attackingFrames--;
    }
}
