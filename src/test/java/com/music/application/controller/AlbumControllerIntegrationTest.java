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
import com.music.application.dto.AlbumDTO;
import com.music.application.entity.Album;
import com.music.application.entity.Artist;
import com.music.application.service.AlbumService;
import com.music.application.service.ArtistService;

@SpringBootTest
@AutoConfigureMockMvc
public class AlbumControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;

    @Test
    void testCreateAndGetAlbum() throws Exception {
        // Create artist first (FK)
        Artist artist = new Artist();
        artist.setName("IntegrationTest Album Artist");
        artist = artistService.save(artist);
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setTitle("IntegrationTest Album");
        albumDTO.setArtistId(artist.getArtistId());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(albumDTO);
        // Create
        String response = mockMvc.perform(post("/api/albums")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest Album");

        // Find by title (using service for id)
        Album album = albumService.findAll().stream()
                .filter(a -> "IntegrationTest Album".equals(a.getTitle()))
                .findFirst().orElse(null);
        assertThat(album).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/albums/" + album.getAlbumId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest Album")));

        // Delete
        mockMvc.perform(delete("/api/albums/" + album.getAlbumId()))
                .andExpect(status().isNoContent());
        artistService.deleteById(artist.getArtistId());
    }
}
