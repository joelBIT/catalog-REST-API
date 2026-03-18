package api._bit.catalog.services;

import api._bit.catalog.domain.Review;
import api._bit.catalog.dto.ReviewDTO;
import api._bit.catalog.dto.ReviewListDTO;
import api._bit.catalog.exceptions.NotFoundException;
import api._bit.catalog.mappers.ReviewMapper;
import api._bit.catalog.repositories.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public ReviewDTO getReviewById(long reviewId) {
        log.info("Fetching review with id: {}", reviewId);

        ReviewDTO review = reviewRepository.findById(reviewId)
                .map(reviewMapper::map)
                .orElseThrow(() -> {
                    log.error("Review not found with id: {}", reviewId);
                    return new NotFoundException("No review found for review id " + reviewId);
                });

        log.info("Successfully fetched review with id: {}", reviewId);
        return review;
    }

    @Override
    public ReviewListDTO getReviewsForGame(int gameId) {
        log.info("Trying to fetch all reviews for game with id {}", gameId);

        Set<ReviewDTO> reviews = reviewRepository.findByGameId(gameId)
                .stream()
                .map(reviewMapper::map)
                .collect(Collectors.toSet());

        log.info("Successfully fetched all reviews for game with id {}", gameId);
        return ReviewListDTO.builder().reviews(reviews).pageSize(reviews.size()).pageNumber(0).build();
    }

    @Override
    public ReviewListDTO getReviews(int pageNumber, int pageSize) {
        log.info("Trying to fetch all reviews for page number {} and page size {}", pageNumber, pageSize);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Set<ReviewDTO> reviews = reviewRepository.findAll(pageable)
                .stream()
                .map(reviewMapper::map)
                .collect(Collectors.toSet());

        log.info("Successfully fetched all reviews for page number {} and page size {}", pageNumber, pageSize);
        return ReviewListDTO.builder().reviews(reviews).pageSize(pageSize).pageNumber(pageNumber).build();
    }

    @Override
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }
}
