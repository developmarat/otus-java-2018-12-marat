package develop.marat.ms.messages;

import develop.marat.ms.services.FrontendService;
import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messageSystem.Addressee;
import develop.marat.ms.messageSystem.Message;



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