package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.GenreDTO;
import com.music.application.entity.Genre;

public class GenreMapperTest {

    private final GenreMapper mapper = new GenreMapper();

    @Test
    void testToDTOAndToEntity() {
        Genre genre = new Genre();
        genre.setGenreId(1);
        genre.setName("Rock");

        GenreDTO dto = mapper.toDTO(genre);
        assertEquals(1, dto.getGenreId());
        assertEquals("Rock", dto.getName());

        Genre entity = mapper.toEntity(dto);
        assertEquals(1, entity.getGenreId());
        assertEquals("Rock", entity.getName());
    }
}
