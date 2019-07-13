package develop.marat.ms.messageSystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


@SuppressWarnings("LoopStatementThatDoesntLoop")
public final class MessageSystem {
    private final static Logger logger = Logger.getLogger(MessageSystem.class.getName());

    private final List<Thread> workers;
    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
    private final ConcurrentHashMap<Message, MessageResult> messagesResult;
    private final Map<Address, Addressee> addresseeMap;

    public MessageSystem() {
        workers = new ArrayList<>();
        messagesMap = new HashMap<>();
        messagesResult = new ConcurrentHashMap<>();
        addresseeMap = new HashMap<>();
    }

    public void addAddressee(Addressee addressee) {
        addresseeMap.put(addressee.getAddress(), addressee);
        messagesMap.put(addressee.getAddress(), new LinkedBlockingQueue<>());
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
        messagesResult.put(message, new MessageResult(message));
    }


    public <T> T takeMessageResult(Message message) {
        if(messagesResult.containsKey(message)){
            MessageResult messageResult = messagesResult.get(message);
            T result = messageResult.takeResult();
            messagesResult.remove(message);
            return result;
        }else{
            return null;
        }
    }


    public void start() {
        for (Map.Entry<Address, Addressee> entry : addresseeMap.entrySet()) {
            String name = "MS-worker-" + entry.getKey().getId();
            Thread thread = new Thread(() -> {
                while (true) {
                    LinkedBlockingQueue<Message> queue = messagesMap.get(entry.getKey());
                    while (true) {
                        try {
                            Message message = queue.take(); //Blocks
                            Object result = message.exec(entry.getValue());
                            messagesResult.get(message).setResult(result);
                        } catch (InterruptedException e) {
                            logger.log(Level.INFO, "Thread interrupted. Finishing: " + name);
                            return;
                        }
                    }
                }
            });
            thread.setName(name);
            thread.start();
            workers.add(thread);
        }
    }

    public void dispose() {
        workers.forEach(Thread::interrupt);
    }
}
