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

import com.music.application.dto.MediaTypeDTO;
import com.music.application.mapper.MediaTypeMapper;
import com.music.application.service.MediaTypeService;

@RestController
@RequestMapping("/api/mediatypes")
public class MediaTypeController {

    @Autowired
    private MediaTypeService mediaTypeService;

    @Autowired
    private MediaTypeMapper mediaTypeMapper;

    @GetMapping
    public List<MediaTypeDTO> getAllMediaTypes() {
        return mediaTypeService.findAll().stream().map(mediaTypeMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaTypeDTO> getMediaTypeById(@PathVariable Integer id) {
        return mediaTypeService.findById(id)
                .map(mediaTypeMapper::toDTO)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MediaTypeDTO> createMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
        var mediaType = mediaTypeMapper.toEntity(mediaTypeDTO);
        var saved = mediaTypeService.save(mediaType);
        return ResponseEntity.ok(mediaTypeMapper.toDTO(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaTypeDTO> updateMediaType(@PathVariable Integer id, @RequestBody MediaTypeDTO mediaTypeDTO) {
        var mediaType = mediaTypeMapper.toEntity(mediaTypeDTO);
        mediaType.setMediaTypeId(id);
        var updated = mediaTypeService.save(mediaType);
        return ResponseEntity.ok(mediaTypeMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMediaType(@PathVariable Integer id) {
        mediaTypeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
