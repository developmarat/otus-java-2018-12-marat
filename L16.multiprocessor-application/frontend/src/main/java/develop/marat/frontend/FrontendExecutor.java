package develop.marat.frontend;


import develop.marat.frontend.template.TemplateProcessorImpl;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.AddressEnum;
import develop.marat.ms_clients_common.SocketClientExecutor;
import develop.marat.ms_clients_common.services.FrontendService;

import java.io.IOException;

public class FrontendExecutor {

    private FrontendService frontendService;
    private SocketClientExecutor socketClientExecutor;

    public FrontendExecutor(String socketServerHost, int socketServerPort) throws IOException {
        frontendService = createFrontendService();
        socketClientExecutor = new SocketClientExecutor(socketServerHost, socketServerPort, frontendService);
    }

    public static void main(String[] args){
        String socketServerHost = args[0];
        int socketServerPort = Integer.parseInt(args[1]);
        try {
            new FrontendExecutor(socketServerHost, socketServerPort).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){

        socketClientExecutor.start();
    }

    public void shutdown() {
        socketClientExecutor.shutdown();
    }

    private FrontendService createFrontendService(){
        TemplateProcessorImpl templateProcessor = new TemplateProcessorImpl();
        return new FrontendServiceImpl(new Address(AddressEnum.Frontend.toString()), templateProcessor);
    }
}
