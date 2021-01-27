package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class PlayerLeftMessage extends GameMessage {
    private boolean complete = false;
    private byte ownerId;

    public PlayerLeftMessage(ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 1) return;
        ownerId = buffer.get();
        complete = true;
    }

    public byte getOwnerId() {
        return ownerId;
    }

    public boolean isComplete() {
        return complete;
    }
}
