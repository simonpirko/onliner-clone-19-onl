package by.tms.onlinerclone.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StoreShowerDto {

    private String superAdminName;

    private String name;

    private List<GoodShowerDto> goods;

    private String logoBase64;
}
