package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.ReviewDTO;
import api._bit.catalog.dto.ReviewListDTO;
import api._bit.catalog.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ReviewListDTO getReviews(
            @RequestParam(value = "pageNr", defaultValue = "0", required = false) int pageNr,
            @RequestParam(value = "pageSize", defaultValue = "15", required = false) int pageSize) {
        return reviewService.getReviews(pageNr, pageSize);
    }

    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable("id") Long id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/game/{id}")
    public ReviewListDTO getReviewsByGame(@PathVariable("id") Long id) {
        return reviewService.getReviewsForGame(id);
    }
}
