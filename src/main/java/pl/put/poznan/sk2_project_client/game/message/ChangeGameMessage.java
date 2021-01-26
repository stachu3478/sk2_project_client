package pl.put.poznan.sk2_project_client.game.message;

public class ChangeGameMessage extends SimpleMessageOut {
    @Override
    protected byte getType() {
        return 5;
    }
}
