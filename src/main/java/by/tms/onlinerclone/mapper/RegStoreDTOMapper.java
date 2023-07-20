package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.dto.RegStoreDto;
import by.tms.onlinerclone.entity.SessionUser;
import by.tms.onlinerclone.entity.Store;
import by.tms.onlinerclone.entity.User;
import lombok.SneakyThrows;

import java.util.List;

public class RegStoreDTOMapper {

    @SneakyThrows
    public static Store regStoreToStore(RegStoreDto regStoreDto) {

        Store store = new Store();
        store.setName(regStoreDto.getName());

        SessionUser sessionUser = regStoreDto.getUser();

        User user = User.builder()
                .id(sessionUser.getId())
                .name(sessionUser.getName())
                .username(sessionUser.getUsername())
                .email(sessionUser.getEmail())
                .address(sessionUser.getAddress())
                .phoneNumber(sessionUser.getPhoneNumber())
                .build();

        store.setSuperAdmin(user);
        store.setAdministrators(List.of(user));
        store.setLogo(regStoreDto.getLogo().getBytes());
        return store;
    }
}
