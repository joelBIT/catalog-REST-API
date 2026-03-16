package api._bit.catalog.services;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;
import api._bit.catalog.exceptions.NotFoundException;
import api._bit.catalog.mappers.GameMapper;
import api._bit.catalog.repositories.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    @Override
    public GameDTO getGameById(long gameId) {
        log.info("Fetching game with id: {}", gameId);

        GameDTO game = gameRepository.findById(gameId)
                .map(gameMapper::map)
                .orElseThrow(() -> {
                    log.error("Game not found with id: {}", gameId);
                    return new NotFoundException("No game found for game id " + gameId);
                });

        log.info("Successfully fetched game with id: {}", gameId);
        return game;
    }

    @Override
    public GameListDTO getAllGames() {
        return null;
    }
}
