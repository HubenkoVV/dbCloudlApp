package ua.training.model.dao.mapper;

import ua.training.model.entities.Periodical;
import ua.training.util.constant.TableColumns;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicalMapper implements GeneralMapper<Periodical> {
    /**
     * Create Periodical object with data from DB
     * @param rs resultSet that was gotten after execution of the request
     * @return periodical object
     * @throws SQLException
     */
    @Override
    public Periodical getFromResultSet(ResultSet rs) throws SQLException {
        return new Periodical.PeriodicalBuilder()
                .buildId(rs.getInt(TableColumns.PERIODICAL_ID))
                .buildName(rs.getString(TableColumns.PERIODICAL_NAME))
                .buildPrice(rs.getInt(TableColumns.PERIODICAL_PRICE))
                .buildShortDescription(rs.getString(TableColumns.PERIODICAL_DESCRIPTION))
                .buildLazy();
    }
}
