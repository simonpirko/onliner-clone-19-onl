package by.tms.onlinerclone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Andrei Lisouski (Andrlis) - 11/07/2023 - 2:08
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HomePage {

    private List<Good> topGoods;
    private List<GoodCategory> categories;

}
