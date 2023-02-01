package com.monkeys.challenge.admin.infrastructure.http;

import com.monkeys.challenge.admin.domain.SecurityRepository;
import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.admin.domain.exceptions.UserCreationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
public class HttpClientSecurityRepository implements SecurityRepository {

    private static final String TOKEN_PATH = "oauth/token";
    private static final String USER_PATH = "api/v2/users";
    private static final String LOGIN_BODY_PATTERN = "grant_type=password&username=%s&password=%s&audience=%s&scope=&client_id=%s&client_secret=%s";
    private static final String MANAGEMENT_TOKEN_BODY_PATTERN = "grant_type=client_credentials&client_id=%s&client_secret=%s&audience=https://monkey-challenge.uk.auth0.com/api/v2/";
    private static final String CREATE_USER_BODY_PATTERN = "{\"email\":\"%s\",\"username\":\"%s\",\"password\":\"%s\",\"connection\":\"Username-Password-Authentication\"}";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${auth0.audience}")
    private String audience;
    @Value("${auth0.client-id}")
    private String clientId;
    @Value("${auth0.client-secret}")
    private String clientSecret;

    @Override
    public void createUser(String email, String username, String password) {
        //? Get management api token
        var token = getManagementToken();

        //? Prepare body
        var body = String.format(CREATE_USER_BODY_PATTERN, email, username, password);

        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        //? Send request
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error("Internal server error: {}", e.getMessage());
            Thread.currentThread().interrupt();
            throw new GenericError();
        }

        if (response.statusCode() != 201) {
            log.error("Error creating user: {}", response.body());
            throw new UserCreationException();
        }
        log.info("User created: {}", response.body());
    }

    @Override
    public String doLoginWithPassword(String username, String password) {
        //? Prepare body
        var body = String.format(LOGIN_BODY_PATTERN, username, password, audience, clientId, clientSecret);

        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + TOKEN_PATH))
                .header(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        //? Send request
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GenericError();
        }

        return response.body();
    }

    private String getManagementToken() {
        //? Prepare body
        var body = String.format(MANAGEMENT_TOKEN_BODY_PATTERN, clientId, clientSecret);

        //? Create request
        var request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + TOKEN_PATH))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        //? Send request
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException |InterruptedException e) {
            log.error("Error getting management token", e);
            Thread.currentThread().interrupt();
            throw new GenericError();
        }

        if (response.statusCode() != 201) {
            log.error("Error getting management api token: {}", response.body());
            throw new GenericError();
        }

        //? Parse response to get just token
        return response.body().split(":")[1].split(",")[0].replace("\"", "");
    }
}
