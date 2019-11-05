package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.Article;
import ua.training.model.service.ArticleService;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.Attributes;
import ua.training.util.constant.Commands;
import ua.training.util.constant.Messages;
import ua.training.util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class AddArticleCommand implements Command{

    private ArticleService articleService;
    private static final Logger logger = LogManager.getLogger(AddArticleCommand.class);

    AddArticleCommand(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION, null);
        request.setAttribute(Attributes.MESSAGE, null);

        Article article = new Article.ArticleBuilder()
                .buildDate(LocalDate.now())
                .buildIdPeriodical(Integer.parseInt(request.getParameter(Attributes.ID_PERIODICAL)))
                .buildName(request.getParameter(Attributes.ARTICLE_NAME))
                .buildText(request.getParameter(Attributes.ARTICLE_TEXT))
                .build();

        try {
            article = articleService.createArticle(article);
        } catch (IncorrectDataException e) {
            logger.info("Failed adding article");

            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_ARTICLE + "?"+
                    Attributes.ARTICLE_NAME + "=" + article.getName() + "&" + Attributes.ARTICLE_TEXT + "=" + article.getText()
                    + "&" + Attributes.ID_PERIODICAL + "=" + article.getIdPeriodical());
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        request.setAttribute(Attributes.ARTICLE, article);
        request.setAttribute(Attributes.MESSAGE, LocalizeMessage.getMessage(Messages.ADD_ARTICLE));
        request.getSession().setAttribute(Attributes.PAGE, Commands.SHOW_ARTICLE + "?"+ Attributes.ARTICLE_ID + "=" + article.getId() );
        logger.info("Added article");
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
