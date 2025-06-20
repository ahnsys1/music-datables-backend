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

import com.music.application.dto.AlbumDTO;
import com.music.application.mapper.AlbumMapper;
import com.music.application.service.AlbumService;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumMapper albumMapper;

    @GetMapping
    public List<AlbumDTO> getAllAlbums() {
        return albumService.findAll().stream().map(albumMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getAlbumById(@PathVariable Integer id) {
        return albumService.findById(id)
                .map(albumMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> createAlbum(@RequestBody AlbumDTO albumDTO) {
        var album = albumMapper.toEntity(albumDTO);
        var saved = albumService.save(album);
        return ResponseEntity.ok(albumMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDTO> updateAlbum(@PathVariable Integer id, @RequestBody AlbumDTO albumDTO) {
        var album = albumMapper.toEntity(albumDTO);
        album.setAlbumId(id);
        var updated = albumService.save(album);
        return ResponseEntity.ok(albumMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Integer id) {
        albumService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
