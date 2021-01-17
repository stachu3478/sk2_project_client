package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Player;

import javax.swing.*;
import java.io.IOException;

public class UIWorker extends SwingWorker<Void, Object> {
    private final Player player;
    private final UpdateCallback callback;
    private State derivedState = State.CONNECTING;

    public enum State {
        CONNECTING,
        CONNECTED,
        FAILED_TO_CONNECT,
        IN_LOBBY,
        IN_GAME
    }

    public UIWorker(Player player, UpdateCallback callback) {
        super();
        this.player = player;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground() throws Exception {
        synchronized (player) {
            Thread thread = new Thread(() -> {
                while (!stateChanged() && player.isConnected()) {
                    synchronized (player) {
                        try {
                            player.getClient().select();
                            player.notify();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                while (player.isConnected()) player.disconnect();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                }
            });

            thread.start();

            while (!stateChanged()) {
                try {
                    player.wait();
                } catch (InterruptedException ignored) {}
            }
        }

        return null;
    }

    @Override
    protected void done() {
        derivedState = deriveState();
        callback.update(derivedState);
    }

    public State getDerivedState() {
        return derivedState;
    }

    private State deriveState() {
        if (player.isConnecting()) return State.CONNECTING;
        else if (player.isConnected() && player.isInLobby()) return State.IN_LOBBY;
        else if (player.isConnected()) return State.CONNECTED;
        else return State.FAILED_TO_CONNECT;
    }

    private boolean stateChanged() {
        return deriveState() != derivedState;
    }

    public interface UpdateCallback {
        void update(State s);
    }

    public UIWorker respawn() {
        UIWorker newWorker = new UIWorker(player, callback);
        newWorker.derivedState = derivedState;
        newWorker.execute();
        return newWorker;
    }
}
