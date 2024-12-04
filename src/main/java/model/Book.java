package model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String author;
    private String title;
    private LocalDate publishedDate;
    private int amount;
    private double price;

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book: Id: " + id + " Title: " + title + " Author: " + author + " Published Date: " + publishedDate + " Amount: " + amount + " Price: " + price;
    }
}
