package develop.marat.ms_clients_common.messages.controller;

import develop.marat.ms.core.TransferObject;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToController;
import develop.marat.ms_clients_common.services.ControllerService;

import java.util.UUID;

public class MsgSetResult extends MsgToController {
    private UUID messageId;
    private TransferObject result;

    public MsgSetResult(Address from, Address to, UUID messageId, Object result) {
        super(from, to);
        this.messageId = messageId;
        this.result = new TransferObject(result);
    }

    @Override
    public Void exec(ControllerService controllerService){
        controllerService.setResult(messageId, result);
        return null;
    }
}