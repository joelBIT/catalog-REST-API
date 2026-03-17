package api._bit.catalog.mappers;

import api._bit.catalog.domain.Game;
import api._bit.catalog.dto.GameDTO;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {

    public GameDTO map(Game game) {
        return GameDTO.builder()
                .id(game.getId())
                .cover("https://tnkcekyijuynctkddkwy.supabase.co/storage/v1/object/public/covers/" + game.getCover())
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
