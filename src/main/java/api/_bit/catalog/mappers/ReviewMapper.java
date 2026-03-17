package api._bit.catalog.mappers;

import api._bit.catalog.domain.Review;
import api._bit.catalog.dto.ReviewDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO map(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .review(review.getReview())
                .heading(review.getHeading())
                .rating(review.getRating())
                .reviewerName(review.getReviewerName())
                .gameId(review.getGame().getId())
                .date(review.getDate())
                .build();
    }
}
