package ua.training.model.service;

import ua.training.model.dao.UserDao;
import ua.training.model.dao.factory.AbstractDaoFactory;
import ua.training.model.entities.User;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.Exceptions;
import ua.training.util.constant.RegexForUser;
import ua.training.util.secure.SecurePasswordMD5;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    /**
     * Method creates new user
     * @param user for creating
     * @return created user
     * @throws IncorrectDataException if some data for creating was incorrect
     */
    public User createUser(User user, String password, String repeatPassword) throws IncorrectDataException {
        checkRegistrationData(user, password, repeatPassword);
        UserDao userDao = daoFactory.createUserDao();
        try{
            userDao.setAutoCommit(false);
            int id = userDao.create(user);
            if (id == 0) {
                throw new SQLIntegrityConstraintViolationException();
            }
            user.setId(id);
        } catch (SQLIntegrityConstraintViolationException e) {
            userDao.rollback();
            throw new IncorrectDataException(Exceptions.NOT_UNIQUE_LOGIN);
        } finally {
            userDao.close();
        }
        return user;
    }

    /**
     * Method gives user by login for SingInCommand and check password with user's real password
     * @param login for search
     * @param password from authorization
     * @return user
     * @throws IncorrectDataException if password is wrong or login isn't exist
     */
    public User getByLoginAndPassword(String login, String password) throws IncorrectDataException {
        try(UserDao userDao = daoFactory.createUserDao()) {
            User user = userDao.findByLogin(login);
            if (user == null) {
                throw new IncorrectDataException(Exceptions.WRONG_LOGIN);
            }
            checkPassword(password, user.getPassword());
            return user;
        }
    }

    /**
     * Method maximizes user account sum on number that user inputted
     * @param user for updating
     * @param money sum for updating
     * @return user with new information about his money
     * @throws IncorrectDataException if format of number is incorrect
     */
    public User updateAccount(User user, String money) throws IncorrectDataException {
        try(UserDao userDao = daoFactory.createUserDao()) {
            if(!isDataCorrect(money, RegexForUser.MONEY))
                throw new IncorrectDataException(Exceptions.WRONG_MONEY);
            user.setMoney(user.getMoney() + Integer.parseInt(money));
            userDao.setAutoCommit(false);
            userDao.updateMoney(user);
            return user;
        }
    }

    /**
     * Method gives user by id
     * @param id for search
     * @return user
     */
    public User getById(int id) {
        User user;
        try(UserDao userDao = daoFactory.createUserDao()) {
            user = userDao.findById(id);
        }
        return user;
    }

    /**
     * Check data for registration action
     * @param user user that did registration
     * @param password for checking with patten
     * @param repeatPassword for checking with password
     * @throws IncorrectDataException if some data is incorrect
     */
    void checkRegistrationData(User user, String password, String repeatPassword) throws IncorrectDataException {
        if (!isDataCorrect(user.getLogin(), RegexForUser.LOGIN)) {
            throw new IncorrectDataException(Exceptions.INCORRECT_LOGIN);
        }
        if (!isDataCorrect(password, RegexForUser.PASSWORD)) {
            throw new IncorrectDataException(Exceptions.INCORRECT_PASSWORD);
        }
        if (!user.getPhone().equals("") && !isDataCorrect(user.getPhone(),RegexForUser.PHONE)) {
            throw new IncorrectDataException(Exceptions.INCORRECT_PHONE);
        }
        if(!password.equals(repeatPassword)){
            throw new IncorrectDataException(Exceptions.INCORRECT_PASSWORD);
        }
    }

    /**
     * Check password for sign in action
     * @param password input
     * @param userPassword from DB
     * @throws IncorrectDataException if passwords are different
     */
    private void checkPassword(String password, String userPassword) throws IncorrectDataException {
        if(!SecurePasswordMD5.verifyPassword(password, userPassword)){
            throw new IncorrectDataException(Exceptions.WRONG_PASSWORD);
        }
    }

    /**
     * Check different data that have a special pattern
     * @param data text for checking
     * @param regex pattern
     * @return result of checking
     */
    boolean isDataCorrect(String data, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(data);
        return m.matches();
    }

}
