package api._bit.catalog.controllers.v1;

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

@AutoConfigureRestDocs
@WebMvcTest(DeveloperController.class)
@ExtendWith(MockitoExtension.class)
public class DeveloperControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeveloperService developerService;

    @Test
    public void shouldReturnAllDevelopers() throws Exception {
        List<String> developers = new ArrayList<>();
        developers.add("Capcom");
        developers.add("Konami");
        when(developerService.getDevelopers()).thenReturn(developers);

        String DEVELOPERS_URL = "/api/v1/developers";
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
}
