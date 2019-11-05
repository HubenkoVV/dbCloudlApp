package ua.training.model.entities.lazyload;

import ua.training.model.dao.ArticleDao;
import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.factory.AbstractDaoFactory;
import ua.training.model.entities.Article;
import ua.training.model.entities.Payment;
import ua.training.model.entities.Periodical;
import ua.training.model.entities.User;

import java.util.List;

public class LazyPeriodical extends Periodical {
    @Override
    public List<User> getUsers() {
        if (super.getUsers() == null) {
            AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();
            UserDao userDao = daoFactory.createUserDao();
            return userDao.findByPeriodical(getId());
        }
        return super.getUsers();
    }

    @Override
    public List<Payment> getPayments() {
        if (super.getPayments() == null) {
            AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();
            PaymentDao paymentDao = daoFactory.createPaymentDao();
            return paymentDao.findByPeriodical(getId());
        }
        return super.getPayments();
    }

    @Override
    public List<Article> getArticles() {
        if (super.getArticles() == null) {
            AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();
            ArticleDao articleDao = daoFactory.createArticleDao();
            return articleDao.findByPeriodical(getId());
        }
        return super.getArticles();
    }
}
