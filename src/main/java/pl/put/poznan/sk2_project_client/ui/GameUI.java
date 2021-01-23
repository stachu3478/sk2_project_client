package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Me;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI {
    private final Me me;
    private final JFrame frame;
    private final ConnectingPanel connectingPanel = new ConnectingPanel();
    private final ConnectionFailedPanel connectionFailedPanel = new ConnectionFailedPanel();
    private final ConnectedPanel connectedPanel;
    private SwappablePanel currentPanel;
    private UIWorker worker;

    public GameUI(Me me) {
        this.me = me;
        this.connectedPanel = new ConnectedPanel(me);

        frame = new JFrame("SK2 Game 0.1-BETA");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FIXME: Normal X closing does not work
        frame.setLayout(new GridLayout());
        frame.setSize(800, 600);
        frame.setVisible(true);

        WindowListener l = new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

        };

        frame.addWindowListener(l);
        worker = new UIWorker(me, new UIWorker.UpdateCallback() {
            @Override
            public void update(UIWorker.State s) {
                stateChanged(s);
            }

            @Override
            public void reload() {
                reloadPanel();
                worker = worker.respawn();
            }
        });
        stateChanged(worker.getDerivedState());
        frame.validate();
        worker.execute();
    }

    public void connecting() {
        setPanel(connectingPanel);
    }

    public void connectionFailed() {
        setPanel(connectionFailedPanel);
    }

    public void connected() {
        setPanel(connectedPanel);
    }

    public void inLobby() {
        LobbyPanel lobbyPanel = new LobbyPanel(me.getGame());
        setPanel(lobbyPanel);
    }

    public void inGame() {
        InGamePanel inGamePanel = new InGamePanel(me);
        setPanel(inGamePanel);
    }

    private void setPanel(SwappablePanel panel) {
        if (currentPanel != null) currentPanel.unset();
        panel.setIn(frame);
        currentPanel = panel;
    }

    public void reloadPanel() {
        currentPanel.update();
    }

    public void stateChanged(UIWorker.State s) {
        if (s == UIWorker.State.CONNECTING) connecting();
        else if (s == UIWorker.State.CONNECTED) connected();
        else if (s == UIWorker.State.IN_LOBBY) inLobby();
        else if (s == UIWorker.State.FAILED_TO_CONNECT) connectionFailed();
        else if (s == UIWorker.State.IN_GAME) inGame();
    }
}
