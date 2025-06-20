package com.music.application.service;

import java.util.List;

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
import com.music.application.entity.Playlist;
import com.music.application.entity.Track;

@SpringBootTest
@Transactional
public class PlaylistServiceIntegrationTest {

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

    private Track track;
    private Playlist playlist;

    @BeforeEach
    void setUp() {
        // Create dependencies: Artist, Album, Genre, MediaType
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

        // Create Track
        track = new Track();
        track.setName("Test Track");
        track.setAlbum(album);
        track.setGenre(genre);
        track.setMediaType(mediaType);
        track.setMilliseconds(1000);
        track.setUnitPrice(1.99);
        track = trackService.save(track);

        // Create Playlist
        playlist = new Playlist();
        playlist.setName("Test Playlist");
        playlist.setTracks(List.of(track));
        playlist = playlistService.save(playlist);
    }

    @Test
    void testCreateAndFindPlaylist() {
        Playlist found = playlistService.findById(playlist.getPlaylistId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Playlist");
        assertThat(found.getTracks()).hasSize(1);
    }

    @Test
    void testUpdatePlaylist() {
        playlist.setName("Updated Playlist");
        // Use a mutable list for tracks to avoid UnsupportedOperationException
        playlist.setTracks(new java.util.ArrayList<>(playlist.getTracks()));
        Playlist updated = playlistService.save(playlist);
        assertThat(updated.getName()).isEqualTo("Updated Playlist");
    }

    @Test
    void testDeletePlaylist() {
        Integer id = playlist.getPlaylistId();
        playlistService.deleteById(id);
        assertThat(playlistService.findById(id)).isEmpty();
    }

    @Test
    void testCreatePlaylist() {
        Playlist newPlaylist = new Playlist();
        newPlaylist.setName("Another Playlist");
        newPlaylist.setTracks(new java.util.ArrayList<>(List.of(track)));
        Playlist saved = playlistService.save(newPlaylist);
        assertThat(saved.getPlaylistId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Another Playlist");
    }

    @Test
    void testReadPlaylist() {
        Playlist found = playlistService.findById(playlist.getPlaylistId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Playlist");
    }

    @Test
    void testUpdatePlaylistTracks() {
        Track newTrack = new Track();
        newTrack.setName("New Track");
        newTrack.setAlbum(track.getAlbum());
        newTrack.setGenre(track.getGenre());
        newTrack.setMediaType(track.getMediaType());
        newTrack.setMilliseconds(2000);
        newTrack.setUnitPrice(2.99);
        newTrack = trackService.save(newTrack);
        playlist.setTracks(new java.util.ArrayList<>(List.of(track, newTrack)));
        Playlist updated = playlistService.save(playlist);
        assertThat(updated.getTracks()).hasSize(2);
    }
}
