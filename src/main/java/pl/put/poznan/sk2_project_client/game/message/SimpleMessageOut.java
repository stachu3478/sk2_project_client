package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.net.MessageOut;

import java.nio.ByteBuffer;

public abstract class SimpleMessageOut extends MessageOut {
    protected abstract byte getType();

    public ByteBuffer serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put(getType());
        buffer.flip();
        return buffer;
    }
}
