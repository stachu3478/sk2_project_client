package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class GameLeftMessage extends GameMessage {
    public GameLeftMessage(ReceivedCallback callback) {
        super(callback);
    }

    @Override
    public void readBuffer(ByteBuffer buffer) { }

    @Override
    public boolean isComplete() {
        return true;
    }
}
