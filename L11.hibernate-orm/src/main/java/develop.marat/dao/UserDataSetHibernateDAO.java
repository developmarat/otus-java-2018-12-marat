package develop.marat.dao;

import develop.marat.models.DataSet;
import org.hibernate.Session;
import org.hibernate.query.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDataSetHibernateDAO<T extends DataSet> implements UsersDAO<T> {
    private Session session;
    private Class<T> type;

    public UserDataSetHibernateDAO(Session session, Class<T> type) {
        this.session = session;
        this.type = type;
    }

    public void save(T dataSet) {
        session.save(dataSet);
    }

    public T read(long id) {
        return session.load(type, id);
    }

    public T readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> from = criteria.from(type);
        criteria.where(builder.equal(from.get("name"), name));
        Query<T> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<T> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        return session.createQuery(criteria).list();
    }

    @Override
    public void update(T user) {
        session.update(user);
    }

    @Override
    public void delete(long id) {
        session.delete(read(id));
    }
}
