package gamestore.domain.dtos;

import gamestore.domain.entities.Game;

import java.util.List;

public class OwnedGamesDto {

    private List<Game> games;

    public OwnedGamesDto() {

    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        getGames().forEach(g -> sb
                        .append(g.getTitle())
                        .append(System.lineSeparator()));

        return sb.toString().trim();
    }
}
