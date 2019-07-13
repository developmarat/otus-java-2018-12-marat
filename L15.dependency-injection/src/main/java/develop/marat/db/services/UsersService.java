package develop.marat.db.services;

import develop.marat.db.models.UserDataSet;

import java.util.List;

public interface UsersService {
    boolean authenticate(String login, String password);

    UserDataSet getUserById(long id);

    UserDataSet getUserByLogin(String login);

    List<UserDataSet> getUsers();

    void save(UserDataSet user);
}
