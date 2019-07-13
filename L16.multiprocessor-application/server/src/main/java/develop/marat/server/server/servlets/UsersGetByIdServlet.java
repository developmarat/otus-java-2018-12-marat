package develop.marat.server.server.servlets;

import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms.core.TransferObject;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.messages.backend.MsgGetUserById;
import develop.marat.server.server.helpers.MessagesHelper;
import develop.marat.server.server.helpers.UsersRequestHelper;

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

            MsgToBackend msgGetUserById = new MsgGetUserById(msContext.getControllerAddress(), msContext.getBackendAddress(), userId);
            msContext.getController().sendMessage(msgGetUserById);

            UserDataSet user = msContext.getController().takeMessageResult(msgGetUserById);

            if (user != null) {
                Map<String, TransferObject> templateData = new HashMap<>();
                templateData.put("user", new TransferObject(user));
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
