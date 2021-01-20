package pl.put.poznan.sk2_project_client.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Config config;
    private final List<Player> players = new ArrayList<>();

    public Game(Config config) {
        this.config = config;
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
}
