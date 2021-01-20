package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.GameJoinMessage;
import pl.put.poznan.sk2_project_client.game.message.GameMessage;
import pl.put.poznan.sk2_project_client.game.message.JoinMessage;
import pl.put.poznan.sk2_project_client.game.message.PlayerJoinedMessage;
import pl.put.poznan.sk2_project_client.net.Message;
import pl.put.poznan.sk2_project_client.net.MessageIdentifier;

import java.nio.ByteBuffer;

public class GameMessageIdentifier extends MessageIdentifier {
    private MessageFilter filter;
    private final Me me;
    private GameMessage.ReceivedCallback joinCallback;
    private GameMessage.ReceivedCallback playerJoinCallback;
    private GameMessage.ReceivedCallback gameJoinCallback;

    public GameMessageIdentifier(Me me) {
        super();
        this.me = me;
    }

    public void setFilter(MessageFilter filter) {
        this.filter = filter;
    }

    public Message createMessage(ByteBuffer buffer) {
        if (buffer.remaining() == 0) return null;
        byte messageType = buffer.get();
        GameMessage message;
        if (messageType == 0) message = new JoinMessage(joinCallback);
        else if (messageType == 1) message = new GameJoinMessage(gameJoinCallback);
        else if (messageType == 2) message = new PlayerJoinedMessage(playerJoinCallback);
        // else if blablabla
        // ... more message type verifies
        else throw new InvalidMessageError("Unknown message type: " + messageType);

        message.setPlayer(me);
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
}
