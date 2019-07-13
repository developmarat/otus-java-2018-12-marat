package develop.marat.ms.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

public class TransferObject {
    private String objectClassName;
    private Object data;

    public TransferObject(Object data) {
        this.data = data;
        this.objectClassName = data.getClass().getCanonicalName();
    }

    public TransferObject(String objectClassName, Object data) {
        this.objectClassName = objectClassName;
        this.data = data;
    }

    private Class<?> getObjectClass() {
        try {
            return Class.forName(objectClassName);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getConvertData() {
        return getConvertData(getObjectClass());
    }

    public Object getConvertData(Class<?> type) {
        if (data.getClass().equals(LinkedTreeMap.class)) {

            JsonObject jsonObject = new Gson().toJsonTree(data).getAsJsonObject();
            return new Gson().fromJson(jsonObject, type);
        } else {
            String json = new Gson().toJson(data);
            return new Gson().fromJson(json, type);
        }
    }
}
