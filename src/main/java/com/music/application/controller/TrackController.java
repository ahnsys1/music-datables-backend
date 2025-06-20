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

import com.music.application.dto.TrackDTO;
import com.music.application.mapper.TrackMapper;
import com.music.application.service.TrackService;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    @Autowired
    private TrackService trackService;

    @Autowired
    TrackMapper trackMapper;

    @GetMapping
    public List<TrackDTO> getAllTracks() {
        return trackService.findAll().stream().map(trackMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable Integer id) {
        return trackService.findById(id)
                .map(trackMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrackDTO> createTrack(@RequestBody TrackDTO trackDTO) {
        var track = trackMapper.toEntity(trackDTO);
        var saved = trackService.save(track);
        return ResponseEntity.ok(trackMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackDTO> updateTrack(@PathVariable Integer id, @RequestBody TrackDTO trackDTO) {
        var track = trackMapper.toEntity(trackDTO);
        track.setTrackId(id);
        var updated = trackService.save(track);
        return ResponseEntity.ok(trackMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Integer id) {
        trackService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
