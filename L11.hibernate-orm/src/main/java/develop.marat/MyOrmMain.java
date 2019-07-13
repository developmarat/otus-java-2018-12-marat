package develop.marat;

import develop.marat.dbCommon.ConnectionHelper;
import develop.marat.dbCommon.HibernateHelper;
import develop.marat.dbServices.DBService;
import develop.marat.dbServices.DBServiceHibernateImpl;
import develop.marat.dbServices.DBServiceImpl;
import develop.marat.models.AddressDataSet;
import develop.marat.models.PhoneDataSet;
import develop.marat.models.UserDataSet;

import java.sql.SQLException;
import java.util.Arrays;

public class MyOrmMain {
    public static void main(String[] args){
        MyOrmMain myOrmMain = new MyOrmMain();
        System.out.println("DBServiceImpl");
        System.out.println("---------------------------------");
        myOrmMain.printDBSImplActions();
        System.out.println("---------------------------------");
        System.out.println("DBServiceHibernateImpl");
        System.out.println("---------------------------------");
        myOrmMain.printDBSHibernateImplActions();
        System.out.println("---------------------------------");
    }

    private void printDBSImplActions(){
        DBServiceImpl<UserDataSet> dbService = new DBServiceImpl<>(ConnectionHelper.getPostgresqlConnection());
        try{
            dbService.prepareTables();
            printDBSActions(dbService);
            dbService.deleteTables();
            dbService.shutdown();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void printDBSHibernateImplActions(){
        DBService<UserDataSet> dbService = new DBServiceHibernateImpl<>(HibernateHelper.getConfiguration());
        try{
            printDBSActions(dbService);
            dbService.shutdown();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    private void printDBSActions(DBService<UserDataSet> dbService) throws SQLException {
        dbService.save(new UserDataSet("Bill Brown", 33, new AddressDataSet("Lomonosova"),
                Arrays.asList(new PhoneDataSet("+7911-111-1111"), new PhoneDataSet("+7-911-111-2222"))));
        dbService.save(new UserDataSet("Joe Frazier", 24, new AddressDataSet("Pushkina"),
                Arrays.asList(new PhoneDataSet("+7911-111-3333"), new PhoneDataSet("+7-911-111-4444"))));
        dbService.save(new UserDataSet("Clint Eastwood", 44, new AddressDataSet("Esenina"),
                Arrays.asList(new PhoneDataSet("+7911-111-5555"), new PhoneDataSet("+7-911-111-6666"))));

        System.out.println("Read id = 1");
        System.out.println("-------------");
        System.out.println( dbService.read(1, UserDataSet.class));
        System.out.println("-------------");
        System.out.println("Read by name = Clint Eastwood");
        System.out.println("-------------");
        System.out.println(dbService.readByName("Clint Eastwood", UserDataSet.class));
        System.out.println("-------------");
        System.out.println("Read all");
        System.out.println("-------------");
        System.out.println(dbService.readAll(UserDataSet.class));
        System.out.println("-------------");

    }

}
