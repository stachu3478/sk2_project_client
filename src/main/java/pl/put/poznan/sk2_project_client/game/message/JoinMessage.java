package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class JoinMessage extends GameMessage {
    public void readBuffer(ByteBuffer buffer) {}

    public boolean isComplete() {
        return true;
    }

    public void receive() {
        if (!ignored)
            player.joinedLobby();
    }
}
