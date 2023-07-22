package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCharacters;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.*;
import java.util.*;

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
                currentSession.createQuery("from Good where category_id =:id", Good.class);
        query.setParameter("id", categoryId);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Good> findByCategoryIdPaginated(long categoryId, int offset, int limit) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("from Good where category_id =:id", Good.class);
        query.setParameter("id", categoryId);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
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

    @Transactional(readOnly = true)
    public List<Good> findByCategoryIdAndByParameters(long categoryId, int offset, int limit, Map<String, String[]> parameters){

        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = currentSession.getCriteriaBuilder();


        CriteriaQuery<Good> cq = cb.createQuery(Good.class);
        Root<Good> good = cq.from(Good.class);
        Join<Good, GoodCharacters> goodCharacters = good.join("goodcharacters");

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(cb.equal(good.get("category_id"), categoryId));

        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {

            predicateList.add(cb.equal(goodCharacters.get("name"), entry.getKey()));

            for (String value : entry.getValue()) {

                predicateList.add(cb.equal(goodCharacters.get("value"), value));
            }
        }

        Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
        cq.select(good).where(predicates);
        Query<Good> query = currentSession.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Good> getTopGoods(int limit) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query = currentSession.createQuery("from Good order by countOfSoldItems DESC", Good.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
