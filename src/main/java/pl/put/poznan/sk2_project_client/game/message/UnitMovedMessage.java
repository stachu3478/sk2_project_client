package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Unit;

import java.nio.ByteBuffer;

public class UnitMovedMessage extends GameMessage {
    private boolean complete = false;
    private int toX;
    private int toY;
    private int unitId;

    public UnitMovedMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 12) return;
        unitId = buffer.getInt();
        toX = buffer.getInt();
        toY = buffer.getInt();
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    public int getUnitId() {
        return unitId;
    }
}

