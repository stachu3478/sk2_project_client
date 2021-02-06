package pl.put.poznan.sk2_project_client.game;

import java.util.Collection;
import java.util.Hashtable;

public class Player {
    protected String nickname;
    protected byte ownerId;
    protected Hashtable<Integer, Unit> units = new Hashtable<>();

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    protected int score;

    public Player(String nickname, byte ownerId) {
        this.nickname = nickname;
        this.ownerId = ownerId;
    }

    public byte getOwnerId() {
        return ownerId;
    }
    public String getNickname() {
        if (nickname.length() > 0) return nickname;
        return "An unnamed manager";
    }

    public void addUnit(Unit unit) {
        units.put(unit.getId(), unit);
    }

    public void removeUnit(int id) {
        units.remove(id);
    }

    public Unit getUnit(int id) {
        return units.get(id);
    }

    public Collection<Unit> getUnits() {
        return units.values();
    }

    public void clearUnits() { units.clear(); }
}
