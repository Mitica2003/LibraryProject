package view.model;

import javafx.beans.property.*;

public class BookDTO {
    private StringProperty author;
    private StringProperty title;
    private IntegerProperty amount;
    private DoubleProperty price;

    public void setAuthor(String author) {
        authorProperty().set(author);
    }

    public String getAuthor() {
        return authorProperty().get();
    }

    public StringProperty authorProperty() {
        if (author == null) {
            author = new SimpleStringProperty(this, "author");
        }

        return author;
    }

    public void setTitle(String title) {
        titleProperty().set(title);
    }

    public String getTitle() {
        return titleProperty().get();
    }

    public StringProperty titleProperty() {
        if (title == null) {
            title = new SimpleStringProperty(this, "title");
        }

        return title;
    }

    public void setAmount(int amount) {
        amountProperty().set(amount);
    }

    public Integer getAmount() {
        return amountProperty().get();
    }

    public IntegerProperty amountProperty() {
        if (amount == null) {
            amount = new SimpleIntegerProperty(this, "amount");
        }

        return amount;
    }

    public void setPrice(double price) {
        priceProperty().set(price);
    }

    public Double getPrice() {
        return priceProperty().get();
    }

    public DoubleProperty priceProperty() {
        if (price == null) {
            price = new SimpleDoubleProperty(this, "price");
        }
        return price;
    }
}
