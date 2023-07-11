package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateUserDao;
import by.tms.onlinerclone.dto.LoginUserDto;
import by.tms.onlinerclone.dto.RegUserDto;
import by.tms.onlinerclone.entity.SessionUser;
import by.tms.onlinerclone.entity.User;
import by.tms.onlinerclone.mapper.RegUserDTOMapper;
import by.tms.onlinerclone.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private HibernateUserDao hibernateUserDao;

    public void save(RegUserDto regUserDto) {
        User user = RegUserDTOMapper.regUserToUser(regUserDto);
        hibernateUserDao.save(user);
    }

    public Optional<SessionUser> login(LoginUserDto loginUserDto) {
        Optional<User> user = hibernateUserDao.findByEmail(loginUserDto.getEmail());
        if (user.isPresent()) {

            User currentUser = user.get();

            if (currentUser.getPassword().equals(loginUserDto.getPassword())) {
                return Optional.of(UserMapper.userToSessionUser(currentUser));
            }
        }
        return Optional.empty();
    }
}
