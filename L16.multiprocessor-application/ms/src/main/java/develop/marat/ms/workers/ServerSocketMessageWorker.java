package develop.marat.ms.workers;

import com.google.gson.Gson;
import develop.marat.ms.annotation.Blocks;
import develop.marat.ms.core.Address;
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

public class ServerSocketMessageWorker {
    private static final int WORKER_COUNT = 2;

    private final ExecutorService executorService;
    private final Socket socket;
    private volatile Address addressFrom;
    private volatile Address addressTo;

    private final BlockingQueue<TransferObject> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<TransferObject> input = new LinkedBlockingQueue<>();

    private final Lock lock = new ReentrantLock();
    private final Condition addressToIsReceived = lock.newCondition();


    public ServerSocketMessageWorker(Socket socket, Address addressFrom) {
        this.socket = socket;
        this.addressFrom = addressFrom;
        System.out.println(this.addressFrom);
        executorService = Executors.newFixedThreadPool(WORKER_COUNT);
    }

    public void init() {
        executorService.execute(this::sendMessage);
        executorService.execute(this::receiveMessage);
    }


    public TransferObject poll() {
        return input.poll();
    }

    public void send(TransferObject transferMessage) {
        output.add(transferMessage);
    }

    public TransferObject take() throws InterruptedException {
        return input.take();
    }

    public boolean isConnected(){
        return socket.isConnected();
    }

    public void close() throws IOException {
        socket.close();
        executorService.shutdown();
    }


    @Blocks
    private void sendMessage(){
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
            out.println(addressFrom);

            while (socket.isConnected()){
                TransferObject transferMessage = output.take();
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
        System.out.println(socket);
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
                    TransferObject transferMessage = new Gson().fromJson(json, TransferObject.class);
                    input.add(transferMessage);
                    stringBuilder = new StringBuilder();
                }
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

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
