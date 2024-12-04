package model.builder;

import model.Book;
import model.Sale;

public class SaleBuilder {
    private Sale sale;

    public SaleBuilder() {
        sale = new Sale();
    }

    public SaleBuilder setId(Long id) {
        sale.setId(id);
        return this;
    }

    public SaleBuilder setBookTitle(String title) {
        sale.setBookTitle(title);
        return this;
    }

    public SaleBuilder setQuantity(int quantity) {
        sale.setQuantity(quantity);
        return this;
    }

    public SaleBuilder setTotalPrice(double price) {
        sale.setTotalPrice(price);
        return this;
    }

    public SaleBuilder setTimestamp(String timestamp) {
        sale.setTimestamp(timestamp);
        return this;
    }

    public Sale build() {
        return sale;
    }
}
