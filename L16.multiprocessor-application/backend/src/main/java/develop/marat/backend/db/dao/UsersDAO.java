package develop.marat.backend.db.dao;


import develop.marat.ms_clients_common.db.models.DataSet;

import java.util.List;

public interface UsersDAO<T extends DataSet> {

    void save(T user);

    T read(long id);

    T readByLogin(String login);

    T readByName(String name);

    List<T> readAll();

    void update(T user);

    void delete(long id);
}