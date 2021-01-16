package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GameUI {
    private final Player player;
    private final JFrame frame;
    private JPanel panel;
    private Object JTextField;
    private int state = -1;
    private final ConnectingPanel connectingPanel = new ConnectingPanel();
    private final ConnectionFailedPanel connectionFailedPanel = new ConnectionFailedPanel();
    private final ConnectedPanel connectedPanel;

    public GameUI(Player player) {
        this.player = player;
        this.connectedPanel = new ConnectedPanel(player);

        frame = new JFrame("SK2 Game 0.1-BETA");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // FIXME: Normal X closing does not work
        frame.setSize(800, 600);
        frame.setVisible(true);

        WindowListener l = new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        };

        frame.addWindowListener(l);
        new SwingWorker() {
            @Override
            protected Void doInBackground() throws Exception {
                while (true) {
                    synchronized (player) {
                        if (player.isConnecting()) connecting();
                        else if (player.isConnected()) connected();
                        else if (player.isInLobby()) lobby();
                        else connectionFailed();
                        player.wait();
                    }
                }
            }

            @Override
            protected void done() {

            }
        }.execute();
    }

    public void connecting() {
        if (state == 0) return;
        state = 0;
        connectingPanel.setIn(frame);
    }

    public void connectionFailed() {
        if (state == 2) return;
        state = 2;
        connectionFailedPanel.setIn(frame);
    }

    public void connected() {
        if (state == 1) return;
        state = 1;
        connectedPanel.setIn(frame);
    }

    public void lobby() {
        if (state == 3) return;
        state = 3;
        LobbyPanel lobbyPanel = new LobbyPanel();
        lobbyPanel.addPlayer(player.getNickname());
        connectedPanel.setIn(frame);
    }

    public void inGame() {
        if (state == 4) return;
        state = 4;
        // TODO: add canvas to the valid pane
        GameCanvas gameCanvas = new GameCanvas();
        frame.add(gameCanvas);
    }
}
