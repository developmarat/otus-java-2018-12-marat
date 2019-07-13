package develop.marat.ms_clients_common.messages.backend;

import develop.marat.ms_clients_common.db.models.PhoneDataSet;
import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.messages.MsgToBackend;
import develop.marat.ms_clients_common.services.BackendService;

public class MsgSaveUser extends MsgToBackend {
    private UserDataSet user;

    public MsgSaveUser(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }


    @Override
    public Void exec(BackendService backendService) {
        setUserToPhones();
        backendService.getUserService().save(user);
        return null;
    }

    private void setUserToPhones(){
        if(user != null && !user.getPhones().isEmpty()){
            for (PhoneDataSet phone: user.getPhones()){
                phone.setUser(user);
            }
        }
    }
}
