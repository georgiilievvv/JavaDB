package gamestore.service;

import gamestore.domain.dtos.*;
import gamestore.domain.entities.Game;
import gamestore.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public String addGame(GameAddDto gameAddDto) {

        Validator validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();


        if (validator.validate(gameAddDto).size() > 0) {
            sb.append("+------------------------------------------------------------+")
                    .append(System.lineSeparator())
                    .append("Cannot add game because of the following validation errors")
                    .append(System.lineSeparator())
                    .append("+------------------------------------------------------------+")
                    .append(System.lineSeparator());
            for (ConstraintViolation<GameAddDto> violation : validator.validate(gameAddDto)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }

            sb.append("+------------------------------------------------------------+");
            return sb.toString().trim();
        }

        if (!this.gameRepository.existsByTitle(gameAddDto.getTitle())) {
            Game entity = this.modelMapper.map(gameAddDto, Game.class);
            this.gameRepository.saveAndFlush(entity);
            sb.append("Added ").append(entity.getTitle());
        } else {
            sb.append("Game already exists.");
        }

        return sb.toString();
    }

    @Override
    public String DeleteGame(DeleteGameDto deleteGameDto) {

        StringBuilder sb = new StringBuilder();


        if (this.gameRepository.findById(deleteGameDto.getId()).isPresent()) {
            Game entity = this.gameRepository.findById(deleteGameDto.getId()).orElse(null);
            sb.append("Deleted ").append(entity.getTitle());
            this.gameRepository.delete(entity);
        } else {
            sb.append("Game not found");
        }

        return sb.toString();
    }

    @Override
    public String EditGame(EditedGameDto editedGameDto, String id) {
        Validator validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();

        StringBuilder sb = new StringBuilder();

        if (validator.validate(editedGameDto).size() > 0) {
            sb.append("+------------------------------------------------------------+")
                    .append(System.lineSeparator())
                    .append("Cannot edit game because of the following validation errors")
                    .append(System.lineSeparator())
                    .append("+------------------------------------------------------------+")
                    .append(System.lineSeparator());

            for (ConstraintViolation<EditedGameDto> violation : validator.validate(editedGameDto)) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }

            sb.append("+------------------------------------------------------------+");
            return sb.toString().trim();
        }

        this.gameRepository.deleteById(id);
        Game entity = modelMapper.map(editedGameDto, Game.class);
        sb.append("Edited ").append(entity.getTitle());
        this.gameRepository.saveAndFlush(entity);
        return sb.toString();
    }

    @Override
    public EditedGameDto findGameById(String id) {
        Game entity = this.gameRepository.findById(id).orElse(null);

        EditedGameDto editedGameDto = modelMapper.map(entity, EditedGameDto.class);
        return editedGameDto;
    }

    @Override
    public boolean gameByIdIsPresent(String id) {
        return this.gameRepository.findById(id).isPresent();
    }

    @Override
    public String ViewAllGames() {

        StringBuilder sb = new StringBuilder();

        List<Game> games = this.gameRepository.findAll();

        for (Game game : games) {
            ViewGameDto dto = modelMapper.map(game, ViewGameDto.class);

            sb.append(dto).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String DetailsAboutGameByTitle(String title) {

        Game game = this.gameRepository.findByTitle(title).orElse(null);

        if (game == null) {
            return "Game not found";
        }

        GameDetailDto dto = modelMapper.map(game, GameDetailDto.class);
        return dto.toString();
    }
}
