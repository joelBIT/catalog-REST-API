package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.services.DeveloperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
@AllArgsConstructor
@Tag(name = "The 8-bit Catalog developer API")
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping
    @Operation(summary = "Get a list of all NES game developers.", description = "Returns a list of all names of the companies that have developed NES games.")
    public List<String> getDevelopers() {
        return developerService.getDevelopers();
    }

    @GetMapping("/{developer_name}/games")
    @Operation(summary = "Get metadata about all games that was developed by the supplied developer.", description = "Returns a list of metadata about all NES games that have been developed by the supplied developer.")
    public List<GameDTO> getGamesByDeveloper(@PathVariable("developer_name") String developerName) {
        return developerService.getGamesByDeveloper(developerName);
    }
}
