package develop.marat;

import java.lang.reflect.Constructor;
import java.util.Collection;

public class ContainerFactory implements ObjectFactory{
    Class<? extends Collection> objectClass;
    private int size = 0;

    public ContainerFactory(Class<? extends Collection> objectClass){
        this.objectClass = objectClass;
    }

    public ContainerFactory(Class<? extends Collection> objectClass, int size){
        this(objectClass);
        this.size = size;
    }

    public Object create()
    {
        try{
            if(size == 0){
                Constructor<? extends Collection> constructor = objectClass.getConstructor();
                return constructor.newInstance();
            }
            else{
                Constructor<? extends Collection> constructor = objectClass.getConstructor(int.class);
                return constructor.newInstance(size);
            }
        }
        catch (Exception exception){
            throw new RuntimeException(exception);
        }
    }
}
