package develop.marat.ms.messages.backend;

import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.services.BackendService;

public class MsgGetCountUser extends MsgToBackend {

    public MsgGetCountUser(Address from, Address to) {
        super(from, to);
    }


    @Override
    public Integer exec(BackendService backendService) {
        return backendService.getUserService().getUsers().size();
    }
}
