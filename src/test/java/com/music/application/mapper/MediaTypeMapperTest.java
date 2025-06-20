package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.MediaTypeDTO;
import com.music.application.entity.MediaType;

public class MediaTypeMapperTest {

    private final MediaTypeMapper mapper = new MediaTypeMapper();

    @Test
    void testToDTOAndToEntity() {
        MediaType mediaType = new MediaType();
        mediaType.setMediaTypeId(1);
        mediaType.setName("MP3");

        MediaTypeDTO dto = mapper.toDTO(mediaType);
        assertEquals(1, dto.getMediaTypeId());
        assertEquals("MP3", dto.getName());

        MediaType entity = mapper.toEntity(dto);
        assertEquals(1, entity.getMediaTypeId());
        assertEquals("MP3", entity.getName());
    }
}
