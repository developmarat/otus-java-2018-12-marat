package develop.marat.server.controller;

import develop.marat.ms_clients_common.services.ControllerService;
import develop.marat.ms.annotation.Blocks;
import develop.marat.ms.core.Address;
import develop.marat.ms.core.BaseMSService;
import develop.marat.ms.core.Message;
import develop.marat.ms.core.TransferObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
public class ControllerServiceImpl extends BaseMSService implements ControllerService {
    private final ConcurrentHashMap<UUID, MessageResult> messagesResult;

    public ControllerServiceImpl(Address address){
        super(address);
        messagesResult = new ConcurrentHashMap<>();
    }

    public void setResult(UUID messageId, TransferObject result){
        getMessageResult(messageId).setResult(result.getConvertData());
    }

    @Blocks
    public <T> T takeMessageResult(Message message) {
        MessageResult messageResult = getMessageResult(message.getId());
        T result = messageResult.takeResult();
        messagesResult.remove(message.getId());
        return result;
    }

    private MessageResult getMessageResult(UUID messageId){
        if(messagesResult.get(messageId) == null){
            messagesResult.put(messageId, new MessageResult(messageId));
        }

        return messagesResult.get(messageId);
    }
}
