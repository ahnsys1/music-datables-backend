package com.music.application.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.music.application.dto.GenreDTO;
import com.music.application.entity.Genre;
import com.music.application.service.GenreService;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreService genreService;

    @Test
    void testCreateAndGetGenre() throws Exception {
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setName("IntegrationTest Genre");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(genreDTO);

        // Create
        String response = mockMvc.perform(post("/api/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest Genre");

        // Find by name (using service for id)
        Genre genre = genreService.findAll().stream()
                .filter(g -> "IntegrationTest Genre".equals(g.getName()))
                .findFirst().orElse(null);
        assertThat(genre).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/genres/" + genre.getGenreId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest Genre")));

        // Delete
        mockMvc.perform(delete("/api/genres/" + genre.getGenreId()))
                .andExpect(status().isNoContent());
    }
}
