package api._bit.catalog.services;

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

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setUp() {
        publisherService = new PublisherServiceImpl(gameRepository);
    }

    @Test
    public void shouldReturnAllDistinctPublishers() {
        List<String> publishers = new ArrayList<>();
        publishers.add("Capcom");
        publishers.add("Konami");
        when(gameRepository.findDistinctPublishers()).thenReturn(publishers);

        List<String> result = publisherService.getPublishers();

        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findDistinctPublishers();
    }
}
