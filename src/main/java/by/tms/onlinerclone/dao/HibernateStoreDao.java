package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Andrei Lisouski (Andrlis) - 03/07/2023 - 19:06
 */

@Component
@Transactional
public class HibernateStoreDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Store store) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(store);
    }

    public void update(Store store) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(store);
    }

    @Transactional(readOnly = true)
    public Store findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Store.class, id);
    }

    @Transactional(readOnly = true)
    public List<Store> findByUserId(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Store> query = currentSession.createQuery("from Store where user_id = :id", Store.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void delete(Store store) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(store);
    }
}
