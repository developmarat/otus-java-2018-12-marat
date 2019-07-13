package develop.marat.server;

import develop.marat.models.UserDataSet;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Auth {

    private static final String ROLE_SESSION_ATTRIBUTE = "role";
    private static final int ROLE_SESSION_EXPIRE_INTERVAL = 600;

    private HttpServletRequest request;

    public Auth(@NotNull HttpServletRequest request) {
        this.request = request;
    }

    public void setRole(UserDataSet.Role role) {
        HttpSession session = request.getSession();
        session.setAttribute(ROLE_SESSION_ATTRIBUTE, role);
        session.setMaxInactiveInterval(ROLE_SESSION_EXPIRE_INTERVAL);
    }

    public UserDataSet.Role getRole() {
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(ROLE_SESSION_ATTRIBUTE) != null){
            return (UserDataSet.Role)session.getAttribute(ROLE_SESSION_ATTRIBUTE);
        }
        else{
            return null;
        }
    }

    public boolean isLogin() {
        HttpSession session = request.getSession(false);
        return session != null;
    }

    public boolean isAdmin() {
        UserDataSet.Role role = getRole();
        return role == UserDataSet.Role.ADMIN;
    }

    public static void setRoleToCurrentUser(HttpServletRequest request, @NotNull UserDataSet.Role role) {
        Auth auth = new Auth(request);
        auth.setRole(role);
    }

    public static boolean currentUserIsLogin(HttpServletRequest request) {
        Auth auth = new Auth(request);
        return auth.isLogin();
    }

    public static boolean currentUserIsAdmin(HttpServletRequest request) {
        Auth auth = new Auth(request);
        return auth.isAdmin();
    }


}
