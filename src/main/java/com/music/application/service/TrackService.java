package com.music.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.music.application.entity.Track;
import com.music.application.repository.TrackRepository;

@Service
@Transactional
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List<Track> findAll() {
        return trackRepository.findAll();
    }

    public Optional<Track> findById(Integer id) {
        return trackRepository.findById(id);
    }

    public Track save(Track track) {
        Track createdTrack = trackRepository.save(track);
        return createdTrack;
    }

    public void deleteById(Integer id) {
        trackRepository.deleteById(id);
    }
}
