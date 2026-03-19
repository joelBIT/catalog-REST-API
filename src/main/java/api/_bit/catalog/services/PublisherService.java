package api._bit.catalog.services;

import api._bit.catalog.dto.GameDTO;

import java.util.List;

public interface PublisherService {
    List<String> getPublishers();
    List<GameDTO> getGamesByPublisher(String publisherName);
}
