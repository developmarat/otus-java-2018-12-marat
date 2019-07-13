package develop.marat.backend;

import develop.marat.ms.core.Address;
import develop.marat.ms.core.BaseMSService;
import develop.marat.ms_clients_common.db.services.UsersService;
import develop.marat.ms_clients_common.services.BackendService;

public class BackendServiceImpl extends BaseMSService implements BackendService {
    private final UsersService usersService;

    public BackendServiceImpl(Address address, UsersService usersService) {
        super(address);
        this.usersService = usersService;
    }

    @Override
    public UsersService getUserService() {
        return usersService;
    }

}
