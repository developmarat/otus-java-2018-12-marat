package develop.marat.backend.db.helpers;

import develop.marat.backend.db.services.DBService;
import develop.marat.ms_clients_common.db.models.AddressDataSet;
import develop.marat.ms_clients_common.db.models.PhoneDataSet;
import develop.marat.ms_clients_common.db.models.UserDataSet;

import java.util.Arrays;

public class DBServiceHelper {
    public static void DBInit(DBService<UserDataSet> dbService) {
        dbService.save(new UserDataSet("admin", "pass", "admin", 1, new AddressDataSet("Root"),
                Arrays.asList(new PhoneDataSet("+7911-111-1111"), new PhoneDataSet("+7-911-111-2222"))));
        dbService.save(new UserDataSet("joe", "pass","Joe Frazier", 24, new AddressDataSet("Pushkina"),
                Arrays.asList(new PhoneDataSet("+7911-111-3333"), new PhoneDataSet("+7-911-111-4444"))));
        dbService.save(new UserDataSet("clint", "pass","Clint Eastwood", 44, new AddressDataSet("Esenina"),
                Arrays.asList(new PhoneDataSet("+7911-111-5555"), new PhoneDataSet("+7-911-111-6666"))));

    }

}
