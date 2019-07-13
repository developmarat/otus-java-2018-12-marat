package develop.marat.ms_clients_common.messages.backend;

import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.services.BackendService;

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
