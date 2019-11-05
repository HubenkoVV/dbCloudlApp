package ua.training.model.dao;

import ua.training.model.entities.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {
    User findByLogin(String login);
    void updateMoney(User user);
    List<User> findByPeriodical(int idPeriodical);
}
