package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateGoodDao;
import by.tms.onlinerclone.dao.HibernateStoreDao;
import by.tms.onlinerclone.dao.HibernateUserDao;
import by.tms.onlinerclone.dto.GoodCreatorDto;
import by.tms.onlinerclone.dto.RegStoreDto;
import by.tms.onlinerclone.entity.*;
import by.tms.onlinerclone.mapper.GoodCreatorDTOMapper;
import by.tms.onlinerclone.mapper.RegStoreDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Andrei Lisouski (Andrlis) - 01/07/2023 - 15:51
 */

@Service
@Transactional
public class StoreService {

    @Autowired
    private HibernateStoreDao hibernateStoreDao;
    @Autowired
    private HibernateGoodDao hibernateGoodDao;

    @Autowired
    private HibernateUserDao hibernateUserDao;

    public void save(RegStoreDto regStoreDto) {
        Store store = RegStoreDTOMapper.regStoreToStore(regStoreDto);
        User byId = hibernateUserDao.findById(regStoreDto.getUser().getId());
        store.setSuperAdmin(byId);
        hibernateStoreDao.save(store);
    }

    @Transactional(readOnly = true)
    public List<Store> findByUser(long id) {
        return hibernateStoreDao.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Optional<Store> findById(long id) {
        Store byId = hibernateStoreDao.findById(id);
        if (byId != null) return Optional.of(byId);
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<Store> findByName(String name) {
        Store byName = hibernateStoreDao.findByName(name);
        if (byName != null) return Optional.of(byName);
        return Optional.empty();
    }

    public void delete(Store store) {
        hibernateStoreDao.delete(store);
    }


    public void update(Store store) {
        hibernateStoreDao.update(store);
    }

    @Transactional(readOnly = true)
    public PageableGoods findPaginatedOffers(String storeName, int page, int size) {
        int offset = paginate(page, size);
        return hibernateStoreDao.findOffersPaginated(storeName, offset, size);
    }

    public boolean addOffer(String storeName, GoodCreatorDto goodCreator) {
        Store byName = hibernateStoreDao.findByName(storeName);

        if (byName != null) {
            Good good = GoodCreatorDTOMapper.goodCreatorDtoToGood(goodCreator);

            GoodCategory category = hibernateGoodDao.findCategory(goodCreator.getCategory());
            if (category != null) {
                good.setCategory(category);
            }

            Map<String, GoodCharacters> characters = hibernateGoodDao.findCharacters(goodCreator.getCharacters());
            Set<GoodCharacters> set = new HashSet<>(goodCreator.getCharacters());

            for (GoodCharacters character : set) {

                GoodCharacters fromDao = characters.get(character.getName());

                if (fromDao != null) {
                    set.remove(character);
                    set.add(fromDao);
                }
            }

            good.setCharacters(set);
            byName.getGoods().add(good);
            hibernateStoreDao.update(byName);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Optional<Good> findOfferByName(String storeName, String offerName) {
        Good offerByName = hibernateStoreDao.findOfferByName(storeName, offerName);
        if (offerByName != null) return Optional.of(offerByName);
        return Optional.empty();
    }

    public boolean updateOffer(String storeName, Good offer) {
        return hibernateStoreDao.updateOffer(storeName, offer);
    }

    public boolean deleteAdmin(String storeName, long id) {
        Optional<Store> byName = findByName(storeName);
        User byId = hibernateUserDao.findById(id);

        if (byName.isPresent() && byId != null) {

            Store store = byName.get();
            store.getAdministrators().remove(byId);
            update(store);
            return true;
        }

        return false;
    }

    public boolean addAdmin(String storeName, String email) {
        Optional<User> byEmail = hibernateUserDao.findByEmail(email);
        Optional<Store> byName = findByName(storeName);

        if (byName.isPresent() && byEmail.isPresent()) {

            Store store = byName.get();
            store.getAdministrators().add(byEmail.get());
            update(store);
            return true;
        }

        return false;
    }

    private int paginate(int page, int size) {
        if (page == 1) {
            return page * size;
        } else {
            return (page - 1) * size;
        }
    }
}
