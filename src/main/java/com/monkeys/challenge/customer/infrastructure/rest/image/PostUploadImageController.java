package com.monkeys.challenge.customer.infrastructure.rest.image;

import com.monkeys.challenge.customer.application.services.image.ImageUploader;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

/**
 * Post controller for uploading an image.
 */
@RestController
@Log4j2
@RequiredArgsConstructor
public class PostUploadImageController {

    private final ImageUploader imageUploader;

    @PostMapping(value = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('update:customers')")
    @Operation(summary = "Upload an image", tags = {"customers"})
    public ResponseEntity<UploadImageResponse> uploadImage(
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        log.debug("Received request to upload an image");
        var imageUrl = imageUploader.uploadImage(image.getBytes(), image.getOriginalFilename());
        return ResponseEntity.created(URI.create(imageUrl)).body(new UploadImageResponse(imageUrl));
    }

    public record UploadImageResponse(String imageUrl) {}
}
