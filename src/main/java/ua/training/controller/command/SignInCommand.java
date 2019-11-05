package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.User;
import ua.training.model.service.UserService;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.Attributes;
import ua.training.util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;

public class SignInCommand implements Command{

    private static final Logger logger = LogManager.getLogger(SignInCommand.class);
    private UserService userService;

    SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute(Attributes.EXCEPTION, null);
        String email = request.getParameter(Attributes.LOGIN_SIGN);
        String password = request.getParameter(Attributes.PASSWORD_SIGN);
        try {
            User user = userService.getByLoginAndPassword(email, password);
            request.getSession().setAttribute(Attributes.USER, user);
            request.getSession().setAttribute(Attributes.USER_ROLE, user.getRole());
        } catch (IncorrectDataException e) {
            logger.info("Sign in by \'" + email + "\' was failed");
            request.setAttribute(Attributes.SIGNUP_EXCEPTION, LocalizeMessage.getException(e.getMessage()));
            request.setAttribute(Attributes.OPEN_LOGIN, "$(\"#login_modal\").modal({show: true});");
            return (String) request.getSession().getAttribute(Attributes.PAGE);
        }
        logger.info("Sign in by \'" + email);
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
