package develop.marat.executors;

import java.sql.*;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
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

    public <T> T queryPrepared(PreparedStatement statement, ResultHandler<T> handler) throws SQLException {
        try (statement) {
            final ResultSet resultSet = statement.executeQuery();
            return handler.handle(resultSet);
        }
    }

    public void updatePrepared(PreparedStatement statement) throws SQLException {
        try (statement) {
            statement.executeUpdate();
        }
    }
}
