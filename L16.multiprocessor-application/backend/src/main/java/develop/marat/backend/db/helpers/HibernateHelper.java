package develop.marat.backend.db.helpers;

import develop.marat.ms_clients_common.db.models.AddressDataSet;
import develop.marat.ms_clients_common.db.models.PhoneDataSet;
import develop.marat.ms_clients_common.db.models.UserDataSet;
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
