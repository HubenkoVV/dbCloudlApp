package ua.training.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.util.constant.Attributes;
import ua.training.util.locale.LocalizeMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class LanguageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LanguageCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        String locale = request.getParameter(Attributes.LOCALE);
        request.getSession().setAttribute(Attributes.LOCALE, locale);
        LocalizeMessage.setLocale(new Locale(locale));
        logger.info("Language was changed on " + locale);
        return (String) request.getSession().getAttribute(Attributes.PAGE);
    }
}
