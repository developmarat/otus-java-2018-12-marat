package develop.marat.orm;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;

public class ORM {
    public static final String ID_COLUMN_NAME = "id";

    private final Class<?> type;
    private final String tableName;
    private final List<Field> fields;
    private final Field id;
    private final Map<String, Field> columns;

    public ORM(@NotNull Class<?> type) {
        this.type = type;
        tableName = obtainTableName();
        fields = obtainFields();
        id = obtainId();
        columns = obtainColumns();
    }

    private String obtainTableName(){
        Table tableAnnotation = type.getDeclaredAnnotation(Table.class);
        if(tableAnnotation != null && !tableAnnotation.name().isEmpty()){
            return tableAnnotation.name();
        }
        else {
            throw new RuntimeException("In object type " + type + " Table name value is not correct!");
        }
    }

    private List<Field> obtainFields() {
        List<Field> fields = new ArrayList<>();
        for(Field field: obtainAllFields()){
            if(!field.isSynthetic()){
                fields.add(field);
            }
        }
        return fields;
    }

    private List<Field> obtainAllFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> currentType = type;
        do{
            fields.addAll(Arrays.asList(currentType.getDeclaredFields()));
            currentType = currentType.getSuperclass();
        }
        while (currentType != null && !(currentType).equals(Object.class));

        return fields;
    }

    private Field obtainId(){
        for (Field field: fields){
            if(field.getDeclaredAnnotation(Id.class) != null){
                return field;
            }
        }

        throw new RuntimeException("In object type " + type + " id is not set!");
    }

    private Map<String, Field> obtainColumns(){
        Map<String, Field> columns = new LinkedHashMap<>();

        for (Field field: fields){
            Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
            if(columnAnnotation != null){
                if(!columnAnnotation.name().isEmpty()){
                    columns.put(columnAnnotation.name(), field);
                }
                else {
                    throw new RuntimeException("In object type " + type + " in column field " + field.getName() + " not set column name!");
                }
            }
        }

        return columns;
    }

    public String getTableName() {
        return tableName;
    }

    public Field getId() {
        return id;
    }

    public Map<String, Field> getColumns() {
        return columns;
    }
}
