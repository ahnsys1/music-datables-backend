package com.music.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.music.application.entity.Playlist;
import com.music.application.repository.PlaylistRepository;

@Service
@Transactional
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> findById(Integer id) {
        return playlistRepository.findById(id);
    }

    public Playlist save(Playlist playlist) {
        Playlist savedPlaylist = playlistRepository.save(playlist);
        return savedPlaylist;
    }

    public void deleteById(Integer id) {
        playlistRepository.deleteById(id);
    }
}
