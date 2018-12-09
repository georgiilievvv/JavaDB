package alararestaurant.domain.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderImportRootXmlDto {

    @XmlElement(name = "order")
    private OrderImportXmlDto[] orders;

    public OrderImportRootXmlDto() {
    }

    public OrderImportXmlDto[] getOrders() {
        return orders;
    }

    public void setOrders(OrderImportXmlDto[] orders) {
        this.orders = orders;
    }
}
