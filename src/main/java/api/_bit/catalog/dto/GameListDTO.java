package api._bit.catalog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class GameListDTO {

    @PositiveOrZero
    private int pageNumber;

    @Positive
    private int pageSize;

    @NotNull
    private Set<GameDTO> games;
}