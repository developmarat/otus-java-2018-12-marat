package develop.marat.ms_clients_common.messages.backend;

import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.services.BackendService;

public class MsgGetUserById extends MsgToBackend {
    private long userId;

    public MsgGetUserById(Address from, Address to, long userId) {
        super(from, to);
        this.userId = userId;
    }


    @Override
    public UserDataSet exec(BackendService backendService) {
        return backendService.getUserService().getUserById(userId);
    }
}
