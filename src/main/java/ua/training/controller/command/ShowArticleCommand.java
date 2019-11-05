package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.Article;
import ua.training.model.service.ArticleService;
import ua.training.model.service.PeriodicalService;
import ua.training.util.constant.Attributes;
import ua.training.util.constant.Commands;
import ua.training.util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class ShowArticleCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ShowArticleCommand.class);
    private ArticleService articleService;
    private PeriodicalService periodicalService;

    ShowArticleCommand(ArticleService articleService, PeriodicalService periodicalService) {
        this.articleService = articleService;
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int idArticle = Integer.parseInt(request.getParameter(Attributes.ARTICLE_ID));
        Article article = articleService.getById(idArticle);

        request.setAttribute(Attributes.PERIODICAL, periodicalService.getById(article.getIdPeriodical()));
        request.setAttribute(Attributes.ARTICLE, article);
        request.getSession().setAttribute(Attributes.PAGE, Commands.SHOW_ARTICLE + "?"+ Attributes.ARTICLE_ID + "=" + idArticle );
        logger.info("Show article with id = " + idArticle);
        return Pages.ARTICLE;
    }
}
