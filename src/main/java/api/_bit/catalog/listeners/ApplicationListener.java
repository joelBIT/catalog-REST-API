package api._bit.catalog.listeners;

import api._bit.catalog.domain.Game;
import api._bit.catalog.services.GameService;
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

    @Value("classpath:games_test.json")
    Resource resourceFile;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Game[] games = mapper.readValue(resourceFile.getContentAsString(Charset.defaultCharset()), Game[].class);
            for (Game game : games) {
                gameService.saveGame(game);
            }
        } catch (IOException e) {
            log.error("Could not load games and reviews");
        }
    }
}
