package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.Genre;

@SpringBootTest
public class GenreServiceIntegrationTest {

    @Autowired
    private GenreService genreService;

    @Test
    void testCreateAndFindGenre() {
        Genre genre = new Genre();
        genre.setName("Test Genre");
        Genre saved = genreService.save(genre);
        assertThat(saved.getGenreId()).isNotNull();
        Genre found = genreService.findById(saved.getGenreId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test Genre");
        genreService.deleteById(saved.getGenreId());
    }
}
