package ua.training.model.service;

import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.factory.AbstractDaoFactory;
import ua.training.model.entities.Periodical;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.util.constant.Exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicalService {
    AbstractDaoFactory daoFactory = AbstractDaoFactory.getInstance();

    /**
     * Method creates new periodical
     * @param periodical for creating
     * @return created periodical
     * @throws IncorrectDataException if some data for creating was incorrect
     */
    public Periodical createPeriodical(Periodical periodical) throws IncorrectDataException {
        PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao();
        try{
            periodicalDao.setAutoCommit(false);
            int id = periodicalDao.create(periodical);
            if (id == 0) {
                throw new SQLIntegrityConstraintViolationException();
            }
            periodical.setId(id);
        } catch (SQLIntegrityConstraintViolationException e) {
            periodicalDao.rollback();
            throw new IncorrectDataException(Exceptions.INCORRECT_DATA);
        } finally {
            periodicalDao.close();
        }
        return periodical;
    }

    /**
     * Method gives periodical by id
     * @param id for search
     * @return periodical
     */
    public Periodical getById(int id) {
        try(PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao()) {
            return periodicalDao.findById(id);
        }
    }

    /**
     * Methods gives periodicals divided on pages
     * @param periodicalsOnPage number of periodicals on one page
     * @return list of periodicals
     */
    public Map<Integer, List<Periodical>> getPeriodicalsOnPages(int periodicalsOnPage) {
        try (PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao()) {
            Map<Integer, List<Periodical>> result = new HashMap<>();
            List<Periodical> pageOfPeriodicals = periodicalDao.findFixedNumberOfPeriodicals(periodicalsOnPage, 0);

            for (int pageNumber = 1; !pageOfPeriodicals.isEmpty(); pageNumber++) {
                result.put(pageNumber, pageOfPeriodicals);
                pageOfPeriodicals = periodicalDao.findFixedNumberOfPeriodicals(periodicalsOnPage, periodicalsOnPage*pageNumber);
            }
            periodicalDao.close();
            return result;
        }
    }

    /**
     * Methods gives periodicals from search divided on pages
     * @param name whole name or part of it for search
     * @param periodicalsOnPage number periodicals on one page
     * @return list of periodicals
     */
    public Map<Integer, List<Periodical>> searchPeriodicals(String name, int periodicalsOnPage) {
        try (PeriodicalDao periodicalDao = daoFactory.createPeriodicalDao()) {
            Map<Integer, List<Periodical>> result = new HashMap<>();
            List<Periodical> pageOfPeriodicals = periodicalDao.searchPeriodicals(name, periodicalsOnPage, 0);

            for (int pageNumber = 1; !pageOfPeriodicals.isEmpty(); pageNumber++) {
                result.put(pageNumber, pageOfPeriodicals);
                pageOfPeriodicals = periodicalDao.searchPeriodicals(name, periodicalsOnPage, periodicalsOnPage*pageNumber);
            }
            periodicalDao.close();
            return result;
        }
    }


}
