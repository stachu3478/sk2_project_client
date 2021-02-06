package pl.put.poznan.sk2_project_client.game.message;

import pl.put.poznan.sk2_project_client.net.Message;

public abstract class GameMessage implements Message {
    boolean ignored = false;
    private final ReceivedCallback callback;

    public GameMessage(ReceivedCallback callback) {
        this.callback = callback;
    }

    public void receive() {
        if (!ignored && callback != null)
            callback.call(this);
    }

    public interface ReceivedCallback {
        void call(GameMessage m);
    }
}
