package develop.marat.servlets;

import develop.marat.services.UsersService;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsersCountServlet extends UsersServlet {

    private static final String USERS_COUNT_TEMPLATE_PAGE = "users_count.html";

    public UsersCountServlet(TemplateProcessor templateProcessor, UsersService userService) {
        super(templateProcessor, userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("count", userService.getUsers().size());

        response.getWriter().println(templateProcessor.getPage(USERS_COUNT_TEMPLATE_PAGE, templateData));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
