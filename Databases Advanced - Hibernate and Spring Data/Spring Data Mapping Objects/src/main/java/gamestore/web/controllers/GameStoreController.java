package gamestore.web.controllers;

import gamestore.domain.dtos.*;
import gamestore.service.GameService;
import gamestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private String loggedInUser;
    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String inputLine = scanner.nextLine();

            String[] inputParams = inputLine.split("\\|");
            switch (inputParams[0]) {
                case "RegisterUser":
                    UserRegisterDto userRegisterDto =
                            new UserRegisterDto(inputParams[1], inputParams[2], inputParams[3], inputParams[4]);

                    System.out.println(this.userService.registerUser(userRegisterDto));
                    break;
                case "LoginUser":
                    if (this.loggedInUser == null) {
                        UserLoginDto userLoginDto =
                                new UserLoginDto(inputParams[1], inputParams[2]);

                        String loginResult = this.userService.loginUser(userLoginDto);

                        if (loginResult.contains("Successfully logged in")) {
                            this.loggedInUser = userLoginDto.getEmail();
                        }

                        System.out.println(loginResult);
                    } else {
                        System.out.println("Session is taken.");
                    }

                    break;
                case "Logout":
                    if (this.loggedInUser == null) {
                        System.out.println("Cannot log out. No user was logged in.");
                    } else {
                        String logoutResult = this.userService.logoutUser(new UserLogoutDto(this.loggedInUser));
                        System.out.println(logoutResult);

                        this.loggedInUser = null;
                    }
                    break;
                case "AddGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {

                        BigDecimal price = new BigDecimal(inputParams[3]);
                        LocalDate releaseDate = LocalDate.parse(inputParams[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                        GameAddDto dto = new GameAddDto(inputParams[1], price, Double.parseDouble(inputParams[2]), inputParams[4], inputParams[5], inputParams[6], releaseDate);
                        System.out.println(this.gameService.addGame(dto));
                    } else {
                        System.out.println("Cannot add games if you are not admin.");
                    }
                    break;
                case "DeleteGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                        DeleteGameDto deleteGameDto = new DeleteGameDto(inputParams[1]);
                        System.out.println(this.gameService.DeleteGame(deleteGameDto));
                    } else {
                        System.out.println("Cannot delete games if you are not admin.");
                    }
                    break;
                case "EditGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                        if (this.gameService.gameByIdIsPresent(inputParams[1])){
                            EditedGameDto editedGameDto = this.gameService.findGameById(inputParams[1]);

                            for (int i = 2; i < inputParams.length; i++) {
                                String[] paramValue = inputParams[i].split("=");

                                String parameter = paramValue[0];
                                String value = paramValue.length == 1? null: paramValue[1];

                                switch (parameter){
                                    case "price":
                                        BigDecimal price = new BigDecimal(value);
                                        editedGameDto.setPrice(price);
                                        break;
                                    case "size":
                                        editedGameDto.setSize(Double.parseDouble(value));
                                        break;
                                    case "title":
                                        editedGameDto.setTitle(value);
                                        break;
                                    case "trailer":
                                        editedGameDto.setTrailer(value);
                                        break;
                                    case "description":
                                        editedGameDto.setDescription(value);
                                        break;
                                    case "imageThumbnail":
                                        editedGameDto.setImageThumbnail(value);
                                        break;
                                    case "releaseDate":
                                        LocalDate releaseDate = LocalDate
                                                .parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                                        editedGameDto.setReleaseDate(releaseDate);
                                        break;
                                }
                            }
                            System.out.println(this.gameService.EditGame(editedGameDto,inputParams[1]));
                        }else {
                            System.out.println("Game not found.");
                            break;
                        }

                    } else {
                        System.out.println("Cannot edit games if you are not admin.");
                    }
                    break;
                case "AllGames":
                    System.out.println(this.gameService.ViewAllGames());
                    break;
                case "DetailGame":
                    System.out.println(this.gameService.DetailsAboutGameByTitle(inputParams[1]));
                    break;
                case "OwnedGames":
                    System.out.println(this.userService.OwnedGames(loggedInUser));
                    break;
            }
        }
    }


}
