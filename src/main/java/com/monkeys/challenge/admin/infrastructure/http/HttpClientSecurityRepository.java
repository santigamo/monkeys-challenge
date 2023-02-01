package com.monkeys.challenge.admin.infrastructure.http;

import com.monkeys.challenge.admin.domain.SecurityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log4j2
public class HttpClientSecurityRepository implements SecurityRepository {

    private static final String TOKEN_PATH = "oauth/token";

    private static final String BODY_PATTERN = "grant_type=password&username=%s&password=%s&audience=%s&scope=&client_id=%s&client_secret=%s";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;
    @Value("${auth0.audience}")
    private String audience;
    @Value("${auth0.client-id}")
    private String clientId;
    @Value("${auth0.client-secret}")
    private String clientSecret;

    @Override
    public String doLoginWithPassword(String username, String password) {
        var body = String.format(BODY_PATTERN, username, password, audience, clientId, clientSecret);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(issuer + TOKEN_PATH))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
}
