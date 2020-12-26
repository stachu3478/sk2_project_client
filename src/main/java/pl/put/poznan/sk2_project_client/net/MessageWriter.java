package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MessageWriter {
    private final SocketChannel channel;
    private final ByteBuffer buffer;

    public MessageWriter(SocketChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(8);
    }

    public void emit(MessageOut message) throws IOException {
        ByteBuffer buff = message.serialize();
        buffer.put(buff);
        writeMessages();
        if (buffer.remaining() > 0) System.out.println("Warning: Yielding data output");
        else buffer.clear();
    }

    public void writeMessages() throws IOException {
        channel.write(buffer);
        if (buffer.remaining() == 0) buffer.clear();
    }
}
