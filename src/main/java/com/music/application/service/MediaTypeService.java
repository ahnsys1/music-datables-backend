package com.music.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.music.application.entity.MediaType;
import com.music.application.repository.MediaTypeRepository;

@Service
@Transactional
public class MediaTypeService {

    @Autowired
    private MediaTypeRepository mediaTypeRepository;

    public List<MediaType> findAll() {
        return mediaTypeRepository.findAll();
    }

    public Optional<MediaType> findById(Integer id) {
        return mediaTypeRepository.findById(id);
    }

    public MediaType save(MediaType mediaType) {
        return mediaTypeRepository.save(mediaType);
    }

    public void deleteById(Integer id) {
        mediaTypeRepository.deleteById(id);
    }
}
