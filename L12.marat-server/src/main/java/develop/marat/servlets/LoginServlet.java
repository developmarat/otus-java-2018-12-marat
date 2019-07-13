package develop.marat.servlets;

import develop.marat.services.UsersService;
import develop.marat.server.Auth;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final String FORM_TEMPLATE_PAGE = "login.html";
    private static final String SUCCESS_LOGIN_TEMPLATE_PAGE = "success_login.html";
    private static final String ERROR_LOGIN_TEMPLATE_PAGE = "error_login.html";

    private final TemplateProcessor templateProcessor;
    private final UsersService userService;

    public LoginServlet(TemplateProcessor templateProcessor, UsersService userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(templateProcessor.getPage(FORM_TEMPLATE_PAGE));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (userService.authenticate(login, password)) {
            Auth.setRoleToCurrentUser(request, userService.getUserByLogin(login).getRole());

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(templateProcessor.getPage(SUCCESS_LOGIN_TEMPLATE_PAGE));
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().println(templateProcessor.getPage(ERROR_LOGIN_TEMPLATE_PAGE));
        }
    }
}
