package develop.marat.server.servlets;

import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.services.MessageSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {

    @Autowired
    private MessageSystemService messageSystemService;

    protected MessageSystemContext msContext;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());

        msContext = messageSystemService.getContext();
    }

}
