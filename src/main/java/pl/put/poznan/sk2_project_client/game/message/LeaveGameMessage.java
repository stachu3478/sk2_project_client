package pl.put.poznan.sk2_project_client.game.message;

public class LeaveGameMessage extends SimpleMessageOut {
    @Override
    protected byte getType() {
        return 4;
    }
}
