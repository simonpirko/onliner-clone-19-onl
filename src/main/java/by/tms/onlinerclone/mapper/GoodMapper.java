package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.dto.GoodShowerDto;
import by.tms.onlinerclone.entity.Good;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GoodMapper {

    public static GoodShowerDto goodToGoodShowerDto(Good good){

        GoodShowerDto goodShowerDto = new GoodShowerDto();
        goodShowerDto.setName(good.getName());
        goodShowerDto.setPrice(good.getPrice());
        goodShowerDto.setDescription(good.getDescription());
        goodShowerDto.setCategory(good.getCategory().getName());
        goodShowerDto.setCharacters(good.getCharacters());

        List<String> photos = new ArrayList<>();

        for (byte[] photo : good.getPhotos()) {
            photos.add(Base64.getEncoder().encodeToString(photo));
        }

        goodShowerDto.setPhotosBase64(photos);
        return goodShowerDto;
    }
}
