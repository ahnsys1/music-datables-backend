package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.music.application.entity.Album;
import com.music.application.entity.Artist;
import com.music.application.entity.Genre;
import com.music.application.entity.MediaType;
import com.music.application.entity.Track;

@SpringBootTest
@Transactional
public class TrackServiceIntegrationTest {

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

    private Track track;

    @BeforeEach
    void setUp() {
        Artist artist = new Artist();
        artist.setName("Test Artist");
        artist = artistService.save(artist);

        Album album = new Album();
        album.setTitle("Test Album");
        album.setArtist(artist);
        album = albumService.save(album);

        Genre genre = new Genre();
        genre.setName("Test Genre");
        genre = genreService.save(genre);

        MediaType mediaType = new MediaType();
        mediaType.setName("Test MediaType");
        mediaType = mediaTypeService.save(mediaType);

        track = new Track();
        track.setName("Test Track");
        track.setAlbum(album);
        track.setGenre(genre);
        track.setMediaType(mediaType);
        track.setMilliseconds(1000);
        track.setUnitPrice(1.99);
        track = trackService.save(track);
    }

    @Test
    void testCreateAndFindTrack() {
        Track found = trackService.findById(track.getTrackId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Track");
    }

    @Test
    void testCreateTrack() {
        Track newTrack = new Track();
        newTrack.setName("Another Track");
        newTrack.setAlbum(track.getAlbum());
        newTrack.setGenre(track.getGenre());
        newTrack.setMediaType(track.getMediaType());
        newTrack.setMilliseconds(2000);
        newTrack.setUnitPrice(2.99);
        Track saved = trackService.save(newTrack);
        assertThat(saved.getTrackId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Another Track");
    }

    @Test
    void testReadTrack() {
        Track found = trackService.findById(track.getTrackId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Track");
    }

    @Test
    void testUpdateTrack() {
        track.setName("Updated Track");
        Track updated = trackService.save(track);
        assertThat(updated.getName()).isEqualTo("Updated Track");
    }

    @Test
    void testUpdateTrackCRUD() {
        track.setName("Updated Track CRUD");
        Track updated = trackService.save(track);
        assertThat(updated.getName()).isEqualTo("Updated Track CRUD");
    }

    @Test
    void testDeleteTrack() {
        Integer id = track.getTrackId();
        trackService.deleteById(id);
        assertThat(trackService.findById(id)).isEmpty();
    }

    @Test
    void testDeleteTrackCRUD() {
        Track newTrack = new Track();
        newTrack.setName("To Delete");
        newTrack.setAlbum(track.getAlbum());
        newTrack.setGenre(track.getGenre());
        newTrack.setMediaType(track.getMediaType());
        newTrack.setMilliseconds(3000);
        newTrack.setUnitPrice(3.99);
        Track saved = trackService.save(newTrack);
        Integer id = saved.getTrackId();
        trackService.deleteById(id);
        assertThat(trackService.findById(id)).isEmpty();
    }
}
