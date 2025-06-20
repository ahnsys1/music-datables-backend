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
import com.music.application.dto.TrackDTO;
import com.music.application.entity.Album;
import com.music.application.entity.Artist;
import com.music.application.entity.Genre;
import com.music.application.entity.Track;
import com.music.application.service.AlbumService;
import com.music.application.service.ArtistService;
import com.music.application.service.GenreService;
import com.music.application.service.MediaTypeService;
import com.music.application.service.TrackService;

@SpringBootTest
@AutoConfigureMockMvc
public class TrackControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TrackService trackService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private MediaTypeService mediaTypeService;

    @Test
    void testCreateAndGetTrack() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // Create dependencies
        Artist artist = new Artist();
        artist.setName("IntegrationTest Track Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("IntegrationTest Track Album");
        album.setArtist(artist);
        album = albumService.save(album);
        Genre genre = new Genre();
        genre.setName("IntegrationTest Track Genre");
        genre = genreService.save(genre);
        com.music.application.entity.MediaType mediaType = new com.music.application.entity.MediaType();
        mediaType.setName("IntegrationTest Track MediaType");
        mediaType = mediaTypeService.save(mediaType);
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setName("IntegrationTest Track");
        trackDTO.setAlbumId(album.getAlbumId());
        trackDTO.setGenreId(genre.getGenreId());
        trackDTO.setMediaTypeId(mediaType.getMediaTypeId());
        trackDTO.setMilliseconds(1000);
        trackDTO.setUnitPrice(1.99);
        String json = objectMapper.writeValueAsString(trackDTO);
        // Create
        String response = mockMvc.perform(post("/api/tracks")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(response).contains("IntegrationTest Track");

        // Find by name (using service for id)editor.parameterHints.enabled
        Track track = trackService.findAll().stream()
                .filter(t -> "IntegrationTest Track".equals(t.getName()))
                .findFirst().orElse(null);
        assertThat(track).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/tracks/" + track.getTrackId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest Track")));

        // Delete
        mockMvc.perform(delete("/api/tracks/" + track.getTrackId()))
                .andExpect(status().isNoContent());
        albumService.deleteById(album.getAlbumId());
        artistService.deleteById(artist.getArtistId());
        genreService.deleteById(genre.getGenreId());
        mediaTypeService.deleteById(mediaType.getMediaTypeId());
    }
}
