package com.music.application.mapper;

import org.springframework.stereotype.Component;

import com.music.application.dto.AlbumDTO;
import com.music.application.entity.Album;

@Component
public class AlbumMapper {

    public AlbumDTO toDTO(Album album) {
        AlbumDTO dto = new AlbumDTO();
        dto.setAlbumId(album.getAlbumId());
        dto.setTitle(album.getTitle());
        if (album.getArtist() != null) {
            dto.setArtistId(album.getArtist().getArtistId());
        }
        return dto;
    }

    public Album toEntity(AlbumDTO dto) {
        Album album = new Album();
        album.setAlbumId(dto.getAlbumId());
        album.setTitle(dto.getTitle());
        // Note: Artist needs to be set separately using ArtistService if needed
        return album;
    }
}
