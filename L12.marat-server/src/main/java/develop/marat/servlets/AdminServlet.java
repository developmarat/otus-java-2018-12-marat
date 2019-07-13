package develop.marat.servlets;

import develop.marat.server.Auth;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String TEMPLATE_PAGE = "admin.html";

    private final TemplateProcessor templateProcessor;

    public AdminServlet(TemplateProcessor templateProcessor) {
        this.templateProcessor = templateProcessor;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("isLogin", Auth.currentUserIsLogin(request));
        templateData.put("isHavePermission", Auth.currentUserIsAdmin(request));

        response.getWriter().println(templateProcessor.getPage(TEMPLATE_PAGE, templateData));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
