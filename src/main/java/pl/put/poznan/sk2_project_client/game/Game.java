package pl.put.poznan.sk2_project_client.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Game {
    private Config config;
    private Map map;
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
