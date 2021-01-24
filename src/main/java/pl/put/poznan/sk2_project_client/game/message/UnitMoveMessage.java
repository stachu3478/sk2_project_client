package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Unit;
import pl.put.poznan.sk2_project_client.net.MessageOut;

import java.nio.ByteBuffer;

public class UnitMoveMessage extends MessageOut {
    private final Unit[] units;
    private int targetX;
    private int targetY;

    public UnitMoveMessage(Unit[] units, int targetX, int targetY) {
        this.units = units;
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public ByteBuffer serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(13 + units.length * 4);
        buffer.put((byte)2);
        buffer.putInt(targetX);
        buffer.putInt(targetY);
        buffer.putInt(units.length);
        for (Unit unit : units) {
            buffer.putInt(unit.getId());
        }
        buffer.flip();
        return buffer;
    }
}
