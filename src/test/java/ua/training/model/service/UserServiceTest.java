package ua.training.model.service;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entities.User;
import ua.training.model.entities.UserRole;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.RegexForUser;
import ua.training.util.secure.SecurePasswordMD5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceTest {

    private DaoFactory daoFactoryMock = mock(DaoFactory.class);
    private UserDao userDaoMock = mock(UserDao.class);

    @Test
    public void createUser() throws Exception {
        User user = new User.UserBuilder()
                .buildId(1)
                .buildLogin("test@test.test")
                .buildName("test")
                .buildPassword("testtest")
                .buildSurname("test")
                .buildRole(UserRole.USER)
                .buildPhone("")
                .buildLazy();
        User failedUser = new User.UserBuilder()
                .buildLogin("test@test.test")
                .buildName("test")
                .buildPassword("testtest")
                .buildSurname("test")
                .buildRole(UserRole.USER)
                .buildPhone("")
                .buildLazy();

        when(daoFactoryMock.createUserDao()).thenReturn(userDaoMock);
        when(userDaoMock.create(user)).thenReturn(1);
        when(userDaoMock.create(failedUser)).thenReturn(0);

        UserService userService = new UserService();
        userService.daoFactory = daoFactoryMock;
        User resultUser = userService.createUser(user, "testtest", "testtest");
        try {
            userService.createUser(failedUser, "testtest", "testtest");
        } catch (IncorrectDataException ignored){}

        verify(daoFactoryMock,times(2)).createUserDao();
        verify(userDaoMock).create(user);
        verify(userDaoMock).create(failedUser);
        verify(userDaoMock, times(2)).close();

        assertThat(resultUser, is(user));
    }

    @Test
    public void getByLoginAndPassword() throws Exception {
        String login = "test@test.test";
        String password = "testtest";
        User user = new User.UserBuilder()
                .buildLogin(login)
                .buildPassword(SecurePasswordMD5.getSecurePassword("testtest"))
                .buildLazy();

        when(daoFactoryMock.createUserDao()).thenReturn(userDaoMock);
        when(userDaoMock.findByLogin(login)).thenReturn(user);

        UserService userService = new UserService();
        userService.daoFactory = daoFactoryMock;
        User resultUser =userService.getByLoginAndPassword(login,password);

        verify(daoFactoryMock).createUserDao();
        verify(userDaoMock).findByLogin(login);
        verify(userDaoMock).close();

        assertThat(resultUser, is(user));
    }

    @Test
    public void updateAccount() throws Exception {
        String money = "12";
        User user = new User.UserBuilder()
                .buildId(1)
                .buildLogin("test@test.test")
                .buildPassword("testtest")
                .buildLazy();

        when(daoFactoryMock.createUserDao()).thenReturn(userDaoMock);
        doNothing().when(userDaoMock).updateMoney(user);

        UserService userService = new UserService();
        userService.daoFactory = daoFactoryMock;
        User resultUser = userService.updateAccount(user,money);

        verify(daoFactoryMock).createUserDao();
        verify(userDaoMock, times(1)).updateMoney(user);
        verify(userDaoMock).close();

        assertEquals(Integer.parseInt(money), resultUser.getMoney());
    }

    @Test
    public void getById() throws Exception {
        int id = 1;
        User user = new User.UserBuilder()
                .buildId(id)
                .buildLazy();

        when(daoFactoryMock.createUserDao()).thenReturn(userDaoMock);
        when(userDaoMock.findById(id)).thenReturn(user);

        UserService userService = new UserService();
        userService.daoFactory = daoFactoryMock;
        User resultUser = userService.getById(id);

        verify(daoFactoryMock).createUserDao();
        verify(userDaoMock).findById(id);
        verify(userDaoMock).close();

        assertThat(resultUser, is(user));
    }

    @Test
    public void checkRegistrationData() throws Exception {
        UserService userService = spy(new UserService());
        String password = "testtest";
        User user = new User.UserBuilder()
                .buildPassword("testpassword")
                .buildLogin("test@test.test")
                .buildPhone("")
                .buildLazy();

        userService.checkRegistrationData(user, password, password);
        try {
            userService.checkRegistrationData(user, password, "");
        } catch (IncorrectDataException ignore){}

        verify(userService).checkRegistrationData(user,password,password);
        verify(userService).checkRegistrationData(user,password,"");
    }

    @Test
    public void isDataCorrect() throws Exception {
        UserService userService = spy(new UserService());
        assertTrue(userService.isDataCorrect("test@test.test", RegexForUser.LOGIN));
        assertTrue(userService.isDataCorrect("testtest123", RegexForUser.PASSWORD));
        assertTrue(userService.isDataCorrect("125", RegexForUser.MONEY));
        assertTrue(userService.isDataCorrect("+38(073)1245785", RegexForUser.PHONE));
    }
}