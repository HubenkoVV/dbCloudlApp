package ua.training.model.dao;

import ua.training.model.entities.Payment;

import java.util.List;

public interface PaymentDao extends GenericDao<Payment> {
    List<Payment> findByUser(int idUser);
    List<Payment> findByPeriodical(int idPeriodical);
}
