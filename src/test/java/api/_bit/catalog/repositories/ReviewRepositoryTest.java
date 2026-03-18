package api._bit.catalog.repositories;

import api._bit.catalog.domain.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private final LocalDateTime date = LocalDateTime.now();
    private Review review1;
    private Review review2;

    @BeforeEach
    void setUp() {
        review1 = Review.builder()
                .id(1L)
                .heading("Turtles old school")
                .review("Some old game")
                .reviewerName("Capcom Co., Ltd.")
                .rating(1)
                .gameId(295)
                .date(date)
                .build();

        review2 = Review.builder()
                .id(2L)
                .heading("Rygar rules")
                .review("Very nice")
                .reviewerName("Not Capcom Co., Ltd.")
                .rating(4)
                .gameId(345)
                .date(date)
                .build();
    }

    @Test
    public void shouldReturnAllReviews() {
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        List<Review> result = reviewRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnReviewById() {
        Review savedReview = reviewRepository.save(review1);
        Review result = reviewRepository.findById(savedReview.getId()).orElse(null);

        assertNotNull(result);
        assertEquals(savedReview.getId(), result.getId());
        assertEquals(savedReview.getHeading(), result.getHeading());
        assertEquals(savedReview.getReview(), result.getReview());
        assertEquals(savedReview.getReviewerName(), result.getReviewerName());
        assertEquals(savedReview.getRating(), result.getRating());
        assertEquals(savedReview.getGameId(), result.getGameId());
        assertEquals(savedReview.getDate(), result.getDate());
    }

    @Test
    public void shouldReturnReviewsByGameId() {
        Review savedReview = reviewRepository.save(review1);
        reviewRepository.save(review2);
        List<Review> result = reviewRepository.findByGameId(savedReview.getGameId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(savedReview.getId(), result.get(0).getId());
        assertEquals(savedReview.getGameId(), result.get(0).getGameId());
    }
}
