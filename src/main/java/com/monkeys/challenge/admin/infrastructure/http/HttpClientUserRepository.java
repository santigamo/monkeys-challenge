package com.monkeys.challenge.admin.infrastructure.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.domain.exceptions.GenericError;
import com.monkeys.challenge.admin.domain.exceptions.UserRemoveException;
import com.monkeys.challenge.admin.domain.exceptions.UserUpdateException;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Log4j2
public class HttpClientUserRepository implements UserRepository {

    private static final String TOKEN_PATH = "oauth/token";
    private static final String USER_PATH = "api/v2/users";
    private static final String LOGIN_BODY_PATTERN = "grant_type=password&username=%s&password=%s&audience=%s&scope=&client_id=%s&client_secret=%s";
    private static final String MANAGEMENT_TOKEN_BODY_PATTERN = "{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"audience\":\"https://monkey-challenge.uk.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}";
    private static final String CREATE_USER_BODY_PATTERN = "{\"email\":\"%s\",\"username\":\"%s\",\"password\":\"%s\",\"connection\":\"Username-Password-Authentication\"}";
   private static final String UPDATE_USER_BODY_PATTERN = "{\"name\":\"%s\",\"username\":\"%s\"}";
    public static final String BEARER = "Bearer ";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error: {}";
    public static final String AUTH0 = "/auth0%7C";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${auth0.audience}")
    private String audience;
    @Value("${auth0.client-id}")
    private String clientId;
    @Value("${auth0.client-secret}")
    private String clientSecret;
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

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
                .header(AUTHORIZATION, BEARER + token)
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        //? Send request
        sendRequest(request);
    }

    @SneakyThrows
    @Override
    public void delete(String id) {
        //? Get management api token
        var token = getManagementToken();

        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH + AUTH0 + id))
                .header(AUTHORIZATION, BEARER + token)
                .DELETE().build();

        //? Send request
        var response = sendRequest(request);

        if (response.statusCode() != 204) {
            var causa = mapper.readTree(response.body()).get("message").asText();
            log.error("Error deleting user: {}", causa);
            throw new UserRemoveException(causa);
        }
        log.info("User deleted: {}", response.body());
    }

    @Override
    public List<User> findAll() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH))
                .header(AUTHORIZATION, BEARER + getManagementToken())
                .GET().build();

       var response = sendRequest(request);

        List<User> list;
        try {
            list = mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            log.error("Error parsing response: {}", e.getMessage());
            throw new GenericError();
        }
        log.debug("Users founds: {}", list.size());
        return list;
    }

    @SneakyThrows
    @Override
    public User updateUser(String id, String name, String username) {
        //? Get management api token
        var token = getManagementToken();

        //? Prepare body
        var body = String.format(UPDATE_USER_BODY_PATTERN, name, username);

        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH + AUTH0 + id))
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, BEARER + token)
                .method("PATCH", HttpRequest.BodyPublishers.ofString(body)).build();

        //? Send request
        var response = sendRequest(request);

        if (response.statusCode() != 200) {
            var causa = mapper.readTree(response.body()).get("message").asText("");
            log.error("Error updating user: {}", causa);
            throw new UserUpdateException(causa);
        }
        log.info("User updated: {}", response.body());

        try {
            return mapper.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing response: {}", e.getMessage());
            throw new GenericError();
        }
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
        var response = sendRequest(request);
        if (response.statusCode() != 200) {
            log.error("Login error: {}", response.body());
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
        var response = sendRequest(request);

        if (response.statusCode() != 200) {
            log.error("Error getting management api token: {}", response.body());
            throw new GenericError();
        }

        //? Parse response
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            log.error("Error getting token from: {}", response.body());
            throw new GenericError();
        }
        return jsonNode.get("access_token").asText();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        try {
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            log.error(INTERNAL_SERVER_ERROR, e.getMessage());
            Thread.currentThread().interrupt();
            throw new GenericError();
        }
    }
}
