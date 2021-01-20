package pl.put.poznan.sk2_project_client.game;

public class Player {
    protected String nickname;
    protected byte ownerId;

    public Player(String nickname, byte ownerId) {
        this.nickname = nickname;
        this.ownerId = ownerId;
    }

    public byte getOwnerId() {
        return ownerId;
    }

    public String getNickname() {
        return nickname;
    }
}
