package by.tms.onlinerclone.dto;

import by.tms.onlinerclone.entity.GoodCharacters;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GoodShowerDto {

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private Set<GoodCharacters> characters;

    private List<String> photosBase64;
}
