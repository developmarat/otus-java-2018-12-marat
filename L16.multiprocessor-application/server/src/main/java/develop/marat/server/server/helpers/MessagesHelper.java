package develop.marat.server.server.helpers;

import develop.marat.ms.core.TransferObject;
import develop.marat.server.ms.MessageSystemContext;
import develop.marat.ms_clients_common.messages.MsgToFrontend;
import develop.marat.ms_clients_common.messages.frontend.MsgRenderTemplate;

import java.util.Map;

public class MessagesHelper {
    public static String takeRenderFrontend(MessageSystemContext msContext, String templateFileName){
        MsgToFrontend msgRender = new MsgRenderTemplate(msContext.getControllerAddress(), msContext.getFrontendAddress(), templateFileName);
        msContext.getController().sendMessage(msgRender);

        return msContext.getController().takeMessageResult(msgRender);
    }

    public static String takeRenderFrontend(MessageSystemContext msContext, String templateFileName, Map<String, TransferObject> templateData){
        MsgToFrontend msgRender = new MsgRenderTemplate(msContext.getControllerAddress(), msContext.getFrontendAddress(), templateFileName, templateData);
        msContext.getController().sendMessage(msgRender);

        return msContext.getController().takeMessageResult(msgRender);
    }
}
