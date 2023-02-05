package com.monkeys.challenge.customer.infrastructure.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.customer.domain.ImageRepository;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

/**
 * Image repository implementation using HTTP Client.
 */
@Log4j2
public class OkHttpImageRepository implements ImageRepository {

    //* ImageBB API URL.
    @Value("${imgbb.api.url}")
    @Setter(AccessLevel.PROTECTED)
    private String imageBBUrl;

    //* ImageBB API key.
    @Setter(AccessLevel.PROTECTED)
    @Value("${imgbb.api.key}")
    private String imageBBApiKey;

    //* Internal server error message.
    private static final String INTERNAL_SERVER_ERROR = "Internal server error: {}";

    //* HTTP client.
    private final OkHttpClient client = new OkHttpClient();

    //* JSON mapper.
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    /**
     * {@inheritDoc}
     */
    @Override
    public String upload(byte[] image, String imageName) {
        var base64Image = java.util.Base64.getEncoder().encodeToString(image);


        var requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", base64Image)
                .addFormDataPart("name", imageName)
                .build();

        var request = new Request.Builder()
                .url(imageBBUrl.formatted(imageBBApiKey))
                .post(requestBody)
                .build();

        var response = sendRequest(request);
        try {
            return  mapper.readTree(response).get("data").get("display_url").asText();
        } catch (Exception e) {
            log.error("Error parsing JSON response: {}", e.getMessage());
            throw new GenericError();
        }
    }

    protected String sendRequest(Request request) {
        var call = client.newCall(request);
        try (var response = call.execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return response.body().string();
            } else {
                log.error(INTERNAL_SERVER_ERROR, response.message());
                throw new GenericError();
            }
        } catch (IOException e) {
            log.error(INTERNAL_SERVER_ERROR, e.getMessage());
            throw new GenericError();
        }
    }
}
