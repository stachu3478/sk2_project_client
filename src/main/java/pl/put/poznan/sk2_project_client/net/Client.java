package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    private final SocketChannel channel;
    private MessageIdentifier messageIdentifier;
    private final MessageWriter messageWriter;
    private ClientDisconnectionCallback disconnectionCallback;

    public Client() throws IOException {
        this.channel = SocketChannel.open(new InetSocketAddress("192.168.0.104", 34780));
        this.messageWriter = new MessageWriter(channel);
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void disconnect() throws IOException {
        channel.close();
        if (disconnectionCallback != null)
            disconnectionCallback.call();
    }

    public void setMessageIdentifier(MessageIdentifier messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
        this.messageIdentifier.setChannel(channel);
    }

    public void onDisconnection(ClientDisconnectionCallback disconnectionCallback) {
        this.disconnectionCallback = disconnectionCallback;
    }

    public void emit(MessageOut message) throws IOException {
        this.messageWriter.emit(message);
    }

    public void flush() throws IOException {
        messageWriter.writeMessages();
        messageIdentifier.readMessages();
    }
}
