package ua.training.model.dao.impl;

import ua.training.model.dao.ArticleDao;
import ua.training.model.dao.mapper.ArticleMapper;
import ua.training.model.dao.util.GeneralConnectionMethod;
import ua.training.model.entities.Article;
import ua.training.util.constant.Requests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class works with table "Article" in DB
 */
public class ArticleDaoJDBC implements ArticleDao {

    private Connection connection;

    public ArticleDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method adds article into DB
     * @param entity - article created in AddArticleCommand
     * @return created article's id
     */
    @Override
    public int create(Article entity) {
        try(PreparedStatement ps = connection.prepareStatement(Requests.INSERT_ARTICLE, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,entity.getName());
            ps.setDate(2, Date.valueOf(entity.getDate()));
            ps.setInt(3,entity.getIdPeriodical());
            ps.setString(4,entity.getText());
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
     * Method gives article by id
     * @param id for search
     * @return article
     */
    @Override
    public Article findById(int id) {
        ArticleMapper articleMapper = new ArticleMapper();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ID_ARTICLE)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return (articleMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return list of articles
     */
    @Override
    public List<Article> findAll() {
        ArticleMapper articleMapper = new ArticleMapper();
        List<Article> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ALL_ARTICLE)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(articleMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Method gives articles from periodical
     * @param idPeriodical periodical's id
     * @return list of articles
     */
    @Override
    public List<Article> findByPeriodical(int idPeriodical) {
        ArticleMapper articleMapper = new ArticleMapper();
        List<Article> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_IDPERIODICAL_ARTICLE)){
            ps.setInt(1,idPeriodical);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(articleMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Method gives articles from periodical on the concrete page
     * @param id periodical's id
     * @param limit number of objects on page
     * @param offset start object
     * @return list of articles on page
     */
    @Override
    public List<Article> findByPeriodicalFixedNumberOfArticles(int id, int limit, int offset) {
        List<Article> resultList = new ArrayList<>();
        ArticleMapper articleMapper = new ArticleMapper();

        try (PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ARTICLES_LIMIT_BY_ID)) {
            ps.setInt(1, id);
            ps.setInt(2, limit);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(articleMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultList;
    }

    @Override
    public void update(Article entity, int id) {

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
