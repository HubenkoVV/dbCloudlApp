package ua.training.model.dao.mapper;

import ua.training.model.entities.Article;
import ua.training.util.constant.TableColumns;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleMapper implements GeneralMapper<Article> {
    /**
     * Create Article object with data from DB
     * @param rs resultSet that was gotten after execution of the request
     * @return article object
     * @throws SQLException
     */
    @Override
    public Article getFromResultSet(ResultSet rs) throws SQLException {
        return new Article.ArticleBuilder()
                .buildDate(rs.getDate(TableColumns.ARTICLE_DATE).toLocalDate())
                .buildId(rs.getInt(TableColumns.ARTICLE_ID))
                .buildIdPeriodical(rs.getInt(TableColumns.ARTICLE_PERIODICAL))
                .buildName(rs.getString(TableColumns.ARTICLE_NAME))
                .buildText(rs.getString(TableColumns.ARTICLE_TEXT))
                .build();
    }
}
