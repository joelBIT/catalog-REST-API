package api._bit.catalog.controllers.v1;

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

@AutoConfigureRestDocs
@WebMvcTest(PublisherController.class)
@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublisherService publisherService;

    @Test
    public void shouldReturnAllPublishers() throws Exception {
        List<String> publishers = new ArrayList<>();
        publishers.add("Capcom");
        publishers.add("Konami");
        when(publisherService.getPublishers()).thenReturn(publishers);

        String PUBLISHERS_URL = "/api/v1/publishers";
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
}
