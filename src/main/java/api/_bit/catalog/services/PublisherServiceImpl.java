package api._bit.catalog.services;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.mappers.GameMapper;
import api._bit.catalog.repositories.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PublisherServiceImpl implements PublisherService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public List<String> getPublishers() {
        log.info("Fetching all distinct publisher companies for all games");

        return gameRepository.findDistinctPublishers();
    }

    @Override
    public List<GameDTO> getGamesByPublisher(String publisherName) {
        log.info("Fetching all games published by {}", publisherName);

        List<GameDTO> games = gameRepository.findByPublisher(publisherName)
                .stream()
                .map(gameMapper::map)
                .toList();

        log.info("Successfully fetched games published by: {}", publisherName);
        return games;
    }
}
