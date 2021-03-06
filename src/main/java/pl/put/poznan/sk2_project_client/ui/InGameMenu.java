package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.ui.rotary_menu.MenuItem;
import pl.put.poznan.sk2_project_client.ui.rotary_menu.RotaryMenu;

import java.awt.*;

public class InGameMenu {
    private Me me;
    private boolean active = false;
    private boolean gameFinished = false;
    private final RotaryMenu menu;
    private final MenuItem backToGameItem;

    public InGameMenu(Me me) {
        this.me = me;
        this.menu = new RotaryMenu();
        backToGameItem = new MenuItem("Back to game", "cancel");
        menu.addItem(backToGameItem);
        menu.addItem(new MenuItem("Change game", "change"));
        menu.addItem(new MenuItem("Leave game", "exit"));
    }

    public void render(Graphics2D g) {
        Rectangle bounds = g.getClipBounds();
        if (menu.isShown()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, bounds.width, bounds.height);
        }
        menu.setPosition(new Point(bounds.width / 2, bounds.height / 2));
        menu.render(g);
        boolean hasFinishedGame = me.hasFinishedGame();
        if (hasFinishedGame && !gameFinished) {
            gameFinished = true;
            if (me.getGame().isWinner(me)) menu.setDefaultText("You win");
            else menu.setDefaultText("You lost");
            setActive(true);
            menu.removeItem("Back to game");
        } else if (!hasFinishedGame && gameFinished) {
            gameFinished = false;
            menu.setDefaultText("");
            menu.addItem(backToGameItem, 0);
        }
    }

    public void setActive(boolean active) {
        if (!active && gameFinished) return;
        this.menu.setShown(active);
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void processClick(Point p) {
        if (!active) return;
        MenuItem item = this.menu.getItemAtPosition(p, 20);
        if (item == null) return;
        if (item.getName().equals("Change game")) me.changeGame();
        if (item.getName().equals("Leave game")) me.leaveGame();
        setActive(false);
    }

    public void processHover(Point p) {
        if (!active) return;
        this.menu.selectItemAtPosition(p, 20);
    }
}
