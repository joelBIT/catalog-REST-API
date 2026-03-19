package api._bit.catalog.services;

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

    @Override
    public List<String> getDevelopers() {
        return gameRepository.findDistinctDevelopers();
    }
}
