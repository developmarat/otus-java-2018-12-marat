package develop.marat.server.server.servlets;

import develop.marat.server.ms.MessageSystemContext;
import develop.marat.server.server.services.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {

    @Autowired
    private ServerService serverService;
    protected MessageSystemContext msContext;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        msContext = serverService.getMSContext();
    }


}
