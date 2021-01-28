package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client {
    private SocketChannel channel;
    private Selector selector;
    private SelectionKey selectionKey;
    private MessageIdentifier messageIdentifier;
    private MessageWriter messageWriter;
    private ConnectionCallback connectionCallback;
    private ClientDisconnectionCallback disconnectionCallback;
    private int connectionAttempts;
    private InetSocketAddress addr;

    public void start(String address, int port) {
        addr = new InetSocketAddress(address, port);
        connect();
    }

    private void connect() {
        connectionAttempts = 1; // Yields so long so changing to 1
        while (connectionAttempts > 0 && (this.channel == null || !this.channel.isConnected())) {
            try {
                this.channel = SocketChannel.open(addr);
                this.selector = Selector.open();

                this.channel.configureBlocking(false);
                this.selectionKey = channel.register(selector, SelectionKey.OP_READ);
                if (connectionCallback != null) connectionCallback.call();
                if (messageIdentifier != null) this.messageIdentifier.setChannel(channel);
                this.messageWriter = new MessageWriter(channel);
            } catch (IOException e) {
                e.printStackTrace();
                connectionAttempts--;
            }
        }
    }

    public void select() throws IOException {
        if (selector != null && channel != null) {
            selector.select();
            if (!this.messageIdentifier.readMessages()) channel.close();
        }
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void disconnect() throws IOException {
        if (channel != null) channel.close();
        if (disconnectionCallback != null)
            disconnectionCallback.call();
    }

    public boolean isConnected() {
        if (channel == null) return false;
        return channel.isConnected();
    }

    public boolean isConnecting() {
        return (channel == null || !channel.isConnected()) && connectionAttempts > 0;
    }

    public void setMessageIdentifier(MessageIdentifier messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
        messageIdentifier.setChannel(channel);
    }

    public void onDisconnection(ClientDisconnectionCallback disconnectionCallback) {
        this.disconnectionCallback = disconnectionCallback;
    }

    public void emit(MessageOut message) {
        try {
            this.messageWriter.emit(message);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (this.channel != null) this.channel.close();
            } catch (IOException err) {
                e.printStackTrace();
            }
        }
    }

    public void flush() throws IOException {
        messageWriter.writeMessages();
        messageIdentifier.readMessages();
    }

    public interface ConnectionCallback {
        void call();
    }
}
