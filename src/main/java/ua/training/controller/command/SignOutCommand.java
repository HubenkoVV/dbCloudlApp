package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.model.entities.UserRole;
import ua.training.util.constant.Attributes;
import ua.training.util.constant.Pages;

import javax.servlet.http.HttpServletRequest;

public class SignOutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(SignOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Attributes.USER_ROLE, UserRole.GUEST);
        request.getSession().setAttribute(Attributes.USER, null);
        logger.info("Sign out");
        request.getSession().setAttribute(Attributes.PAGE, Pages.HOME);
        return Pages.HOME;
    }
}
