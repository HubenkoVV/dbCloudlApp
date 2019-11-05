package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.Periodical;
import ua.training.model.service.PeriodicalService;
import ua.training.util.constant.Attributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddToBasketCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddToBasketCommand.class);
    private PeriodicalService periodicalService;

    AddToBasketCommand(PeriodicalService periodicalService) {
        this.periodicalService = periodicalService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {

        List<Periodical> periodicalsInBasket = (List<Periodical>) request.getSession().getAttribute(Attributes.PERIODICALS_IN_BASKET);
        if(periodicalsInBasket == null) periodicalsInBasket = new ArrayList<>();

        int periodicalId = Integer.parseInt(request.getParameter(Attributes.ID_PERIODICAL));
        int fullPrice = 0;

        String priceInJSP = String.valueOf(request.getSession().getAttribute(Attributes.FULL_PRICE));
        if (priceInJSP != null && !priceInJSP.equals("null"))
            fullPrice = Integer.parseInt(priceInJSP);

        Periodical periodical = periodicalService.getById(periodicalId);
        periodicalsInBasket.add(periodical);
        fullPrice += periodical.getPrice();

        request.getSession().setAttribute(Attributes.FULL_PRICE, fullPrice);
        request.getSession().setAttribute(Attributes.PERIODICALS_IN_BASKET, periodicalsInBasket);
        logger.info("Added periodical with id " + periodicalId +" to basket");
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
