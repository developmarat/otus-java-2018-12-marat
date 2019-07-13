package develop.marat.dbServices;

import develop.marat.dao.UsersDAO;
import develop.marat.dao.UsersDataSetDAO;
import develop.marat.executors.Executor;
import develop.marat.models.DataSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBServiceImpl<T extends DataSet> implements DBService<T> {
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS users (\n" +
            "  id        BIGSERIAL NOT NULL PRIMARY KEY,\n" +
            "  name VARCHAR(255),\n" +
            "  age       SMALLINT\n" +
            ");";

    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS users;";

    private final Connection connection;
    private final Executor executor;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
        executor = new Executor(connection);
    }

    public void prepareTables() {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTables() {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_USER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(T user) {
        UsersDAO<T> dao = new UsersDataSetDAO(executor, user.getClass());
        dao.save(user);
    }

    @Override
    public T read(long id, Class<T> type) {
        UsersDAO<T> dao = new UsersDataSetDAO<>(executor, type);
        return dao.read(id);
    }

    @Override
    public T readByName(String name, Class<T> type) {
        UsersDAO<T> dao = new UsersDataSetDAO<>(executor, type);
        return dao.readByName(name);
    }

    @Override
    public List<T> readAll(Class<T> type) {
        UsersDAO<T> dao = new UsersDataSetDAO<>(executor, type);
        return dao.readAll();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(T user) {
        UsersDAO<T> dao = new UsersDataSetDAO(executor, user.getClass());
        dao.update(user);
    }

    @Override
    public void delete(long id, Class<T> type) {
        UsersDAO<T> dao = new UsersDataSetDAO<>(executor, type);
        dao.delete(id);
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

