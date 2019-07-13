package develop.marat.ms_clients_common.messages;

import develop.marat.ms_clients_common.services.FrontendService;
import develop.marat.ms.core.Address;
import develop.marat.ms.core.Addressee;
import develop.marat.ms.core.Message;



public abstract class MsgToFrontend extends Message {
    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public Object exec(Addressee addressee) {
        if (addressee instanceof FrontendService) {
            return exec((FrontendService) addressee);
        } else {
            throw new RuntimeException("Addressee type mismatch. Need type FrontendService");
        }
    }

    public abstract Object exec(FrontendService frontendService);
}