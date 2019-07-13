package develop.marat.server.server.servlets;

import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms.core.TransferObject;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.messages.backend.MsgGetUsers;
import develop.marat.server.server.helpers.MessagesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class UsersListServlet extends BaseServlet {

    private static final String USERS_TEMPLATE_PAGE = "users.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        MsgToBackend msgGetUsers = new MsgGetUsers(msContext.getControllerAddress(), msContext.getBackendAddress());
        msContext.getController().sendMessage(msgGetUsers);

        List<UserDataSet> users = msContext.getController().takeMessageResult(msgGetUsers);
        Map<String, TransferObject> templateData = new HashMap<>();
        templateData.put("users", new TransferObject(users));

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, USERS_TEMPLATE_PAGE, templateData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);

    }
}
