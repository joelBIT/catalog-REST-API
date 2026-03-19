package api._bit.catalog.services;

import api._bit.catalog.mappers.GameMapper;
import api._bit.catalog.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class DeveloperServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private DeveloperServiceImpl developerService;

    private final String COVERS_URL = "/covers-url/";
    private final GameMapper gameMapper = new GameMapper(COVERS_URL);

    @BeforeEach
    void setUp() {
        developerService = new DeveloperServiceImpl(gameRepository, gameMapper);
    }

    @Test
    public void shouldReturnAllDistinctDevelopers() {
        List<String> developers = new ArrayList<>();
        developers.add("Capcom");
        developers.add("Konami");
        when(gameRepository.findDistinctDevelopers()).thenReturn(developers);

        List<String> result = developerService.getDevelopers();

        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findDistinctDevelopers();
    }
}
