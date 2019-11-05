package ua.training.model.dao.factory;

import ua.training.model.dao.ArticleDao;
import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.UserDao;
import ua.training.model.dao.datasource.PoolConnection;
import ua.training.model.dao.impl.ArticleDaoJDBC;
import ua.training.model.dao.impl.PaymentDaoJDBC;
import ua.training.model.dao.impl.PeriodicalDaoJDBC;
import ua.training.model.dao.impl.UserDaoJDBC;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory extends AbstractDaoFactory {

    private DataSource dataSource = PoolConnection.getDataSource();

    @Override
    public UserDao createUserDao() { return new UserDaoJDBC(getConnection()); }

    @Override
    public PaymentDao createPaymentDao() {
        return new PaymentDaoJDBC(getConnection());
    }

    @Override
    public PeriodicalDao createPeriodicalDao() {
        return new PeriodicalDaoJDBC(getConnection());
    }

    @Override
    public ArticleDao createArticleDao() {
        return new ArticleDaoJDBC(getConnection());
    }

    private Connection getConnection(){
        try{
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
