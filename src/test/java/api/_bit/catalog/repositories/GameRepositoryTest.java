package api._bit.catalog.repositories;

import api._bit.catalog.domain.Game;
import api._bit.catalog.domain.GameCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    private final LocalDate releaseDate = LocalDate.now();

    private final Game turtles = Game.builder()
                .id(30L)
                .category(GameCategory.ACTION)
                .title("Turtles")
                .description("Some old game")
                .publisher("Capcom Co., Ltd.")
                .developer("Another")
                .players(1)
                .cover("turtles.jpg")
                .releaseDate(releaseDate)
                .build();

    private final Game rygar = Game.builder()
                .id(44L)
                .category(GameCategory.ADVENTURE)
                .title("Rygar")
                .description("Very nice")
                .publisher("Not Capcom Co., Ltd.")
                .developer("Another company")
                .players(1)
                .cover("rygar.jpg")
                .releaseDate(releaseDate)
                .build();

    @Test
    public void shouldReturnAllGames() {
        gameRepository.save(rygar);
        gameRepository.save(turtles);
        List<Game> result = gameRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnGameById() {
        Game savedGame = gameRepository.save(rygar);
        Game result = gameRepository.findById(savedGame.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(savedGame.getId(), result.getId());
        assertEquals(savedGame.getTitle(), result.getTitle());
        assertEquals(savedGame.getDescription(), result.getDescription());
        assertEquals(savedGame.getPlayers(), result.getPlayers());
        assertEquals(savedGame.getDeveloper(), result.getDeveloper());
        assertEquals(savedGame.getPublisher(), result.getPublisher());
        assertEquals(savedGame.getReleaseDate(), result.getReleaseDate());
        assertEquals(savedGame.getCover(), result.getCover());
        assertEquals(savedGame.getCategory(), result.getCategory());
    }

    @Test
    public void shouldReturnAllDevelopers() {
        gameRepository.save(turtles);
        gameRepository.save(rygar);
        Game megaMan = Game.builder()
                .id(304L)
                .category(GameCategory.ACTION)
                .title("Mega man")
                .developer("Another")
                .build();
        gameRepository.save(megaMan);

        List<String> result = gameRepository.findDistinctDevelopers();

        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnAllPublishers() {
        gameRepository.save(turtles);
        gameRepository.save(rygar);
        Game megaMan = Game.builder()
                .id(304L)
                .category(GameCategory.ACTION)
                .title("Mega man")
                .publisher("Another")
                .build();
        gameRepository.save(megaMan);

        List<String> result = gameRepository.findDistinctPublishers();

        assertEquals(3, result.size());
    }

    @Test
    public void shouldReturnGamesByPublisher() {
        gameRepository.save(turtles);
        gameRepository.save(rygar);

        List<Game> result = gameRepository.findByPublisher(turtles.getPublisher());

        assertEquals(1, result.size());
    }

    @Test
    public void shouldReturnGamesByDeveloper() {
        gameRepository.save(turtles);
        gameRepository.save(rygar);

        List<Game> result = gameRepository.findByDeveloper(turtles.getDeveloper());

        assertEquals(1, result.size());
    }
}
