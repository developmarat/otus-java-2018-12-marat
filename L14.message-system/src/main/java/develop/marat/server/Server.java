package develop.marat.server;

import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.server.filters.AdminFilter;
import develop.marat.server.servlets.*;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Server {
    private MessageSystemContext messageSystemContext;
    private org.eclipse.jetty.server.Server server;

    public Server(int port, MessageSystemContext messageSystemContext){
        server = new org.eclipse.jetty.server.Server(port);
        this.messageSystemContext = messageSystemContext;

        initServer();
    }

    private void initServer(){
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new AdminServlet(messageSystemContext)), "/admin");
        context.addServlet(new ServletHolder(new LoginServlet(messageSystemContext)), "/admin/login");
        context.addServlet(new ServletHolder(new UsersListServlet(messageSystemContext)), "/admin/users");
        context.addServlet(new ServletHolder(new UsersGetByIdServlet(messageSystemContext)), "/admin/users/get_by_id");
        context.addServlet(new ServletHolder(new UsersCountServlet(messageSystemContext)), "/admin/users/count");
        context.addServlet(new ServletHolder(new UsersAdd(messageSystemContext)), "/admin/users/add");

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
        server.stop();
    }
}
