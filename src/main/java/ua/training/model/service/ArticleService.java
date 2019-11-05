package ua.training.model.service;

import org.jetbrains.annotations.NotNull;
import ua.training.model.dao.ArticleDao;
import ua.training.model.dao.factory.AbstractDaoFactory;
import ua.training.model.entities.Article;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.Exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {

    AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    /**
     * Method creates new article
     * @param article for creating
     * @return created article
     * @throws IncorrectDataException if some data for creating was incorrect
     */
    public Article createArticle(Article article) throws IncorrectDataException {
        ArticleDao articleDao = daoFactory.createArticleDao();
        try{
            articleDao.setAutoCommit(false);
            article.setText(textareaToHTML(article.getText()));
            int id = articleDao.create(article);
            if (id == 0) {
                throw new SQLIntegrityConstraintViolationException();
            }
            article.setId(id);
        } catch (SQLIntegrityConstraintViolationException e) {
            articleDao.rollback();
            throw new IncorrectDataException(Exceptions.INCORRECT_DATA);
        } finally {
            articleDao.close();
        }
        return article;
    }

    /**
     * Method gives article by id
     * @param id for search
     * @return article
     */
    public Article getById(int id) {
        try(ArticleDao articleDao = daoFactory.createArticleDao()) {
            return articleDao.findById(id);
        }
    }

    /**
     * Methods gives articles from periodical divided on pages
     * @param id of periodical
     * @param articlesOnPage number of articles on one page
     * @return list of articles
     */
    public Map<Integer, List<Article>> getArticlesOnPagesByPeriodical(int id, int articlesOnPage) {
        try (ArticleDao articleDao = daoFactory.createArticleDao()) {
            Map<Integer, List<Article>> result = new HashMap<>();
            List<Article> pageOfArticles = articleDao
                    .findByPeriodicalFixedNumberOfArticles(id, articlesOnPage, 0);

            for (int pageNumber = 1; !pageOfArticles.isEmpty(); pageNumber++) {
                result.put(pageNumber, pageOfArticles);
                pageOfArticles = articleDao
                        .findByPeriodicalFixedNumberOfArticles(id, articlesOnPage, articlesOnPage*pageNumber);
            }
            return result;
        }
    }

    /**
     * Method transforms text from textarea field from jsp page
     * to one string
     * @param text from textarea
     * @return string of text
     */
    @NotNull String textareaToHTML(String text){
        String textInHTML = text.replaceAll("\r\n", "<p>");
        return "<p>" + textInHTML;
    }
}
