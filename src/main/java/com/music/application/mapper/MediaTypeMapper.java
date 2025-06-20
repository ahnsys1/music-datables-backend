package com.music.application.mapper;

import org.springframework.stereotype.Component;

import com.music.application.dto.MediaTypeDTO;
import com.music.application.entity.MediaType;

@Component
public class MediaTypeMapper {

    public MediaTypeDTO toDTO(MediaType mediaType) {
        MediaTypeDTO dto = new MediaTypeDTO();
        dto.setMediaTypeId(mediaType.getMediaTypeId());
        dto.setName(mediaType.getName());
        return dto;
    }

    public MediaType toEntity(MediaTypeDTO dto) {
        MediaType mediaType = new MediaType();
        mediaType.setMediaTypeId(dto.getMediaTypeId());
        mediaType.setName(dto.getName());
        return mediaType;
    }
}
