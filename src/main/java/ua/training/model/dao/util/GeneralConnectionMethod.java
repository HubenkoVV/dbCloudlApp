package ua.training.model.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class contains general methods for work with connection
 */
public class GeneralConnectionMethod {
    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setAutoCommit(Connection connection, boolean state) {
        try {
            connection.setAutoCommit(state);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
