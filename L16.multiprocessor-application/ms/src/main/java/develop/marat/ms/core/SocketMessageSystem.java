package develop.marat.ms.core;

import develop.marat.ms.annotation.Blocks;
import develop.marat.ms.workers.ServerSocketMessageWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;



@SuppressWarnings("InfiniteLoopStatement")
public final class SocketMessageSystem {
    private static final int THREADS_COUNT = 2;
    private static final String ADDRESS_NAME = "Server";

    private final int port;
    private final ExecutorService executorService;
    private final List<ServerSocketMessageWorker> workers;

    private final Map<Address, BlockingQueue<TransferObject>> messagesMap;

    public SocketMessageSystem(int port) {
        this.port = port;
        executorService = Executors.newFixedThreadPool(THREADS_COUNT);
        workers = new CopyOnWriteArrayList<>();
        messagesMap = new ConcurrentHashMap<>();
    }

    private void addAddressee(Address addressee) {
        messagesMap.put(addressee, new LinkedBlockingQueue<>());
    }

    private void sendMessage(TransferObject transferMessage) {
        Message message = (Message) transferMessage.getConvertData(SimpleMessage.class);
        if(messagesMap.get(message.getTo()) == null){
            addAddressee(message.getTo());
        }
        messagesMap.get(message.getTo()).add(transferMessage);
    }

    private TransferObject pollMessage(Address address) {
        BlockingQueue<TransferObject> queue = messagesMap.get(address);
        return queue.poll();
    }



    public void start() throws IOException{
        executorService.execute(this::messageReceiveProcessing);
        executorService.execute(this::messageSendProcessing);

        try (ServerSocket serverSocket = new ServerSocket(port)){

            System.out.println("Server started on port: " + serverSocket.getLocalPort());

            while(!Thread.currentThread().isInterrupted()){
                Socket socket = serverSocket.accept();  //blocks
                initSocket(socket);
            }
        }

        shutdown();
    }

    @Blocks
    private void initSocket(Socket socket) throws IOException{
        ServerSocketMessageWorker worker = new ServerSocketMessageWorker(socket, new Address(ADDRESS_NAME));
        worker.init();
        Address addressTo = worker.takeAddressTo();
        if(addressTo != null){
            addAddressee(addressTo);
            workers.add(worker);
        }else{
            socket.close();
        }
    }


    private void messageReceiveProcessing(){
        while (true){
            for (ServerSocketMessageWorker worker : workers){
                if(workerIsConnectedAndNotRemove(worker)){
                    TransferObject message = worker.poll();
                    if (message != null){
                        sendMessage(message);
                    }
                }
            }
        }
    }

    private void messageSendProcessing(){
        while (true){
            for (ServerSocketMessageWorker worker : workers){
                if(workerIsConnectedAndNotRemove(worker)){
                    TransferObject message = pollMessage(worker.getAddressTo());
                    if (message != null){
                        worker.send(message);
                    }
                }
            }
        }
    }

    private boolean workerIsConnectedAndNotRemove(ServerSocketMessageWorker worker){
        if(worker.isConnected()){
            return true;
        }else {
            workers.remove(worker);
            return false;
        }
    }

    public void shutdown(){
        dispose();
        executorService.shutdown();
    }

    private void dispose(){
        workers.forEach(w->{
            try {
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
