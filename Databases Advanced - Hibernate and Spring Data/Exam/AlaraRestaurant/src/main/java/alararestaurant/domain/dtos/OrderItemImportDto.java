package alararestaurant.domain.dtos;

import alararestaurant.domain.entities.Item;

import javax.validation.Constraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OrderItemImportDto {

    private Item item;
    private Integer quantity;

    public OrderItemImportDto() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @NotNull
    @Positive
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
