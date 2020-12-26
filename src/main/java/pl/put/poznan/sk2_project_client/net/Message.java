package pl.put.poznan.sk2_project_client.net;

import java.nio.ByteBuffer;

public interface Message {
    void readBuffer(ByteBuffer buffer);
    boolean isComplete();
    void receive();
}
