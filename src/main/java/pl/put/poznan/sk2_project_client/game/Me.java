package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.GameMessage;
import pl.put.poznan.sk2_project_client.game.message.JoinMessage;
import pl.put.poznan.sk2_project_client.game.message.PlayMessage;
import pl.put.poznan.sk2_project_client.net.Client;
import pl.put.poznan.sk2_project_client.net.ClientDisconnectionCallback;

import java.io.IOException;

public class Player {
    private Client client;
    private String nickname;
    private boolean inLobby = false;
    private byte ownerId;
    private Game game;
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

    public void disconnect() throws IOException {
        client.disconnect();
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

    public String getNickname() {
        return nickname;
    }

    // Message handles: TODO: implement all required
    public void joinedLobby(GameMessage msg) {
        JoinMessage m = (JoinMessage) msg;
        inLobby = true;
        this.ownerId = m.getOwnerId();
        this.game = new Game(m.getMinPlayersToStart(), m.getMaxPlayersCount());
        this.game.addPlayer(nickname);
    }

    public void playerJoined() {}

    public Game getGame() {
        return game;
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
