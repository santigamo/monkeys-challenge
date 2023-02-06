package com.monkeys.challenge.admin.infrastructure.rest.login;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.shared.ChallengeApplication;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class ITPostLoginControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_login_successfully() throws Exception {

        // When - Request is made
        // Then - User is logged in
        var response = mvc.perform(
                post("/login")
                .contentType("application/json")
                .content("{\"username\":\"test@email.com\",\"password\":\"Password!\"}")
                )
                .andReturn()
                .getResponse().getContentAsString();

        var token = mapper.readTree(response).get("access_token").asText();
        assertNotNull(token);
    }

    @Test
    void should_not_login_with_invalid_credentials() throws Exception {

        // When - Request is made
        // Then - User is not logged in
        var response = mvc.perform(
                        post("/login")
                                .contentType("application/json")
                                .content("{\"username\":\"test@email.com\",\"password\":\"badPassword\"}"))
                .andExpect(status().isUnauthorized());
    }
}
