package develop.marat.server.servlets;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.messages.backend.MsgGetUsers;
import develop.marat.server.helpers.MessagesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class UsersListServlet extends UsersServlet {

    private static final String USERS_TEMPLATE_PAGE = "users.html";

    public UsersListServlet(MessageSystemContext msContext) {
        super(msContext);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MsgToBackend msgGetUsers = new MsgGetUsers(null, msContext.getBackendAddress());
        msContext.getMessageSystem().sendMessage(msgGetUsers);

        List<UserDataSet> users = msContext.getMessageSystem().takeMessageResult(msgGetUsers);
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("users", users);

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, USERS_TEMPLATE_PAGE, templateData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);

    }
}
