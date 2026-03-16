package api._bit.catalog.controllers.v1;

import api._bit.catalog.domain.GameCategory;
import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.dto.GameListDTO;
import api._bit.catalog.services.GameService;
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

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;

@AutoConfigureRestDocs
@WebMvcTest(GameController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    private final String GAMES_URL = "/api/v1/games";
    private GameDTO gameDTO;

    @BeforeEach
    void setUp() {
        gameDTO = GameDTO.builder()
                .id(3L)
                .category(GameCategory.ACTION)
                .title("Mega Man")
                .description("Some old game")
                .publisher("Capcom Co., Ltd.")
                .developer("Another")
                .players(1)
                .cover("mega_man.jpg")
                .releaseDate(LocalDateTime.now())
                .build();
    }

    @Test
    public void shouldRetrieveGamesForPageNumberAndPageSize() throws Exception {
        GameListDTO gameListDTO = GameListDTO.builder().pageNumber(1).pageSize(10)
                .games(Collections.singleton(gameDTO)).build();
        when(gameService.getAllGames(1, 10)).thenReturn(gameListDTO);

        ResultActions response = mockMvc.perform(get(GAMES_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNr","1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.games.size()", CoreMatchers
                        .is(gameListDTO.getGames().size())))
                .andDo(document("get-games",
                        queryParameters(
                                parameterWithName("pageNr").description("The number of the page to retrieve."),
                                parameterWithName("pageSize").description("The number of games retrieved in a page.")
                        ),
                        responseFields(
                                fieldWithPath("pageNumber").description("The page number."),
                                fieldWithPath("pageSize").description("The number of games for the page."),
                                fieldWithPath("games").description("The list of games for the page."),
                                fieldWithPath("games[].id").description("ID of the game."),
                                fieldWithPath("games[].title").description("The title of the game."),
                                fieldWithPath("games[].cover").description("The game cover URL."),
                                fieldWithPath("games[].category").description("The category of the game."),
                                fieldWithPath("games[].description").description("Description about the game."),
                                fieldWithPath("games[].players").description("The number of possible players in the game."),
                                fieldWithPath("games[].developer").description("The developer of the game."),
                                fieldWithPath("games[].publisher").description("The publisher of the game."),
                                fieldWithPath("games[].releaseDate").description("The date the game was released on some continent.")
                        )
                ));
    }

    @Test
    public void shouldReturnGameById() throws Exception {
        when(gameService.getGameById(2)).thenReturn(gameDTO);

        ResultActions response = mockMvc.perform(get(GAMES_URL + "/{id}", 2).contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", CoreMatchers.is(gameDTO.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", CoreMatchers.is(gameDTO.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category", CoreMatchers.is(gameDTO.getCategory().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cover", CoreMatchers.is(gameDTO.getCover())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.developer", CoreMatchers.is(gameDTO.getDeveloper())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", CoreMatchers.is(gameDTO.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.players", CoreMatchers.is(gameDTO.getPlayers())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate", CoreMatchers.is(gameDTO.getReleaseDate().toString())))
                .andDo(document("get-game-by-id", pathParameters(
                                parameterWithName("id").description("ID of the game to retrieve.")),
                        responseFields(
                                fieldWithPath("id").description("ID of the game."),
                                fieldWithPath("title").description("The title of the game."),
                                fieldWithPath("description").description("Description of the game."),
                                fieldWithPath("category").description("The category of the game."),
                                fieldWithPath("cover").description("The game cover URL."),
                                fieldWithPath("developer").description("The developer of the game."),
                                fieldWithPath("publisher").description("The publisher of the game."),
                                fieldWithPath("players").description("The number of possible players in the game."),
                                fieldWithPath("releaseDate").description("The date the game was released on some continent.")
                        )
                ));
    }
}
