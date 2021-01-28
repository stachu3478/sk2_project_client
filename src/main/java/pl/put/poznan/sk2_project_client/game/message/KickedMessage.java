package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Config;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class KickedMessage extends GameMessage {
    private boolean complete = false;
    private boolean lengthRead = false;
    private byte reasonLength;
    private String reason;

    public KickedMessage(ReceivedCallback callback) {
        super(callback);
    }

    public void readBuffer(ByteBuffer buffer) {
        if (!lengthRead) {
            if (buffer.remaining() == 0) return;
            reasonLength = buffer.get();
            lengthRead = true;
        }
        if (buffer.remaining() < reasonLength) return;
        byte[] bytes = new byte[reasonLength];
        buffer.get(bytes);
        reason = new String(bytes, StandardCharsets.US_ASCII);
        complete = true;
    }

    public String getReason() {
        return reason;
    }

    public boolean isComplete() {
        return complete;
    }
}
