package ua.training.model.dao;

import ua.training.model.entities.Periodical;

import java.util.List;

public interface PeriodicalDao extends GenericDao<Periodical> {
    List<Periodical> findByUser(int idUser);
    List<Periodical> findByPayment(int idPayment);
    List<Periodical> findFixedNumberOfPeriodicals(int limit, int offset);
    List<Periodical> searchPeriodicals(String name, int limit, int offset);
}
