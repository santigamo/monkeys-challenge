package com.monkeys.challenge.customer.infrastructure.rest.image;

import com.monkeys.challenge.customer.application.services.image.ImageUploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Tag("unit-test")
class PostUploadImageControllerTest {

    private ImageUploader imageUploader;
    private PostUploadImageController controller;

    @BeforeEach
    void setUp() {
        imageUploader = mock(ImageUploader.class);
        controller = new PostUploadImageController(imageUploader);
    }

    @Test
    void uploadImage() throws IOException {
        // Given - image upload request
        var expectedImageUrl = "http://image.url";
        when(imageUploader.uploadImage(any(), any())).thenReturn(expectedImageUrl);
        MultipartFile image = mock(MultipartFile.class);

        var expectedResponse = ResponseEntity.created(URI.create(expectedImageUrl)).body(new PostUploadImageController.UploadImageResponse(expectedImageUrl));
        // When - the controller is called
        var actualResponse = controller.uploadImage(image);

        // Then - the response is the expected one
        assertEquals(expectedResponse, actualResponse);
        verify(imageUploader).uploadImage(any(), any());
    }
}