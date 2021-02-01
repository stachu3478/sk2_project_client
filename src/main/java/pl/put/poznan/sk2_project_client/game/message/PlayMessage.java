package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.net.MessageOut;

import java.nio.ByteBuffer;

public class PlayMessage extends MessageOut {
    private final String nickname;

    public PlayMessage(String nickname) {
        this.nickname = nickname.substring(0, 255);
    } // prevent too long nickname to be sent as only one byte determines the length

    public ByteBuffer serialize() {
        ByteBuffer buffer = ByteBuffer.allocate(2 + nickname.length());
        buffer.put((byte)0);
        buffer.put((byte)nickname.length());
        buffer.put(nickname.getBytes());
        buffer.flip();
        return buffer;
    }
}
