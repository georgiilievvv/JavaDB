package alararestaurant.service;

import alararestaurant.domain.dtos.OrderItemImportDto;
import alararestaurant.domain.dtos.xml.ItemImportXmlDto;
import alararestaurant.domain.dtos.xml.OrderImportRootXmlDto;
import alararestaurant.domain.dtos.xml.OrderImportXmlDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final static String ORDERS_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/orders.xml";

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public OrderServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository, EmployeeRepository employeeRepository, ItemRepository itemRepository, FileUtil fileUtil, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return this.fileUtil.readFile(ORDERS_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        StringBuilder importResult = new StringBuilder();
        JAXBContext context = JAXBContext.newInstance(OrderImportRootXmlDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        OrderImportRootXmlDto OrdersDto = (OrderImportRootXmlDto) unmarshaller
                .unmarshal(new File(ORDERS_FILE_PATH));

        for (OrderImportXmlDto orderDto : OrdersDto.getOrders()) {

            Employee employee = this.employeeRepository.findByName(orderDto.getEmployee()).orElse(null);

            if (employee == null || !this.validationUtil.isValid(orderDto)) {
                importResult.append("Invalid data format.")
                        .append(System.lineSeparator());

                continue;
            }

            List<OrderItemImportDto> orderItemsDto = new ArrayList<>();
            for (ItemImportXmlDto itemImpotXmlDto : orderDto.getItemImportRootXmlDto().getItemImpotXmlDtos()) {

                Item item = this.itemRepository.findByName(itemImpotXmlDto.getName()).orElse(null);

                if (item == null){
                    continue;
                }

                OrderItemImportDto orderItemDto = new OrderItemImportDto();
                orderItemDto.setItem(item);
                orderItemDto.setQuantity(itemImpotXmlDto.getQuantity());

                if (!this.validationUtil.isValid(orderItemDto) || orderItemDto.getQuantity() == 0){
                    importResult.append("Invalid data format.")
                            .append(System.lineSeparator());

                    continue;
                }

                orderItemsDto.add(orderItemDto);
            }

            LocalDateTime date = LocalDateTime.parse(orderDto.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            Order orderEntity = this.modelMapper.map(orderDto, Order.class);
            orderEntity.setDateTime(date);
            orderEntity.setEmployee(employee);
            orderEntity = this.orderRepository.saveAndFlush(orderEntity);

            importResult.append(String.format("Order for %s on %s added", orderEntity.getCustomer(), orderEntity.getDateTime()))
                    .append(System.lineSeparator());

            for (OrderItemImportDto orderItemDto : orderItemsDto) {

                OrderItem orderItem = this.modelMapper.map(orderItemDto, OrderItem.class);

                OrderItem orderItemEntity = new OrderItem();
                orderItemEntity.setItem(orderItem.getItem());
                orderItemEntity.setOrder(orderEntity);
                orderItemEntity.setQuantity(orderItem.getQuantity());

                this.orderItemRepository.saveAndFlush(orderItemEntity);
            }
        }

        return null;
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {

        StringBuilder exportData = new StringBuilder();

        List<Order> orders = this.orderRepository.ordersfinishedbytheBurgerFlippers();

        String curEmployee = "";

        for (Order order : orders) {

            if (!curEmployee.equals(order.getEmployee().getName())){

                exportData.append("Name: ").append(order.getEmployee().getName()).append(System.lineSeparator());
                curEmployee = order.getEmployee().getName();
                exportData.append("Orders:").append(System.lineSeparator());
            }

            exportData.append("\tCustomer: ").append(order.getCustomer()).append(System.lineSeparator());
            exportData.append("\tItems:").append(System.lineSeparator());

            for (OrderItem orderItem : order.getOrderItems()) {

                exportData.append("\t\tName: ").append(orderItem.getItem().getName()).append(System.lineSeparator());
                exportData.append("\t\tPrice: ").append(orderItem.getItem().getPrice()).append(System.lineSeparator());
                exportData.append("\t\tQuantity: ").append(orderItem.getQuantity()).append(System.lineSeparator()).append(System.lineSeparator());
            }
        }
        return exportData.toString().trim();
    }
}
