package api._bit.catalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReviewDTO {

    @Positive
    private long id;

    @NotBlank
    private String reviewerName;

    @Positive
    private long gameId;

    @PositiveOrZero
    private int rating;

    @NotBlank
    private String heading;

    @NotBlank
    private String review;

    private LocalDate date;
}
