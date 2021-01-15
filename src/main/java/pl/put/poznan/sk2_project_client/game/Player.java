package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.PlayMessage;
import pl.put.poznan.sk2_project_client.net.Client;
import pl.put.poznan.sk2_project_client.net.ClientDisconnectionCallback;

import java.io.IOException;

public class Player {
    private Client client;
    private String nickname;
    private boolean inLobby = false;
    private byte ownerId;
    private String address;
    private int port;

    public Player(String address, int port) {
        this.address = address;
        this.port = port;
        client = new Client();
    }

    public void connect() {
        client.start(address, port);
    }

    public void setMessageIdentifier(GameMessageIdentifier messageIdentifier) {
        client.setMessageIdentifier(messageIdentifier);
    }

    public boolean isConnected() {
        if (client == null) return false;
        return client.isConnected();
    }

    public boolean isConnecting() {
        return client == null || client.isConnecting();
    }

    public void onDisconnection(ClientDisconnectionCallback callback) {
        client.onDisconnection(callback);
    }

    public Client getClient() {
        return client;
    }

    // Message actuators: TODO: implement all required
    public void play(String nickname) throws IOException {
        this.nickname = nickname;
        this.client.emit(new PlayMessage(nickname));
    }

    // Message handles: TODO: implement all required
    public void setOwnerId(byte ownerId) {
        inLobby = true;
        this.ownerId = ownerId;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public void selectFor(long milliseconds) throws IOException {
        client.selectFor(milliseconds);
    }

    public byte getOwnerId() {
        return ownerId;
    }
}
