package develop.marat.ms_clients_common.messages;

import develop.marat.ms_clients_common.services.BackendService;
import develop.marat.ms.core.Address;
import develop.marat.ms.core.Addressee;
import develop.marat.ms.core.Message;



public abstract class MsgToBackend extends Message {

    public MsgToBackend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public Object exec(Addressee addressee) {
        if (addressee instanceof BackendService) {
            return exec((BackendService) addressee);
        } else {
            throw new RuntimeException("Addressee type mismatch. Need type BackendService");
        }
    }

    public abstract Object exec(BackendService backendService);
}
