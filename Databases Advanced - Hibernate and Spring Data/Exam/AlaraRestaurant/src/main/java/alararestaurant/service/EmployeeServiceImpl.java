package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeImportDto;
import alararestaurant.domain.dtos.PositionImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEES_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder importResult = new StringBuilder();
        EmployeeImportDto[] employeeImportDtos =
                this.gson.fromJson(employees, EmployeeImportDto[].class);

        for (EmployeeImportDto employeeImportDto : employeeImportDtos) {

            Position position = this.positionRepository.findByName(employeeImportDto.getPosition()).orElse(null);

            if (position == null) {

                position = new Position();
                position.setName(employeeImportDto.getPosition());


                PositionImportDto positionDto = this.modelMapper.map(position, PositionImportDto.class);

                if (!this.validationUtil.isValid(positionDto)){
                    importResult.append("Invalid data format.")
                            .append(System.lineSeparator());

                    continue;
                }

                position = this.modelMapper.map(positionDto, Position.class);
            }


            if (!this.validationUtil.isValid(employeeImportDto) ){
                importResult.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            this.positionRepository.saveAndFlush(position);
            Employee employeeEntity = this.modelMapper.map(employeeImportDto, Employee.class);
            employeeEntity.setPosition(position);

            this.employeeRepository.saveAndFlush(employeeEntity);

            importResult.append(String.format("Record %s successfully imported.", employeeEntity.getName()))
            .append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }
}
