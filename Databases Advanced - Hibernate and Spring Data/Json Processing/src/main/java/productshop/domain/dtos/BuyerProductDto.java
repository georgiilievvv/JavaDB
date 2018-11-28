package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class BuyerProductDto {

    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String firstName;
    @Expose
    private String lastName;

    public BuyerProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
