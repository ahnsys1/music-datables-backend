package com.music.application.mapper;

import org.springframework.stereotype.Component;

import com.music.application.dto.GenreDTO;
import com.music.application.entity.Genre;

@Component
public class GenreMapper {

    public GenreDTO toDTO(Genre genre) {
        GenreDTO dto = new GenreDTO();
        dto.setGenreId(genre.getGenreId());
        dto.setName(genre.getName());
        return dto;
    }

    public Genre toEntity(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setGenreId(dto.getGenreId());
        genre.setName(dto.getName());
        return genre;
    }
}
