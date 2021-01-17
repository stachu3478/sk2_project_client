package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.GameMessage;
import pl.put.poznan.sk2_project_client.game.message.JoinMessage;
import pl.put.poznan.sk2_project_client.net.Message;
import pl.put.poznan.sk2_project_client.net.MessageIdentifier;

import java.nio.ByteBuffer;

public class GameMessageIdentifier extends MessageIdentifier {
    private MessageFilter filter;
    private final Player player;
    private JoinMessage.ReceivedCallback joinCallback;

    public GameMessageIdentifier(Player player) {
        super();
        this.player = player;
    }

    public void setFilter(MessageFilter filter) {
        this.filter = filter;
    }

    public Message createMessage(ByteBuffer buffer) {
        if (buffer.remaining() == 0) return null;
        byte messageType = buffer.get();
        GameMessage message;
        if (messageType == 0) message = new JoinMessage(joinCallback);
        // else if blablabla
        // ... more message type verifies
        else throw new InvalidMessageError("Unknown message type: " + messageType);

        message.setPlayer(player);
        return message;
    }

    public void setJoinCallback(JoinMessage.ReceivedCallback joinCallback) {
        this.joinCallback = joinCallback;
    }
}
