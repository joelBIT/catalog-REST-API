package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;
import api._bit.catalog.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor
@Tag(name = "The 8-bit Catalog games API")
public class GameController {
    private final GameService gameService;

    @GetMapping
    @Operation(summary = "Get metadata about games in the 8-bit Catalog.", description = "Returns metadata about a number of games depending on supplied pageNumber and pageSize")
    public GameListDTO getGames(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize) {
        return gameService.getGames(pageNumber, pageSize);
    }

    @GetMapping("/{game_id}")
    @Operation(summary = "Get metadata about a NES game by ID", description = "Returns metadata about the game with a matching ID")
    public GameDTO getGame(@PathVariable("game_id") Long id) {
        return gameService.getGameById(id);
    }
}
