package com.monkeys.challenge.customer.application.services.image;

import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit-test")
class ImageUploaderTest {

    private ImageRepository imageRepository;
    private ImageUploader imageUploader;

    @BeforeEach
    void setUp() {
        imageRepository = mock(ImageRepository.class);
        imageUploader = new ImageUploader(imageRepository);
    }

    @Test
    void should_upload_a_image_successfully() {
        // Given
        var expectedUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
        when(imageRepository.upload(any(), any()))
                .thenReturn(expectedUrl);

        // When
        var actualUrl = imageUploader.uploadImage("image".getBytes(), "image/png");
        // Then
        assertEquals(expectedUrl, actualUrl);
        verify(imageRepository).upload(any(), any());
    }
}