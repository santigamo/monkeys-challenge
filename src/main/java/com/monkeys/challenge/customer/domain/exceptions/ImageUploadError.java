package com.monkeys.challenge.customer.domain.exceptions;

/**
 * Exception thrown when a customer is not found.
 */
public class ImageUploadError extends RuntimeException {

    /**
     * Constructor.
     * @param message The message.
     */
    public ImageUploadError(String message) {
        super(message);
    }
}
