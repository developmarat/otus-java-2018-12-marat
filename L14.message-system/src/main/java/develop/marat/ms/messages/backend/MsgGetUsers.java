package develop.marat.ms.messages.backend;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.services.BackendService;

import java.util.List;

public class MsgGetUsers extends MsgToBackend {

    public MsgGetUsers(Address from, Address to) {
        super(from, to);
    }


    @Override
    public List<UserDataSet> exec(BackendService backendService) {
        return backendService.getUserService().getUsers();
    }
}
