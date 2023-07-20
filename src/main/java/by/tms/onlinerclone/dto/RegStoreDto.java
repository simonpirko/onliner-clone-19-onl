package by.tms.onlinerclone.dto;

import by.tms.onlinerclone.entity.SessionUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class RegStoreDto {

    @Pattern(regexp = "[A-Za-z&$#@*., -!?+=()\\[\\]{}<>\\d]*", message = "This store name contains invalid characters")
    private String name;

    private MultipartFile logo;

    private SessionUser user;
}
