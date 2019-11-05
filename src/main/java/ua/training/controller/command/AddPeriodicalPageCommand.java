package ua.training.controller.command;

import ua.training.util.constant.Attributes;
import ua.training.util.constant.Commands;
import ua.training.util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class AddPeriodicalPageCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(Attributes.PERIODICAL_NAME);
        String description = request.getParameter(Attributes.PERIODICAL_DESCRIPTION);

        request.setAttribute(Attributes.PERIODICAL_NAME, name);
        request.setAttribute(Attributes.PERIODICAL_DESCRIPTION, description);
        request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_PERIODICAL + "&" + Attributes.PERIODICAL_NAME + "="
                + name + "&" + Attributes.PERIODICAL_DESCRIPTION + "=" + description);
        return Pages.ADD_PERIODICAL;
    }
}
