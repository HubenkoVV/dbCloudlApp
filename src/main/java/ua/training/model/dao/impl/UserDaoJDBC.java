package ua.training.model.dao.impl;

import ua.training.model.dao.UserDao;
import ua.training.model.dao.mapper.UserMapper;
import ua.training.model.dao.util.GeneralConnectionMethod;
import ua.training.model.entities.User;
import ua.training.util.constant.Requests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class works with table "User" in DB
 */
public class UserDaoJDBC implements UserDao {

    private Connection connection;

    public UserDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method gives user by entered login
     * @param login for search
     * @return user
     */
    @Override
    public User findByLogin(String login) {
        UserMapper userMapper = new UserMapper();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_LOGIN_USER, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,login);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return (userMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method adds user into DB
     * @param entity - user created in RegistrationCommand
     * @return created user's id
     */
    @Override
    public int create(User entity) {
        try(PreparedStatement ps = connection.prepareStatement(Requests.INSERT_USER, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,entity.getName());
            ps.setString(2,entity.getSurname());
            ps.setString(3,entity.getLogin());
            ps.setString(4,entity.getPassword());
            ps.setString(5,entity.getRole().getName());
            ps.setInt(6,entity.getMoney());
            ps.setString(7,entity.getPhone());
            ps.executeUpdate();
            connection.commit();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method gives user by id
     * @param id for search
     * @return user
     */
    @Override
    public User findById(int id) {
        UserMapper userMapper = new UserMapper();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ID_USER)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return (userMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return list of users
     */
    @Override
    public List<User> findAll() {
        UserMapper userMapper = new UserMapper();
        List<User> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ALL_USERS)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    resultList.add(userMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Method adds entered sum to user's account
     * @param user with new money sum
     */
    @Override
    public void updateMoney(User user) {
        try (PreparedStatement ps = connection.prepareStatement(Requests.UPDATE_USER_MONEY)) {
            ps.setInt(1, user.getMoney());
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method gives list of subscribers
     * @param idPeriodical for find
     * @return list of subscribers
     */
    @Override
    public List<User> findByPeriodical(int idPeriodical) {
        UserMapper userMapper = new UserMapper();
        List<User> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_PERIODICAL_USER)){
            ps.setInt(1,idPeriodical);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(userMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void update(User entity, int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        GeneralConnectionMethod.close(connection);
    }

    @Override
    public void setAutoCommit(boolean state) {
        GeneralConnectionMethod.setAutoCommit(connection,state);
    }

    @Override
    public void rollback() {
        GeneralConnectionMethod.rollback(connection);
    }
}
