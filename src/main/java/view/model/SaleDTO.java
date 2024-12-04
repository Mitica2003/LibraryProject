package view.model;

import javafx.beans.property.*;

public class SaleDTO {
    private StringProperty bookTitle;
    private IntegerProperty quantity;
    private DoubleProperty price;

    private LongProperty id;
    private StringProperty timestamp;
    public void setBookTitle(String bookTitle) {
        bookTitleProperty().set(bookTitle);
    }

    public String getBookTitle() {
        return bookTitleProperty().get();
    }

    public StringProperty bookTitleProperty() {
        if (bookTitle == null) {
            bookTitle = new SimpleStringProperty();
        }

        return bookTitle;
    }

    public void setQuantity(Integer quantity) {
        quantityProperty().set(quantity);
    }

    public Integer getQuantity() {
        return quantityProperty().get();
    }

    public IntegerProperty quantityProperty() {
        if (quantity == null) {
            quantity = new SimpleIntegerProperty();
        }

        return quantity;
    }

    public void setTotalPrice(Double price) {
        priceProperty().set(price);
    }

    public Double getTotalPrice() {
        return priceProperty().get();
    }

    public DoubleProperty priceProperty() {
        if (price == null) {
            price = new SimpleDoubleProperty();
        }

        return price;
    }

    public void setID(Long id) {
        idProperty().set(id);
    }

    public Long getID() {
        return idProperty().get();
    }

    public LongProperty idProperty() {
        if (id == null) {
            id = new SimpleLongProperty();
        }

        return id;
    }

    public void setTimestamp(String timestamp) {
        timestampProperty().set(timestamp);
    }

    public String getTimestamp() {
        return timestampProperty().get();
    }

    public StringProperty timestampProperty() {
        if (timestamp == null) {
            timestamp = new SimpleStringProperty();
        }

        return timestamp;
    }
}
