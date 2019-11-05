package ua.training.model.dao.impl;

import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.mapper.PaymentMapper;
import ua.training.model.dao.util.GeneralConnectionMethod;
import ua.training.model.entities.Payment;
import ua.training.model.entities.Periodical;
import ua.training.util.constant.Requests;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class works with table "Payment" in DB
 */
public class PaymentDaoJDBC implements PaymentDao {

    private Connection connection;

    public PaymentDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method adds payment into DB and connect user with periodicals and periodicals with payment
     * @param entity - payment created in BuyPeriodicalsCommand
     * @return created payment's id
     */
    @Override
    public int create(Payment entity) {
        try(PreparedStatement ps = connection.prepareStatement(Requests.INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psPayPeriodical = connection.prepareStatement(Requests.INSERT_PERIODICAL_PAYMENT);
            PreparedStatement psUserPeriodical = connection.prepareStatement(Requests.INSERT_USER_PERIODICAL);
            PreparedStatement psUpdateMoney = connection.prepareStatement(Requests.UPDATE_USER_MONEY_BUY)){
            ps.setInt(1,entity.getPrice());
            ps.setInt(2,entity.getIdUser());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int idPayment = rs.getInt(1);

            for (Periodical periodical: entity.getPeriodicals()) {
                psPayPeriodical.setInt(1, periodical.getId());
                psPayPeriodical.setInt(2, idPayment);
                psPayPeriodical.executeUpdate();

                psUserPeriodical.setInt(1,entity.getIdUser());
                psUserPeriodical.setInt(2,periodical.getId());
                psUserPeriodical.executeUpdate();
            }

            psUpdateMoney.setInt(1,entity.getPrice());
            psUpdateMoney.setInt(2,entity.getIdUser());
            psUpdateMoney.executeUpdate();
            connection.commit();
            return idPayment;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Method gives payment by id
     * @param id for search
     * @return payment
     */
    @Override
    public Payment findById(int id) {
        PaymentMapper paymentMapper = new PaymentMapper();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ID_PAYMENT)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return (paymentMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return list of payments
     */
    @Override
    public List<Payment> findAll() {
        PaymentMapper paymentMapper = new PaymentMapper();
        List<Payment> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_ALL_PAYMENT)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(paymentMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Method gives payments by user
     * @param idUser user's id
     * @return list of payments
     */
    @Override
    public List<Payment> findByUser(int idUser) {
        PaymentMapper paymentMapper = new PaymentMapper();
        List<Payment> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_USER_PAYMENT)){
            ps.setInt(1,idUser);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(paymentMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * Method gives payments by periodical
     * @param idPeriodical periodical's id
     * @return list of payments
     */
    @Override
    public List<Payment> findByPeriodical(int idPeriodical) {
        PaymentMapper paymentMapper = new PaymentMapper();
        List<Payment> resultList = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(Requests.SELECT_PERIODICAL_PAYMENT)){
            ps.setInt(1,idPeriodical);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resultList.add(paymentMapper.getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public void update(Payment entity, int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void close() {
        GeneralConnectionMethod.close(connection);
    }

    @Override
    public void setAutoCommit(boolean state) {
        GeneralConnectionMethod.setAutoCommit(connection,state);
    }

    @Override
    public void rollback() {
        GeneralConnectionMethod.rollback(connection);
    }
}
