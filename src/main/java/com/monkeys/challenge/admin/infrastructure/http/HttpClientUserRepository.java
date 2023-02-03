package com.monkeys.challenge.admin.infrastructure.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.domain.UserRole;
import com.monkeys.challenge.admin.domain.exceptions.*;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import lombok.AccessLevel;
import lombok.Setter;
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
    private static final String LOGIN_BODY_PATTERN = "grant_type=password&username=%s&password=%s&audience=%s&client_id=%s&client_secret=%s";
    private static final String MANAGEMENT_TOKEN_BODY_PATTERN = "{\"client_id\":\"%s\",\"client_secret\":\"%s\",\"audience\":\"https://monkey-challenge.uk.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}";
    private static final String CREATE_USER_BODY_PATTERN = "{\"email\":\"%s\",\"username\":\"%s\",\"password\":\"%s\",\"connection\":\"Username-Password-Authentication\"}";
   private static final String UPDATE_USER_BODY_PATTERN = "{\"name\":\"%s\",\"username\":\"%s\"}";
   private static final String ADMIN_ROLE_BODY_PATTERN = """
           {
              "roles": [
                "%s"
              ]
            }
           """;
    public static final String BEARER = "Bearer ";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error: {}";
    public static final String AUTH0 = "/auth0%7C";
    public static final String MESSAGE = "message";
    public static final String ROLE_PATH_FRAGMENT = "/roles";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    @Setter(AccessLevel.PROTECTED)
    private String issuer;
    @Value("${auth0.audience}")
    @Setter(AccessLevel.PROTECTED)
    private String audience;
    @Value("${auth0.client-id}")
    @Setter(AccessLevel.PROTECTED)
    private String clientId;
    @Value("${auth0.client-secret}")
    @Setter(AccessLevel.PROTECTED)
    private String clientSecret;
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @SneakyThrows
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
        var response = sendRequest(request);

        if (response.statusCode() != 201) {
            var causa = mapper.readTree(response.body()).get(MESSAGE).asText();
            log.error("Error creating user: {}", causa);
            throw new UserCreationException();
        }

        log.debug("User created successfully: {}", response.body());
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
            var causa = mapper.readTree(response.body()).get(MESSAGE).asText();
            log.error("Error deleting user: {}", causa);
            throw new UserRemoveException(causa);
        }
        log.info("User deleted: {}", id);
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
            var causa = mapper.readTree(response.body()).get(MESSAGE).asText("");
            log.error("Error updating user: {}", causa);
            throw new UserUpdateException(causa);
        }

        try {
            log.info("User updated: {}", response.body());
            return mapper.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            log.error("Error parsing response: {}", e.getMessage());
            throw new GenericError();
        }
    }

    /**
     * @param userId The userId of the user to get roles
     * @return A list of {@link UserRole}
     */
    @Override
    public List<UserRole> getUserRoles(String userId) {
        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH + AUTH0 + userId+ ROLE_PATH_FRAGMENT))
                .header(AUTHORIZATION, BEARER + getManagementToken())
                .GET().build();

        //? Send request
        var response = sendRequest(request);
        List<UserRole> userRoles;
        try{
            if (response.statusCode() != 200) throw new GenericError();
            userRoles = mapper.readValue(response.body(), new TypeReference<>() {});
        } catch (Exception e) {
            log.error("Error getting user roles: {}", e.getMessage());
            throw new GenericError();
        }
        log.debug("Found {} user roles", userRoles.size());
        return userRoles;
    }

    /**
     * @param userId The userId of the user to add admin role
     * @param roleId The roleId of the role to add
     */
    @Override
    public void addRole(String userId, String roleId) {
        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH + AUTH0 + userId+ ROLE_PATH_FRAGMENT))
                .header(AUTHORIZATION, BEARER + getManagementToken())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString(ADMIN_ROLE_BODY_PATTERN.formatted(roleId))).build();

        //? Send request
        var response = sendRequest(request);
        if (response.statusCode() != 204) {
            log.error("Set role \"{}\" error: {}",roleId, response.body());
            throw new GenericError();
        }
        log.debug("Role \"{}\" added successfully", roleId);
    }

    /**
     * @param userId The userId of the user to remove admin role
     * @param roleId The roleId of the role to remove
     */
    @Override
    public void removeRole(String userId, String roleId) {
        //? Create request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + USER_PATH + AUTH0 + userId+ ROLE_PATH_FRAGMENT))
                .header(AUTHORIZATION, BEARER + getManagementToken())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .method("DELETE", HttpRequest.BodyPublishers.ofString(ADMIN_ROLE_BODY_PATTERN.formatted(roleId))).build();

        //? Send request
        var response = sendRequest(request);
        if (response.statusCode() != 204) {
            log.error("Delete role \"{}\" error: {}",roleId, response.body());
            throw new GenericError();
        }
        log.debug("Role \"{}\" removed successfully",roleId);
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
            throw new UserAuthenticationException();
        }

        return response.body();
    }

    protected String getManagementToken() {
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
        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(response.body());
        } catch (JsonProcessingException e) {
            log.error("Error getting token from: {}", response.body());
            throw new GenericError();
        }
        return jsonNode.get("access_token").asText();
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
