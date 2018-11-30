package app.ccb.services;

import app.ccb.domain.dtos.ClientImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import com.google.gson.Gson;
import org.apache.catalina.LifecycleState;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final static String CLIENTS_JSON_FILE_PATH = "C:\\Users\\joroi\\Desktop\\ZIPS\\ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean clientsAreImported() {
        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENTS_JSON_FILE_PATH);
    }

    @Override
    public String importClients(String clients) {
        StringBuilder importResult = new StringBuilder();
        ClientImportDto[] clientImportDtos = this.gson.fromJson(clients, ClientImportDto[].class);

        for (ClientImportDto clientImportDto : clientImportDtos) {
            if (!this.validationUtil.isValid(clientImportDto)) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            Employee employeeEntity = this.employeeRepository
                    .findByFullName(clientImportDto.getAppointedEmployee())
                    .orElse(null);

            if (employeeEntity == null) {
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            Client clientEntity = this.modelMapper.map(clientImportDto, Client.class);
            clientEntity
                    .setFullName(String.format("%s %s", clientImportDto.getFirstName(), clientImportDto.getLastName()));
            clientEntity.getEmployees().add(employeeEntity);

            this.clientRepository.saveAndFlush(clientEntity);

            importResult.append(String.format("Successfully imported Client - %s", clientEntity.getFullName())).append(System.lineSeparator());

        }

        return importResult.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {

        StringBuilder importResut = new StringBuilder();
        Client client = this.clientRepository.familyGuy().get(0);

        importResut.append("Full Name: ").append(client.getFullName())
                .append(System.lineSeparator())
                .append("Age: ").append(client.getAge())
                .append(System.lineSeparator())
                .append("Bank Account: ").append(client.getBankAccount().getAccountNumber())
                .append(System.lineSeparator());

        for (Card card : client.getBankAccount().getCards()) {
            importResut.append("   Card Number: ").append(card.getCardNumber())
                    .append(System.lineSeparator())
                    .append("   Card Status: ").append(card.getCardStatus())
                    .append(System.lineSeparator());
        }

        return importResut.toString();
    }
}
