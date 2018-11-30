package app.ccb.services;

import app.ccb.domain.dtos.importXml.BankAccountImportDto;
import app.ccb.domain.dtos.importXml.BankAccountImportRootXmlDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final static String BANK_ACCOUNTS_XML_FILE_PATH = "C:\\Users\\joroi\\Desktop\\ZIPS\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";

    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, ClientRepository clientRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return this.fileUtil.readFile(BANK_ACCOUNTS_XML_FILE_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException {

        StringBuilder importResult = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(BankAccountImportRootXmlDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        BankAccountImportRootXmlDto acDto = (BankAccountImportRootXmlDto) unmarshaller
                .unmarshal(new File(BANK_ACCOUNTS_XML_FILE_PATH));


        for (BankAccountImportDto bankAccountImportDto : acDto.getBankAccounts()) {

            if (!validationUtil.isValid(bankAccountImportDto)){
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            List<Client> clients = this.clientRepository.findByFullName(bankAccountImportDto.getClient());

            if (clients.size() == 0){
                importResult.append("Error: Incorrect Data!").append(System.lineSeparator());

                continue;
            }

            BankAccount bankAccountEntity = modelMapper.map(bankAccountImportDto, BankAccount.class);
            bankAccountEntity.setClient(clients.get(0));
            this.bankAccountRepository.saveAndFlush(bankAccountEntity);

            importResult.append("Successfully imported Bank Account - ")
                    .append(bankAccountEntity.getAccountNumber())
            .append(System.lineSeparator());
        }

        return importResult.toString();
    }
}
