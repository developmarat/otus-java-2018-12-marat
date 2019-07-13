package develop.marat.server.server.servlets;

import develop.marat.ms.core.TransferObject;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.messages.backend.MsgGetCountUser;
import develop.marat.server.server.helpers.MessagesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsersCountServlet extends BaseServlet {

    private static final String USERS_COUNT_TEMPLATE_PAGE = "users_count.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsgToBackend msgGetCountUser = new MsgGetCountUser(msContext.getControllerAddress(), msContext.getBackendAddress());
        msContext.getController().sendMessage(msgGetCountUser);

        int countUser = msContext.getController().takeMessageResult(msgGetCountUser);
        Map<String, TransferObject> templateData = new HashMap<>();
        templateData.put("count", new TransferObject(countUser));

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, USERS_COUNT_TEMPLATE_PAGE, templateData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }
}
