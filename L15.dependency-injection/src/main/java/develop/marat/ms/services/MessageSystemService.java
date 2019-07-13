package develop.marat.ms.services;

import develop.marat.db.services.UsersService;
import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messageSystem.MessageSystem;
import develop.marat.server.template.TemplateProcessor;

public class MessageSystemService {
    private MessageSystem messageSystem;
    private MessageSystemContext context;

    private TemplateProcessor templateProcessor;
    private UsersService usersService;

    public MessageSystemService(UsersService usersService, TemplateProcessor templateProcessor) {
        messageSystem = new MessageSystem();
        context = new MessageSystemContext(messageSystem);
        this.usersService = usersService;
        this.templateProcessor = templateProcessor;

        initFrontend();
        initBackend();

    }

    private void initFrontend() {
        Address frontendAddress = new Address("Frontend");
        context.setFrontendAddress(frontendAddress);
        FrontendService frontendService = new FrontendServiceImpl(context, frontendAddress, templateProcessor);
        frontendService.init();
    }

    private void initBackend() {
        Address backendAddress = new Address("Backend");
        context.setBackendAddress(backendAddress);
        BackendService backendService = new BackendServiceImpl(context, backendAddress, usersService);
        backendService.init();
    }

    public void start() {
        messageSystem.start();
    }

    public void dispose() {
        messageSystem.dispose();
    }

    public MessageSystemContext getContext(){
        return context;
    }
}
