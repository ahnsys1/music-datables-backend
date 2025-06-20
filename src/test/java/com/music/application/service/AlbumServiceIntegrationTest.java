package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.Album;
import com.music.application.entity.Artist;

@SpringBootTest
public class AlbumServiceIntegrationTest {

    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;

    @Test
    void testCreateAndFindAlbum() {
        Artist artist = new Artist();
        artist.setName("AlbumTest Artist");
        artist = artistService.save(artist);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setArtist(artist);
        Album saved = albumService.save(album);
        assertThat(saved.getAlbumId()).isNotNull();
        Album found = albumService.findById(saved.getAlbumId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Test Album");
        assertThat(found.getArtist().getArtistId()).isEqualTo(artist.getArtistId());
        albumService.deleteById(saved.getAlbumId());
        artistService.deleteById(artist.getArtistId());
    }

    @Test
    void testCreateAlbum() {
        Artist artist = new Artist();
        artist.setName("CRUD Album Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("CRUD Album");
        album.setArtist(artist);
        Album saved = albumService.save(album);
        assertThat(saved.getAlbumId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("CRUD Album");
    }

    @Test
    void testReadAlbum() {
        Artist artist = new Artist();
        artist.setName("Read Album Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("Read Album");
        album.setArtist(artist);
        Album saved = albumService.save(album);
        Album found = albumService.findById(saved.getAlbumId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Read Album");
    }

    @Test
    void testUpdateAlbum() {
        Artist artist = new Artist();
        artist.setName("Update Album Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("To Update");
        album.setArtist(artist);
        Album saved = albumService.save(album);
        saved.setTitle("Updated Album");
        Album updated = albumService.save(saved);
        assertThat(updated.getTitle()).isEqualTo("Updated Album");
    }

    @Test
    void testDeleteAlbum() {
        Artist artist = new Artist();
        artist.setName("Delete Album Artist");
        artist = artistService.save(artist);
        Album album = new Album();
        album.setTitle("To Delete");
        album.setArtist(artist);
        Album saved = albumService.save(album);
        Integer id = saved.getAlbumId();
        albumService.deleteById(id);
        assertThat(albumService.findById(id)).isEmpty();
    }
}
