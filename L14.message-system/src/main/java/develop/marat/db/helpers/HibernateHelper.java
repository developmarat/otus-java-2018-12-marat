package develop.marat.db.helpers;

import develop.marat.db.models.AddressDataSet;
import develop.marat.db.models.PhoneDataSet;
import develop.marat.db.models.UserDataSet;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateHelper {
    public static Configuration getConfiguration() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        Configuration configuration = new Configuration()
                .configure(new File(classLoader.getResource("config/hibernate.cfg.xml").getFile()));

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        return configuration;
    }
}
