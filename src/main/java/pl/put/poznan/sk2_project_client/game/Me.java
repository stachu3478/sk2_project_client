package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.*;
import pl.put.poznan.sk2_project_client.net.Client;
import pl.put.poznan.sk2_project_client.net.ClientDisconnectionCallback;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Me extends Player {
    private Client client;
    private boolean inLobby = false;
    private boolean inGame = false;
    private Game game;
    private String address;
    private int port;

    public Me(String address, int port) {
        super("", (byte) -1);
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

    public void moveUnits(ArrayList<Unit> units, Point target) throws IOException {
        this.client.emit(new UnitMoveMessage(units.toArray(new Unit[0]), target.x, target.y));
    }

    public void attackUnits(ArrayList<Unit> units, Unit target) throws IOException {
        this.client.emit(new AttackMessage(units.toArray(new Unit[0]), target));
    }

    // Message handles: TODO: implement all required
    public void joinedLobby(GameMessage msg) {
        JoinMessage m = (JoinMessage) msg;
        inLobby = true;
        inGame = false;
        this.ownerId = m.getOwnerId();
        this.game = new Game(m.getConfig());
        this.game.addPlayer(this);
    }

    public void playerJoined(GameMessage msg) {
        PlayerJoinedMessage m = (PlayerJoinedMessage) msg;
        this.game.addPlayer(new Player(m.getNickname(), m.getOwnerId()));
    }

    public void joinedGame(GameMessage msg) {
        GameJoinMessage m = (GameJoinMessage) msg;
        inGame = true;
        inLobby = false;
        for (Unit unit : m.getUnits()) {
            game.addUnit(unit);
        }
    }

    public void unitMoved(GameMessage msg) {
        UnitMovedMessage m = (UnitMovedMessage) msg;
        game.getMap().moveUnit(game.findUnit(m.getUnitId()), m.getToX(), m.getToY());
    }

    public void unitAttacked(GameMessage msg) {
        UnitAttackedMessage m = (UnitAttackedMessage) msg;
        game.findUnit(m.getAttackerUnitId()).attack(game.findUnit(m.getAttackedUnitId()), m.getAttackedUnitHitPoints());
    }

    public Game getGame() {
        return game;
    }

    public boolean isInLobby() {
        return inLobby;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void selectFor(long milliseconds) throws IOException {
        client.selectFor(milliseconds);
    }
}
