package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class SuccessfullySoldProductsDto {

    @Expose
    private String firstName;
    @Expose
    private String LastName;
    @Expose
    private List<BuyerProductDto> soldProducts;

    public SuccessfullySoldProductsDto() {
        this.soldProducts = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public List<BuyerProductDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<BuyerProductDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
