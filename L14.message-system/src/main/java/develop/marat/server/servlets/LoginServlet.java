package develop.marat.server.servlets;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.messages.backend.MsgLoginAuthenticate;
import develop.marat.ms.messages.backend.MsgGetUserRoleByLogin;
import develop.marat.server.auth.Auth;
import develop.marat.server.helpers.MessagesHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final String FORM_TEMPLATE_PAGE = "login.html";
    private static final String SUCCESS_LOGIN_TEMPLATE_PAGE = "success_login.html";
    private static final String ERROR_LOGIN_TEMPLATE_PAGE = "error_login.html";

    private final MessageSystemContext msContext;

    public LoginServlet(MessageSystemContext msContext) {
        this.msContext = msContext;
    }

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

        MsgToBackend msgToBackendLogin = new MsgLoginAuthenticate(null, msContext.getBackendAddress(), login, password);
        msContext.getMessageSystem().sendMessage(msgToBackendLogin);
        boolean isAuthenticate = msContext.getMessageSystem().takeMessageResult(msgToBackendLogin);

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
        MsgToBackend msgGetUserRole = new MsgGetUserRoleByLogin(null, msContext.getBackendAddress(), login);
        msContext.getMessageSystem().sendMessage(msgGetUserRole);
        UserDataSet.Role role = msContext.getMessageSystem().takeMessageResult(msgGetUserRole);
        Auth.setRoleToCurrentUser(request, role);
    }
}
