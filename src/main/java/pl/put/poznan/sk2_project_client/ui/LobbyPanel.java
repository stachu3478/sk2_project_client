package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Game;
import pl.put.poznan.sk2_project_client.game.Player;

import javax.swing.*;
import java.awt.*;

public class LobbyPanel extends SwappablePanel {
    private final Game game;
    private JPanel playersInfo;
    private int countdown = -1;
    private SwingWorker<Void, Object> downCounter;
    private final JLabel waitingText;
    private final JLabel startInfo;

    public LobbyPanel(Game game) {
        super();
        this.game = game;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        waitingText = new JLabel("Waiting for players...");
        waitingText.setForeground(Color.LIGHT_GRAY);
        waitingText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(waitingText);

        startInfo = new JLabel("Minimum players to start: " + game.getConfig().getMinPlayersToStart());
        startInfo.setForeground(Color.LIGHT_GRAY);
        startInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startInfo);
        update();
    }

    public void update() {
        if (playersInfo != null) panel.remove(playersInfo);
        playersInfo = new JPanel();
        playersInfo.setLayout(new BoxLayout(playersInfo, BoxLayout.Y_AXIS));
        playersInfo.setBackground(Color.BLACK);
        int playersCount = game.getPlayers().size();
        JLabel playersCountInfo = new JLabel("Players: " + playersCount + " / " + game.getConfig().getMaxPlayersCount());
        playersCountInfo.setForeground(Color.LIGHT_GRAY);
        playersCountInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        playersInfo.add(playersCountInfo);
        int players = 0;
        for (Player player : game.getPlayers()) {
            players++;
            JLabel nickText = new JLabel(players + ". " + player.getNickname());
            nickText.setForeground(Color.LIGHT_GRAY);
            nickText.setAlignmentX(Component.CENTER_ALIGNMENT);
            playersInfo.add(nickText);
        }
        panel.add(playersInfo);
        panel.revalidate();
        panel.repaint();
        if (countdown == -1 && playersCount >= game.getConfig().getMinPlayersToStart()) startCountdown();
        if (frame != null) frame.repaint();
    }

    private void startCountdown() {
        panel.remove(startInfo);
        countdown = game.getConfig().getCountdownSeconds();
        updateCountdown();
        downCounter = new DownCounter();
        downCounter.execute();
    }

    private void updateCountdown() {
        StringBuilder secondsString = new StringBuilder(Integer.toString(countdown % 60));
        while (secondsString.length() < 2) secondsString.insert(0, "0");
        waitingText.setText("Starting in 0" + (countdown / 60) + ":" + secondsString);
    }

    private class DownCounter extends SwingWorker<Void, Object> {
        @Override
        protected Void doInBackground() throws Exception {
            Thread.sleep(1000);
            countdown--;
            return null;
        }

        @Override
        protected void done() {
            updateCountdown();
            if (countdown <= 0) return;
            downCounter = new DownCounter();
            downCounter.execute();
        }
    }
}
