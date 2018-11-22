package gamestore.repository;

import gamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {

    boolean existsByTitle(String title);

    Optional<Game> findById(String id);

    Optional<Game> findByTitle(String title);
}
