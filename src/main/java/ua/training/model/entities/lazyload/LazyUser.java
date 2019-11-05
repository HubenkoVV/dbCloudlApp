package ua.training.model.entities.lazyload;

import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.factory.AbstractDaoFactory;
import ua.training.model.entities.Payment;
import ua.training.model.entities.Periodical;
import ua.training.model.entities.User;

import java.util.List;

public class LazyUser extends User {
    @Override
    public List<Payment> getPayments() {
        if (super.getPeriodicals() == null) {
            AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();
            PaymentDao paymentDao = daoFactory.createPaymentDao();
            return paymentDao.findByUser(getId());
        }
        return super.getPayments();
    }

    @Override
    public List<Periodical> getPeriodicals() {
        if (super.getPeriodicals() == null) {
            AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();
            PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao();
            return periodicalDao.findByUser(getId());
        }
        return super.getPeriodicals();
    }
}
