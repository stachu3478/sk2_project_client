package pl.put.poznan.sk2_project_client.game;

import java.util.Collection;
import java.util.Hashtable;

public class Game {
    private final Config config;
    private final Map map;
    private final Hashtable<Byte, Player> players = new Hashtable<>();
    private final Hashtable<Integer, Unit> units = new Hashtable<>();

    public Game(Config config) {
        this.config = config;
        map = new Map(config.getMapWidth(), config.getMapHeight());
    }

    public boolean isFinishedFor(Player player) {
        if (player.getUnits().isEmpty()) return true;
        for (Player p : players.values()) {
            if (!p.getUnits().isEmpty()) return isWinner(p);
        }
        return true;
    }

    public boolean isWinner(Player player) {
        if (player.getUnits().isEmpty()) return false;
        for (Player p : players.values()) {
            if (!p.getUnits().isEmpty() && p != player) return false;
        }
        return true;
    }

    public void addPlayer(Player player) {
        players.put(player.getOwnerId(), player);
    }

    public void removePlayer(byte ownerId) { players.remove(ownerId); }

    public Player findPlayer(byte ownerId){ return players.get(ownerId); }

    public void addUnit(Unit unit) {
        players.get(unit.getOwnerId()).addUnit(unit);
        units.put(unit.getId(), unit);
        map.setUnit(unit);
    }

    public Unit findUnit(int id) {
        return units.get(id);
    }

    public void removeUnit(int id) {
        Unit unit = units.remove(id);
        players.get(unit.getOwnerId()).removeUnit(id);
        map.clearUnit(unit.getXPos(), unit.getYPos());
    }

    public Collection<Player> getPlayers() {
        return players.values();
    }

    public Hashtable<Byte, Player> getPlayersMap() {
        return players;
    }

    public Config getConfig() {
        return config;
    }

    public Map getMap() {
        return map;
    }
}
