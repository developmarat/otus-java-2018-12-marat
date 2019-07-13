package develop.marat;

import develop.marat.db.ConnectionHelper;
import develop.marat.db.DBService;
import develop.marat.db.DBServiceImpl;
import develop.marat.executors.Executor;
import develop.marat.models.DataSet;
import develop.marat.models.UserDataSet;

import java.sql.Connection;
import java.sql.SQLException;

public class MyOrmMain {
    public static void main(String[] args) {
        try (final Connection connection = ConnectionHelper.getPostgresqlConnection()) {
            final DBService dbService = new DBServiceImpl(connection);
            dbService.prepareTables();

            Executor executor = new Executor(connection);

            DataSet empty = new DataSet();
            executor.save(empty);

            UserDataSet userOne = new UserDataSet("Bill", 20);
            executor.save(userOne);

            Long loadId = 2L;
            DataSet simply = executor.load(loadId, DataSet.class);
            System.out.println(simply);
            UserDataSet full = executor.load(loadId, UserDataSet.class);
            System.out.println(full);

            //dbService.deleteTables();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
