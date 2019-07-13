package develop.marat.server.controller;

import develop.marat.ms_clients_common.AddressEnum;
import develop.marat.ms_clients_common.SocketClientExecutor;
import develop.marat.ms.core.Address;
import develop.marat.ms.core.Message;
import develop.marat.ms_clients_common.services.ControllerService;

import java.io.IOException;

public class ControllerExecutor {

    private final Address address;
    private final ControllerService controllerService;
    private final SocketClientExecutor clientExecutor;

    public ControllerExecutor(String socketServerHost, int socketServerPort, String contextPath) throws IOException {
        address = new Address(AddressEnum.Controller.toString() + contextPath);
        controllerService = createControllerService();
        clientExecutor = new SocketClientExecutor(socketServerHost, socketServerPort, controllerService);
    }

    public Address getAddress(){
        return address;
    }

    public void start(){
        clientExecutor.start();
    }

    public void shutdown() {
        clientExecutor.shutdown();
    }

    public void sendMessage(Message message){
        clientExecutor.sendMessage(message);
    }

    public <T> T takeMessageResult(Message message) {
        return controllerService.takeMessageResult(message);
    }

    private ControllerService createControllerService(){
        return new ControllerServiceImpl(address);
    }
}
