package api._bit.catalog.services;

import api._bit.catalog.domain.Review;
import api._bit.catalog.dto.ReviewDTO;
import api._bit.catalog.dto.ReviewListDTO;
import api._bit.catalog.mappers.ReviewMapper;
import api._bit.catalog.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private final ReviewMapper reviewMapper = new ReviewMapper();
    private final LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    private final Review review = Review.builder()
            .id(305L)
            .heading("Turtles old school")
            .review("Some old game")
            .reviewerName("Capcom Co., Ltd.")
            .gameId(345)
            .rating(1)
            .date(date)
            .build();

    @BeforeEach
    void setUp() {
        reviewService = new ReviewServiceImpl(reviewRepository, reviewMapper);
    }

    @Test
    public void shouldGetReviewDTOById() {
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        ReviewDTO result = reviewService.getReviewById(review.getId());

        assertEquals(review.getId(), result.getId());
        assertEquals(review.getReview(), result.getReview());
        assertEquals(review.getRating(), result.getRating());
        assertEquals(review.getHeading(), result.getHeading());
        verify(reviewRepository, times(1)).findById(review.getId());
    }

    @Test
    public void shouldReturnAllReviewsForPageAndSize() {
        Page<Review> page = mock(Page.class);
        when(reviewRepository.findAll(any(Pageable.class))).thenReturn(page);

        ReviewListDTO result = reviewService.getReviews(1, 5);

        assertNotNull(result);
        verify(reviewRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void shouldReturnAllReviewsForGame() {
        when(reviewRepository.findByGameId(eq(review.getGameId()))).thenReturn(Collections.singletonList(review));

        ReviewListDTO result = reviewService.getReviewsForGame(review.getGameId());

        assertNotNull(result);
        verify(reviewRepository, times(1)).findByGameId(eq(review.getGameId()));
    }
}
