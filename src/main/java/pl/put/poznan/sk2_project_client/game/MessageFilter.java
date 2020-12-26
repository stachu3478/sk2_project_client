package pl.put.poznan.sk2_project_client.game;

public interface MessageFilter {
    boolean shouldIgnore(byte messageId);
}
