package api._bit.catalog.listeners;

import api._bit.catalog.domain.Game;
import api._bit.catalog.domain.Review;
import api._bit.catalog.services.GameService;
import api._bit.catalog.services.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
@Slf4j
public class ApplicationListener {

    @Autowired
    private GameService gameService;

    @Autowired
    private ReviewService reviewService;

    @Value("classpath:games.json")
    Resource gamesFile;

    @Value("classpath:reviews.json")
    Resource reviewsFile;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info(event.toString());

        ObjectMapper mapper = new ObjectMapper();
        try {
            Game[] games = mapper.readValue(gamesFile.getContentAsString(Charset.defaultCharset()), Game[].class);
            for (Game game : games) {
                gameService.saveGame(game);
            }

            Review[] reviews = mapper.readValue(reviewsFile.getContentAsString(Charset.defaultCharset()), Review[].class);
            for (Review review : reviews) {
                System.out.println(review.getId());
                reviewService.saveReview(review);
            }
        } catch (IOException e) {
            log.error("Could not load games and reviews");
        }
    }
}
