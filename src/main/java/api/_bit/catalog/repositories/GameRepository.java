package api._bit.catalog.repositories;

import api._bit.catalog.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>  {

    @Query(
            value = "SELECT DISTINCT g.developer FROM GAMES g",
            nativeQuery = true
    )
    List<String> findDistinctDevelopers();

    @Query(
            value = "SELECT DISTINCT g.publisher FROM GAMES g",
            nativeQuery = true
    )
    List<String> findDistinctPublishers();

    List<Game> findByDeveloper(String developerName);
    List<Game> findByPublisher(String publisherName);
}