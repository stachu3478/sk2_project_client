package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public abstract class MessageIdentifier {
    private SocketChannel channel;
    private Message lastMessage;
    ByteBuffer buffer;

    public MessageIdentifier() {
        buffer = ByteBuffer.allocate(8);
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public boolean readMessages() throws IOException {
        buffer.flip();
        ByteBuffer buff = ByteBuffer.allocate(16);
        buff.put(buffer);
        int read = channel.read(buff);
        buff.flip();
        if (read == -1) return false; // EOF
        createMessages(buff);

        buffer = ByteBuffer.allocate(8);
        buffer.put(buff);

        return true;
    }

    protected abstract Message createMessage(ByteBuffer buffer);

    private void createMessages(ByteBuffer buffer) {
        if (lastMessage == null) {
            lastMessage = createMessage(buffer);
        }
        while (lastMessage != null) {
            lastMessage.readBuffer(buffer);
            if (lastMessage.isComplete()) {
                lastMessage.receive();
                lastMessage = createMessage(buffer);
            } else break;
        }
    }
}
