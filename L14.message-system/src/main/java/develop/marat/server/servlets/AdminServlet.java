package develop.marat.server.servlets;

import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.server.auth.Auth;
import develop.marat.server.helpers.MessagesHelper;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String TEMPLATE_PAGE = "admin.html";

    private final MessageSystemContext msContext;

    public AdminServlet(MessageSystemContext msContext) {
        this.msContext = msContext;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("isLogin", Auth.currentUserIsLogin(request));
        templateData.put("isHavePermission", Auth.currentUserIsAdmin(request));

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, TEMPLATE_PAGE, templateData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }
}
