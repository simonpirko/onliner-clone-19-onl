package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateGoodDao;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCharacters;
import by.tms.onlinerclone.entity.PageableGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Denis Smirnov on 29.06.2023
 */
@Service
public class GoodService {

    private static final int COUNT_OF_TOP_ITEMS = 9;

    @Autowired
    private HibernateGoodDao hibernateGoodDao;

    public void save(Good good) {
        hibernateGoodDao.save(good);
    }

    public void delete(Good good) {
        hibernateGoodDao.delete(good);
    }

    public Optional<Good> findByID(long id) {
        Good byId = hibernateGoodDao.findById(id);
        if (byId != null) {
            return Optional.of(byId);
        }
        return Optional.empty();
    }

    public void update(Good good) {
        hibernateGoodDao.update(good);
    }

    public Good findGoodByName(String name) {
        return hibernateGoodDao.findByGoodName(name);
    }

    public List<Good> findAll() {
        return hibernateGoodDao.findAll();
    }

    public PageableGoods findBySimilarityInName(String name, int page, int size) {

        int offset = paginate(page, size);
        return hibernateGoodDao.findBySimilarityInName(name, offset, size);
    }

    public List<Good> findByCategoryName(String name) {
        return hibernateGoodDao.findByCategoryName(name);
    }

    public PageableGoods findByCategoryNamePaginated(String name, int page, int size){

        int offset = paginate(page, size);
        return hibernateGoodDao.findByCategoryNamePaginated(name, offset, size);

    }

    public Map<String, List<String>> findCharactersToSelect(String categoryName) {

        Set<String> ch = new HashSet<>();
        Map<String, List<String>> characters = new HashMap<>();

        List<Good> byCategoryId = hibernateGoodDao.findByCategoryName(categoryName);

        for (Good good : byCategoryId) {
            for (GoodCharacters character : good.getCharacters()) {
                ch.add(character.getName());
            }
        }

        for (String s : ch) {
            List<String> strings = hibernateGoodDao.characterValues(s);
            characters.put(s, strings);
        }
        return characters;
    }

    public PageableGoods findByCategoryNameAndByParameters(String name, int page, int size, Map<String, String[]> parameters){
        int offset = paginate(page, size);
        return hibernateGoodDao.findByCategoryNameAndByParameters(name, offset, size, parameters);
    }

    private int paginate(int page, int size){
        if(page == 1){
            return page * size;
        } else {
            return  (page - 1) * size;
        }
    }

    public List<Good> getMostTrendingGoods(){
        return  hibernateGoodDAO.getTopGoods(COUNT_OF_TOP_ITEMS);
    }
}
