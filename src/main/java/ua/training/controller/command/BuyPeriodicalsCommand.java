package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.Payment;
import ua.training.model.entities.Periodical;
import ua.training.model.entities.User;
import ua.training.model.entities.UserRole;
import ua.training.model.service.PaymentService;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.model.service.exception.NotEnoughMoneyException;
import ua.training.util.constant.Attributes;
import ua.training.util.constant.Exceptions;
import ua.training.util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BuyPeriodicalsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(BuyPeriodicalsCommand.class);
    private PaymentService paymentService;

    BuyPeriodicalsCommand(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION,null);

        if(request.getSession().getAttribute(Attributes.USER_ROLE) == UserRole.GUEST){
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(Exceptions.NOT_LOGGED_IN));
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        final int[] price = {(Integer) request.getSession().getAttribute(Attributes.FULL_PRICE)};
        User user = (User) request.getSession().getAttribute(Attributes.USER);
        List<Periodical> periodicals = (List<Periodical>) request.getSession().getAttribute(Attributes.PERIODICALS_IN_BASKET);
        user.getPeriodicals().forEach(periodical -> {
            if(periodicals.contains(periodical)) {
                periodicals.remove(periodical);
                price[0] -= periodical.getPrice();
            }
        });

        try{
            Payment payment = new Payment.PaymentBuilder()
                    .buildIdUser(user.getId())
                    .buildPeriodicals(periodicals)
                    .buildPrice(price[0])
                    .build();
            payment = paymentService.createPayment(payment, user);
            user.setMoney(user.getMoney() - payment.getPrice());
        } catch (IncorrectDataException | NotEnoughMoneyException e) {
            request.setAttribute(Attributes.EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            logger.info("Buying periodicals was failed");
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        request.getSession().setAttribute(Attributes.PERIODICALS_IN_BASKET, null);
        request.getSession().setAttribute(Attributes.USER, user);
        request.getSession().setAttribute(Attributes.FULL_PRICE, 0);
        logger.info("Periodicals from basket were bought. Price = " + price[0]);
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
