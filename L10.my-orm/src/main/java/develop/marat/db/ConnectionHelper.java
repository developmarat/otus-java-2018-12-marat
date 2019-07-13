package develop.marat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getPostgresqlConnection() throws SQLException {

        final String connString = "jdbc:postgresql://" +    // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "test?" +                                   // db name
                "user=postgres&" +                         // login
                "password=123456";                   // password

        return DriverManager.getConnection(connString);
    }

}
