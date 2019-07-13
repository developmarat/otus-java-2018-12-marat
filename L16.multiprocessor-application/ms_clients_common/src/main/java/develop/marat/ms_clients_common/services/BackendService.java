package develop.marat.ms_clients_common.services;

import develop.marat.ms_clients_common.db.services.UsersService;
import develop.marat.ms.core.Addressee;

public interface BackendService extends Addressee {
    UsersService getUserService();
}
