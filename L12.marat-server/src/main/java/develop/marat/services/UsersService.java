package develop.marat.services;

import develop.marat.models.UserDataSet;

import java.util.List;
import java.util.Optional;

public class UsersService {
    private final DBService<UserDataSet> dbService;

    public UsersService(DBService<UserDataSet> dbService) {
        this.dbService = dbService;
    }

    public boolean authenticate(String login, String password) {
        return Optional.ofNullable(dbService.readByLogin(login, UserDataSet.class))
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public UserDataSet getUserByLogin(long id) {
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
