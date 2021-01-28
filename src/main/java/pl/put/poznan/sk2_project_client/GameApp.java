package pl.put.poznan.sk2_project_client;

//import com.sun.org.apache.xpath.internal.operations.String;
import pl.put.poznan.sk2_project_client.game.GameMessageIdentifier;
import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.ui.GameUI;
import pl.put.poznan.sk2_project_client.ui.ImageLoader;
import pl.put.poznan.sk2_project_client.ui.rotary_menu.MenuItem;
import pl.put.poznan.sk2_project_client.game.ClientConfig;
import java.io.*;

import java.awt.*;

public class GameApp {
    private static ClientConfig cfg= new ClientConfig();
    private final Me me;
    private GameMessageIdentifier messageIdentifier;
    private GameUI ui;

    private static java.lang.String address;
    private static int port;

    public static void main(String[] args) {
        address = cfg.getAddress();
        port = cfg.getPort();
        if (args.length > 0) address = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        new GameApp();
    }

    public GameApp() {
        me = new Me(address, port);
        EventQueue.invokeLater(() -> {
            ui = new GameUI(me);
        });

        ImageLoader loader = new ImageLoader()
                .loadImage("play", "/play.png")
                .loadImage("change", "/change.png")
                .loadImage("exit", "/exit.png")
                .loadImage("ok", "/ok.png")
                .loadImage("cancel", "/cancel.png");
        MenuItem.useLoader(loader);

        synchronized (me) {
            messageIdentifier = new GameMessageIdentifier(me);
            messageIdentifier.setJoinCallback(me::joinedLobby);
            messageIdentifier.setPlayerJoinCallback(me::playerJoined);
            messageIdentifier.setGameJoinCallback(me::joinedGame);
            messageIdentifier.setUnitMovedCallback(me::unitMoved);
            messageIdentifier.setUnitAttackedCallback(me::unitAttacked);
            messageIdentifier.setUnitDestroyedCallback(me::unitDestroyed);
            messageIdentifier.setUnitSpawnedCallback(me::unitSpawned);
            messageIdentifier.setGameLeftCallback(me::leftGame);
            messageIdentifier.setPlayerLeftCallback(me::playerLeft);
            messageIdentifier.setPlayerScoreChangedCallback(me::playerScoreChanged);
            me.setMessageIdentifier(messageIdentifier);
        }
    }
}
