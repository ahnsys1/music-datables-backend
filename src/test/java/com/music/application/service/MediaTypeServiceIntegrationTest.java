package com.music.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.music.application.entity.MediaType;

@SpringBootTest
public class MediaTypeServiceIntegrationTest {

    @Autowired
    private MediaTypeService mediaTypeService;

    @Test
    void testCreateAndFindMediaType() {
        MediaType mediaType = new MediaType();
        mediaType.setName("Test MediaType");
        MediaType saved = mediaTypeService.save(mediaType);
        assertThat(saved.getMediaTypeId()).isNotNull();
        MediaType found = mediaTypeService.findById(saved.getMediaTypeId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test MediaType");
        mediaTypeService.deleteById(saved.getMediaTypeId());
    }
}
