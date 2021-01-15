package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class JoinMessage extends GameMessage {
    private int bytesRead = 0;
    private byte minPlayersToStart;
    private byte ownerId;
    private final ReceivedCallback callback;

    public JoinMessage(ReceivedCallback callback) {
        this.callback = callback;
    }

    public void readBuffer(ByteBuffer buffer) {
        if (bytesRead == 0 && buffer.hasRemaining()) {
            bytesRead++;
            minPlayersToStart = buffer.get();
        }
        if (bytesRead == 1 && buffer.hasRemaining()) {
            bytesRead++;
            ownerId = buffer.get();
        }
    }

    public byte getMinPlayersToStart() {
        return minPlayersToStart;
    }

    public byte getOwnerId() {
        return ownerId;
    }

    public boolean isComplete() {
        return bytesRead >= 2;
    }

    public void receive() {
        if (!ignored)
            callback.call(this);
    }

    public interface ReceivedCallback {
        void call(JoinMessage m);
    }
}
