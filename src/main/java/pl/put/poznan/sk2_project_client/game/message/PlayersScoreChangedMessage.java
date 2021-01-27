package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Unit;
import pl.put.poznan.sk2_project_client.net.MessageOut;
import sun.plugin2.message.Message;

import java.nio.ByteBuffer;

public class PlayersScoreChangedMessage extends GameMessage {
    private boolean complete = false;
    private byte ownerId;
    private int score;

    public PlayersScoreChangedMessage(GameMessage.ReceivedCallback callback) {
        super(callback);
    }

    @Override
    public void readBuffer(ByteBuffer buffer) {
        if (buffer.remaining() < 5) return;
        ownerId = buffer.get();
        score = buffer.getInt();
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public byte getOwnerId() {
        return ownerId;
    }

    public int getScore() {
        return score;
    }
}
