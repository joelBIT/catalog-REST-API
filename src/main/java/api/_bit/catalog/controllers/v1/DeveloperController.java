package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.services.DeveloperService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/developers")
@AllArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping
    public List<String> getDevelopers() {
        return developerService.getDevelopers();
    }

    @GetMapping("/{developer_name}/games")
    public List<GameDTO> getGamesByDeveloper(@PathVariable("developer_name") String developerName) {
        return developerService.getGamesByDeveloper(developerName);
    }
}
