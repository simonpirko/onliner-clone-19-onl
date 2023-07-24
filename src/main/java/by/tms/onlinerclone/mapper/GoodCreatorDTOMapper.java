package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.dto.GoodCreatorDto;
import by.tms.onlinerclone.entity.Good;
import lombok.SneakyThrows;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class GoodCreatorDTOMapper {

    @SneakyThrows
    public static Good goodCreatorDtoToGood(GoodCreatorDto goodCreator){

        List<byte[]> photos = new ArrayList<>();

        for (MultipartFile photo : goodCreator.getPhotos()) {
            photos.add(photo.getBytes());
        }

        return Good.builder()
                .name(goodCreator.getName())
                .description(goodCreator.getDescription())
                .photos(photos)
                .build();
    }
}
