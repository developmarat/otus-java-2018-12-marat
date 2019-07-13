package develop.marat.ms.services;

import develop.marat.db.services.UsersService;
import develop.marat.ms.messageSystem.Addressee;

public interface BackendService extends Addressee {
    void init();

    UsersService getUserService();
}
