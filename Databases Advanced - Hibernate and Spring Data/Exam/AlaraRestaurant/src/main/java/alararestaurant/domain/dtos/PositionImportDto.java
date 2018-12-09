package alararestaurant.domain.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class PositionImportDto {

    private String name;

    public PositionImportDto() {
    }

    @NotNull
    @Length(min = 3, max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
