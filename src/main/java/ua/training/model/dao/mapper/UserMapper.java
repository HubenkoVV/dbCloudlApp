package ua.training.model.dao.mapper;

import ua.training.model.entities.User;
import ua.training.model.entities.UserRole;
import ua.training.util.constant.TableColumns;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements GeneralMapper<User> {
    /**
     * Create User object with data from DB
     * @param rs resultSet that was gotten after execution of the request
     * @return user
     * @throws SQLException
     */
    @Override
    public User getFromResultSet(ResultSet rs) throws SQLException {
        return new User.UserBuilder()
                .buildId(rs.getInt(TableColumns.USER_ID))
                .buildLogin(rs.getString(TableColumns.USER_LOGIN))
                .buildPassword(rs.getString(TableColumns.USER_PASSWORD))
                .buildName(rs.getString(TableColumns.USER_NAME))
                .buildSurname(rs.getString(TableColumns.USER_SURNAME))
                .buildMoney(rs.getInt(TableColumns.USER_MONEY))
                .buildPhone(rs.getString(TableColumns.USER_PHONE))
                .buildRole(UserRole.valueOf(rs.getString(TableColumns.USER_ROLE)))
                .buildLazy();
    }
}
