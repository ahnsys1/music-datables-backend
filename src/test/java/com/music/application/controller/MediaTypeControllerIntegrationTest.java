package com.music.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.application.dto.MediaTypeDTO;
import com.music.application.service.MediaTypeService;

@SpringBootTest
@AutoConfigureMockMvc
public class MediaTypeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MediaTypeService mediaTypeService;

    @Test
    void testCreateAndGetMediaType() throws Exception {
        MediaTypeDTO mediaTypeDTO = new MediaTypeDTO();
        mediaTypeDTO.setName("IntegrationTest MediaType");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(mediaTypeDTO);
        // Create
        String response = mockMvc.perform(post("/api/mediatypes")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest MediaType");

        // Find by name (using service for id)
        com.music.application.entity.MediaType mediaType = mediaTypeService.findAll().stream()
                .filter(m -> "IntegrationTest MediaType".equals(m.getName()))
                .findFirst().orElse(null);
        assertThat(mediaType).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/mediatypes/" + mediaType.getMediaTypeId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest MediaType")));

        // Delete
        mockMvc.perform(delete("/api/mediatypes/" + mediaType.getMediaTypeId()))
                .andExpect(status().isNoContent());
    }
}
