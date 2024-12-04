package view.model.builder;

import view.model.SaleDTO;

public class SaleDTOBuilder {
    private SaleDTO saleDTO;

    public SaleDTOBuilder() {
        saleDTO = new SaleDTO();
    }

    public SaleDTOBuilder setBookTitle(String title) {
        saleDTO.setBookTitle(title);
        return this;
    }

    public SaleDTOBuilder setQuantity(Integer quantity) {
        saleDTO.setQuantity(quantity);
        return this;
    }

    public SaleDTOBuilder setTotalPrice(Double price) {
        saleDTO.setTotalPrice(price);
        return this;
    }

    public SaleDTOBuilder setID(Long id) {
        saleDTO.setID(id);
        return this;
    }

    public SaleDTOBuilder setTimestamp(String timestamp) {
        saleDTO.setTimestamp(timestamp);
        return this;
    }

    public SaleDTO build() {
        return saleDTO;
    }
}
