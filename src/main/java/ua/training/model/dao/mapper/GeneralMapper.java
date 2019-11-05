package ua.training.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface GeneralMapper<T> {
    T getFromResultSet(ResultSet rs) throws SQLException;
}
