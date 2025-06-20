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

import com.music.application.dto.PlaylistDTO;
import com.music.application.mapper.PlaylistMapper;
import com.music.application.service.PlaylistService;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private PlaylistMapper playlistMapper;

    @GetMapping
    public List<PlaylistDTO> getAllPlaylists() {
        return playlistService.findAll().stream().map(playlistMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDTO> getPlaylistById(@PathVariable Integer id) {
        return playlistService.findById(id)
                .map(playlistMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlaylistDTO> createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        var playlist = playlistMapper.toEntity(playlistDTO);
        var saved = playlistService.save(playlist);
        return ResponseEntity.ok(playlistMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDTO> updatePlaylist(@PathVariable Integer id, @RequestBody PlaylistDTO playlistDTO) {
        var playlist = playlistMapper.toEntity(playlistDTO);
        playlist.setPlaylistId(id);
        var updated = playlistService.save(playlist);
        return ResponseEntity.ok(playlistMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Integer id) {
        playlistService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
