package develop.marat.ms_clients_common.messages.backend;

import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.services.BackendService;

public class MsgGetCountUser extends MsgToBackend {

    public MsgGetCountUser(Address from, Address to) {
        super(from, to);
    }


    @Override
    public Integer exec(BackendService backendService) {
        return backendService.getUserService().getUsers().size();
    }
}
