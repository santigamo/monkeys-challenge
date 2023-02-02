package com.monkeys.challenge.customer.infrastructure.rest.image;

import com.monkeys.challenge.customer.application.services.image.ImageUploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RestController
@Log4j2
@RequiredArgsConstructor
public class PostUploadImageController {

    private final ImageUploader imageUploader;

    @PostMapping(value = "/image/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        log.debug("Received request to upload an image");
        var imageUrl = imageUploader.uploadImage(image.getBytes(), image.getName());
        return ResponseEntity.created(URI.create(imageUrl)).build();
    }
}
