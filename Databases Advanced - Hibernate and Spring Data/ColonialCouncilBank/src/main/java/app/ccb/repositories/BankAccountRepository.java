package app.ccb.repositories;

import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    Optional<BankAccount> findByClient(Client client);

    Optional<BankAccount> findByAccountNumber(String accountNumber);

}
