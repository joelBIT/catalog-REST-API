package api._bit.catalog.repositories;

import api._bit.catalog.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long>  {
}