package develop.marat.backend.db.services;

import develop.marat.backend.db.dao.UsersHibernateDAO;
import develop.marat.ms_clients_common.db.models.UserDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DBServiceHibernateImpl<T extends UserDataSet> implements DBService<T> {
    private final SessionFactory sessionFactory;

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> {
            return session.getTransaction().getStatus().name();
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void save(T user) {
        runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO(session, user.getClass());
            dao.save(user);
        });
    }

    @Override
    public T read(long id, Class<T> type) {
        return runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO<>(session, type);
            return dao.read(id);
        });
    }

    @Override
    public T readByLogin(String login, Class<T> type) {
        return runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO<>(session, type);
            return dao.readByLogin(login);
        });
    }

    @Override
    public T readByName(String name, Class<T> type) {
        return runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO<>(session, type);
            return dao.readByName(name);
        });
    }

    @Override
    public List<T> readAll(Class<T> type) {
        return runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO<>(session, type);
            return dao.readAll();
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(T user) {
        runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO(session, user.getClass());
            dao.update(user);
        });
    }

    @Override
    public void delete(long id, Class<T> type) {
        runInSession(session -> {
            UsersHibernateDAO<T> dao = new UsersHibernateDAO<>(session, type);
            dao.delete(id);
        });
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    private void runInSession(Consumer<Session> consumer) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

}
