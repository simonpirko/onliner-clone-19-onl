package by.tms.onlinerclone.dto;

import by.tms.onlinerclone.entity.SessionUser;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class RegStoreDto {

    @Pattern(regexp = "([A-Za-z&$#@*.,\\d ]\\-?)*", message = "This store name contains invalid characters")
    private String name;

    private SessionUser user;
}
