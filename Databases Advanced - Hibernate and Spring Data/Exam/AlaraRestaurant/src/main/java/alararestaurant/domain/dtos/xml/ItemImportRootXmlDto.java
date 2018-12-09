package alararestaurant.domain.dtos.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemImportRootXmlDto {

    @XmlElement(name = "item")
    private ItemImportXmlDto[] itemImportXmlDtos;

    public ItemImportRootXmlDto() {
    }

    public ItemImportXmlDto[] getItemImpotXmlDtos() {
        return itemImportXmlDtos;
    }

    public void setItemImportXmlDtos(ItemImportXmlDto[] itemImportXmlDtos) {
        this.itemImportXmlDtos = itemImportXmlDtos;
    }
}
