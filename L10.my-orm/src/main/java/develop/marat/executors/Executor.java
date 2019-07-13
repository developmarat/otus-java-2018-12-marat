package develop.marat.executors;

import develop.marat.dao.DAO;
import develop.marat.dao.DAOFactory;
import develop.marat.models.DataSet;

import java.sql.*;

public class Executor {

    private final Connection connection;
    private DAOFactory daoFactory;

    public Executor(Connection connection) {
        this.connection = connection;
        daoFactory = new DAOFactory(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataSet> void save(T user) throws SQLException {
        DAO<T> dao = daoFactory.createDAO(user.getClass());
        dao.create(user);
    }

    @SuppressWarnings("unchecked")
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        DAO<T> dao = daoFactory.createDAO(clazz);
        return dao.getById(id);
    }

    public <T> T query(String query, ResultHandler<T> handler) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            final ResultSet result = statement.executeQuery(query);
            return handler.handle(result);
        }
    }

    public void update(String update) throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(update);
        }
    }
}
