package develop.marat.servlets;

import develop.marat.services.UsersService;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;

public class UsersListServlet extends UsersServlet {

    private static final String USERS_TEMPLATE_PAGE = "users.html";

    public UsersListServlet(TemplateProcessor templateProcessor, UsersService userService) {
        super(templateProcessor, userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("users", userService.getUsers());

        response.getWriter().println(templateProcessor.getPage(USERS_TEMPLATE_PAGE, templateData));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
