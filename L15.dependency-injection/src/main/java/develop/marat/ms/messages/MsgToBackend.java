package develop.marat.ms.messages;

import develop.marat.ms.services.BackendService;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messageSystem.Addressee;
import develop.marat.ms.messageSystem.Message;



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
