package ua.training.util.constant;

import java.util.ResourceBundle;

/**
 * Class gets all db requests from resource
 */
public class Requests {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("requests");

    public static String INSERT_USER = getMessage("insert.user");
    public static String INSERT_PAYMENT = getMessage("insert.payment");
    public static String INSERT_PERIODICAL = getMessage("insert.periodical");
    public static String INSERT_ARTICLE = getMessage("insert.article");
    public static String INSERT_USER_PERIODICAL = getMessage("insert.user.periodical");
    public static String INSERT_PERIODICAL_PAYMENT = getMessage("insert.periodical.payment");
    public static String SELECT_ALL_USERS = getMessage("select.all.users");
    public static String SELECT_ALL_PAYMENT = getMessage("select.all.payment");
    public static String SELECT_ALL_PERIODICAL = getMessage("select.all.periodical");
    public static String SELECT_ALL_ARTICLE = getMessage("select.all.article");
    public static String SELECT_ID_USER = getMessage("select.id.user");
    public static String SELECT_ID_PAYMENT = getMessage("select.id.payment");
    public static String SELECT_ID_PERIODICAL = getMessage("select.id.periodical");
    public static String SELECT_ID_ARTICLE = getMessage("select.id.article");
    public static String SELECT_IDPERIODICAL_ARTICLE = getMessage("select.idperiodical.article");
    public static String SELECT_LOGIN_USER = getMessage("select.login.user");
    public static String SELECT_USER_PAYMENT = getMessage("select.user.payment");
    public static String SELECT_USER_PERIODICAL = getMessage("select.user.periodical");
    public static String SELECT_PERIODICAL_PAYMENT = getMessage("select.periodical.payment");
    public static String SELECT_PERIODICAL_USER = getMessage("select.periodical.user");
    public static String SELECT_PAYMENT_PERIODICAL = getMessage("select.payment.periodical");
    public static String UPDATE_USER_MONEY = getMessage("update.user.money");
    public static String UPDATE_USER_MONEY_BUY = getMessage("update.user.money.buy");
    public static String SELECT_PERIODICALS_LIMIT = getMessage("select.periodicals.limit");
    public static String SELECT_ARTICLES_LIMIT_BY_ID = getMessage("select.articles.by.periodical");
    public static String SEARCH_PERIODICAL = getMessage("search.periodical");

    private static String getMessage(String message) {
        return bundle.getString(message);
    }
}
