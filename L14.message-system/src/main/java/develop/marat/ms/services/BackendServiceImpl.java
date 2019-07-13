package develop.marat.ms.services;

import develop.marat.db.services.UsersService;
import develop.marat.ms.messageSystem.MessageSystemContext;
import develop.marat.ms.messageSystem.Address;

public class BackendServiceImpl extends BaseMSService implements BackendService{
    private final UsersService usersService;

    public BackendServiceImpl(MessageSystemContext context, Address address, UsersService usersService) {
        super(context, address);
        this.usersService = usersService;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public UsersService getUserService() {
        return usersService;
    }
}
