package develop.marat.dbCommon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    public static Connection getPostgresqlConnection() {

        final String connString = "jdbc:postgresql://" +    // db type
                "localhost:" +                              // host name
                "5432/" +                                   // port
                "test?" +                                   // db name
                "user=postgres&" +                         // login
                "password=123456";                   // password

        try {
            return DriverManager.getConnection(connString);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
