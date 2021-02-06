package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Unit;

import java.nio.ByteBuffer;

public class GameJoinMessage extends GameMessage {
    private boolean complete = false;
    private boolean lengthRead = false;
    private Unit[] units;

    public GameJoinMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (!lengthRead) {
            if (buffer.remaining() < 4) return;
            units = new Unit[buffer.getInt()];
            lengthRead = true;
        }
        if (buffer.remaining() < 14 * units.length) return;
        for (int i = 0; i < units.length; i++) {
            units[i] = new Unit((byte)0);
            units[i].setId(buffer.getInt());
            units[i].setPercentHp(buffer.get());
            units[i].setOwnerId(buffer.get());
            units[i].setPos(buffer.getInt(), buffer.getInt());
        }
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public Unit[] getUnits() {
        return units;
    }
}
