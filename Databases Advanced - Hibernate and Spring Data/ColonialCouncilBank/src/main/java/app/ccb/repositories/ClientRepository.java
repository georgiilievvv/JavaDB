package app.ccb.repositories;

import app.ccb.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByFullName(String name);

    @Query("" +
            "SELECT c " +
            "FROM app.ccb.domain.entities.Client AS c " +
            "ORDER BY size(c.bankAccount.cards) DESC ")
    List<Client> familyGuy();
}
