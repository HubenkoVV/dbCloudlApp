package ua.training.util.constant;

/**
 * Interface with all names of attributes that are used by Request in Servlet
 */
public interface Attributes {
    String PAGE = "page";
    String EXCEPTION = "exception";
    String MESSAGE = "message";
    String OPEN_REGISTRATION = "registration_modal_open";
    String OPEN_LOGIN = "login_modal_open";
    String LOCALE = "locale";

    String USER = "user";
    String NAME = "name";
    String SURNAME = "surname";
    String PHONE = "phone";
    String LOGIN = "login";
    String LOGIN_SIGN = "loginSignIn";
    String PASSWORD_SIGN = "passwordSignIn";
    String PASSWORD = "password";
    String PASSWORD_AGAIN = "passwordAgain";
    String USER_ROLE = "user_role";
    String MONEY = "user_money";

    String REG_EXCEPTION = "reg_exception";
    String SIGNUP_EXCEPTION = "signup_exception";

    String FULL_PRICE = "full_price";
    String PERIODICALS_IN_BASKET = "periodicals_in_basket";

    String PERIODICAL_PAGE = "periodicals_page";
    String PERIODICALS_USER = "user_periodicals";
    String ARTICLES_PAGE = "articles_page";
    String PAGES = "pages";
    String CURRENT_PAGE = "current_page";
    String PERIODICAL_LIST = "periodicals_list";
    String ARTICLE_LIST = "article_list";

    String PERIODICAL_NAME = "periodical_name";
    String PERIODICAL_PRICE = "periodical_price";
    String PERIODICAL_DESCRIPTION = "periodical_description";

    String ARTICLE_NAME = "article_name";
    String ARTICLE_TEXT = "article_text";
    String ARTICLE_ID = "article_id";

    String ARTICLE= "article";
    String ID_PERIODICAL = "id_periodical";
    String PERIODICAL = "periodical";
    String PERIODICAL_SEARCH = "search_name";
}
