package develop.marat;

import develop.marat.db.helpers.DBServiceHelper;
import develop.marat.db.helpers.HibernateHelper;
import develop.marat.db.models.UserDataSet;
import develop.marat.db.services.DBService;
import develop.marat.db.services.DBServiceHibernateImpl;
import develop.marat.db.services.UsersService;
import develop.marat.db.services.UsersServiceImpl;
import develop.marat.ms.services.MessageSystemService;
import develop.marat.server.Server;
import develop.marat.server.template.TemplateProcessor;
import develop.marat.server.template.TemplateProcessorImpl;

public class Main {
    public static void main(String[] args) {
        DBService<UserDataSet>  dbService = new DBServiceHibernateImpl<>(HibernateHelper.getConfiguration());
        DBServiceHelper.DBInit(dbService);

        UsersService usersService = new UsersServiceImpl(dbService);
        TemplateProcessor templateProcessor = new TemplateProcessorImpl();
        MessageSystemService messageSystemService = new MessageSystemService(usersService, templateProcessor);
        messageSystemService.start();

        int port = 8080;
        new Server(port, messageSystemService.getContext()).start();

    }
}
