package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Good;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Denis Smirnov on 29.06.2023
 */
@Repository
@Transactional
public class HibernateGoodDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Good good) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(good);
    }

    public void delete(Good good) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(good);
    }

    public void update(Good good) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(good);
    }

    @Transactional()
    public Good findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Good.class, id);
    }

    @Transactional(readOnly = true)
    public Good findByGoodName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("from Good where name = :un order by id desc", Good.class);
        query.setParameter("un", name);
        Good singleResult = query.getSingleResult();
        return singleResult;
    }

    public List<Good> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query = currentSession.createQuery("from Good", Good.class);
        List<Good> resultList = query.getResultList();
        return resultList;
    }
    @Transactional(readOnly = true)
    public List<Good> findByCategoryId(long categoryId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("from Good where category_id = :id", Good.class);
        query.setParameter("id", categoryId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<String> characterValues(String characterName){
        Set<String> values = new HashSet<>();
        Session currentSession = sessionFactory.getCurrentSession();

        Query<String> query = currentSession.createQuery("select gc.value from GoodCharacters gc where gc.name =: name", String.class);
        query.setParameter("name", characterName);
        return query.getResultList();
    }
}
