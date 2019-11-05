package ua.training.model.service;

import org.junit.Test;
import ua.training.model.dao.PaymentDao;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entities.Payment;
import ua.training.model.entities.Periodical;
import ua.training.model.entities.User;
import ua.training.model.entities.UserRole;
import ua.training.model.service.exception.IncorrectDataException;
import ua.training.model.service.exception.NotEnoughMoneyException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PaymentServiceTest {

    private DaoFactory daoFactoryMock = mock(DaoFactory.class);
    private PaymentDao paymentDaoMock = mock(PaymentDao.class);

    @Test
    public void createPayment() throws Exception {
        User user = new User.UserBuilder()
                .buildLogin("test@test.test")
                .buildPhone("")
                .buildSurname("test")
                .buildName("test")
                .buildRole(UserRole.USER)
                .buildMoney(13)
                .buildPassword("testtest")
                .buildLazy();
        List<Periodical> periodicalList = new ArrayList<Periodical>(){{
            add(new Periodical.PeriodicalBuilder().buildId(1).buildLazy());
        }};

        Payment payment = new Payment.PaymentBuilder()
                .buildId(1)
                .buildIdUser(1)
                .buildPrice(13)
                .buildPeriodicals(periodicalList)
                .buildLazy();
        Payment failedPayment = new Payment.PaymentBuilder()
                .buildId(2)
                .buildIdUser(1)
                .buildPrice(-1)
                .buildPeriodicals(periodicalList)
                .buildLazy();
        Payment failedPayment2 = new Payment.PaymentBuilder()
                .buildId(2)
                .buildIdUser(1)
                .buildPrice(14)
                .buildPeriodicals(periodicalList)
                .buildLazy();

        when(daoFactoryMock.createPaymentDao()).thenReturn(paymentDaoMock);
        when(paymentDaoMock.create(payment)).thenReturn(1);
        when(paymentDaoMock.create(failedPayment)).thenReturn(0);
        when(paymentDaoMock.create(failedPayment2)).thenReturn(0);

        PaymentService paymentService = new PaymentService();
        paymentService.daoFactory = daoFactoryMock;
        Payment resultPayment = paymentService.createPayment(payment, user);
        try {
            paymentService.createPayment(failedPayment, user);
            paymentService.createPayment(failedPayment2, user);
        } catch (IncorrectDataException ignored){}

        verify(daoFactoryMock,times(2)).createPaymentDao();
        verify(paymentDaoMock).create(payment);
        verify(paymentDaoMock).create(failedPayment);
        verify(paymentDaoMock).create(failedPayment2);
        verify(paymentDaoMock, times(2)).close();

        assertThat(resultPayment, is(payment));
    }

    @Test
    public void checkUserBalance() throws Exception {
        PaymentService paymentService = spy(new PaymentService());

        paymentService.checkUserBalance(0,0);
        paymentService.checkUserBalance(0,1);
        try {
            paymentService.checkUserBalance(1, 0);
        } catch (NotEnoughMoneyException ignore){}

        verify(paymentService).checkUserBalance(0,0);
        verify(paymentService).checkUserBalance(0,1);
        verify(paymentService).checkUserBalance(1,0);
    }

}