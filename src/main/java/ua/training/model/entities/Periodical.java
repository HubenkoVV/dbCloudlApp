package ua.training.model.entities;

import ua.training.model.entities.lazyload.LazyPeriodical;

import java.util.List;

public class Periodical {
    private int id;
    private String name;
    private String shortDescription;
    private int price;

    private List<User> users;
    private List<Payment> payments;
    private List<Article> articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Periodical that = (Periodical) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + shortDescription.hashCode();
        result = 31 * result + price;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        result = 31 * result + (articles != null ? articles.hashCode() : 0);
        return result;
    }

    public static final class PeriodicalBuilder{
        private int id;
        private String name;
        private String shortDescription;
        private int price;

        public PeriodicalBuilder buildId(int id) {
            this.id = id;
            return this;
        }

        public PeriodicalBuilder buildName(String name) {
            this.name = name;
            return this;
        }

        public PeriodicalBuilder buildShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public PeriodicalBuilder buildPrice(int price) {
            this.price = price;
            return this;
        }

        public Periodical build(){
            Periodical periodical = new Periodical();
            periodical.setId(id);
            periodical.setName(name);
            periodical.setPrice(price);
            periodical.setShortDescription(shortDescription);
            return periodical;
        }

        public LazyPeriodical buildLazy(){
            LazyPeriodical periodical = new LazyPeriodical();
            periodical.setId(id);
            periodical.setName(name);
            periodical.setPrice(price);
            periodical.setShortDescription(shortDescription);
            return periodical;
        }
    }
}
