package develop.marat.server.server.servlets;

import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.messages.backend.MsgLoginAuthenticate;
import develop.marat.ms_clients_common.messages.backend.MsgGetUserRoleByLogin;
import develop.marat.server.server.auth.Auth;
import develop.marat.server.server.helpers.MessagesHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends BaseServlet {
    private static final String FORM_TEMPLATE_PAGE = "login.html";
    private static final String SUCCESS_LOGIN_TEMPLATE_PAGE = "success_login.html";
    private static final String ERROR_LOGIN_TEMPLATE_PAGE = "error_login.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageRender = MessagesHelper.takeRenderFrontend(msContext, FORM_TEMPLATE_PAGE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        MsgToBackend msgToBackendLogin = new MsgLoginAuthenticate(msContext.getControllerAddress(), msContext.getBackendAddress(), login, password);
        msContext.getController().sendMessage(msgToBackendLogin);
        boolean isAuthenticate = msContext.getController().takeMessageResult(msgToBackendLogin);

        String pageRender;
        if(isAuthenticate){
            setRoleToCurrentUser(request, login);

            pageRender = MessagesHelper.takeRenderFrontend(msContext, SUCCESS_LOGIN_TEMPLATE_PAGE);
            response.setStatus(HttpServletResponse.SC_OK);

        }else{
            pageRender = MessagesHelper.takeRenderFrontend(msContext, ERROR_LOGIN_TEMPLATE_PAGE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        response.getWriter().println(pageRender);
    }

    private void setRoleToCurrentUser(HttpServletRequest request, String login){
        MsgToBackend msgGetUserRole = new MsgGetUserRoleByLogin(msContext.getControllerAddress(), msContext.getBackendAddress(), login);
        msContext.getController().sendMessage(msgGetUserRole);
        String roleName = msContext.getController().takeMessageResult(msgGetUserRole);
        UserDataSet.Role role = UserDataSet.Role.valueOf(roleName);
        Auth.setRoleToCurrentUser(request, role);
    }
}
