package ua.training.model.dao.factory;

import ua.training.model.dao.ArticleDao;
import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.UserDao;

public abstract class AbstractDaoFactory{
    private static AbstractDaoFactory daoFactory;

    public abstract UserDao createUserDao();
    public abstract PaymentDao createPaymentDao();
    public abstract PeriodicalDao createPeriodicalDao();
    public abstract ArticleDao createArticleDao();

    public static AbstractDaoFactory getInstance(){
        if( daoFactory == null ){
            synchronized (AbstractDaoFactory.class){
                if(daoFactory==null){
                    daoFactory = new DaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
