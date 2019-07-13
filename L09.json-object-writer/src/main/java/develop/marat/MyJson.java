package develop.marat;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class MyJson {

    private static final Set<Class<?>> primitiveWrapperTypes = getPrimitiveWrapperTypes();

    public String toJson(@NotNull final Object src){

        Writer writer = new Writer();
        JSONObject jsonObject = writer.getJSONObject(src);
        return jsonObject.toJSONString();

    }

    public <T> T fromJson(final String json, final Class<T> type){
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject)parser.parse(json);
            Reader reader = new Reader();
            return (T)reader.getObject(jsonObject, type);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    private static Set<Class<?>> getPrimitiveWrapperTypes()
    {
        Class<?>[] primitiveWrappers = {Boolean.class, Character.class, Byte.class, Short.class, Integer.class,
                Long.class, Float.class, Double.class, Void.class};

        return new HashSet<>(Arrays.asList(primitiveWrappers));
    }

    private static boolean isPrimitiveWrapperType(final Class<?> type) {

        return primitiveWrapperTypes.contains(type);
    }

    private class Writer {

        public JSONObject getJSONObject(Object src){

            JSONObject jsonObject = new JSONObject();

            for (Field field : getAllFields(src.getClass())) {
                if(field.isSynthetic()){
                    continue;
                }

                boolean changeAccessible = false;
                if(!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true); // You might want to set modifier to public first.
                    changeAccessible = true;
                }

                try{
                    if(field.get(src) != null) {
                        jsonObject.put(field.getName(), getValidValue(field.get(src)));
                    }
                }
                catch (IllegalAccessException e){
                    throw new RuntimeException(e);
                }

                if(changeAccessible) {
                    field.setAccessible(false);
                }
            }

            return jsonObject;
        }

        private Object getValidValue(Object value){

            if(value == null){
                return null;
            }

            Class type = value.getClass();

            if(isPrimitiveWrapperType(type)) {
                return getValidPrimitiveValue(value);
            }
            else if(type.isArray()) {
                Collection collectionValue = getCollectFromArray(value);
                return getValidValue(collectionValue);
            }
            else if(Collection.class.isAssignableFrom(type)) {
                return getValidValue((Collection)value);
            }
            else if(Map.class.isAssignableFrom(type)) {
                return getValidValue((Map) value);
            }

            return getJSONObject(value);
        }

        private Object getValidPrimitiveValue(Object value){

            if(value.getClass().isAssignableFrom(Character.class)){
                return (int)(Character)value;
            }

            return value;
        }

        private Collection getCollectFromArray(Object array) {
            int length = Array.getLength(array);
            Collection collection = createCollection(Collection.class, length);
            for(int i= 0; i < length; i++){
                Object value = Array.get(array, i);
                collection.add(value);
            }

            return collection;
        }

        private Object getValidValue(Collection collection) {
            System.out.println(collection.getClass());
            Collection validCollection = createCollection(Collection.class, collection.size());
            for (Object value: collection) {
                validCollection.add(getValidValue(value));
            }

            return validCollection;
        }

        private Object getValidValue(Map map) {
            Map validMap = createMap(map.getClass(), map.size());

            for (Object key: map.keySet()) {
                Object value = map.get(key);
                Object validKey = getValidValue(key);
                Object validValue = getValidValue(value);

                validMap.put(validKey, validValue);
            }

            return validMap;
        }
    }

    private class Reader {

        public Object getObject(JSONObject jsonObject, Class<?> type) {
            Object resultObject = create(type);

            for (Field field : getAllFields(type)) {
                if(field.isSynthetic()){
                    continue;
                }

                boolean changeAccessible = false;
                if(!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true); // You might want to set modifier to public first.
                    changeAccessible = true;
                }

                Object value = getValidValue(jsonObject.get(field.getName()), field.getType());

                try {
                    field.set(resultObject, value);

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if(changeAccessible) {
                    field.setAccessible(false);
                }
            }

            return resultObject;
        }

        private Object getValidValue(Object value, Class<?> type) {
            if(value == null){
                return null;
            }

            if(type.isPrimitive() || isPrimitiveWrapperType(type)) {
                return getValidPrimitiveValue(value, type);
            }
            else if(type.isArray()) {
                return getValidArrayValue((JSONArray)value, type);
            }
            else if(Collection.class.isAssignableFrom(type)) {
                return getValidCollectionValue((JSONArray)value, type);
            }
            else if(Map.class.isAssignableFrom(type)) {
                return getValidMapValue((JSONObject)value, type);
            }

            return getObject((JSONObject)value, type);
        }

        private Object getValidPrimitiveValue(Object value, Class<?> type) {

            if (value.getClass().isAssignableFrom(Long.class) && !type.isAssignableFrom(Long.class)) {
                Long longValue = (Long) value;
                if (type.isAssignableFrom(byte.class) || type.isAssignableFrom(Byte.class)) {
                    return (Object)longValue.byteValue();
                } else if (type.isAssignableFrom(char.class) || type.isAssignableFrom(Character.class)) {
                    return (Object)(char)longValue.intValue();
                } else if (type.isAssignableFrom(short.class) || type.isAssignableFrom(Short.class)) {
                    return (Object)longValue.shortValue();
                } else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
                    return (Object)longValue.intValue();
                } else if (type.isAssignableFrom(float.class) || type.isAssignableFrom(Float.class)) {
                    return (Object)longValue.floatValue();
                } else if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class)) {
                    return (Object)longValue.doubleValue();
                }
            }

            return value;
        }

        private Object getValidArrayValue(JSONArray jsonObject, Class<?> type) {

            Object[] values = getValidCollectionValue(jsonObject, type).toArray();
            Object validArray = createArray(type.getComponentType(), values.length);

            for(int i = 0; i < values.length; i++){
                Object validItem = getValidValue(values[i], type.getComponentType());
                Array.set(validArray, i, validItem);
            }

            return validArray;
        }

        private Collection getValidCollectionValue(JSONArray jsonObject, Class<?> type) {
            Collection validCollectionValue = createCollection((Class<? extends Collection>)type, jsonObject.size());

            validCollectionValue.addAll(jsonObject);
            return validCollectionValue;
        }

        private Map getValidMapValue(JSONObject jsonObject, Class<?> type) {
            Map validMapValue = createMap((Class<? extends Map>)type, jsonObject.size());

            for(Object key: jsonObject.keySet()) {
                validMapValue.put(key, jsonObject.get(key));
            }

            return validMapValue;
        }
    }

    private static Field[] getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentType = type;
        do{
            fields.addAll(Arrays.asList(currentType.getDeclaredFields()));
            currentType = currentType.getSuperclass();
        }
        while (currentType != null && !(currentType).equals(Object.class));

        return fields.toArray(new Field[0]);
    }

    private <T> T create(Class<?> type) {
        try {
            Constructor<?> constructor = type.getConstructor();
            return (T)constructor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private <T> T createArray(Class<T> typeItem, int size) {
        return (T)Array.newInstance(typeItem, size);
    }

    private <T> Collection<T> createCollection(Class<? extends Collection> type, int size) {
        if(!Modifier.isAbstract(type.getModifiers()) && !type.isInterface()) {
            try {
                Constructor<?> constructor = type.getConstructor(int.class);
                return (Collection<T>)constructor.newInstance(size);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            return new ArrayList<>(size);
        }
    }


    private <K, V> Map<K, V>  createMap(Class<? extends Map> type, int size) {
        if(!Modifier.isAbstract(type.getModifiers()) && !type.isInterface()) {
            try {
                Constructor<?> constructor = type.getConstructor(int.class);
                return (Map<K, V>)constructor.newInstance(size);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            return new LinkedHashMap(size);
        }
    }

}


