package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.dto.GoodShowerDto;
import by.tms.onlinerclone.dto.PageableGoodsShowerDto;
import by.tms.onlinerclone.entity.Good;
import by.tms.onlinerclone.entity.PageableGoods;

import java.util.ArrayList;
import java.util.List;

public class PageableGoodsMapper {

    public static PageableGoodsShowerDto pageableGoodsToPageableGoodsShowDto(PageableGoods pageableGoods){

        PageableGoodsShowerDto pageableGoodsShowerDto = new PageableGoodsShowerDto();

        List<GoodShowerDto> goodShowerDtoList = new ArrayList<>();
        for (Good good : pageableGoods.getGoodList()) {
            goodShowerDtoList.add(GoodMapper.goodToGoodShowerDto(good));
        }

        pageableGoodsShowerDto.setGoodList(goodShowerDtoList);
        pageableGoodsShowerDto.setCountOfPages(pageableGoods.getCountOfPages());
        pageableGoodsShowerDto.setSize(pageableGoods.size);
        return pageableGoodsShowerDto;
    }
}
