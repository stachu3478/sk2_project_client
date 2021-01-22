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
        int read = 0;
        int bufferMultiplier = 1;
        do {
            buffer.flip();
            ByteBuffer buff = ByteBuffer.allocate((buffer.remaining() + 16 + read) * bufferMultiplier);
            buff.put(buffer);
            read = channel.read(buff);
            buff.flip();
            createMessages(buff);
            if (read == -1) {
                buffer.clear();
                return false; // EOF
            } else {
                System.out.println(read + " bytes read");
            }

            buffer = ByteBuffer.allocate(buff.remaining());
            buffer.put(buff);
            bufferMultiplier *= 2;
        } while (read > 0);


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
