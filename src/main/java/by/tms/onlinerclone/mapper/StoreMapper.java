package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.dto.GoodShowerDto;
import by.tms.onlinerclone.dto.StoreShowerDto;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.Store;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class StoreMapper {

    public static StoreShowerDto storeToStoreShowerDto(Store store){

        StoreShowerDto storeShowerDto = new StoreShowerDto();

        storeShowerDto.setSuperAdminName(store.getSuperAdmin().getName());
        storeShowerDto.setName(store.getName());

        List<GoodShowerDto> goodShowerDtoList = new ArrayList<>();
        for (Good good : store.getGoods()) {
            goodShowerDtoList.add(GoodMapper.goodToGoodShowerDto(good));
        }

        storeShowerDto.setGoods(goodShowerDtoList);
        storeShowerDto.setLogoBase64(Base64.getEncoder().encodeToString(store.getLogo()));
        return storeShowerDto;
    }
}
