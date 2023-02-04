package com.monkeys.challenge.customer.infrastructure.http;

import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.customer.domain.ImageRepository;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Image repository implementation using HTTP Client.
 */
@Log4j2
public class HttpClientImageRepository implements ImageRepository {

    //* Internal server error message.
    public static final String INTERNAL_SERVER_ERROR = "Internal server error: {}";

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(byte[] image, String imageName) {
        return null;
    }

    protected HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error(INTERNAL_SERVER_ERROR, e.getMessage());
            Thread.currentThread().interrupt();
            throw new GenericError();
        }
    }
}
