package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCategory;
import by.tms.onlinerclone.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrei Lisouski (Andrlis) - 11/07/2023 - 2:18
 */
@Repository
@Transactional
public class HibernateGoodCategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(GoodCategory category) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(category);
    }

    public void delete(GoodCategory category) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(category);
    }

    public void update(GoodCategory category) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(category);
    }

    @Transactional(readOnly = true)
    public Optional<GoodCategory> findByGoodCategoryName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<GoodCategory> query = currentSession.createQuery("from GoodCategory where name = :name", GoodCategory.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<GoodCategory> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<GoodCategory> query = currentSession.createQuery("from GoodCategory", GoodCategory.class);
        List<GoodCategory> resultList = query.getResultList();
        return resultList;
    }
}
