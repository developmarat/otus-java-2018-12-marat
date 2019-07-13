package develop.marat.ms.messages.frontend;

import develop.marat.ms.messageSystem.Address;
import develop.marat.ms.messages.MsgToFrontend;
import develop.marat.ms.services.FrontendService;

import java.util.Map;

public class MsgRenderTemplate extends MsgToFrontend {

    protected String templateFileName;
    private Map<String, Object> templateData;

    public MsgRenderTemplate(Address from, Address to, String templateFileName, Map<String, Object> templateData) {
        super(from, to);
        this.templateFileName = templateFileName;
        this.templateData = templateData;
    }

    public MsgRenderTemplate(Address from, Address to, String templateFileName) {
        this(from, to, templateFileName, null);
    }

    @Override
    public String exec(FrontendService frontendService) {
        return frontendService.render(templateFileName, templateData);
    }
}