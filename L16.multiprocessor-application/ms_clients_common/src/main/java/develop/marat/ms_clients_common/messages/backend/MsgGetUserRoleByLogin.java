package develop.marat.ms_clients_common.messages.backend;

import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.services.BackendService;


public class MsgGetUserRoleByLogin extends MsgToBackend {
    private String login;

    public MsgGetUserRoleByLogin(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }


    @Override
    public String exec(BackendService backendService) {
        return backendService.getUserService().getUserByLogin(login).getRole().toString();
    }
}
