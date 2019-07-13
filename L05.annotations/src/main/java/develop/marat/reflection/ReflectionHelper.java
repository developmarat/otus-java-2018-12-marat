package develop.marat.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper {

    private ReflectionHelper(){

    }

    public static <T> T instantiate(Class<T> type){
        try {
            final Constructor<T> constructor = type.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> Method[] getDeclaredMethods(Class<T> type){
        try{
            return type.getDeclaredMethods();
        } catch(SecurityException e){
            e.printStackTrace();
        }

        return null;
    }

    public static Annotation[] getDeclaredAnnotations(Method method){
        return method.getDeclaredAnnotations();
    }

    public static Object callMethod(Object object, Method method){
        boolean isAccessible = true;
        try{
            isAccessible = method.canAccess(object);
            method.setAccessible(true);

            method.invoke(object);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            e.printStackTrace();
        } finally {
            if (method != null && !isAccessible) {
                method.setAccessible(false);
            }
        }

        return null;
    }
}
