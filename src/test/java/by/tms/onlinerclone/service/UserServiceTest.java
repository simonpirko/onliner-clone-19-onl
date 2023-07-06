package by.tms.onlinerclone.service;

import by.tms.onlinerclone.dao.HibernateUserDao;
import by.tms.onlinerclone.entity.SessionUser;
import by.tms.onlinerclone.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private HibernateUserDao hibernateUserDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave() {
        RegUserDto regUserDto = new RegUserDto();
        User user = new User();
        when(RegUserDTOMapper.RegUserToUser(regUserDto)).thenReturn(user);

        userService.save(regUserDto);

        verify(hibernateUserDao, times(1)).save(user);
    }

    @Test
    public void testLogin() {

        LoginUserDto loginUserDto = new LoginUserDto();
        User user = new User();

        when(hibernateUserDao.findByEmail(loginUserDto.getEmail())).thenReturn(Optional.of(user));

        Optional<SessionUser> sessionUser = userService.login(loginUserDto);

        assertTrue(sessionUser.isPresent());

    }

}