package pl.put.poznan.sk2_project_client.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;

public class Client {
    private SocketChannel channel;
    private Selector selector;
    private SelectionKey selectionKey;
    private MessageIdentifier messageIdentifier;
    private MessageWriter messageWriter;
    private ConnectionCallback connectionCallback;
    private ClientDisconnectionCallback disconnectionCallback;

    public void start(String address, int port) {
        int attempts = 3;
        while (attempts > 0 && (this.channel == null || !this.channel.isConnected())) {
            try {
                this.channel = SocketChannel.open(new InetSocketAddress(address, port));
                this.selector = Selector.open();

                this.channel.configureBlocking(false);
                this.selectionKey = channel.register(selector, SelectionKey.OP_READ);
                if (connectionCallback != null) connectionCallback.call();
                if (messageIdentifier != null) this.messageIdentifier.setChannel(channel);
                this.messageWriter = new MessageWriter(channel);
            } catch (IOException e) {
                e.printStackTrace();
                attempts--;
            }
        }
    }

    public Object selectFor(long milliseconds) throws IOException {
        Instant start = Instant.now();
        if (messageIdentifier == null || !this.channel.isConnected()) return null;

        selector.select(milliseconds);
        this.messageIdentifier.readMessages();

        Instant finish = Instant.now();
        long elapsed = Duration.between(start, finish).toMillis();
        if (elapsed < milliseconds) {
            return selectFor(elapsed - milliseconds); // heaps off!
        }
        return null;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void disconnect() throws IOException {
        channel.close();
        if (disconnectionCallback != null)
            disconnectionCallback.call();
    }

    public boolean isConnected() {
        if (channel == null) return false;
        return channel.isConnected();
    }

    public boolean isConnecting() {
        return channel == null || channel.isConnectionPending();
    }

    public void setMessageIdentifier(MessageIdentifier messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
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

    public interface ConnectionCallback {
        void call();
    }
}
