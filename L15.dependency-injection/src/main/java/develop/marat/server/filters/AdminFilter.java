package develop.marat.server.filters;

import develop.marat.server.auth.Auth;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(Auth.currentUserIsAdmin(request)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else{
            response.setStatus(403);
        }
    }

    @Override
    public void destroy() {

    }
}
