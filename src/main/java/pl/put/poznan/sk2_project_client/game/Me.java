package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.*;
import pl.put.poznan.sk2_project_client.net.Client;
import pl.put.poznan.sk2_project_client.net.ClientDisconnectionCallback;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Me extends Player {
    private Client client;
    private boolean inLobby = false;
    private boolean inGame = false;
    private boolean disconnecting = false;
    private Game game;
    private String address;
    private int port;
    private final UnitSelector unitSelector = new UnitSelector();

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
        disconnecting = true;
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

    public void moveUnits(Vector<Unit> units, Point target) {
        this.client.emit(new UnitMoveMessage(units.toArray(new Unit[0]), target.x, target.y));
    }

    public void attackUnits(Vector<Unit> units, Unit target) {
        this.client.emit(new AttackMessage(units.toArray(new Unit[0]), target));
    }

    public void leaveGame() {
        this.client.emit(new LeaveGameMessage());
    }

    // Message handles: TODO: implement all required
    public void joinedLobby(GameMessage msg) {
        JoinMessage m = (JoinMessage) msg;
        inLobby = true;
        inGame = false;
        this.ownerId = m.getOwnerId();
        this.game = new Game(m.getConfig());
        this.game.addPlayer(this);
        clearUnits();
    }

    public void playerJoined(GameMessage msg) {
        PlayerJoinedMessage m = (PlayerJoinedMessage) msg;
        this.game.addPlayer(new Player(m.getNickname(), m.getOwnerId()));
    }

    public void joinedGame(GameMessage msg) {
        GameJoinMessage m = (GameJoinMessage) msg;
        inGame = true;
        inLobby = false;
        clearUnits();
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
        Unit attacker = game.findUnit(m.getAttackerUnitId()); // FIXME: attacker should != null
        if (attacker != null) attacker.attack(game.findUnit(m.getAttackedUnitId()), m.getAttackedUnitHitPoints());
    }

    public void unitDestroyed(GameMessage msg) {
        UnitDestroyedMessage m = (UnitDestroyedMessage) msg;
        unitSelector.unselect(game.findUnit(m.getDestroyedUnitId()));
        game.removeUnit(m.getDestroyedUnitId());
    }

    public void unitSpawned(GameMessage msg) {
        UnitSpawnedMessage m = (UnitSpawnedMessage) msg;
        game.addUnit(m.getUnit());
    }

    public void leftGame(GameMessage m) {
        inGame = false;
        inLobby = false;
        clearUnits();
    }

    public void playerLeft(GameMessage msg) {
        PlayerLeftMessage m = (PlayerLeftMessage) msg;
        game.removePlayer(m.getOwnerId());
    }

    public void playerScoreChanged(GameMessage msg){
        PlayersScoreChangedMessage m = (PlayersScoreChangedMessage) msg;
        game.findPlayer(m.getOwnerId()).setScore(m.getScore());
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

    public UnitSelector getUnitSelector() {
        return unitSelector;
    }

    public void changeGame() {
        this.client.emit(new ChangeGameMessage());
    }

    public boolean hasFinishedGame() {
        return isInGame() && game.isFinishedFor(this);
    }

    public boolean isDisconnecting() {
        return disconnecting;
    }

    @Override
    public void clearUnits() {
        super.clearUnits();
        unitSelector.clear();
    }
}
