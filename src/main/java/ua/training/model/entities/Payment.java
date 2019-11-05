package ua.training.model.entities;

import ua.training.model.entities.lazyload.LazyPayment;

import java.util.List;

public class Payment {
    private int id;
    private int price;

    private int idUser;
    private List<Periodical> periodicals;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    protected void setPrice(int price) {
        this.price = price;
    }

    public int getIdUser() {
        return idUser;
    }

    protected void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public List<Periodical> getPeriodicals() {
        return periodicals;
    }

    public void setPeriodicals(List<Periodical> periodicals) {
        this.periodicals = periodicals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return id == payment.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        result = 31 * result + idUser;
        result = 31 * result + periodicals.hashCode();
        return result;
    }

    public static final class PaymentBuilder{
        private int id;
        private int price;
        private int idUser;
        private List<Periodical> periodicals;

        public PaymentBuilder buildId(int id) {
            this.id = id;
            return this;
        }

        public PaymentBuilder buildPrice(int price) {
            this.price = price;
            return this;
        }

        public PaymentBuilder buildIdUser(int idUser) {
            this.idUser = idUser;
            return this;
        }

        public PaymentBuilder buildPeriodicals(List<Periodical> periodicals) {
            this.periodicals = periodicals;
            return this;
        }

        public Payment build(){
            Payment payment = new Payment();
            payment.setId(id);
            payment.setIdUser(idUser);
            payment.setPrice(price);
            payment.setPeriodicals(periodicals);
            return payment;
        }

        public LazyPayment buildLazy(){
            LazyPayment payment = new LazyPayment();
            payment.setId(id);
            payment.setIdUser(idUser);
            payment.setPrice(price);
            payment.setPeriodicals(periodicals);
            return payment;
        }
    }
}
