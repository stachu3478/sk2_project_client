package pl.put.poznan.sk2_project_client.game.message;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class PlayerJoinedMessage extends GameMessage {
    private boolean complete = false;
    private boolean lengthRead = false;
    private String nickname;
    private byte length;
    private byte ownerId;

    public PlayerJoinedMessage(ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (!lengthRead) {
            if (buffer.remaining() < 2) return;
            ownerId = buffer.get();
            length = buffer.get();
            lengthRead = true;
        }
        if (buffer.remaining() < length) return;
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        nickname = new String(bytes, StandardCharsets.US_ASCII);
        complete = true;
    }

    public String getNickname() { return nickname; }

    public byte getOwnerId() {
        return ownerId;
    }

    public boolean isComplete() {
        return complete;
    }
}
