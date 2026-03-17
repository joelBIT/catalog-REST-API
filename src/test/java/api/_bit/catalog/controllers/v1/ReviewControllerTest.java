package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.ReviewDTO;
import api._bit.catalog.dto.ReviewListDTO;
import api._bit.catalog.services.ReviewService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;

@AutoConfigureRestDocs
@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    private final String REVIEWS_URL = "/api/v1/reviews";
    private ReviewDTO reviewDTO;
    private final LocalDate date = LocalDate.now();

    @BeforeEach
    void setUp() {
        reviewDTO = ReviewDTO.builder()
                .id(2L)
                .heading("Mega Man rules")
                .review("Some old game")
                .reviewerName("Ken Kurtz")
                .rating(4)
                .gameId(300L)
                .date(date)
                .build();
    }

    @Test
    public void shouldReturnAllReviewsByPageNumberAndPageSize() throws Exception {
        ReviewListDTO reviewListDTO = ReviewListDTO.builder().pageNumber(1).pageSize(10)
                .reviews(Collections.singleton(reviewDTO)).build();
        when(reviewService.getReviews(1, 10)).thenReturn(reviewListDTO);

        ResultActions response = mockMvc.perform(get(REVIEWS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNumber","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviews.size()", CoreMatchers
                        .is(reviewListDTO.getReviews().size())))
                .andDo(document("get-reviews",
                        queryParameters(
                                parameterWithName("pageNumber").description("The number of the page to retrieve."),
                                parameterWithName("pageSize").description("The number of reviews retrieved in a page.")
                        ),
                        responseFields(
                                fieldWithPath("pageNumber").description("The page number."),
                                fieldWithPath("pageSize").description("The number of reviews for the page."),
                                fieldWithPath("reviews").description("The list of reviews for the page."),
                                fieldWithPath("reviews[].id").description("ID of the review."),
                                fieldWithPath("reviews[].rating").description("The rating given by the reviewer."),
                                fieldWithPath("reviews[].heading").description("The title of the review."),
                                fieldWithPath("reviews[].review").description("The review written by the reviewer."),
                                fieldWithPath("reviews[].date").description("The date when the review was created."),
                                fieldWithPath("reviews[].gameId").description("The ID of the reviewed game."),
                                fieldWithPath("reviews[].reviewerName").description("The name of the reviewer.")
                        )
                ));
    }

    @Test
    public void shouldReturnReviewById() throws Exception {
        when(reviewService.getReviewById(2)).thenReturn(reviewDTO);

        ResultActions response = mockMvc.perform(get(REVIEWS_URL + "/{id}", 2).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.heading", CoreMatchers.is(reviewDTO.getHeading())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.review", CoreMatchers.is(reviewDTO.getReview())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewerName", CoreMatchers.is(reviewDTO.getReviewerName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rating", CoreMatchers.is(reviewDTO.getRating())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(reviewDTO.getDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameId", CoreMatchers.is((int) reviewDTO.getGameId())))
                .andDo(document("get-review-by-id", pathParameters(
                                parameterWithName("id").description("ID of the review to retrieve.")),
                        responseFields(
                                fieldWithPath("id").description("ID of the review."),
                                fieldWithPath("heading").description("The title of the review."),
                                fieldWithPath("date").description("The date when the review was created."),
                                fieldWithPath("rating").description("The rating given by the reviewer."),
                                fieldWithPath("review").description("The review text written by the reviewer."),
                                fieldWithPath("gameId").description("The ID of the reviewed game."),
                                fieldWithPath("reviewerName").description("The name of the reviewer.")
                        )
                ));
    }

    @Test
    public void shouldReturnAllReviewsByGame() throws Exception {
        ReviewListDTO reviewListDTO = ReviewListDTO.builder().pageNumber(0).pageSize(1)
                .reviews(Collections.singleton(reviewDTO)).build();
        when(reviewService.getReviewsForGame(100)).thenReturn(reviewListDTO);

        ResultActions response = mockMvc.perform(get(REVIEWS_URL + "/game/{id}", 100)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviews.size()", CoreMatchers
                        .is(reviewListDTO.getReviews().size())))
                .andDo(document("get-reviews-by-game-id",
                        pathParameters(
                                parameterWithName("id").description("Get reviews for the game with this ID.")
                        ),
                        responseFields(
                                fieldWithPath("pageNumber").description("The page number."),
                                fieldWithPath("pageSize").description("The number of reviews for the page."),
                                fieldWithPath("reviews").description("The list of reviews for the page."),
                                fieldWithPath("reviews[].id").description("ID of the review."),
                                fieldWithPath("reviews[].rating").description("The rating given by the reviewer."),
                                fieldWithPath("reviews[].heading").description("The title of the review."),
                                fieldWithPath("reviews[].review").description("The review written by the reviewer."),
                                fieldWithPath("reviews[].date").description("The date when the review was created."),
                                fieldWithPath("reviews[].gameId").description("The ID of the reviewed game."),
                                fieldWithPath("reviews[].reviewerName").description("The name of the reviewer.")
                        )
                ));
    }
}
