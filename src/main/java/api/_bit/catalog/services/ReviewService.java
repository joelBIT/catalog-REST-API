package api._bit.catalog.services;

import api._bit.catalog.dto.ReviewDTO;
import api._bit.catalog.dto.ReviewListDTO;

public interface ReviewService {
    ReviewDTO getReviewById(long reviewId);
    ReviewListDTO getReviewsForGame(long gameId);
    ReviewListDTO getReviews(int pageNumber, int pageSize);
}
