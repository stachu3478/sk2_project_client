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

    public void readMessages() throws IOException {
        channel.read(buffer);
        createMessages();
        if (buffer.remaining() == 0) buffer.clear();
    }

    protected abstract Message createMessage(ByteBuffer buffer);

    private void createMessages() {
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
