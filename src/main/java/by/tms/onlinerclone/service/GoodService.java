package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateGoodDAO;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.GoodCharacters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Good> findByCategoryId(long id) {
        return hibernateGoodDAO.findByCategoryId(id);
    }

    public List<Good> findByCategoryIdPaginated(long id, int page, int size){
        int offset = paginate(page, size);
        return hibernateGoodDAO.findByCategoryIdPaginated(id, size, offset);
    }

    public Map<String, List<String>> findCharactersToSelect(long categoryId) {

        Set<String> ch = new HashSet<>();
        Map<String, List<String>> characters = new HashMap<>();

        List<Good> byCategoryId = hibernateGoodDAO.findByCategoryId(categoryId);

        for (Good good : byCategoryId) {
            for (GoodCharacters character : good.getCharacters()) {
                ch.add(character.getName());
            }
        }

        for (String s : ch) {
            List<String> strings = hibernateGoodDAO.characterValues(s);
            characters.put(s, strings);
        }
        return characters;
    }

    public List<Good> findByCategoryIdAndByParameters(long id, int page, int size, Map<String, String[]> parameters){
        int offset = paginate(page, size);
        return hibernateGoodDAO.findByCategoryIdAndByParameters(id, offset, size, parameters);
    }

    private int paginate(int page, int size){
        if(page == 1){
            return page * size;
        } else {
            return  (page - 1) * size;
        }
    }

    public List<Good> getMostTrendingGoods(){
        return  hibernateGoodDAO.getTopGoods();
    }
}
