package develop.marat.backend.db.services;

import develop.marat.backend.db.helpers.DBServiceHelper;
import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.ms_clients_common.db.services.UsersService;

import java.util.List;
import java.util.Optional;

public class UsersServiceImpl implements UsersService {
    private final DBService<UserDataSet> dbService;

    public UsersServiceImpl(DBService<UserDataSet> dbService) {
        this.dbService = dbService;
    }

    public void init() {
        DBServiceHelper.DBInit(dbService);
    }


    public boolean authenticate(String login, String password) {
        return Optional.ofNullable(dbService.readByLogin(login, UserDataSet.class))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public UserDataSet getUserById(long id) {
        return dbService.read(id, UserDataSet.class);
    }

    public UserDataSet getUserByLogin(String login) {
        return dbService.readByLogin(login, UserDataSet.class);
    }

    public List<UserDataSet> getUsers() {
        return dbService.readAll(UserDataSet.class);
    }

    public void save(UserDataSet user) {
        dbService.save(user);
    }
}
