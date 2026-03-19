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
public class DeveloperServiceImpl implements DeveloperService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public List<String> getDevelopers() {
        log.info("Fetching all distinct developer companies for all games");

        return gameRepository.findDistinctDevelopers();
    }

    @Override
    public List<GameDTO> getGamesByDeveloper(String developerName) {
        log.info("Fetching all games developed by {}", developerName);

        List<GameDTO> games = gameRepository.findByDeveloper(developerName)
                .stream()
                .map(gameMapper::map)
                .toList();

        log.info("Successfully fetched games developed by: {}", developerName);
        return games;
    }
}