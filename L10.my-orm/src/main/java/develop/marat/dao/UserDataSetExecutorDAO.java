package develop.marat.dao;

import develop.marat.executors.Executor;
import develop.marat.models.UserDataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDataSetExecutorDAO extends BaseDAO<UserDataSet> {
    private static final String TABLE_NAME = "users";

    UserDataSetExecutorDAO(Executor executor) {
        super(executor);
    }

    @Override
    public void create(UserDataSet user) throws SQLException {
        executor.update("INSERT INTO " + TABLE_NAME + " (name, age) VALUES ('" + user.getName() + "', " + user.getAge() + ");");
    }

    @Override
    public UserDataSet getById(long id) throws SQLException {
        return executor.query("SELECT id, name, age FROM " + TABLE_NAME + " WHERE id = " + id + ';', UserDataSetExecutorDAO::extract);
    }

    @Override
    public List<UserDataSet> getAll() throws SQLException {
        return executor.query("SELECT id, name, age FROM " + TABLE_NAME + ';', UserDataSetExecutorDAO::extractList);
    }

    @Override
    public void update(UserDataSet user) throws SQLException {
        executor.update("UPDATE " + TABLE_NAME + " SET name = '" + user.getName() + "', age = " + user.getAge() + " WHERE id = " + user.getId() + ';');
    }

    @Override
    public void delete(int id) throws SQLException {
        executor.update("DELETE FROM " + TABLE_NAME + " WHERE id = " + id + ';');
    }

    private static UserDataSet create(ResultSet resultSet) throws SQLException {
        return new UserDataSet(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getInt("age"));
    }


    private static UserDataSet extract(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        return create(resultSet);
    }

    private static List<UserDataSet> extractList(ResultSet resultSet) throws SQLException {
        final List<UserDataSet> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(create(resultSet));
        }
        return result;
    }
}

