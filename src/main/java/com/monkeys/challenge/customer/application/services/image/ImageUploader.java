package com.monkeys.challenge.customer.application.services.image;

import com.monkeys.challenge.customer.domain.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
public class ImageUploader {

    private final ImageRepository imageRepository;

    public String uploadImage(byte[] image, String imageName) {
        var imageUrl = imageRepository.upload(image, imageName);
        log.debug("Image uploaded: {}", imageUrl);
        return imageUrl;
    }
}
