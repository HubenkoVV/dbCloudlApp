package ua.training.model.entities.lazyload;

import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.factory.AbstractDaoFactory;
import ua.training.model.entities.Payment;
import ua.training.model.entities.Periodical;

import java.util.List;

public class LazyPayment extends Payment {
    @Override
    public List<Periodical> getPeriodicals() {
        if (super.getPeriodicals() == null) {
            AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();
            PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao();
            return periodicalDao.findByPayment(getId());
        }
        return super.getPeriodicals();
    }


}
