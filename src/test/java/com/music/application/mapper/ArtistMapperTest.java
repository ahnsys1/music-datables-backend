package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.ArtistDTO;
import com.music.application.entity.Artist;

public class ArtistMapperTest {

    private final ArtistMapper mapper = new ArtistMapper();

    @Test
    void testToDTOAndToEntity() {
        Artist artist = new Artist();
        artist.setArtistId(1);
        artist.setName("Test Artist");

        ArtistDTO dto = mapper.toDTO(artist);
        assertEquals(1, dto.getArtistId());
        assertEquals("Test Artist", dto.getName());

        Artist entity = mapper.toEntity(dto);
        assertEquals(1, entity.getArtistId());
        assertEquals("Test Artist", entity.getName());
    }
}
