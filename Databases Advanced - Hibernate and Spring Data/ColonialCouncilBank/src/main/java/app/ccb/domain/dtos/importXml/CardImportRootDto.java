package app.ccb.domain.dtos.importXml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cards")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardImportRootDto {

    @XmlElement(name = "card")
    private CardImportDto[] cards;

    public CardImportRootDto() {
    }

    public CardImportDto[] getCards() {
        return cards;
    }

    public void setCards(CardImportDto[] cards) {
        this.cards = cards;
    }
}
