package gamestore.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GameDetailDto {

    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameDetailDto() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  "Title: " + title + "\n" +
                "Price: " + price + "\n" +
                "Description: " + this.PrettyPrintDescription() + "\n" +
                "Release date: " + releaseDate ;
    }

    public String PrettyPrintDescription(){
        StringBuilder collector = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        String[] splitDescription = this.getDescription().split(" ");

        for (String word : splitDescription) {
            sb.append(word).append(" ");

            if (sb.length() > 60){
                collector.append(sb).append(System.lineSeparator());
                sb = new StringBuilder();
            }
        }

        return collector.toString().trim();
    }
}
