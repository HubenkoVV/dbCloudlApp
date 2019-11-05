package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.User;
import ua.training.model.entities.UserRole;
import ua.training.model.service.UserService;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.Attributes;
import ua.training.util.locale.LocalizeMessage;
import ua.training.util.secure.SecurePasswordMD5;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private UserService userService;

    RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(Attributes.LOGIN);
        request.setAttribute(Attributes.EXCEPTION, null);
        request.setAttribute(Attributes.MESSAGE, null);
        try {
            User user = userService.createUser(
                    new User.UserBuilder()
                            .buildLogin(request.getParameter(Attributes.LOGIN))
                            .buildName(request.getParameter(Attributes.NAME))
                            .buildSurname(request.getParameter(Attributes.SURNAME))
                            .buildPassword(SecurePasswordMD5.getSecurePassword(request.getParameter(Attributes.PASSWORD)))
                            .buildRole(UserRole.USER)
                            .buildMoney(0)
                            .buildPhone(request.getParameter(Attributes.PHONE))
                            .buildLazy(), request.getParameter(Attributes.PASSWORD), request.getParameter(Attributes.PASSWORD_AGAIN));
            request.getSession().setAttribute(Attributes.USER, user);
            request.getSession().setAttribute(Attributes.USER_ROLE, user.getRole());
        } catch (IncorrectDataException e) {
            request.setAttribute(Attributes.REG_EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            logger.info("Registration by \'" + email + "\' was failed");
            request.setAttribute(Attributes.OPEN_REGISTRATION, "$(\"#registration_modal\").modal({show: true});");
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }

        logger.info("Registration by \'" + email + "\'");
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
