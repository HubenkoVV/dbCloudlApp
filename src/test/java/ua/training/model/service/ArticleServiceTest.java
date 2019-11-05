package ua.training.model.service;

import org.junit.Test;
import ua.training.model.dao.ArticleDao;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entities.Article;
import ua.training.model.service.exception.IncorrectDataException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class ArticleServiceTest {

    private DaoFactory daoFactoryMock = mock(DaoFactory.class);
    private ArticleDao articleDaoMock = mock(ArticleDao.class);

    @Test
    public void createArticle() throws Exception {
        Article article = new Article.ArticleBuilder()
                .buildId(1)
                .buildText("test")
                .buildName("test")
                .buildDate(LocalDate.now())
                .buildIdPeriodical(1)
                .build();
        Article failedArticle = new Article.ArticleBuilder()
                .buildId(2)
                .buildText("test")
                .buildName("test")
                .buildDate(LocalDate.now())
                .buildIdPeriodical(1)
                .build();

        when(daoFactoryMock.createArticleDao()).thenReturn(articleDaoMock);
        when(articleDaoMock.create(article)).thenReturn(1);
        when(articleDaoMock.create(failedArticle)).thenReturn(0);

        ArticleService articleService = new ArticleService();
        articleService.daoFactory = daoFactoryMock;
        Article resultArticle = articleService.createArticle(article);
        try {
            articleService.createArticle(failedArticle);
        } catch (IncorrectDataException ignored){}

        verify(daoFactoryMock,times(2)).createArticleDao();
        verify(articleDaoMock).create(article);
        verify(articleDaoMock).create(failedArticle);
        verify(articleDaoMock, times(2)).close();

        assertThat(resultArticle, is(article));
    }

    @Test
    public void getById() throws Exception {
        int id = 1;
        Article article = new Article.ArticleBuilder()
                .buildId(id)
                .build();

        when(daoFactoryMock.createArticleDao()).thenReturn(articleDaoMock);
        when(articleDaoMock.findById(id)).thenReturn(article);

        ArticleService articleService = new ArticleService();
        articleService.daoFactory = daoFactoryMock;
        Article resultArticle = articleService.getById(id);

        verify(daoFactoryMock).createArticleDao();
        verify(articleDaoMock).findById(id);
        verify(articleDaoMock).close();

        assertThat(resultArticle, is(article));
    }

    @Test
    public void getArticlesOnPagesByPeriodical() throws Exception {
        int id = 1;
        Map<Integer, List<Article>> listArticles = new HashMap<>();
        listArticles.put(1, new ArrayList<Article>(){{
            add(new Article.ArticleBuilder().buildId(1).buildIdPeriodical(1).build());
            add(new Article.ArticleBuilder().buildId(2).buildIdPeriodical(1).build());
            add(new Article.ArticleBuilder().buildId(3).buildIdPeriodical(1).build());
            add(new Article.ArticleBuilder().buildId(4).buildIdPeriodical(1).build());}});
        listArticles.put(2, new ArrayList<Article>(){{
            add(new Article.ArticleBuilder().buildId(5).buildIdPeriodical(1).build());
            add(new Article.ArticleBuilder().buildId(6).buildIdPeriodical(1).build());}});

        when(daoFactoryMock.createArticleDao()).thenReturn(articleDaoMock);
        when(articleDaoMock.findByPeriodicalFixedNumberOfArticles(id,4,0)).thenReturn(listArticles.get(1));
        when(articleDaoMock.findByPeriodicalFixedNumberOfArticles(id,4,4)).thenReturn(listArticles.get(2));

        ArticleService articleService = new ArticleService();
        articleService.daoFactory = daoFactoryMock;
        Map<Integer, List<Article>> resultListArticles = articleService.getArticlesOnPagesByPeriodical(id, 4);

        verify(daoFactoryMock).createArticleDao();
        verify(articleDaoMock).findByPeriodicalFixedNumberOfArticles(id, 4,0);
        verify(articleDaoMock).findByPeriodicalFixedNumberOfArticles(id,4,4);

        assertThat(resultListArticles, is(listArticles));
    }

    @Test
    public void textareaToHTML() throws Exception{
        ArticleService articleService = spy(new ArticleService());
        String text = "Test test\r\n Test \r\n Test \r\n\r\n\r\n Test";
        String resultText = articleService.textareaToHTML(text);
        verify(articleService).textareaToHTML(text);
        assertEquals("<p>" + text.replaceAll("\r\n","<p>") ,resultText);
    }

}