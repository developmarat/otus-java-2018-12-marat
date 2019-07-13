package develop.marat.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBServiceImpl implements DBService {
    private static final String CREATE_TABLE_USER = "CREATE TABLE IF NOT EXISTS users (\n" +
            "  id        BIGSERIAL NOT NULL PRIMARY KEY,\n" +
            "  name VARCHAR(255),\n" +
            "  age       SMALLINT\n" +
            ");";

    private static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS users;";

    private final Connection connection;

    public DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void prepareTables() throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_USER);
        }
    }

    @Override
    public void deleteTables() throws SQLException {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE_USER);
        }
    }
}

