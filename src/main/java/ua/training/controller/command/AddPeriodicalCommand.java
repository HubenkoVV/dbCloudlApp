package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.Periodical;
import ua.training.model.service.PeriodicalService;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.*;
import ua.training.util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class AddPeriodicalCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddPeriodicalCommand.class);
    private PeriodicalService periodicalService;

    AddPeriodicalCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION, null);
        request.setAttribute(Attributes.MESSAGE, null);
        Periodical periodical = new Periodical.PeriodicalBuilder()
                .buildName(request.getParameter(Attributes.PERIODICAL_NAME))
                .buildShortDescription(request.getParameter(Attributes.PERIODICAL_DESCRIPTION))
                .build();

        try {
            String price = request.getParameter(Attributes.PERIODICAL_PRICE);
            checkPriceValue(price);
            periodical.setPrice(Integer.parseInt(price));
            periodicalService.createPeriodical(periodical);
        } catch (IncorrectDataException e) {
            logger.info("Failed adding periodical");
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            request.getSession().setAttribute(Attributes.PAGE, Commands.ADD_PERIODICAL + "?"+
                    Attributes.PERIODICAL_NAME + "=" + periodical.getName() + "&" + Attributes.PERIODICAL_DESCRIPTION + "="
                    + periodical.getShortDescription());
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        logger.info("Added periodical");
        request.setAttribute(Attributes.MESSAGE, LocalizeMessage.getMessage(Messages.ADD_PERIODICAL));
        request.getSession().setAttribute(Attributes.PAGE, Commands.PERIODICALS + "?" + Attributes.PERIODICAL_PAGE
                + "=" + 1);
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }

    private void checkPriceValue(String price) throws IncorrectDataException {
        if(!Pattern.compile(RegexForUser.MONEY).matcher(price).matches())
            throw new IncorrectDataException(Exceptions.WRONG_MONEY);
    }
}
