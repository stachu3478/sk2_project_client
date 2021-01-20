package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Me;

import javax.swing.*;
import java.io.IOException;

public class UIWorker extends SwingWorker<Void, Object> {
    private final Me me;
    private final UpdateCallback callback;
    private State derivedState = State.CONNECTING;

    public enum State {
        CONNECTING,
        CONNECTED,
        FAILED_TO_CONNECT,
        IN_LOBBY,
        IN_GAME
    }

    public UIWorker(Me me, UpdateCallback callback) {
        super();
        this.me = me;
        this.callback = callback;
    }

    @Override
    protected Void doInBackground() {
        synchronized (me) {
            try {
                me.getClient().select();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void done() {
        if (!me.isConnected()) me.connect();
        State newState = deriveState();
        if (newState != derivedState) {
            derivedState = deriveState();
            callback.update(derivedState);
        }
        callback.reload();
    }

    public State getDerivedState() {
        return derivedState;
    }

    private State deriveState() {
        if (me.isConnecting()) return State.CONNECTING;
        else if (me.isConnected() && me.isInLobby()) return State.IN_LOBBY;
        else if (me.isConnected()) return State.CONNECTED;
        else return State.FAILED_TO_CONNECT;
    }

    public interface UpdateCallback {
        void update(State s);
        void reload();
    }

    public UIWorker respawn() {
        UIWorker newWorker = new UIWorker(me, callback);
        newWorker.derivedState = derivedState;
        newWorker.execute();
        return newWorker;
    }
}
