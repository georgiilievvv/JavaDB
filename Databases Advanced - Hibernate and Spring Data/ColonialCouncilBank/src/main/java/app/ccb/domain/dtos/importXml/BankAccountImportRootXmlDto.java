package app.ccb.domain.dtos.importXml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountImportRootXmlDto {

    @XmlElement(name = "bank-account")
    private BankAccountImportDto[] bankAccounts;

    public BankAccountImportRootXmlDto() {
    }

    public BankAccountImportDto[] getBankAccounts() {
        return bankAccounts;
    }

    public void setBanckAccounts(BankAccountImportDto[] bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
