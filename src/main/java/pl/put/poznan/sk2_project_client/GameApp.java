package pl.put.poznan.sk2_project_client;

import pl.put.poznan.sk2_project_client.game.GameMessageIdentifier;
import pl.put.poznan.sk2_project_client.game.Player;
import pl.put.poznan.sk2_project_client.net.MessageIdentifier;
import pl.put.poznan.sk2_project_client.ui.GameUI;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GameApp /* extends Application */ { // FIXME: Cant use javafx Application with multiple threads
    private static Player player;

    private static Game.UiCallback callback;

    public static void create(Game.UiCallback c, Player p) {
        callback = c;
        player = p;
        callback.call(new GameUI(player));
    }
}
