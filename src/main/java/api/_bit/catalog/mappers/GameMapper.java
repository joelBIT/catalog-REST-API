package api._bit.catalog.mappers;

import api._bit.catalog.domain.Game;
import api._bit.catalog.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    private final String coversUrl;

    @Autowired
    public GameMapper(@Value("${covers.url}") String coversUrl) {
        this.coversUrl = coversUrl;
    }

    public GameDTO map(Game game) {
        return GameDTO.builder()
                .id(game.getId())
                .cover(coversUrl + game.getCover())
                .title(game.getTitle())
                .category(game.getCategory())
                .developer(game.getDeveloper())
                .publisher(game.getPublisher())
                .players(game.getPlayers())
                .description(game.getDescription())
                .releaseDate(game.getReleaseDate())
                .build();
    }
}
