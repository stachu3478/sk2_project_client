package pl.put.poznan.sk2_project_client.game;

import pl.put.poznan.sk2_project_client.game.message.PlayMessage;
import pl.put.poznan.sk2_project_client.net.Client;

import java.io.IOException;

public class Player {
    private Client client;
    private String nickname;
    private boolean inLobby = false;

    public Player() {}

    public void connect() throws IOException {
        client = new Client();
        client.setMessageIdentifier(new GameMessageIdentifier(this));
    }

    public boolean isConnected() {
        if (client == null) return false;
        return client.getChannel().isConnected();
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
    public void joinedLobby() {
        inLobby = true;
        System.out.println("Joined lobby");
    }
}
