package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.services.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@AllArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public List<String> getPublishers() {
        return publisherService.getPublishers();
    }

    @GetMapping("/{publisher_name}/games")
    public List<GameDTO> getGamesByPublisher(@PathVariable("publisher_name") String publisherName) {
        return publisherService.getGamesByPublisher(publisherName);
    }
}
