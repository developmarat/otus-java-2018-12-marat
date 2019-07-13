package develop.marat.db.helpers;

import develop.marat.db.models.AddressDataSet;
import develop.marat.db.models.PhoneDataSet;
import develop.marat.db.models.UserDataSet;
import org.hibernate.cfg.Configuration;

public class HibernateHelper {
    public static Configuration getConfiguration(String configPath) {
        Configuration configuration = new Configuration().configure(configPath);

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        return configuration;
    }
}
