package alararestaurant.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
public class Order extends BaseEntity {

    private String customer;
    private LocalDateTime dateTime;
    private OrderType orderType;
    private Employee employee;
    private List<OrderItem> orderItems;

    public Order() {
    }

    @Column(name = "customer",nullable = false)
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "date_time",nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Column(columnDefinition = "enum('ForHere','ToGo') default 'ForHere'")
    @Enumerated(value = EnumType.STRING)
    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id",
            nullable = false
    )
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToMany(targetEntity = OrderItem.class, mappedBy = "order")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
