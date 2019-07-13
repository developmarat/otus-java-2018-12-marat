package develop.marat.servlets;

import develop.marat.services.UsersService;
import develop.marat.models.AddressDataSet;
import develop.marat.models.PhoneDataSet;
import develop.marat.models.UserDataSet;
import develop.marat.template.TemplateProcessor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersAdd extends UsersServlet {

    private static final String ADD_USER_TEMPLATE_PAGE = "users_add.html";
    private static final String SUCCESS_ADD_USER_TEMPLATE_PAGE = "users_add_success.html";

    public UsersAdd(TemplateProcessor templateProcessor, UsersService userService) {
        super(templateProcessor, userService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(templateProcessor.getPage(ADD_USER_TEMPLATE_PAGE));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        int age = Integer.parseInt(request.getParameter("age"));
        String street = request.getParameter("street");
        AddressDataSet address = new AddressDataSet(street);

        String[] phonesParams = {"phone1", "phone2", "phone3"};
        List<PhoneDataSet> phones = new ArrayList<>();
        for (String phoneParam: phonesParams){
            String phoneNumber = request.getParameter(phoneParam);
            if(phoneNumber != null && !phoneNumber.isEmpty()){
                phones.add(new PhoneDataSet(phoneNumber));
            }
        }

        UserDataSet user = new UserDataSet(login, name, password, age, address, phones);
        userService.save(user);

        response.getWriter().println(templateProcessor.getPage(SUCCESS_ADD_USER_TEMPLATE_PAGE));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
