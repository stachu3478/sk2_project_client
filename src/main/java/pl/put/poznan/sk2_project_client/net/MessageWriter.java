package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MessageWriter {
    private final SocketChannel channel;
    private final ByteBuffer buffer;

    public MessageWriter(SocketChannel channel) {
        this.channel = channel;
        this.buffer = ByteBuffer.allocate(128);
    }

    public void emit(MessageOut message) throws IOException {
        ByteBuffer buff = message.serialize();
        buffer.put(buff);
        buffer.flip();
        writeMessages();
        buffer.clear();
    }

    public void writeMessages() throws IOException {
        int written = channel.write(buffer);
        // System.out.println(written + " bytes written");
        if (buffer.remaining() == 0) buffer.clear();
        else {
            System.out.println("Warning: Yielding data output of " + buffer.remaining() + " bytes. This is not handled. The app may crash.");
        }
    }
}
