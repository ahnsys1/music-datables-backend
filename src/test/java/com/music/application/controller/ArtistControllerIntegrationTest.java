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
import com.music.application.dto.ArtistDTO;
import com.music.application.entity.Artist;
import com.music.application.service.ArtistService;

@SpringBootTest
@AutoConfigureMockMvc
public class ArtistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ArtistService artistService;

    @Test
    void testCreateAndGetArtist() throws Exception {
        ArtistDTO artistDTO = new ArtistDTO();
        artistDTO.setName("IntegrationTest Artist");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(artistDTO);
        // Create
        String response = mockMvc.perform(post("/api/artists")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest Artist");

        // Find by name (using service for id)
        Artist artist = artistService.findAll().stream()
                .filter(a -> "IntegrationTest Artist".equals(a.getName()))
                .findFirst().orElse(null);
        assertThat(artist).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/artists/" + artist.getArtistId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest Artist")));

        // Delete
        mockMvc.perform(delete("/api/artists/" + artist.getArtistId()))
                .andExpect(status().isNoContent());
    }
}
