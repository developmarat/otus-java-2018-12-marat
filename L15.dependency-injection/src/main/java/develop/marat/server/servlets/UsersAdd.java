package develop.marat.server.servlets;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.messages.backend.MsgSaveUser;
import develop.marat.server.helpers.MessagesHelper;
import develop.marat.server.helpers.UsersRequestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersAdd extends BaseServlet {

    private static final String ADD_USER_TEMPLATE_PAGE = "users_add.html";
    private static final String SUCCESS_ADD_USER_TEMPLATE_PAGE = "users_add_success.html";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageRender = MessagesHelper.takeRenderFrontend(msContext, ADD_USER_TEMPLATE_PAGE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final UserDataSet user = UsersRequestHelper.getCreateUser(request);

        MsgToBackend msgSaveUser = new MsgSaveUser(null, msContext.getBackendAddress(), user);
        msContext.getMessageSystem().sendMessage(msgSaveUser);

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, SUCCESS_ADD_USER_TEMPLATE_PAGE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }

}
