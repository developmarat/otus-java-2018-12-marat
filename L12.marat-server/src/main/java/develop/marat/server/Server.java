package develop.marat.server;

import develop.marat.services.DBService;
import develop.marat.services.DBServiceHibernateImpl;
import develop.marat.services.UsersService;
import develop.marat.filters.AdminFilter;
import develop.marat.helpers.DBServiceHelper;
import develop.marat.helpers.HibernateHelper;
import develop.marat.models.UserDataSet;
import develop.marat.servlets.*;
import develop.marat.template.TemplateProcessor;
import develop.marat.template.TemplateProcessorImpl;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Server {
    private org.eclipse.jetty.server.Server server;
    private DBService<UserDataSet> dbService;
    private TemplateProcessor templateProcessor;
    private UsersService usersService;

    public Server(int port){
        server = new org.eclipse.jetty.server.Server(port);
        dbService = new DBServiceHibernateImpl<>(HibernateHelper.getConfiguration());
        templateProcessor = new TemplateProcessorImpl();
        usersService = new UsersService(dbService);

        DBServiceHelper.DBInit(dbService);

        initServer();
    }

    private void initServer(){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AdminServlet(templateProcessor)), "/admin");
        context.addServlet(new ServletHolder(new LoginServlet(templateProcessor, usersService)), "/admin/login");
        context.addServlet(new ServletHolder(new UsersListServlet(templateProcessor, usersService)), "/admin/users");
        context.addServlet(new ServletHolder(new UsersGetByIdServlet(templateProcessor, usersService)), "/admin/users/get_by_id");
        context.addServlet(new ServletHolder(new UsersCountServlet(templateProcessor, usersService)), "/admin/users/count");
        context.addServlet(new ServletHolder(new UsersAdd(templateProcessor, usersService)), "/admin/users/add");

        context.addFilter(new FilterHolder(new AdminFilter()), "/admin/users/*", null);

        server.setHandler(new HandlerList(context));
    }

    public void start(){
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws Exception {
        dbService.shutdown();
        server.stop();
    }
}
