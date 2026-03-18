package api._bit.catalog.services;

import api._bit.catalog.domain.Review;
import api._bit.catalog.dto.ReviewDTO;
import api._bit.catalog.dto.ReviewListDTO;

public interface ReviewService {
    ReviewDTO getReviewById(long reviewId);
    ReviewListDTO getReviewsForGame(int gameId);
    ReviewListDTO getReviews(int pageNumber, int pageSize);
    void saveReview(Review review);
}
