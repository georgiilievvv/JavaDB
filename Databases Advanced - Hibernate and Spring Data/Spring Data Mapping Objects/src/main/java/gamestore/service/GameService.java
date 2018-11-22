package gamestore.service;

import gamestore.domain.dtos.DeleteGameDto;
import gamestore.domain.dtos.EditedGameDto;
import gamestore.domain.dtos.GameAddDto;

public interface GameService {

    String addGame(GameAddDto gameAddDto);

    String DeleteGame(DeleteGameDto deleteGameDto);

    String EditGame(EditedGameDto deleteGameDto,String id);

    String ViewAllGames();

    String DetailsAboutGameByTitle(String title);

    boolean gameByIdIsPresent(String id);

    EditedGameDto findGameById(String inputParam);
}
