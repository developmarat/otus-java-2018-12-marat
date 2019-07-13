package develop.marat.servlets;

import develop.marat.services.UsersService;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.HttpServlet;

public abstract class UsersServlet extends HttpServlet {

    protected final TemplateProcessor templateProcessor;
    protected final UsersService userService;

    public UsersServlet(TemplateProcessor templateProcessor, UsersService userService) {
        this.templateProcessor = templateProcessor;
        this.userService = userService;
    }

}
