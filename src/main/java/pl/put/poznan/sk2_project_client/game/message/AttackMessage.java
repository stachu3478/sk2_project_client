package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Unit;
import pl.put.poznan.sk2_project_client.net.MessageOut;

import java.nio.ByteBuffer;

public class AttackMessage extends MessageOut {
    private final Unit[] units;
    private final Unit target;

    public AttackMessage(Unit[] units, Unit target) {
        this.units = units;
        this.target = target;
    }

    public ByteBuffer serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(9 + units.length * 4);
        buffer.put((byte) 3);
        buffer.putInt(target.getId());
        buffer.putInt(units.length);
        for (Unit unit : units) {
            buffer.putInt(unit.getId());
        }
        buffer.flip();
        return buffer;
    }
}