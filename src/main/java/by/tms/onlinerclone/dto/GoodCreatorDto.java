package by.tms.onlinerclone.dto;

import by.tms.onlinerclone.entity.GoodCategory;
import by.tms.onlinerclone.entity.GoodCharacters;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GoodCreatorDto {

    private String name;

    private String description;

    private BigDecimal price;

    private GoodCategory category;

    private List<GoodCharacters> characters;

    private List<byte[]> photos;
}
