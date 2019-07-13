package develop.marat.server.helpers;

import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messages.MsgToFrontend;
import develop.marat.ms.messages.frontend.MsgRenderTemplate;

import java.util.Map;

public class MessagesHelper {
    public static String takeRenderFrontend(MessageSystemContext msContext, String templateFileName){
        MsgToFrontend msgRender = new MsgRenderTemplate(null, msContext.getFrontendAddress(), templateFileName);
        msContext.getMessageSystem().sendMessage(msgRender);

        return msContext.getMessageSystem().takeMessageResult(msgRender);
    }

    public static String takeRenderFrontend(MessageSystemContext msContext, String templateFileName, Map<String, Object> templateData){
        MsgToFrontend msgRender = new MsgRenderTemplate(null, msContext.getFrontendAddress(), templateFileName, templateData);
        msContext.getMessageSystem().sendMessage(msgRender);

        return msContext.getMessageSystem().takeMessageResult(msgRender);
    }
}
