package develop.marat.db.services;

import develop.marat.db.models.DataSet;

import java.util.List;

public interface DBService<T extends DataSet> {

    void save(T user);

    T read(long id, Class<T> type);

    T readByLogin(String login, Class<T> type);

    T readByName(String name, Class<T> type);

    List<T> readAll(Class<T> type);

    void update(T user);

    void delete(long id, Class<T> type);

    void shutdown();
}