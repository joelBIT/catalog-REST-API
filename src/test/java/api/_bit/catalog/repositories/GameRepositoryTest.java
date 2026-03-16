package api._bit.catalog.repositories;

import api._bit.catalog.domain.Game;
import api._bit.catalog.domain.GameCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    private Game turtles = Game.builder()
                .category(GameCategory.ACTION)
                .title("Turtles")
                .description("Some old game")
                .publisher("Capcom Co., Ltd.")
                .developer("Another")
                .players(1)
                .cover("turtles.jpg")
                .releaseDate(LocalDateTime.now())
                .build();

    private Game rygar = Game.builder()
                .category(GameCategory.ADVENTURE)
                .title("Rygar")
                .description("Very nice")
                .publisher("Not Capcom Co., Ltd.")
                .developer("Another company")
                .players(1)
                .cover("rygar.jpg")
                .releaseDate(LocalDateTime.now())
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
        Game result = gameRepository.findById(savedGame.getId()).orElseThrow();

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
}
