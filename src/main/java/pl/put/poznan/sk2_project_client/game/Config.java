package pl.put.poznan.sk2_project_client.game;

public class Config {
    private byte minPlayersToStart;
    private byte maxPlayersCount;
    private byte countdownSeconds;
    private int mapWidth;
    private int mapHeight;

    public byte getMinPlayersToStart() {
        return minPlayersToStart;
    }

    public void setMinPlayersToStart(byte minPlayersToStart) {
        this.minPlayersToStart = minPlayersToStart;
    }

    public byte getMaxPlayersCount() {
        return maxPlayersCount;
    }

    public void setMaxPlayersCount(byte maxPlayersCount) {
        this.maxPlayersCount = maxPlayersCount;
    }

    public byte getCountdownSeconds() {
        return countdownSeconds;
    }

    public void setCountdownSeconds(byte countdownSeconds) {
        this.countdownSeconds = countdownSeconds;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }
}
