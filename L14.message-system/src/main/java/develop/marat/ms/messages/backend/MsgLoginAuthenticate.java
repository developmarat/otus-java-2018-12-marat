package develop.marat.ms.messages.backend;

import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.services.BackendService;

public class MsgLoginAuthenticate extends MsgToBackend {
    private String login;
    private String password;

    public MsgLoginAuthenticate(Address from, Address to, String login, String password) {
        super(from, to);
        this.login = login;
        this.password = password;
    }


    @Override
    public Boolean exec(BackendService backendService) {
        return backendService.getUserService().authenticate(login, password);
    }
}
