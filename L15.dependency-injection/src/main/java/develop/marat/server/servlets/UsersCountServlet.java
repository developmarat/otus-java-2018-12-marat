package develop.marat.server.servlets;

import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.messages.backend.MsgGetCountUser;
import develop.marat.server.helpers.MessagesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsersCountServlet extends BaseServlet {

    private static final String USERS_COUNT_TEMPLATE_PAGE = "users_count.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        MsgToBackend msgGetCountUser = new MsgGetCountUser(null, msContext.getBackendAddress());
        msContext.getMessageSystem().sendMessage(msgGetCountUser);

        int countUser = msContext.getMessageSystem().takeMessageResult(msgGetCountUser);
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("count", countUser);

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, USERS_COUNT_TEMPLATE_PAGE, templateData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }
}
