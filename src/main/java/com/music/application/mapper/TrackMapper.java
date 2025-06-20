package com.music.application.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.music.application.dto.TrackDTO;
import com.music.application.entity.Track;
import com.music.application.service.AlbumService;
import com.music.application.service.GenreService;
import com.music.application.service.MediaTypeService;

@Component
public class TrackMapper {

    @Autowired
    @Lazy
    private AlbumService albumService;
    @Autowired
    @Lazy
    private MediaTypeService mediaTypeService;
    @Autowired
    @Lazy
    private GenreService genreService;

    public TrackDTO toDTO(Track track) {
        TrackDTO dto = new TrackDTO();
        dto.setTrackId(track.getTrackId());
        dto.setName(track.getName());
        dto.setComposer(track.getComposer());
        dto.setMilliseconds(track.getMilliseconds());
        dto.setBytes(track.getBytes());
        dto.setUnitPrice(track.getUnitPrice());
        if (track.getAlbum() != null) {
            dto.setAlbumId(track.getAlbum().getAlbumId());
        }
        if (track.getMediaType() != null) {
            dto.setMediaTypeId(track.getMediaType().getMediaTypeId());
        }
        if (track.getGenre() != null) {
            dto.setGenreId(track.getGenre().getGenreId());
        }
        return dto;
    }

    public Track toEntity(TrackDTO dto) {
        Track track = new Track();
        track.setTrackId(dto.getTrackId());
        track.setName(dto.getName());
        track.setComposer(dto.getComposer());
        track.setMilliseconds(dto.getMilliseconds());
        track.setBytes(dto.getBytes());
        track.setUnitPrice(dto.getUnitPrice());
        if (dto.getAlbumId() != null) {
            Optional<com.music.application.entity.Album> albumOpt = albumService.findById(dto.getAlbumId());
            albumOpt.ifPresent(track::setAlbum);
        }
        if (dto.getMediaTypeId() != null) {
            Optional<com.music.application.entity.MediaType> mediaTypeOpt = mediaTypeService.findById(dto.getMediaTypeId());
            mediaTypeOpt.ifPresent(track::setMediaType);
        }
        if (dto.getGenreId() != null) {
            Optional<com.music.application.entity.Genre> genreOpt = genreService.findById(dto.getGenreId());
            genreOpt.ifPresent(track::setGenre);
        }
        // InvoiceLines and Playlists can be set similarly if needed
        return track;
    }
}
