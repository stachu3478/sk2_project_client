package pl.put.poznan.sk2_project_client;

import pl.put.poznan.sk2_project_client.game.GameMessageIdentifier;
import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.ui.GameUI;

import java.awt.*;

public class GameApp {
    private final Me me;
    private GameMessageIdentifier messageIdentifier;
    private GameUI ui;

    private static String address = "192.168.110.135";
    private static int port = 34780;

    public static void main(String[] args) {
        if (args.length > 0) address = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        new GameApp();
    }

    public GameApp() {
        me = new Me(address, port);
        EventQueue.invokeLater(() -> {
            ui = new GameUI(me);
        });

        synchronized (me) {
            messageIdentifier = new GameMessageIdentifier(me);
            messageIdentifier.setJoinCallback(me::joinedLobby);
            messageIdentifier.setPlayerJoinCallback(me::playerJoined);
            messageIdentifier.setGameJoinCallback(me::joinedGame);
            messageIdentifier.setUnitMovedCallback(me::unitMoved);
            messageIdentifier.setUnitAttackedCallback(me::unitAttacked);
            messageIdentifier.setUnitDestroyedCallback(me::unitDestroyed);
            messageIdentifier.setUnitSpawnedCallback(me::unitSpawned);
            me.setMessageIdentifier(messageIdentifier);
        }
    }
}
