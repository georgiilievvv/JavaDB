package gamestore.domain.dtos;

public class DeleteGameDto {

    private String id;

    public DeleteGameDto() {
    }

    public DeleteGameDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
