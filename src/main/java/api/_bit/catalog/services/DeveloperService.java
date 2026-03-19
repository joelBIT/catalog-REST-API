package api._bit.catalog.services;

import api._bit.catalog.dto.GameDTO;

import java.util.List;

public interface DeveloperService {
    List<String> getDevelopers();
    List<GameDTO> getGamesByDeveloper(String developerName);
}
