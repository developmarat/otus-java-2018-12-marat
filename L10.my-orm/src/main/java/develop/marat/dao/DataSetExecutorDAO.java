package develop.marat.dao;

import develop.marat.executors.Executor;
import develop.marat.models.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataSetExecutorDAO extends BaseDAO<DataSet> {
    private static final String TABLE_NAME = "users";

    DataSetExecutorDAO(Executor executor) {
        super(executor);
    }

    @Override
    public void create(DataSet data) throws SQLException {
        executor.update("INSERT INTO " + TABLE_NAME + " DEFAULT VALUES;");
    }

    @Override
    public DataSet getById(long id) throws SQLException {
        return executor.query("SELECT id FROM " + TABLE_NAME + " WHERE id = " + id + ';', DataSetExecutorDAO::extract);
    }

    @Override
    public List<DataSet> getAll() throws SQLException {
        return executor.query("SELECT id FROM " + TABLE_NAME + ';', DataSetExecutorDAO::extractList);
    }

    @Override
    public void update(DataSet user) throws SQLException {
    }

    @Override
    public void delete(int id) throws SQLException {
        executor.update("DELETE FROM " + TABLE_NAME + " WHERE id = " + id + ';');
    }

    private static DataSet create(ResultSet resultSet) throws SQLException {
        return new DataSet(resultSet.getLong("id"));
    }


    private static DataSet extract(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        return create(resultSet);
    }

    private static List<DataSet> extractList(ResultSet resultSet) throws SQLException {
        final List<DataSet> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(create(resultSet));
        }
        return result;
    }

}

