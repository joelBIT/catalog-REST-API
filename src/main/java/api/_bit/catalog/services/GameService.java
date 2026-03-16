package api._bit.catalog.services;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;

public interface GameService {
    GameDTO getGameById(long gameId);
    GameListDTO getAllGames(int pageNr, int pageSize);
}
