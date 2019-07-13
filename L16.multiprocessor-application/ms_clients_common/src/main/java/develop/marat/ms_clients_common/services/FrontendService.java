package develop.marat.ms_clients_common.services;

import develop.marat.ms.core.TransferObject;
import develop.marat.ms.core.Addressee;
import java.util.Map;


public interface FrontendService extends Addressee {
    String render(String templateFileName, Map<String, TransferObject> templateData);
}

