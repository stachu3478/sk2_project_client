package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;

public class UnitAttackedMessage extends GameMessage {
    private boolean complete = false;
    private int attackerUnitId;
    private int attackedUnitId;
    private byte attackedUnitHitPoints; // percent hit points

    public UnitAttackedMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 9) return;
        attackerUnitId = buffer.getInt();
        attackedUnitId = buffer.getInt();
        attackedUnitHitPoints = buffer.get();
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public int getAttackerUnitId() {
        return attackerUnitId;
    }

    public int getAttackedUnitId() {
        return attackedUnitId;
    }

    public byte getAttackedUnitHitPoints() {
        return attackedUnitHitPoints;
    }
}
