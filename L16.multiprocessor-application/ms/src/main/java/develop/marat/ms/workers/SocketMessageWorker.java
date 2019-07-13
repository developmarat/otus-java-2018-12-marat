package develop.marat.ms.workers;

import com.google.gson.Gson;
import develop.marat.ms.annotation.Blocks;
import develop.marat.ms.core.Address;
import develop.marat.ms.core.Message;
import develop.marat.ms.core.TransferObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SocketMessageWorker implements MessageWorker {
    private static final int WORKER_COUNT = 2;

    private final ExecutorService executorService;
    private final Socket socket;
    private volatile Address addressFrom;
    private volatile Address addressTo;

    private final BlockingQueue<Message> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Message> input = new LinkedBlockingQueue<>();

    private final Lock lock = new ReentrantLock();
    private final Condition addressToIsReceived = lock.newCondition();


    public SocketMessageWorker(Socket socket, Address addressFrom) {
        this.socket = socket;
        this.addressFrom = addressFrom;
        executorService = Executors.newFixedThreadPool(WORKER_COUNT);
    }

    public SocketMessageWorker(String host, int port, Address address) throws IOException {
        this(new Socket(host, port), address);
    }

    @Override
    public void init() {
        executorService.execute(this::sendMessage);
        executorService.execute(this::receiveMessage);
    }


    @Override
    public Message poll() {
        return input.poll();
    }

    @Override
    public void send(Message message) {
        output.add(message);
    }

    @Override
    public Message take() throws InterruptedException {
        return input.take();
    }

    @Override
    public boolean isConnected(){
        return socket.isConnected();
    }

    @Override
    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }


    @Blocks
    private void sendMessage(){
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
            out.println(addressFrom);

            while (socket.isConnected()){
                Message message = output.take();
                TransferObject transferMessage = new TransferObject(message);
                String json = new Gson().toJson(transferMessage);
                out.println(json);
                out.println();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Blocks
    private void receiveMessage(){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))){

            String inputLine;
            if((inputLine = reader.readLine()) != null && !inputLine.equals("null")){
                setAddressTo(new Address(inputLine));
            }

            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()){
                    String json = stringBuilder.toString();
                    Message message = getMessageFromGson(json);
                    input.add(message);
                    stringBuilder = new StringBuilder();
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message getMessageFromGson(String json){
        TransferObject transferMessage = new Gson().fromJson(json, TransferObject.class);
        return (Message) transferMessage.getConvertData();
    }

    @Override
    public Address getAddressFrom() {
        return addressFrom;
    }

    @Override
    public Address getAddressTo() {
        return addressTo;
    }

    private void setAddressTo(Address addressTo){
        this.addressTo = addressTo;
        lock.lock();
        try{
            addressToIsReceived.signal();
        }finally{
            lock.unlock();
        }
    }

    @Blocks
    @Override
    public Address takeAddressTo() {
        lock.lock();
        try{
            while (addressTo == null) {
                addressToIsReceived.await();
            }
            addressToIsReceived.signal();
            return addressTo;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }
}
