package develop.marat.servlets;

import develop.marat.services.UsersService;
import develop.marat.models.UserDataSet;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsersGetByIdServlet extends UsersServlet {

    private static final String FORM_TEMPLATE_PAGE = "users_get_by_id.html";
    private static final String USER_NOT_FIND_TEMPLATE_PAGE = "users_not_found_by_id.html";
    private static final String USER_TEMPLATE_PAGE = "users_item.html";

    public UsersGetByIdServlet(TemplateProcessor templateProcessor, UsersService userService) {
        super(templateProcessor, userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if(request.getParameter("id") != null && Long.parseLong(request.getParameter("id")) > 0){
            long id = Long.parseLong(request.getParameter("id"));
            UserDataSet user = userService.getUserByLogin(id);
            if(user != null){
                Map<String, Object> templateData = new HashMap<>();
                templateData.put("user", user);
                response.getWriter().println(templateProcessor.getPage(USER_TEMPLATE_PAGE, templateData));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                response.getWriter().println(templateProcessor.getPage(USER_NOT_FIND_TEMPLATE_PAGE));
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else{
            response.getWriter().println(templateProcessor.getPage(FORM_TEMPLATE_PAGE));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
