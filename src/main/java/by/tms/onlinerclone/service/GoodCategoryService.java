package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateGoodCategoryDao;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrei Lisouski (Andrlis) - 11/07/2023 - 2:24
 */
@Service
public class GoodCategoryService {
    
    @Autowired
    private HibernateGoodCategoryDao hibernateGoodCategoryDao;
    
    
    public void save(GoodCategory category) {
        hibernateGoodCategoryDao.save(category);
    }

    public void delete(GoodCategory category) {
        hibernateGoodCategoryDao.delete(category);
    }

    public void update(GoodCategory category) {
        hibernateGoodCategoryDao.update(category);
    }

//    public GoodCategory findGoodCategoryByName(String name) {
//        return hibernateGoodCategoryDao.findByGoodCategoryName(name);
//    }

    public List<GoodCategory> findAll() {
        return hibernateGoodCategoryDao.findAll();
    }
}
