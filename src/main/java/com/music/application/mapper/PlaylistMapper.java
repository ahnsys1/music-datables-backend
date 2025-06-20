package com.music.application.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.music.application.dto.PlaylistDTO;
import com.music.application.entity.Playlist;
import com.music.application.entity.Track;

@Component
public class PlaylistMapper {

    public PlaylistDTO toDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setPlaylistId(playlist.getPlaylistId());
        dto.setName(playlist.getName());
        if (playlist.getTracks() != null) {
            dto.setTrackIds(playlist.getTracks().stream()
                    .map(Track::getTrackId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    public Playlist toEntity(PlaylistDTO dto) {
        Playlist playlist = new Playlist();
        playlist.setPlaylistId(dto.getPlaylistId());
        playlist.setName(dto.getName());
        // Tracks should be set in the service layer if needed
        return playlist;
    }
}
