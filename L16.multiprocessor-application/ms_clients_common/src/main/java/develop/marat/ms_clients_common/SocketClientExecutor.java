package develop.marat.ms_clients_common;

import develop.marat.ms.core.Addressee;
import develop.marat.ms.core.Message;
import develop.marat.ms_clients_common.messages.controller.MsgSetResult;
import develop.marat.ms.workers.MessageWorker;
import develop.marat.ms.workers.SocketMessageWorker;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketClientExecutor {
    private final Addressee addressee;
    private final MessageWorker clientWorker;
    private final ExecutorService executorService;

    private Runnable externalDisposeAction;

    public SocketClientExecutor(String host, int port, Addressee addressee) throws IOException {
        this.addressee = addressee;
        clientWorker = new SocketMessageWorker(host, port, addressee.getAddress());
        clientWorker.init();
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    public void start() {
        executorService.execute(() -> {
            while (clientWorker.isConnected()) {
                try {
                    Message inputMessage = clientWorker.take();
                    Object result = null;
                    try{
                        result = inputMessage.exec(this.addressee);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if (result != null) {
                        Message msgSetResult = new MsgSetResult(addressee.getAddress(), inputMessage.getFrom(), inputMessage.getId(), result);
                        clientWorker.send(msgSetResult);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            shutdown();
        });
    }

    public void setExternalDisposeAction(Runnable externalDisposeAction){
        this.externalDisposeAction = externalDisposeAction;
    }

    public void shutdown() {
        try {
            clientWorker.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        if(externalDisposeAction != null){
            externalDisposeAction.run();
        }
    }

    public void sendMessage(Message message) {
        clientWorker.send(message);
    }
}
