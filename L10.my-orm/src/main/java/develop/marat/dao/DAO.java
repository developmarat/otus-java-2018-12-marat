package develop.marat.dao;


import java.sql.SQLException;
import java.util.List;

public interface  DAO <T>{

    void create(T user) throws SQLException;

    T getById(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    void update(T user) throws SQLException;

    void delete(int id) throws SQLException;
}
