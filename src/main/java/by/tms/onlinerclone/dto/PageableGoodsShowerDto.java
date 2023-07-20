package by.tms.onlinerclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableGoodsShowerDto {

    public List<GoodShowerDto> goodList;

    public Long countOfPages;

    public int size;
}
