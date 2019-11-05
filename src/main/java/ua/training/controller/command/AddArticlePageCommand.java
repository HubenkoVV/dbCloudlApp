package ua.training.controller.command;

import ua.training.model.service.PeriodicalService;
import ua.training.util.constant.Attributes;
import ua.training.util.constant.Commands;
import ua.training.util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class AddArticlePageCommand implements Command {

    private PeriodicalService periodicalService;

    AddArticlePageCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(Attributes.ARTICLE_NAME);
        String text = request.getParameter(Attributes.ARTICLE_TEXT);
        int idPeriodical = Integer.parseInt(request.getParameter(Attributes.ID_PERIODICAL));

        request.setAttribute(Attributes.PERIODICAL, periodicalService.getById(idPeriodical));
        request.setAttribute(Attributes.ARTICLE_NAME, name);
        request.setAttribute(Attributes.ARTICLE_TEXT, text);

        request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_ARTICLE + "?" + Attributes.ID_PERIODICAL + "=" + idPeriodical
                + "&" + Attributes.ARTICLE_NAME + "=" + name + "&" + Attributes.ARTICLE_TEXT + "=" + text);
        return Pages.ADD_ARTICLE;
    }
}
