package develop.marat.server.servlets;

import develop.marat.ms.messageSystem.MessageSystemContext;

import javax.servlet.http.HttpServlet;

public abstract class UsersServlet extends HttpServlet {

    protected final MessageSystemContext msContext;

    public UsersServlet(MessageSystemContext msContext) {
        this.msContext = msContext;
    }

}
