package pl.put.poznan.sk2_project_client.game;

public class InvalidMessageError extends Error {
    private final String msg;

    public InvalidMessageError(String s) {
        msg = s;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
