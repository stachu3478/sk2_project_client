package pl.put.poznan.sk2_project_client.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Config config;
    private Map map;
    private final List<Player> players = new ArrayList<>();

    public Game(Config config) {
        this.config = config;
        map = new Map(config.getMapWidth(), config.getMapHeight());
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Config getConfig() {
        return config;
    }

    public Map getMap() {
        return map;
    }
}
