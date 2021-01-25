package pl.put.poznan.sk2_project_client.game;

import java.util.Collection;
import java.util.HashMap;

public class Game {
    private final Config config;
    private final Map map;
    private final HashMap<Byte, Player> players = new HashMap<>();
    private final HashMap<Integer, Unit> units = new HashMap<>();

    public Game(Config config) {
        this.config = config;
        map = new Map(config.getMapWidth(), config.getMapHeight());
    }

    public void addPlayer(Player player) {
        players.put(player.getOwnerId(), player);
    }

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

    public Config getConfig() {
        return config;
    }

    public Map getMap() {
        return map;
    }
}
