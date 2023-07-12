package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateOrderDao;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private HibernateOrderDao hibernateOrderDao;

    public void save(Order order){
        hibernateOrderDao.save(order);
    }

    public List<Order> findPaginatedByUsername(String username, int page, int size) {
        int offset = (page - 1) * size;
        return hibernateOrderDao.findPaginatedByUsername(username, offset, size);
    }

    public List<Order> findByUser(long id){
        return hibernateOrderDao.findByUserId(id);
    }

    public Optional<Order> findById(long id){
        Order byId = hibernateOrderDao.findById(id);
        if (byId != null) return Optional.of(byId);
        return Optional.empty();
    }


    public void delete(Order order){
        hibernateOrderDao.delete(order);
    }
}
