package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.*;
import pl.put.poznan.sk2_project_client.net.Message;
import pl.put.poznan.sk2_project_client.net.MessageIdentifier;

import java.nio.ByteBuffer;

public class GameMessageIdentifier extends MessageIdentifier {
    private GameMessage.ReceivedCallback joinCallback;
    private GameMessage.ReceivedCallback playerJoinCallback;
    private GameMessage.ReceivedCallback gameJoinCallback;
    private GameMessage.ReceivedCallback unitMovedCallback;
    private GameMessage.ReceivedCallback unitAttackedCallback;
    private GameMessage.ReceivedCallback unitDestroyedCallback;
    private GameMessage.ReceivedCallback unitSpawnedCallback;
    private GameMessage.ReceivedCallback gameLeftCallback;
    private GameMessage.ReceivedCallback playerScoreChangedCallback;
    private GameMessage.ReceivedCallback kickedCallback;
    private GameMessage.ReceivedCallback playerLeftCallback;

    public GameMessageIdentifier() {
        super();
    }

    public Message createMessage(ByteBuffer buffer) {
        if (buffer.remaining() == 0) return null;
        byte messageType = buffer.get();
        GameMessage message;
        if (messageType == 0) message = new JoinMessage(joinCallback);
        else if (messageType == 1) message = new GameJoinMessage(gameJoinCallback);
        else if (messageType == 2) message = new PlayerJoinedMessage(playerJoinCallback);
        else if (messageType == 3) message = new UnitMovedMessage(unitMovedCallback);
        else if (messageType == 4) message = new UnitAttackedMessage(unitAttackedCallback);
        else if (messageType == 5) message = new UnitDestroyedMessage(unitDestroyedCallback);
        else if (messageType == 6) message = new UnitSpawnedMessage(unitSpawnedCallback);
        else if (messageType == 7) message = new GameLeftMessage(gameLeftCallback);
        else if (messageType == 9) message = new KickedMessage(kickedCallback);
        else if (messageType == 10) message = new PlayerLeftMessage(playerLeftCallback);
        else if (messageType == 11) message = new PlayersScoreChangedMessage(playerScoreChangedCallback);
        // else if (one message more and ... i'll explode!)
        // ... more message type verifies
        else throw new InvalidMessageError("Unknown message type: " + messageType);

        return message;
    }

    public void setJoinCallback(JoinMessage.ReceivedCallback joinCallback) {
        this.joinCallback = joinCallback;
    }

    public void setPlayerJoinCallback(PlayerJoinedMessage.ReceivedCallback playerJoinCallback) {
        this.playerJoinCallback = playerJoinCallback;
    }

    public void setGameJoinCallback(GameJoinMessage.ReceivedCallback gameJoinCallback) {
        this.gameJoinCallback = gameJoinCallback;
    }

    public void setUnitMovedCallback(GameMessage.ReceivedCallback unitMovedCallback) {
        this.unitMovedCallback = unitMovedCallback;
    }

    public void setUnitAttackedCallback(GameMessage.ReceivedCallback unitAttackedCallback) {
        this.unitAttackedCallback = unitAttackedCallback;
    }

    public void setUnitDestroyedCallback(GameMessage.ReceivedCallback unitDestroyedCallback) {
        this.unitDestroyedCallback = unitDestroyedCallback;
    }

    public void setUnitSpawnedCallback(GameMessage.ReceivedCallback unitSpawnedCallback) {
        this.unitSpawnedCallback = unitSpawnedCallback;
    }

    public void setGameLeftCallback(GameMessage.ReceivedCallback gameLeftCallback) {
        this.gameLeftCallback = gameLeftCallback;
    }

    public void setKickedCallback(GameMessage.ReceivedCallback kickedCallback) {
        this.kickedCallback = kickedCallback;
    }

    public void setPlayerLeftCallback(GameMessage.ReceivedCallback playerLeftCallback) {
        this.playerLeftCallback = playerLeftCallback;
    }

    public void setPlayerScoreChangedCallback(GameMessage.ReceivedCallback playerScoreChangedCallback) {
        this.playerScoreChangedCallback = playerScoreChangedCallback;
    }
}
