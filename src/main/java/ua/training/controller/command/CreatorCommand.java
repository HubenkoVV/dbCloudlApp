package ua.training.controller.command;

import ua.training.model.service.ArticleService;
import ua.training.model.service.PaymentService;
import ua.training.model.service.PeriodicalService;
import ua.training.model.service.UserService;
import ua.training.util.constant.Commands;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CreatorCommand {

    private Map<String, Command> commands;

    public CreatorCommand() {
        UserService userService = new UserService();
        PeriodicalService periodicalService = new PeriodicalService();
        PaymentService paymentService = new PaymentService();
        ArticleService articleService = new ArticleService();

        commands = new HashMap<>();
        commands.put(Commands.LANGUAGE, new LanguageCommand());
        commands.put(Commands.LOGIN, new SignInCommand(userService));
        commands.put(Commands.REGISTRATION, new RegistrationCommand(userService));
        commands.put(Commands.SIGNOUT, new SignOutCommand());
        commands.put(Commands.ADD_ARTICLE,new AddArticlePageCommand(periodicalService));
        commands.put(Commands.ADD_PERIODICAL,new AddPeriodicalPageCommand());
        commands.put(Commands.CREATE_PERIODICAL, new AddPeriodicalCommand(periodicalService));
        commands.put(Commands.ARTICLES, new ArticleListCommand(articleService, periodicalService));
        commands.put(Commands.BUY_PERIODICAL, new BuyPeriodicalsCommand(paymentService));
        commands.put(Commands.PERIODICALS, new PeriodicalListCommand(periodicalService, userService));
        commands.put(Commands.SHOW_ARTICLE,new ShowArticleCommand(articleService, periodicalService));
        commands.put(Commands.UPDATE_ACCOUNT, new RecruitmentCommand(userService));
        commands.put(Commands.TO_BASKET, new AddToBasketCommand(periodicalService));
        commands.put(Commands.MY_ACCOUNT, new MyAccountCommand());
        commands.put(Commands.DELETE_FROM_BASKET, new DeleteFromBasketCommand(periodicalService));
        commands.put(Commands.CREATE_ARTICLE, new AddArticleCommand(articleService));
        commands.put("/", new PeriodicalListCommand(periodicalService, userService));
    }

    public String action(HttpServletRequest request){
        String path = request.getRequestURI();
        String name = path.replaceAll(".*/api/" , "");
        Command command = commands.getOrDefault(name, new PeriodicalListCommand(new PeriodicalService(), new UserService()));
        return command.execute(request);
    }
}
