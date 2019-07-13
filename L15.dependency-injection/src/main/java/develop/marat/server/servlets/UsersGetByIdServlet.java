package develop.marat.server.servlets;

import develop.marat.db.models.UserDataSet;
import develop.marat.ms.messages.MsgToBackend;
import develop.marat.ms.messages.backend.MsgGetUserById;
import develop.marat.server.helpers.MessagesHelper;
import develop.marat.server.helpers.UsersRequestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsersGetByIdServlet extends BaseServlet {

    private static final String FORM_TEMPLATE_PAGE = "users_get_by_id.html";
    private static final String USER_NOT_FIND_TEMPLATE_PAGE = "users_not_found_by_id.html";
    private static final String USER_TEMPLATE_PAGE = "users_item.html";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pageRender;

        if (UsersRequestHelper.hasUserId(request) && UsersRequestHelper.getUserId(request) > 0) {
            final long userId = UsersRequestHelper.getUserId(request);

            MsgToBackend msgGetUserById = new MsgGetUserById(null, msContext.getBackendAddress(), userId);
            msContext.getMessageSystem().sendMessage(msgGetUserById);

            UserDataSet user = msContext.getMessageSystem().takeMessageResult(msgGetUserById);

            if (user != null) {
                Map<String, Object> templateData = new HashMap<>();
                templateData.put("user", user);
                pageRender = MessagesHelper.takeRenderFrontend(msContext, USER_TEMPLATE_PAGE, templateData);
            } else {
                pageRender = MessagesHelper.takeRenderFrontend(msContext, USER_NOT_FIND_TEMPLATE_PAGE);
            }

        } else {
            pageRender = MessagesHelper.takeRenderFrontend(msContext, FORM_TEMPLATE_PAGE);
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(pageRender);
    }

}
