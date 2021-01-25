package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class UnitDestroyedMessage extends GameMessage {
    private boolean complete = false;
    private int destroyedUnitId;

    public UnitDestroyedMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 4) return;
        destroyedUnitId = buffer.getInt();
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getDestroyedUnitId() {
        return destroyedUnitId;
    }
}
