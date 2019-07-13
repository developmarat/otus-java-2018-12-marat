package develop.marat.server.server.servlets;

import develop.marat.ms.core.TransferObject;
import develop.marat.server.server.auth.Auth;
import develop.marat.server.server.helpers.MessagesHelper;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends BaseServlet {
    private static final String TEMPLATE_PAGE = "admin.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, TransferObject> templateData = new HashMap<>();
        templateData.put("isLogin", new TransferObject(Auth.currentUserIsLogin(request)));
        templateData.put("isHavePermission", new TransferObject(Auth.currentUserIsAdmin(request)));

        String pageRender = MessagesHelper.takeRenderFrontend(msContext, TEMPLATE_PAGE, templateData);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }
}
