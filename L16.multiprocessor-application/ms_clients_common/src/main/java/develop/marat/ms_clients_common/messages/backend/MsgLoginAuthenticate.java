package develop.marat.ms_clients_common.messages.backend;

import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.services.BackendService;

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
