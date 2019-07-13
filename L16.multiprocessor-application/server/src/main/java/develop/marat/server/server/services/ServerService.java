package develop.marat.server.server.services;

import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.AddressEnum;
import develop.marat.server.ms.MessageSystemContext;
import develop.marat.server.controller.ControllerExecutor;

import java.io.IOException;

public class ServerService {
    private ControllerExecutor controllerExecutor;
    private MessageSystemContext messageSystemContext;

    public ServerService(String socketServerHost, int socketServerPort, String contextPath) throws IOException {
        controllerExecutor = new ControllerExecutor(socketServerHost, socketServerPort, contextPath);
        messageSystemContext = createAndInitMSContext();
    }

    public void init() {
        controllerExecutor.start();
    }

    public MessageSystemContext getMSContext(){
        return messageSystemContext;
    }

    public void shutdown(){
        controllerExecutor.shutdown();
    }


    private MessageSystemContext createAndInitMSContext(){
        MessageSystemContext msContext = new MessageSystemContext();
        msContext.setBackendAddress(new Address(AddressEnum.Backend.toString()));
        msContext.setFrontendAddress(new Address(AddressEnum.Frontend.toString()));
        msContext.setController(controllerExecutor);
        return msContext;
    }
}
