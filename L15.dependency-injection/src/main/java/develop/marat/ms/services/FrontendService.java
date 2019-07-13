package develop.marat.ms.services;

import develop.marat.ms.messageSystem.Addressee;

import java.util.Map;


public interface FrontendService extends Addressee {
    void init();

    String render(String templateFileName, Map<String, Object> templateData);
}

