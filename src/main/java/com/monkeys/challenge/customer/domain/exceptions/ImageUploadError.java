package com.monkeys.challenge.customer.domain.exceptions;

public class ImageUploadError extends RuntimeException {
    public ImageUploadError(String message) {
        super(message);
    }
}
