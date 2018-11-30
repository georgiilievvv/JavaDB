package app.ccb.domain.dtos.importXml;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "card")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardImportDto {

    @XmlAttribute(name = "status")
    private String cardStatus;

    @XmlAttribute(name = "account-number")
    private String accountNumber;

    @XmlElement(name = "card-number")
    private String cardNumber;

    public CardImportDto() {
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
