package com.monkeys.challenge.admin.infrastructure.http;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.shared.ChallengeApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Transactional
@SpringBootTest(classes = ChallengeApplication.class)
class HttpClientUserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    private static final String email = "test@email.com";
    private static final String username = "test";
    private static final String password = "test";

    @Mock
    HttpResponse<String> mockResponse;

    @Spy
    HttpClient httpClient;

    @Disabled
    @Test
    void createUser() throws IOException, InterruptedException {
        // Given
        HttpResponse reponse = mock(HttpResponse.class);
        when(httpClient.send(any(), any())).thenReturn(reponse);


        // When
        userRepository.createUser(email, username, password);
    }

    @Disabled
    @Test
    void delete() {
    }

    @Disabled
    @Test
    void findAll() {
    }

    @Disabled
    @Test
    void updateUser() {
    }

    @Disabled
    @Test
    void changeAdminStatus() {
    }

    @Disabled
    @Test
    void doLoginWithPassword() {
    }
}