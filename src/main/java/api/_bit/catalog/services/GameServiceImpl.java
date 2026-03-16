package api._bit.catalog.services;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;
import api._bit.catalog.exceptions.NotFoundException;
import api._bit.catalog.mappers.GameMapper;
import api._bit.catalog.repositories.GameRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

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
    public GameListDTO getAllGames(int pageNr, int pageSize) {
        log.info("Trying to fetch all games for page number {} and page size {}", pageNr, pageSize);

        Pageable pageable = PageRequest.of(pageNr, pageSize);
        Set<GameDTO> games = gameRepository.findAll(pageable)
                .stream()
                .map(gameMapper::map)
                .collect(Collectors.toSet());

        log.info("Successfully fetched all games for page number {} and page size {}", pageNr, pageSize);
        return GameListDTO.builder().games(games).pageSize(pageSize).pageNumber(pageNr).build();
    }
}
