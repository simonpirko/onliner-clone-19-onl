package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateStoreDao;
import by.tms.onlinerclone.entity.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrei Lisouski (Andrlis) - 01/07/2023 - 15:51
 */

@Service
public class StoreService {

    @Autowired
    private HibernateStoreDao hibernateStoreDao;

    public void save(Store store){
        hibernateStoreDao.save(store);
    }

    public List<Store> findByUser(long id){
        return hibernateStoreDao.findByUserId(id);
    }

    public Optional<Store> findById(long id){
        Store byId = hibernateStoreDao.findById(id);
        if (byId != null) return Optional.of(byId);
        return Optional.empty();
    }

    public void delete(Store store){
        hibernateStoreDao.delete(store);
    }

}
