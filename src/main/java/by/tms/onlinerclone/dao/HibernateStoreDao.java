package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.PageableGoods;
import by.tms.onlinerclone.entity.Store;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Andrei Lisouski (Andrlis) - 03/07/2023 - 19:06
 */

@Component
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

    public Store findById(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Store.class, id);
    }

    public List<Store> findByUserId(long id) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Store> query = currentSession.createQuery("from Store where user_id = :id", Store.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Store findByName(String name) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Store> query = currentSession.createQuery("from Store where name = :name", Store.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    public PageableGoods findOffersPaginated(String storeName, int offset, int size) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query =
                currentSession.createQuery("select g from Store s join s.goods g where s.name =: name", Good.class);
        query.setParameter("name", storeName);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        List<Good> resultList = query.getResultList();
        Query<Long> countQuery = currentSession.createQuery("select s.goods.size from Store s", Long.class);
        Long countGoods = countQuery.getSingleResult();
        return createPageableGoods(resultList, size, countGoods);
    }

    public void delete(Store store) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(store);
    }

    public Good findOfferByName(String storeName, String offerName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query = currentSession.createQuery("select g from Store s join s.goods g where s.name =: storeName and g.name =: goodName", Good.class);
        query.setParameter("storeName", storeName);
        query.setParameter("goodName", offerName);
        return query.getSingleResult();
    }

    public Good findOfferById(String storeName, Long offerId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Good> query = currentSession.createQuery("select g from Store s join s.goods g where s.name =: storeName and g.name =: goodId", Good.class);
        query.setParameter("storeName", storeName);
        query.setParameter("goodId", offerId);
        return query.getSingleResult();
    }

    public boolean updateOffer(String storeName, Good offer){
        Session currentSession = sessionFactory.getCurrentSession();
        Good offerById = findOfferById(storeName, offer.getId());

        if (offerById != null){

            currentSession.update(offer);
            return true;
        }

        return false;
    }

    private PageableGoods createPageableGoods(List<Good> goodList, int size, Long countGoods){
        PageableGoods pageableGoods = new PageableGoods();
        pageableGoods.setSize(size);
        pageableGoods.setGoodList(goodList);

        Long countOfPages = (long) Math.ceil(countGoods / size);
        pageableGoods.setCountOfPages(countOfPages);
        return pageableGoods;
    }
}
