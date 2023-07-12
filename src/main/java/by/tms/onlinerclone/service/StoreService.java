package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateStoreDao;
import by.tms.onlinerclone.dto.GoodCreatorDto;
import by.tms.onlinerclone.dto.RegStoreDto;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.PageableGoods;
import by.tms.onlinerclone.entity.Store;
import by.tms.onlinerclone.mapper.RegStoreDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Andrei Lisouski (Andrlis) - 01/07/2023 - 15:51
 */

@Service
@Transactional
public class StoreService {

    @Autowired
    private HibernateStoreDao hibernateStoreDao;

    public void save(RegStoreDto regStoreDto){
        Store store = RegStoreDTOMapper.regStoreToStore(regStoreDto);
        hibernateStoreDao.save(store);
    }

    @Transactional(readOnly = true)
    public List<Store> findByUser(long id){
        return hibernateStoreDao.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Optional<Store> findById(long id){
        Store byId = hibernateStoreDao.findById(id);
        if (byId != null) return Optional.of(byId);
        return Optional.empty();
    }

    public void delete(Store store){
        hibernateStoreDao.delete(store);
    }

    @Transactional(readOnly = true)
    public PageableGoods findPaginatedOffers(String storeName, int page, int size){
        int offset = paginate(page, size);
        return hibernateStoreDao.findOffersPaginated(storeName, offset, size);
    }

    public boolean addOffer(String storeName, GoodCreatorDto goodCreator){
        Store byName = hibernateStoreDao.findByName(storeName);

        if (byName != null){
             byName.getGoods().add(good);
             hibernateStoreDao.update(byName);
             return true;
        }
        return false;
    }

    public Optional<Good> findOfferByName(String storeName, String offerName){
        Good offerByName = hibernateStoreDao.findOfferByName(storeName, offerName);
        if (offerByName != null) return Optional.of(offerByName);
        return Optional.empty();
    }

    public boolean updateOffer(String storeName, Good offer){
        return hibernateStoreDao.updateOffer(storeName, offer);
    }

    private int paginate(int page, int size){
        if(page == 1){
            return page * size;
        } else {
            return  (page - 1) * size;
        }
    }
}
