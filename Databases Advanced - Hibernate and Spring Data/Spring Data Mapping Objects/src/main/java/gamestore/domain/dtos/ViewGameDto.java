package gamestore.domain.dtos;

import java.math.BigDecimal;

public class ViewGameDto {

    private String title;
    private BigDecimal price;

    public ViewGameDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.getTitle() + " " + this.getPrice();
    }
}