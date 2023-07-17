package by.tms.onlinerclone.dao;

import by.tms.onlinerclone.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class HibernateOrderDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Order order){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(order);
    }

    public void update(Order order){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(order);
    }


    public Order findById(long id){
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Order.class, id);
    }


    public List<Order> findPaginatedByUsername(String username, int offset, int size) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Order> query = currentSession.createQuery("FROM Order o WHERE o.user.username = :username", Order.class);
        query.setParameter("username", username);
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query.getResultList();
    }


    public List<Order> findByUserId(long id){
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Order> query = currentSession.createQuery("from Order where user_id = :id", Order.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void delete(Order order){
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.delete(order);
    }
}
