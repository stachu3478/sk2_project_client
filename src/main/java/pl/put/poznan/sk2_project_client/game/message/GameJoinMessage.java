package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class GameJoinMessage extends GameMessage {
    private boolean complete = false;

    public GameJoinMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 4) return;
        buffer.getInt(); // TODO: read units
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }
}
