package develop.marat.ms.workers;


import develop.marat.ms.annotation.Blocks;
import develop.marat.ms.core.Address;
import develop.marat.ms.core.Message;

import java.io.IOException;

public interface MessageWorker {
    Address takeAddressTo();
    Address getAddressFrom();
    Address getAddressTo();
    void init();
    Message poll();
    void send(Message message);
    @Blocks
    Message take() throws InterruptedException;
    boolean isConnected();
    void close() throws IOException;
}
