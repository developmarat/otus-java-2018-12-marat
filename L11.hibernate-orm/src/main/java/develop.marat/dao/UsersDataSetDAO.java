package develop.marat.dao;

import develop.marat.executors.Executor;
import develop.marat.models.DataSet;
import develop.marat.orm.ORM;
import org.jetbrains.annotations.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UsersDataSetDAO<T extends DataSet> implements UsersDAO<T> {

    private static Map<Class<?>, ORM> ormByType = new LinkedHashMap<>();

    private final Executor executor;
    private final Class<T> type;
    private final ORM orm;

    public UsersDataSetDAO(@NotNull Executor executor, @NotNull Class<T> type) {
        this.executor = executor;
        this.type = type;

        if (ormByType.get(this.type) == null) {
            ormByType.put(this.type, new ORM(this.type));
        }

        orm = ormByType.get(this.type);
    }


    @Override
    public void save(T object) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO " + orm.getTableName());

        if (!orm.getColumns().isEmpty()) {
            queryBuilder.append(" (" + getColumnsStr() + ") VALUES (" + getPlaceHoldersStr() + ")");
        } else {
            queryBuilder.append(" DEFAULT VALUES;");
        }

        String query = queryBuilder.toString();
        try {
            PreparedStatement preparedStatement = executor.getConnection().prepareStatement(query);
            fillPreparedStatement(preparedStatement, object);
            executor.updatePrepared(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public T read(long id) {
        String query = "SELECT " + getColumnsWithIdStr() + " FROM " + orm.getTableName() + " WHERE " + ORM.ID_COLUMN_NAME + " = ?";
        try {
            PreparedStatement preparedStatement = executor.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            return executor.queryPrepared(preparedStatement, this::extract);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public T readByName(String name) {
        String nameColumn = "name";
        String query = "SELECT " + getColumnsWithIdStr() + " FROM " + orm.getTableName() + " WHERE " + nameColumn + " = ?";
        try {
            PreparedStatement preparedStatement = executor.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            return executor.queryPrepared(preparedStatement, this::extract);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<T> readAll() {
        String query = "SELECT " + getColumnsWithIdStr() + " FROM " + orm.getTableName() + ";";
        try {
            PreparedStatement preparedStatement = executor.getConnection().prepareStatement(query);
            return executor.queryPrepared(preparedStatement, this::extractList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(T object) {
        String query = "UPDATE " + orm.getTableName() + " SET " + getColumnsSetWithPlaceHoldersStr() +
                " WHERE " + ORM.ID_COLUMN_NAME + " =  ?;";

        try {
            PreparedStatement preparedStatement = executor.getConnection().prepareStatement(query);
            fillPreparedStatement(preparedStatement, object);
            preparedStatement.setLong(orm.getColumns().size() + 1, getId(object));
            executor.updatePrepared(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(long id) {
        String query = "DELETE FROM " + orm.getTableName() + " WHERE " + ORM.ID_COLUMN_NAME + " =  ?;";

        try {
            PreparedStatement preparedStatement = executor.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            executor.updatePrepared(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private long getId(T object) {
        long id = getFieldValue(orm.getId(), object);
        if (id > 0) {
            return id;
        } else {
            throw new RuntimeException("Field id is not correct!");
        }

    }

    @SuppressWarnings("unchecked")
    private <V> V getFieldValue(Field field, T object) {
        boolean changeAccessible = false;

        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true); // You might want to set modifier to public first.
            changeAccessible = true;
        }

        try {
            return (V) field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (changeAccessible) {
                field.setAccessible(false);
            }
        }
    }

    private String getColumnsStr() {
        StringJoiner columns = new StringJoiner(", ");

        for (String columnName : orm.getColumns().keySet()) {
            columns.add(columnName);
        }

        return columns.toString();
    }

    private String getColumnsWithIdStr() {
        StringJoiner columns = new StringJoiner(", ");
        columns.add(ORM.ID_COLUMN_NAME);

        for (String columnName : orm.getColumns().keySet()) {
            columns.add(columnName);
        }

        return columns.toString();
    }

    private String getPlaceHoldersStr() {
        String placeHolderItem = "?";
        StringJoiner placeholders = new StringJoiner(", ");
        for (int i = 0; i < orm.getColumns().size(); i++) {
            placeholders.add(placeHolderItem);
        }

        return placeholders.toString();
    }

    private String getColumnsSetWithPlaceHoldersStr() {
        StringJoiner columns = new StringJoiner(", ");
        String placeHolderItem = "?";
        for (String columnName : orm.getColumns().keySet()) {
            columns.add(columnName + " = " + placeHolderItem);
        }

        return columns.toString();
    }

    private void fillPreparedStatement(PreparedStatement statement, T object) {
        int index = 1;
        for (Field field : orm.getColumns().values()) {
            fillPreparedStatement(statement, index, field, object);
            index++;
        }
    }

    private void fillPreparedStatement(PreparedStatement statement, int index, Field field, T object) {
        Class<?> fieldType = field.getType();

        try {
            if (fieldType.isAssignableFrom(byte.class) || fieldType.isAssignableFrom(Byte.class)) {
                statement.setByte(index, getFieldValue(field, object));
            } else if (fieldType.isAssignableFrom(short.class) || fieldType.isAssignableFrom(Short.class)) {
                statement.setShort(index, getFieldValue(field, object));
            } else if (fieldType.isAssignableFrom(int.class) || fieldType.isAssignableFrom(Integer.class)) {
                statement.setInt(index, getFieldValue(field, object));
            } else if (fieldType.isAssignableFrom(float.class) || fieldType.isAssignableFrom(Float.class)) {
                statement.setFloat(index, getFieldValue(field, object));
            } else if (fieldType.isAssignableFrom(double.class) || fieldType.isAssignableFrom(Double.class)) {
                statement.setDouble(index, getFieldValue(field, object));
            } else if (fieldType.isAssignableFrom(long.class) || fieldType.isAssignableFrom(Long.class)) {
                statement.setLong(index, getFieldValue(field, object));
            } else if (fieldType.isAssignableFrom(String.class)) {
                statement.setString(index, getFieldValue(field, object));
            } else {
                statement.setObject(index, getFieldValue(field, object));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private T createAndFill(ResultSet resultSet) {
        T object = createObject();
        fillObject(object, resultSet);
        return object;
    }

    @SuppressWarnings("unchecked")
    private T createObject() {
        try {
            Constructor<?> constructor = type.getConstructor();
            return (T) constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillObject(T object, ResultSet resultSet) {
        //id
        Field idField = orm.getId();
        Object idValue = getFieldValueFromResultSet(ORM.ID_COLUMN_NAME, idField.getType(), resultSet);
        setFieldValue(idField, object, idValue);

        //columns
        for (Map.Entry<String, Field> entry : orm.getColumns().entrySet()) {
            String columnName = entry.getKey();
            Field field = entry.getValue();
            Object value = getFieldValueFromResultSet(columnName, field.getType(), resultSet);
            setFieldValue(field, object, value);
        }
    }

    private Object getFieldValueFromResultSet(String columnName, Class<?> fieldType, ResultSet resultSet) {

        try {
            if (fieldType.isAssignableFrom(byte.class) || fieldType.isAssignableFrom(Byte.class)) {
                return resultSet.getByte(columnName);
            } else if (fieldType.isAssignableFrom(short.class) || fieldType.isAssignableFrom(Short.class)) {
                return resultSet.getShort(columnName);
            } else if (fieldType.isAssignableFrom(int.class) || fieldType.isAssignableFrom(Integer.class)) {
                return resultSet.getInt(columnName);
            } else if (fieldType.isAssignableFrom(float.class) || fieldType.isAssignableFrom(Float.class)) {
                return resultSet.getFloat(columnName);
            } else if (fieldType.isAssignableFrom(double.class) || fieldType.isAssignableFrom(Double.class)) {
                return resultSet.getDouble(columnName);
            } else if (fieldType.isAssignableFrom(long.class) || fieldType.isAssignableFrom(Long.class)) {
                return resultSet.getLong(columnName);
            } else if (fieldType.isAssignableFrom(String.class)) {
                return resultSet.getString(columnName);
            } else {
                return resultSet.getObject(columnName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldValue(Field field, T object, Object value) {
        boolean changeAccessible = false;

        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true); // You might want to set modifier to public first.
            changeAccessible = true;
        }

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            if (changeAccessible) {
                field.setAccessible(false);
            }
        }
    }

    private T extract(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return null;
            }
            return createAndFill(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<T> extractList(ResultSet resultSet) {
        final List<T> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                result.add(createAndFill(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
