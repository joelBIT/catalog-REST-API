package api._bit.catalog.mappers;

import api._bit.catalog.domain.Game;
import api._bit.catalog.domain.GameCategory;
import api._bit.catalog.dto.GameDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapperTest {
    private final String COVERS_URL = "/covers-url/";
    private final GameMapper gameMapper = new GameMapper(COVERS_URL);
    private final Game game = Game.builder()
            .id(3L)
            .category(GameCategory.ACTION)
            .title("Mega Man")
            .description("Some old game")
            .publisher("Capcom Co., Ltd.")
            .developer("Another")
            .players(1)
            .cover("mega_man.jpg")
            .releaseDate(LocalDate.now())
            .build();

    @Test
    public void shouldConvertGameToGameDto() {
        GameDTO result = gameMapper.map(game);

        assertEquals(game.getId(), result.getId());
        assertEquals(game.getCategory(), result.getCategory());
        assertEquals(game.getTitle(), result.getTitle());
        assertEquals(game.getDescription(), result.getDescription());
        assertEquals(game.getDeveloper(), result.getDeveloper());
        assertEquals(game.getPublisher(), result.getPublisher());
        assertEquals(game.getPlayers(), result.getPlayers());
        assertEquals(COVERS_URL + game.getCover(), result.getCover());
        assertEquals(game.getReleaseDate(), result.getReleaseDate());
    }
}
