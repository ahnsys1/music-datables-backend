package com.music.application.mapper;

import org.springframework.stereotype.Component;

import com.music.application.dto.ArtistDTO;
import com.music.application.entity.Artist;

@Component
public class ArtistMapper {

    public ArtistDTO toDTO(Artist artist) {
        ArtistDTO dto = new ArtistDTO();
        dto.setArtistId(artist.getArtistId());
        dto.setName(artist.getName());
        return dto;
    }

    public Artist toEntity(ArtistDTO dto) {
        Artist artist = new Artist();
        artist.setArtistId(dto.getArtistId());
        artist.setName(dto.getName());
        return artist;
    }
}
