package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MessageWriter {
    private final SocketChannel channel;
    private ByteBuffer buffer;

    public MessageWriter(SocketChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(0);
    }

    public void emit(MessageOut message) throws IOException {
        ByteBuffer buff = message.serialize();
        ByteBuffer newBuffer = ByteBuffer.allocate(buff.capacity() + buffer.remaining());
        newBuffer.put(buffer);
        newBuffer.put(buff);
        newBuffer.flip();
        buffer = newBuffer;
        writeMessages();
    }

    public void writeMessages() throws IOException {
        int written = channel.write(buffer);
        // System.out.println(written + " bytes written");
    }
}
