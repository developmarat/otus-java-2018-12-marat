package develop.marat.ms_clients_common.services;

import develop.marat.ms.core.TransferObject;
import develop.marat.ms.core.Addressee;
import develop.marat.ms.core.Message;

import java.util.UUID;

public interface ControllerService extends Addressee {
    void setResult(UUID messageId, TransferObject result);
    <T> T takeMessageResult(Message message);
}
