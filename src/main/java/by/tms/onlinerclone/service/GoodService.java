package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateGoodDAO;
import by.tms.onlinerclone.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Denis Smirnov on 29.06.2023
 */
@Service
public class GoodService {

    @Autowired
    private HibernateGoodDAO hibernateGoodDAO;


    public void save(Good good) {
        hibernateGoodDAO.save(good);
    }

    public void delete(Good good) {
        hibernateGoodDAO.delete(good);
    }

    public Optional<Good> findByID(long id) {
        Good byId = hibernateGoodDAO.findById(id);
        if (byId != null) {
            return Optional.of(byId);
        }
        return Optional.empty();
    }

    public void update(Good good) {
        hibernateGoodDAO.update(good);
    }

    public Good findGoodByName(String name) {
        return hibernateGoodDAO.findByGoodName(name);
    }

    public List<Good> findAll() {
        return hibernateGoodDAO.findAll();
    }

    public List<Good> findByCategoryId(long id) { return hibernateGoodDAO.findByCategoryId(id); }
}
