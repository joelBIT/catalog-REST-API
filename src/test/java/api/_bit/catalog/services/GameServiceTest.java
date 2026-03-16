package api._bit.catalog.services;

import api._bit.catalog.domain.Game;
import api._bit.catalog.domain.GameCategory;
import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;
import api._bit.catalog.mappers.GameMapper;
import api._bit.catalog.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameService;

    private final GameMapper gameMapper = new GameMapper();
    private final LocalDate releaseDate = LocalDate.now();

    private final GameDTO gameDTO = GameDTO.builder()
                .id(3L)
                .category(GameCategory.ACTION)
                .title("Turtles")
                .description("Some old game")
                .publisher("Capcom Co., Ltd.")
                .developer("Another")
                .players(1)
                .cover("turtles.jpg")
                .releaseDate(releaseDate)
                .build();

    private final Game game = Game.builder()
            .id(3L)
            .category(GameCategory.ACTION)
            .title("Turtles")
            .description("Some old game")
            .publisher("Capcom Co., Ltd.")
            .developer("Another")
            .players(1)
            .cover("turtles.jpg")
            .releaseDate(releaseDate)
            .build();

    @BeforeEach
    void setUp() {
        gameService = new GameServiceImpl(gameRepository, gameMapper);
    }

    @Test
    public void shouldGetGameDTOById() {
        when(gameRepository.findById(gameDTO.getId())).thenReturn(Optional.of(game));

        GameDTO result = gameService.getGameById(gameDTO.getId());

        assertEquals(gameDTO.getId(), result.getId());
        assertEquals(gameDTO.getDescription(), result.getDescription());
        assertEquals(gameDTO.getPlayers(), result.getPlayers());
        assertEquals(gameDTO.getTitle(), result.getTitle());
        verify(gameRepository, times(1)).findById(gameDTO.getId());
    }

    @Test
    public void shouldReturnAllGamesForPageAndSize() {
        Page<Game> page = mock(Page.class);
        when(gameRepository.findAll(any(Pageable.class))).thenReturn(page);

        GameListDTO result = gameService.getAllGames(1, 5);

        assertNotNull(result);
        verify(gameRepository, times(1)).findAll(any(Pageable.class));
    }
}
