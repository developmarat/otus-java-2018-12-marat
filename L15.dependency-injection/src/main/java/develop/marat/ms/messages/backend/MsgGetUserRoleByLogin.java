package develop.marat.ms.messages.backend;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.services.BackendService;


public class MsgGetUserRoleByLogin extends MsgToBackend {
    private String login;

    public MsgGetUserRoleByLogin(Address from, Address to, String login) {
        super(from, to);
        this.login = login;
    }


    @Override
    public UserDataSet.Role exec(BackendService backendService) {
        return backendService.getUserService().getUserByLogin(login).getRole();
    }
}
