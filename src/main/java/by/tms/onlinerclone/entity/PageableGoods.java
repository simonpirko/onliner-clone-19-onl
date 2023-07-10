package by.tms.onlinerclone.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageableGoods {

    public List<Good> goodList;

    public Long countOfPages;

    public int size;
}
