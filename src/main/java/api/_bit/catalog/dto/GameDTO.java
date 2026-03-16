package api._bit.catalog.dto;

import api._bit.catalog.domain.GameCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GameDTO {

    @Positive
    private long id;

    @NotBlank
    private String title;

    @Positive
    private int players;

    @NotBlank
    private String publisher;

    @NotBlank
    private String developer;

    @NotBlank
    private String cover;

    @NotBlank
    private String description;

    @Enumerated(EnumType.STRING)
    private GameCategory category;

    @NotNull
    private LocalDate releaseDate;
}
