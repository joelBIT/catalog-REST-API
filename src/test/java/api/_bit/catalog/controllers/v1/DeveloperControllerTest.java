package api._bit.catalog.controllers.v1;

import api._bit.catalog.dto.GameDTO;
import api._bit.catalog.services.DeveloperService;
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
@WebMvcTest(DeveloperController.class)
@ExtendWith(MockitoExtension.class)
public class DeveloperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeveloperService developerService;

    private final String DEVELOPERS_URL = "/api/v1/developers";

    @Test
    public void shouldReturnAllDevelopers() throws Exception {
        List<String> developers = new ArrayList<>();
        developers.add("Capcom");
        developers.add("Konami");
        when(developerService.getDevelopers()).thenReturn(developers);

        ResultActions response = mockMvc.perform(get(DEVELOPERS_URL)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers
                        .is(2)))
                .andDo(document("get-developers",
                        responseFields(
                                fieldWithPath("[]").description("A list of distinct names of the developer companies of the games in the catalog.")
                        )
                ));
    }

    @Test
    public void shouldReturnAllGamesByDeveloper() throws Exception {
        GameDTO game = GameDTO.builder().id(3L).developer("Capcom Co., Ltd.").build();
        List<GameDTO> games = new ArrayList<>();
        games.add(game);
        when(developerService.getGamesByDeveloper(game.getDeveloper())).thenReturn(games);

        ResultActions response = mockMvc.perform(get(DEVELOPERS_URL + "/{developer}/games", game.getDeveloper())
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers
                        .is(1)))
                .andDo(document("get-games-by-developer",
                        pathParameters(
                                parameterWithName("developer").description("Name of the games' developer.")),
                        responseFields(
                                fieldWithPath("[]").description("The list of games for the developer."),
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