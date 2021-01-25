package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Unit;

import java.nio.ByteBuffer;

public class UnitSpawnedMessage extends GameMessage {
    private boolean complete = false;
    private Unit unit;

    public UnitSpawnedMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 14) return;
        unit = new Unit((byte)0);
        unit.setId(buffer.getInt());
        unit.setPercentHp(buffer.get());
        unit.setOwnerId(buffer.get());
        unit.setPos(buffer.getInt(), buffer.getInt());
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public Unit getUnit() {
        return unit;
    }
}
