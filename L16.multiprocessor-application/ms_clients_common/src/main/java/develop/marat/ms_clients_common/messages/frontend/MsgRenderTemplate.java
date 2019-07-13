package develop.marat.ms_clients_common.messages.frontend;

import develop.marat.ms.core.TransferObject;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToFrontend;
import develop.marat.ms_clients_common.services.FrontendService;

import java.util.Map;

public class MsgRenderTemplate extends MsgToFrontend {

    private String templateFileName;
    private Map<String, TransferObject> templateData;

    public MsgRenderTemplate(Address from, Address to, String templateFileName, Map<String, TransferObject> templateData) {
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