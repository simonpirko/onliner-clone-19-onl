package by.tms.onlinerclone.mapper;

import by.tms.onlinerclone.entity.SessionUser;
import by.tms.onlinerclone.entity.User;

public class UserMapper {
    public static SessionUser userToSessionUser(User user){
        SessionUser sessionUser = new SessionUser();
        sessionUser.setId(user.getId());
        sessionUser.setName(user.getName());
        sessionUser.setUsername(user.getUsername());
        sessionUser.setEmail(user.getEmail());
        sessionUser.setAddress(user.getAddress());
        sessionUser.setPhoneNumber(user.getPhoneNumber());
        return sessionUser;
    }
}
