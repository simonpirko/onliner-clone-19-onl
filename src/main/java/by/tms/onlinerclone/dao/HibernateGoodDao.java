package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCategory;
import by.tms.onlinerclone.entity.GoodCharacters;
import by.tms.onlinerclone.entity.PageableGoods;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author Denis Smirnov on 29.06.2023
 */
@Repository
public class HibernateGoodDao {

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

    public Good findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Good.class, id);
    }

    public Good findByGoodName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("from Good where name = :un order by id desc", Good.class);
        query.setParameter("un", name);
        return query.getSingleResult();
    }

    public List<Good> findAll() {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query = currentSession.createQuery("from Good", Good.class);
        return query.getResultList();
    }

    public List<Good> findByCategoryName(String categoryName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("from Good g where  g.category.name =: categoryName", Good.class);
        query.setParameter("categoryName", categoryName);
        return query.getResultList();
    }

    public PageableGoods findBySimilarityInName(String name, int offset, int size) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = currentSession.getCriteriaBuilder();

        CriteriaQuery<Good> cq = cb.createQuery(Good.class);
        CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
        Root<Good> from = cq.from(Good.class);

        cq.select(from).where(cb.like(from.get("name"), name));
        countCQ.select(cb.count(from)).where(cb.like(from.get("name"), name));

        Query<Good> query = currentSession.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        List<Good> resultList = query.getResultList();

        Query<Long> countQuery = currentSession.createQuery(countCQ);
        Long countGoods = countQuery.getSingleResult();

        return createPageableGoods(resultList, size, countGoods);

    }

    public PageableGoods findByCategoryNamePaginated(String categoryName, int offset, int size) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("from Good g where g.category.name =: categoryName", Good.class);
        query.setParameter("categoryName", categoryName);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        List<Good> resultList = query.getResultList();
        Query<Long> countQuery = currentSession.createQuery("select count (g) from Good g where g.category.name =: categoryName", Long.class);
        Long countGoods = countQuery.getSingleResult();
        return createPageableGoods(resultList, size, countGoods);
    }

    public List<String> characterValues(String characterName){
        Session currentSession = sessionFactory.getCurrentSession();
        Query<String> query = currentSession.createQuery("select gc.value from GoodCharacters gc where gc.name =: name", String.class);
        query.setParameter("name", characterName);
        return query.getResultList();
    }

    public GoodCategory findCategory(String categoryName){
        Session currentSession = sessionFactory.getCurrentSession();
        Query<GoodCategory> query = currentSession.createQuery("from GoodCategory where name =: name", GoodCategory.class);
        query.setParameter("name", categoryName);
        return query.getSingleResult();
    }
    public Map<String, GoodCharacters> findCharacters(Set<GoodCharacters> goodCharacters){
        Map<String, GoodCharacters> charactersMap = new HashMap<>();

        Session currentSession = sessionFactory.getCurrentSession();

        for (GoodCharacters goodCharacter : goodCharacters) {
            Query<GoodCharacters> query = currentSession.createQuery("from GoodCharacters gc where gc.name =: name and gc.value =: cValue", GoodCharacters.class);
            query.setParameter("name", goodCharacter.getName());
            query.setParameter("cValue", goodCharacter.getValue());
            GoodCharacters singleResult = query.getSingleResult();
            charactersMap.put(singleResult.getName(), singleResult);
        }
        return charactersMap;
    }

    public PageableGoods findByCategoryNameAndByParameters(String categoryName, int offset, int size, Map<String, String[]> parameters){

        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = currentSession.getCriteriaBuilder();

        CriteriaQuery<Good> cq = cb.createQuery(Good.class);
        CriteriaQuery<Long> countCQ = cb.createQuery(Long.class);
        Root<Good> good = cq.from(Good.class);
        Join<Good, GoodCharacters> goodCharacters = good.join("goodcharacters");

        List<Predicate> predicateList = new ArrayList<>();
        predicateList.add(cb.equal(goodCharacters.get("categoryName"), categoryName));

        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {

            predicateList.add(cb.equal(goodCharacters.get("name"), entry.getKey()));

            for (String value : entry.getValue()) {

                predicateList.add(cb.equal(goodCharacters.get("value"), value));
            }
        }

        Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
        cq.select(good).where(predicates);
        countCQ.select(cb.count(good)).where(predicates);

        Query<Good> query = currentSession.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        List<Good> resultList = query.getResultList();
        Query<Long> countQuery = currentSession.createQuery(countCQ);
        Long countGoods = countQuery.getSingleResult();

        return createPageableGoods(resultList, size, countGoods);
    }


    private PageableGoods createPageableGoods(List<Good> goodList, int size, Long countGoods){
        PageableGoods pageableGoods = new PageableGoods();
        pageableGoods.setSize(size);
        pageableGoods.setGoodList(goodList);

        Long countOfPages = (long) Math.ceil(countGoods / size);
        pageableGoods.setCountOfPages(countOfPages);
        return pageableGoods;
    }

    public List<Good> getTopGoods(int limit) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query = currentSession.createQuery("from Good order by countOfSoldItems DESC", Good.class);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
