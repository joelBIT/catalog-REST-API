package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.services.PublisherService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restdocs.test.autoconfigure.AutoConfigureRestDocs;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

@AutoConfigureRestDocs
@WebMvcTest(PublisherController.class)
@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherService publisherService;

    private final String PUBLISHERS_URL = "/api/v1/publishers";

    @Test
    public void shouldReturnAllPublishers() throws Exception {
        List<String> publishers = new ArrayList<>();
        publishers.add("Capcom");
        publishers.add("Konami");
        when(publisherService.getPublishers()).thenReturn(publishers);

        ResultActions response = mockMvc.perform(get(PUBLISHERS_URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers
                        .is(2)))
                .andDo(document("get-publishers",
                        responseFields(
                                fieldWithPath("[]").description("A list of distinct names of the publisher companies of the games in the catalog.")
                        )
                ));
    }

    @Test
    public void shouldReturnAllGamesByPublisher() throws Exception {
        GameDTO game = GameDTO.builder().id(3L).publisher("Capcom Co., Ltd.").build();
        List<GameDTO> games = new ArrayList<>();
        games.add(game);
        when(publisherService.getGamesByPublisher(game.getPublisher())).thenReturn(games);

        ResultActions response = mockMvc.perform(get(PUBLISHERS_URL + "/{publisher}/games", game.getPublisher())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers
                        .is(1)))
                .andDo(document("get-games-by-publisher",
                        pathParameters(
                                parameterWithName("publisher").description("Name of the games' publisher.")),
                        responseFields(
                                fieldWithPath("[]").description("The list of games for the publisher."),
                                fieldWithPath("[].id").description("ID of the game."),
                                fieldWithPath("[].title").description("The title of the game."),
                                fieldWithPath("[].cover").description("The game cover URL."),
                                fieldWithPath("[].category").description("The category of the game."),
                                fieldWithPath("[].description").description("Description about the game."),
                                fieldWithPath("[].players").description("The number of possible players in the game."),
                                fieldWithPath("[].developer").description("The developer of the game."),
                                fieldWithPath("[].publisher").description("The publisher of the game."),
                                fieldWithPath("[].releaseDate").description("The date the game was released on some continent.")
                        )
                ));
    }
}
