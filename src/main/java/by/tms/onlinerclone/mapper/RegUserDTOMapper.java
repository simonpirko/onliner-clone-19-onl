package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.dto.RegUserDto;
import by.tms.onlinerclone.entity.User;

public class RegUserDTOMapper {
    public static User regUserToUser(RegUserDto regUserDto){
        User user = new User();
        user.setName(regUserDto.getName());
        user.setUsername(regUserDto.getUsername());
        user.setEmail(regUserDto.getEmail());
        user.setAddress(regUserDto.getAddress());
        user.setPhoneNumber(regUserDto.getPhoneNumber());
        user.setPassword(regUserDto.getPassword());
        return user;
    }
}
