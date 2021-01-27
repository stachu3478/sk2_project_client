package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Game;
import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.game.Player;
import pl.put.poznan.sk2_project_client.ui.rotary_menu.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LobbyPanel extends SwappablePanel {
    private final Me me;
    private JPanel playersInfo;
    private int countdown = -1;
    private Timer downCounter;
    private final JLabel waitingText;
    private final JLabel startInfo;
    private final static int RENDERING_PERIOD_MILLIS = 17; // 60 fps
    private final Timer renderingTimer = new Timer(RENDERING_PERIOD_MILLIS, (e) -> panel.repaint());
    private final InGameMenu menu;

    public LobbyPanel(Me me) {
        super();
        this.me = me;
        this.menu = new InGameMenu(me);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        waitingText = new JLabel("Waiting for players...");
        waitingText.setForeground(Color.LIGHT_GRAY);
        waitingText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(waitingText);

        startInfo = new JLabel("Minimum players to start: " + me.getGame().getConfig().getMinPlayersToStart());
        startInfo.setForeground(Color.LIGHT_GRAY);
        startInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startInfo);
        update();
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == 27) menu.setActive(!menu.isActive());
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                super.mouseClicked(evt);
                menu.processClick(new Point(evt.getX(), evt.getY()));
                panel.requestFocusInWindow();
            }
        });
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                menu.processHover(new Point(e.getX(), e.getY()));
            }
        });
    }

    public void update() {
        Game game = me.getGame();
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
        countdown = me.getGame().getConfig().getCountdownSeconds();
        updateCountdown();
        downCounter = new Timer(1000, (e) -> {
            countdown--;
            updateCountdown();
            if (countdown <= 0) downCounter.stop();
        });
        downCounter.start();
    }

    private void updateCountdown() {
        StringBuilder secondsString = new StringBuilder(Integer.toString(countdown % 60));
        while (secondsString.length() < 2) secondsString.insert(0, "0");
        waitingText.setText("Starting in 0" + (countdown / 60) + ":" + secondsString);
    }

    @Override
    public void draw(Graphics2D g) {
        menu.render(g);
    }

    @Override
    public void mounted() {
        renderingTimer.start();
    }

    @Override
    public void dismounted() {
        renderingTimer.stop();
    }
}
