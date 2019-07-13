package develop.marat.server.server.helpers;

import develop.marat.ms_clients_common.db.models.AddressDataSet;
import develop.marat.ms_clients_common.db.models.PhoneDataSet;
import develop.marat.ms_clients_common.db.models.UserDataSet;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class UsersRequestHelper {

    public static boolean hasUserId(HttpServletRequest request){
        return request.getParameter("id") != null;
    }

    public static long getUserId(HttpServletRequest request){
        return Long.parseLong(request.getParameter("id"));
    }

    public static UserDataSet getCreateUser(HttpServletRequest request){
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

        return new UserDataSet(login, name, password, age, address, phones);
    }
}
