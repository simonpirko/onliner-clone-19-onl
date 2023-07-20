package by.tms.onlinerclone.dto;

import by.tms.onlinerclone.entity.GoodCharacters;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class GoodCreatorDto {

    @Pattern(regexp = "[A-Za-z&$#@*., -!?+=()\\[\\]{}<>\\d]*", message = "This good name contains invalid characters")
    private String name;

    @Pattern(regexp = "[A-Za-z&$#@*., -!?+=()\\[\\]{}<>\\d]*", message = "This description contains invalid characters")
    private String description;

    private BigDecimal price;

    @Pattern(regexp = "([A-Za-z])*", message = "This category contains invalid characters")
    private String category;

    private Set<GoodCharacters> characters;

    private List<MultipartFile> photos;
}
