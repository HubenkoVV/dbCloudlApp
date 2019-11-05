package ua.training.util.constant;

/**
 * Interface with names of table columns in DB
 */
public interface TableColumns {

    String USER_ID = "iduser";
    String USER_NAME = "name";
    String USER_SURNAME = "surname";
    String USER_LOGIN = "login";
    String USER_PASSWORD = "password";
    String USER_ROLE = "role";
    String USER_PHONE = "phone";
    String USER_MONEY = "money";

    String PERIODICAL_ID = "idperiodical";
    String PERIODICAL_NAME = "namePer";
    String PERIODICAL_PRICE = "pricePer";
    String PERIODICAL_DESCRIPTION = "short_description";

    String ARTICLE_ID = "idarticle";
    String ARTICLE_NAME = "nameArt";
    String ARTICLE_DATE = "date";
    String ARTICLE_PERIODICAL = "idperiodical";
    String ARTICLE_TEXT = "text";

    String PAYMENT_ID = "idpayment";
    String PAYMENT_PRICE = "pricePay";
    String PAYMENT_USER = "iduser";
}
