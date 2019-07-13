package develop.marat.ms.messages.backend;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.services.BackendService;

public class MsgSaveUser extends MsgToBackend {
    private UserDataSet user;

    public MsgSaveUser(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }


    @Override
    public Void exec(BackendService backendService) {
        backendService.getUserService().save(user);
        return null;
    }
}
