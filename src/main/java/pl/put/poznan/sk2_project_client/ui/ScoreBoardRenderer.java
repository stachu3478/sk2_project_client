package pl.put.poznan.sk2_project_client.ui;

import pl.put.poznan.sk2_project_client.game.Game;
import pl.put.poznan.sk2_project_client.game.Player;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ScoreBoardRenderer {
    private static String NAME = "Scoreboard: ";
    private final Game game;

    public ScoreBoardRenderer(Game game) {
        this.game = game;
    }

    public void render(Graphics2D g) {
        int x=5;
        int y=20;
        int max = 0;
        int Width2 = g.getFontMetrics().stringWidth(NAME);
        int width3 = g.getFontMetrics().stringWidth("    ");
        for(Player player: game.getPlayers()){
            String nick = player.getNickname();
            int score = player.getScore();
            int szerokosc = g.getFontMetrics().stringWidth(nick +" "+ score);
            if(max < szerokosc){
                max = szerokosc;
            }
        }
        if(max < Width2){
            max = Width2;
        }
        g.setColor(Color.BLACK);
        g.drawRect(0,0,x+max+width3,100);
        g.fillRect(0,0,x+max+width3,100);
        List<Map.Entry<Byte, Player>> Sorted = getSortedMap(game);
        for(Map.Entry<Byte, Player> entry: Sorted){
            Player player = entry.getValue();
            String nick = player.getNickname();
            int score = player.getScore();
            //g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(2));
            g.setFont(new Font("Consolas", Font.PLAIN, 10));

            g.setColor(ColorManager.getColorByOwnerId(player.getOwnerId())); // draw color square of player
            g.fillRect(0,y + g.getFontMetrics().getHeight(),3,g.getFontMetrics().getHeight());

            g.setColor(Color.WHITE);
            g.drawString(NAME,x,10);
            g.drawString(nick,x,y+=20);
            String s = String.valueOf(score);
            g.drawString(s,x+max,y);
        }
    }

    private java.util.List<Map.Entry<Byte, Player>> getSortedMap(Game game){
        java.util.List<Map.Entry<Byte, Player>> list = new LinkedList<Map.Entry<Byte, Player>>(game.getPlayersMap().entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Byte, Player>>() {
            @Override
            public int compare(Map.Entry<Byte, Player> o1, Map.Entry<Byte, Player> o2) {
                return Integer.compare(o2.getValue().getScore(), o1.getValue().getScore());
            }
        });
        return list;
    }
}
