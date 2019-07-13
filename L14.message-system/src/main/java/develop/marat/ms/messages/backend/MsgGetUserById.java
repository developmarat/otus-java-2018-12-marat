package develop.marat.ms.messages.backend;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.services.BackendService;

public class MsgGetUserById extends MsgToBackend {
    private long id;

    public MsgGetUserById(Address from, Address to, long id) {
        super(from, to);
        this.id = id;
    }


    @Override
    public UserDataSet exec(BackendService backendService) {
        return backendService.getUserService().getUserById(id);
    }
}
