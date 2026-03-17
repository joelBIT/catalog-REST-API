package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;
import api._bit.catalog.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/games")
@AllArgsConstructor
public class GameController {
    private final GameService gameService;

    @GetMapping
    public GameListDTO getGames(
            @RequestParam(value = "pageNr", defaultValue = "0", required = false) int pageNr,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize) {
        return gameService.getGames(pageNr, pageSize);
    }

    @GetMapping("/{game_id}")
    public GameDTO getGame(@PathVariable("game_id") Long id) {
        return gameService.getGameById(id);
    }
}
