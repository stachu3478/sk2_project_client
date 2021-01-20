package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Config;

import java.nio.ByteBuffer;

public class JoinMessage extends GameMessage {
    private boolean complete = false;
    private byte ownerId;
    private final Config config = new Config();

    public JoinMessage(ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 12) return;
        config.setMinPlayersToStart(buffer.get());
        ownerId = buffer.get();
        config.setMaxPlayersCount(buffer.get());
        config.setCountdownSeconds(buffer.get());
        config.setMapWidth(buffer.getInt());
        config.setMapHeight(buffer.getInt());
        complete = true;
    }

    public Config getConfig() {
        return config;
    }

    public byte getOwnerId() {
        return ownerId;
    }

    public boolean isComplete() {
        return complete;
    }
}
