package com.music.application.controller;

import java.util.List;

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
import com.music.application.dto.PlaylistDTO;
import com.music.application.entity.Album;
import com.music.application.entity.Artist;
import com.music.application.entity.Genre;
import com.music.application.entity.MediaType;
import com.music.application.entity.Playlist;
import com.music.application.entity.Track;
import com.music.application.service.AlbumService;
import com.music.application.service.ArtistService;
import com.music.application.service.GenreService;
import com.music.application.service.MediaTypeService;
import com.music.application.service.PlaylistService;
import com.music.application.service.TrackService;

@SpringBootTest
@AutoConfigureMockMvc
public class PlaylistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlaylistService playlistService;
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
    void testCreateAndGetPlaylist() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // Create dependencies
        Artist artist = new Artist();
        artist.setName("IntegrationTest Playlist Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("IntegrationTest Playlist Album");
        album.setArtist(artist);
        album = albumService.save(album);
        Genre genre = new Genre();
        genre.setName("IntegrationTest Playlist Genre");
        genre = genreService.save(genre);
        MediaType mediaType = new MediaType();
        mediaType.setName("IntegrationTest Playlist MediaType");
        mediaType = mediaTypeService.save(mediaType);
        Track track = new Track();
        track.setName("IntegrationTest Playlist Track");
        track.setAlbum(album);
        track.setGenre(genre);
        track.setMediaType(mediaType);
        track.setMilliseconds(1000);
        track.setUnitPrice(1.99);
        track = trackService.save(track);
        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setName("IntegrationTest Playlist");
        playlistDTO.setTrackIds(java.util.List.of(track.getTrackId()));
        String json = objectMapper.writeValueAsString(playlistDTO);
        // Create
        String response = mockMvc.perform(post("/api/playlists")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(response).contains("IntegrationTest Playlist");

        // Find by name (using service for id)
        List<Playlist> all = playlistService.findAll();
        Playlist playlist = all.stream()
                .filter(p -> "IntegrationTest Playlist".equals(p.getName()))
                .findFirst().orElse(null);
        assertThat(playlist).isNotNull();

        // Get by id
        mockMvc.perform(get("/api/playlists/" + playlist.getPlaylistId()))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("IntegrationTest Playlist")));

        // Delete
        mockMvc.perform(delete("/api/playlists/" + playlist.getPlaylistId()))
                .andExpect(status().isNoContent());
        trackService.deleteById(track.getTrackId());
        albumService.deleteById(album.getAlbumId());
        artistService.deleteById(artist.getArtistId());
        genreService.deleteById(genre.getGenreId());
        mediaTypeService.deleteById(mediaType.getMediaTypeId());
    }
}
