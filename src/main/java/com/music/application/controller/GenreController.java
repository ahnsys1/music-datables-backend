package com.music.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.music.application.dto.GenreDTO;
import com.music.application.mapper.GenreMapper;
import com.music.application.service.GenreService;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreMapper genreMapper;

    @GetMapping
    public List<GenreDTO> getAllGenres() {
        return genreService.findAll().stream().map(genreMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Integer id) {
        return genreService.findById(id)
                .map(genreMapper::toDTO)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@RequestBody GenreDTO genreDTO) {
        var genre = genreMapper.toEntity(genreDTO);
        var saved = genreService.save(genre);
        return ResponseEntity.ok(genreMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable Integer id, @RequestBody GenreDTO genreDTO) {
        var genre = genreMapper.toEntity(genreDTO);
        genre.setGenreId(id);
        var updated = genreService.save(genre);
        return ResponseEntity.ok(genreMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Integer id) {
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
