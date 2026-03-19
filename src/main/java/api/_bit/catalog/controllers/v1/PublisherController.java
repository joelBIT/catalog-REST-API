package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.services.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
@AllArgsConstructor
@Tag(name = "The 8-bit Catalog publisher API")
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    @Operation(summary = "Get a list of all NES game publishers.", description = "Returns a list of all names of the companies that have published NES games.")
    public List<String> getPublishers() {
        return publisherService.getPublishers();
    }

    @GetMapping("/{publisher_name}/games")
    @Operation(summary = "Get metadata about all games that was published by the supplied publisher.", description = "Returns a list of metadata about all NES games that have been published by the supplied publisher.")
    public List<GameDTO> getGamesByPublisher(@PathVariable("publisher_name") String publisherName) {
        return publisherService.getGamesByPublisher(publisherName);
    }
}
