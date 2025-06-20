package com.music.application.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.music.application.dto.TrackDTO;
import com.music.application.entity.Track;

public class TrackMapperTest {

    private final TrackMapper mapper = new TrackMapper();

    @Test
    void testToDTOAndToEntity() {
        Track track = new Track();
        track.setTrackId(1);
        track.setName("Test Track");
        track.setComposer("Composer");
        track.setMilliseconds(300000);
        track.setBytes(5000000);
        track.setUnitPrice(1.99);

        TrackDTO dto = mapper.toDTO(track);
        assertEquals(1, dto.getTrackId());
        assertEquals("Test Track", dto.getName());
        assertEquals("Composer", dto.getComposer());
        assertEquals(300000, dto.getMilliseconds());
        assertEquals(Integer.valueOf(5000000), dto.getBytes());
        assertEquals(1.99, dto.getUnitPrice());

        Track entity = mapper.toEntity(dto);
        assertEquals(1, entity.getTrackId());
        assertEquals("Test Track", entity.getName());
        assertEquals("Composer", entity.getComposer());
        assertEquals(300000, entity.getMilliseconds());
        assertEquals(Integer.valueOf(5000000), entity.getBytes());
        assertEquals(1.99, entity.getUnitPrice());
    }
}
