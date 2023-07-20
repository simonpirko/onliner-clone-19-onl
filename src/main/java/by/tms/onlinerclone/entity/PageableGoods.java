package by.tms.onlinerclone.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageableGoods {

    public List<Good> goodList;

    public Long countOfPages;

    public int size;
}
