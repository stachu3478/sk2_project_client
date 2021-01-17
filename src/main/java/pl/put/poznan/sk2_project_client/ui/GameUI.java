package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameUI {
    private final Player player;
    private final JFrame frame;
    private final ConnectingPanel connectingPanel = new ConnectingPanel();
    private final ConnectionFailedPanel connectionFailedPanel = new ConnectionFailedPanel();
    private final ConnectedPanel connectedPanel;
    private UIWorker worker;

    public GameUI(Player player) {
        this.player = player;
        this.connectedPanel = new ConnectedPanel(player);

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
        worker = new UIWorker(player, this::stateChanged);
        stateChanged(worker.getDerivedState());
        frame.validate();
        frame.pack();
    }

    public void connecting() {
        connectingPanel.setIn(frame);
    }

    public void connectionFailed() {
        connectingPanel.dlaczegoToKurwaNieDziala(); // Jak ta funkcja jest to działa
        connectionFailedPanel.setIn(frame);
    }

    public void connected() {
        connectingPanel.dlaczegoToKurwaNieDziala(); // Jak ta funkcja jest to działa
        connectedPanel.setIn(frame);
    }

    public void inLobby() {
        LobbyPanel lobbyPanel = new LobbyPanel();
        lobbyPanel.addPlayer(player.getNickname());
        connectedPanel.dlaczegoToKurwaNieDziala(); // Jak ta funkcja jest to działa
        lobbyPanel.setIn(frame);
    }

    public void inGame() {
        // TODO: add canvas to the valid pane
        GameCanvas gameCanvas = new GameCanvas();
        frame.add(gameCanvas);
    }

    public void stateChanged(UIWorker.State s) {
        if (s == UIWorker.State.CONNECTING) connecting();
        else if (s == UIWorker.State.CONNECTED) connected();
        else if (s == UIWorker.State.IN_LOBBY) inLobby();
        else if (s == UIWorker.State.FAILED_TO_CONNECT) connectionFailed();
        else if (s == UIWorker.State.IN_GAME) inGame();
        worker = worker.respawn();
    }
}
