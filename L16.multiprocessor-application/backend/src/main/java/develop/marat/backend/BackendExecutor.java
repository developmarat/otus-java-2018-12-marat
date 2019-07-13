package develop.marat.backend;

import develop.marat.backend.db.helpers.HibernateHelper;
import develop.marat.backend.db.services.DBService;
import develop.marat.backend.db.services.UsersServiceImpl;
import develop.marat.ms.core.Address;
import develop.marat.ms_clients_common.db.models.UserDataSet;
import develop.marat.backend.db.services.DBServiceHibernateImpl;
import develop.marat.ms_clients_common.AddressEnum;
import develop.marat.ms_clients_common.SocketClientExecutor;
import develop.marat.ms_clients_common.services.BackendService;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class BackendExecutor {
    private DBService<UserDataSet> dbService;
    private SocketClientExecutor socketClientExecutor;

    public BackendExecutor(String socketServerHost, int socketServerPort) throws IOException {
        dbService = getDbService();
        BackendService backendService = getBackendService();
        socketClientExecutor = new SocketClientExecutor(socketServerHost, socketServerPort, backendService);
        socketClientExecutor.setExternalDisposeAction(this::shutdownDBService);
    }

    public static void main(String[] args) {
        String socketServerHost = args[0];
        int socketServerPort = Integer.parseInt(args[1]);
        try {
            new BackendExecutor(socketServerHost, socketServerPort).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        socketClientExecutor.start();
    }

    private DBService<UserDataSet> getDbService(){
        Configuration hibernateConfiguration = HibernateHelper.getConfiguration("config/hibernate.cfg.xml");
        return dbService = new DBServiceHibernateImpl<>(hibernateConfiguration);
    }

    private BackendService getBackendService(){
        UsersServiceImpl usersService = new UsersServiceImpl(dbService);
        usersService.init();
        return new BackendServiceImpl(new Address(AddressEnum.Backend.toString()), usersService);
    }


    public void shutdown() {
        shutdownDBService();
        socketClientExecutor.shutdown();
    }

    private void shutdownDBService() {
        dbService.shutdown();
    }
}
