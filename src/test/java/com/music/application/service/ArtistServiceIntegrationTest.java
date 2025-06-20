package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.Artist;

@SpringBootTest
public class ArtistServiceIntegrationTest {

    @Autowired
    private ArtistService artistService;

    @Test
    void testCreateAndFindArtist() {
        Artist artist = new Artist();
        artist.setName("Test Artist");
        Artist saved = artistService.save(artist);
        assertThat(saved.getArtistId()).isNotNull();
        Artist found = artistService.findById(saved.getArtistId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Artist");
        artistService.deleteById(saved.getArtistId());
    }

    @Test
    void testCreateArtist() {
        Artist artist = new Artist();
        artist.setName("CRUD Artist");
        Artist saved = artistService.save(artist);
        assertThat(saved.getArtistId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("CRUD Artist");
    }

    @Test
    void testReadArtist() {
        Artist artist = new Artist();
        artist.setName("Read Artist");
        Artist saved = artistService.save(artist);
        Artist found = artistService.findById(saved.getArtistId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Read Artist");
    }

    @Test
    void testUpdateArtist() {
        Artist artist = new Artist();
        artist.setName("To Update");
        Artist saved = artistService.save(artist);
        saved.setName("Updated Name");
        Artist updated = artistService.save(saved);
        assertThat(updated.getName()).isEqualTo("Updated Name");
    }

    @Test
    void testDeleteArtist() {
        Artist artist = new Artist();
        artist.setName("To Delete");
        Artist saved = artistService.save(artist);
        Integer id = saved.getArtistId();
        artistService.deleteById(id);
        assertThat(artistService.findById(id)).isEmpty();
    }
}
