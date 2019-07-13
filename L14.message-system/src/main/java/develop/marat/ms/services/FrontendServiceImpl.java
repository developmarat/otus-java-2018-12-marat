package develop.marat.ms.services;

import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messageSystem.Address;
import develop.marat.server.template.TemplateProcessor;

import java.io.IOException;
import java.util.Map;


public class FrontendServiceImpl extends BaseMSService implements FrontendService {
    private final TemplateProcessor templateProcessor;


    public FrontendServiceImpl(MessageSystemContext context, Address address, TemplateProcessor templateProcessor) {
        super(context, address);
        this.templateProcessor = templateProcessor;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public String render(String templateFileName, Map<String, Object> templateData) {
        try {
            return this.templateProcessor.getPage(templateFileName, templateData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
