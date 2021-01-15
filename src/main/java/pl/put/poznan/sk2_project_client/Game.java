package pl.put.poznan.sk2_project_client;

import pl.put.poznan.sk2_project_client.game.GameMessageIdentifier;
import pl.put.poznan.sk2_project_client.game.Player;
import pl.put.poznan.sk2_project_client.ui.GameUI;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;


public class Game {
    private final Player player;
    private GameMessageIdentifier messageIdentifier;
    private volatile GameUI ui;

    private int minPlayersToStart;
    private boolean shouldStop;
    private boolean connecting = true;

    private static String address = "192.168.110.135";
    private static int port = 34780;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        if (args.length > 0) address = args[0];
        if (args.length > 1) port = Integer.parseInt(args[1]);

        new Game();
    }

    public Game() throws InvocationTargetException, InterruptedException {
        player = new Player(address, port);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameApp.create(new UiCallback() {
                    private final Player lock = player;

                    @Override
                    public void call(GameUI i) {
                        // if (!connecting) {
                            synchronized (lock) {
                                ui = i;
                                // notify(); // FIXME: IllegalMonitorStateException
                            }
                        // }
                    }

                    @Override
                    public void stop() {
                        shouldStop = true;
                    }
                }, player);
            }
        });

        messageIdentifier = new GameMessageIdentifier(player);
        player.setMessageIdentifier(messageIdentifier);
        player.connect();

        while (ui == null); // player.wait(); FIXME: IllegalMonitorStateException

        synchronized (player) {
            connecting = false;
            if (player.isConnected()) bindCallbacks();
        }
    }

    private void bindCallbacks() {
        player.onDisconnection(() -> {
            System.out.println("Connection lost");
        });

        messageIdentifier.setJoinCallback((message) -> {
            player.setOwnerId(message.getOwnerId());
            minPlayersToStart = message.getMinPlayersToStart();
            System.out.println("Joined lobby");
        });
    }

    interface UiCallback {
        void call(GameUI ui);
        void stop();
    }
}
