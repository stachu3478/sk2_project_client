package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.game.Me;
import pl.put.poznan.sk2_project_client.net.Message;

public abstract class GameMessage implements Message {
    Me me;
    boolean ignored = false;
    private final ReceivedCallback callback;

    public GameMessage(ReceivedCallback callback) {
        this.callback = callback;
    }

    public void setPlayer(Me me) {
        this.me = me;
    }

    public void ignore() {
        this.ignored = true;
    }

    public void receive() {
        if (!ignored && callback != null)
            callback.call(this);
    }

    public interface ReceivedCallback {
        void call(GameMessage m);
    }


}
