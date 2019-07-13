package develop.marat.ms_clients_common.messages;

import develop.marat.ms.core.Address;
import develop.marat.ms.core.Addressee;
import develop.marat.ms.core.Message;
import develop.marat.ms_clients_common.services.ControllerService;


public abstract class MsgToController extends Message {

    public MsgToController(Address from, Address to) {
        super(from, to);
    }

    @Override
    public Object exec(Addressee addressee) {
        if (addressee instanceof ControllerService) {
            return exec((ControllerService) addressee);
        } else {
            throw new RuntimeException("Addressee type mismatch. Need type ControllerService");
        }
    }

    public abstract Object exec(ControllerService controllerService);
}