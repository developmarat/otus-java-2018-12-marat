package develop.marat.db;

import java.sql.SQLException;

public interface DBService {
    public void prepareTables() throws SQLException;
    public void deleteTables() throws SQLException;
}
