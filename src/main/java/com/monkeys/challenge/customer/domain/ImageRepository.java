package com.monkeys.challenge.customer.domain;

public interface ImageRepository {

    /**
     * Uploads an image.
     * @param image The image file to upload.
     * @param imageName The name of the image file.
     * @return The URL of the uploaded image.
     */
    String upload(byte[] image, String imageName);
}
