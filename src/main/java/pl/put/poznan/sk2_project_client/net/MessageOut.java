package pl.put.poznan.sk2_project_client.net;

import java.nio.ByteBuffer;

public abstract class MessageOut {
    public abstract ByteBuffer serialize();
}
