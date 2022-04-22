package dbservice.dao;

import dbservice.dataSets.UsersDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public long insertUser(String name) {
        return (Long) session.save(new UsersDataSet(name));
    }

    public UsersDataSet get(long id) {
        return session.get(UsersDataSet.class, id);
    }

    public long getUserId(String name) {
        Criteria criteria = session.createCriteria(UsersDataSet.class);
        return ((UsersDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult()).getId();
    }
}
