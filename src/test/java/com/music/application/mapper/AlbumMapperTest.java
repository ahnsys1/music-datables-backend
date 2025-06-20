package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.AlbumDTO;
import com.music.application.entity.Album;

public class AlbumMapperTest {

    private final AlbumMapper mapper = new AlbumMapper();

    @Test
    void testToDTOAndToEntity() {
        Album album = new Album();
        album.setAlbumId(1);
        album.setTitle("Test Album");

        AlbumDTO dto = mapper.toDTO(album);
        assertEquals(1, dto.getAlbumId());
        assertEquals("Test Album", dto.getTitle());

        Album entity = mapper.toEntity(dto);
        assertEquals(1, entity.getAlbumId());
        assertEquals("Test Album", entity.getTitle());
    }
}
