package api._bit.catalog.services;

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

    @Override
    public List<String> getPublishers() {
        log.info("Fetching all distinct publisher companies for all games");

        return gameRepository.findDistinctPublishers();
    }
}
