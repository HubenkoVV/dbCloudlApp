package ua.training.model.entities;

import java.time.LocalDate;
import java.util.Date;

public class Article {
    private int id;
    private String name;
    private String text;
    private int idPeriodical;
    private LocalDate date;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIdPeriodical() {
        return idPeriodical;
    }

    void setIdPeriodical(int idPeriodical) {
        this.idPeriodical = idPeriodical;
    }

    public LocalDate getDate() {
        return date;
    }

    void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return id == article.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + idPeriodical;
        result = 31 * result + date.hashCode();
        return result;
    }

    public static final class ArticleBuilder{
        private int id;
        private String name;
        private String text;
        private int idPeriodical;
        private LocalDate date;

        public ArticleBuilder buildId(int id) {
            this.id = id;
            return this;
        }

        public ArticleBuilder buildName(String name) {
            this.name = name;
            return this;
        }

        public ArticleBuilder buildText(String text) {
            this.text = text;
            return this;
        }

        public ArticleBuilder buildIdPeriodical(int idPeriodical) {
            this.idPeriodical = idPeriodical;
            return this;
        }

        public ArticleBuilder buildDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public Article build(){
            Article article = new Article();
            article.setId(id);
            article.setName(name);
            article.setText(text);
            article.setIdPeriodical(idPeriodical);
            article.setDate(date);
            return article;
        }
    }
}
