package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Player;
import pl.put.poznan.sk2_project_client.net.Message;

public abstract class GameMessage implements Message {
    Player player;
    boolean ignored = false;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void ignore() {
        this.ignored = true;
    }
}
