package com.monkeys.challenge.customer.application.services.image;

import com.monkeys.challenge.customer.domain.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Service class to upload images.
 */
@AllArgsConstructor
@Log4j2
public class ImageUploader {

    //* Image repository.
    private final ImageRepository imageRepository;

    /**
     * Uploads an image.
     *
     * @param image     Image to upload.
     * @param imageName Image name.
     * @return Image URL.
     */
    public String uploadImage(byte[] image, String imageName) {
        var imageUrl = imageRepository.upload(image, imageName);
        log.debug("Image uploaded: {}", imageUrl);
        return imageUrl;
    }
}
