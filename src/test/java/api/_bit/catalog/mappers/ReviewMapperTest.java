package api._bit.catalog.mappers;

import api._bit.catalog.domain.Game;
import api._bit.catalog.domain.Review;
import api._bit.catalog.dto.ReviewDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReviewMapperTest {
    private final ReviewMapper reviewMapper = new ReviewMapper();
    private final Review review = Review.builder()
            .id(3L)
            .heading("Mega Man is nice")
            .review("Very nice indeed")
            .reviewerName("Ken Kurtz")
            .game(Game.builder().id(560L).build())
            .rating(4)
            .date(LocalDate.now())
            .build();

    @Test
    public void shouldConvertReviewToReviewDto() {
        ReviewDTO result = reviewMapper.map(review);

        assertEquals(review.getId(), result.getId());
        assertEquals(review.getHeading(), result.getHeading());
        assertEquals(review.getReview(), result.getReview());
        assertEquals(review.getReviewerName(), result.getReviewerName());
        assertEquals(review.getRating(), result.getRating());
        assertEquals(review.getDate(), result.getDate());
    }
}
