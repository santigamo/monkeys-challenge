package com.monkeys.challenge.admin.infrastructure.rest.create;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import com.monkeys.challenge.shared.ChallengeApplication;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class ITPostCreateUserControllerTest  extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_create_user_successfully() throws Exception {
        // Given - Logged user
        loggedUser(mvc);
        String createUserBody = """
            {
                "email" : "integration@test.com",
                "username" : "jdoe",
                "password" : "Password!"
            }
            """;

        // When - Request is made
        // Then - User is created and can be found
        mvc.perform(
                post("/users")
                        .header("Authorization", "Bearer " + AUTH_TOKEN)
                        .contentType("application/json")
                        .content(createUserBody)
                )
                .andExpect(status().isOk());

        var createdUser = getCreatedUser(userRepository, "integration@test.com");
        assertTrue(createdUser.isPresent());

        // After all
        removeCreatedUser(userRepository, createdUser.get().id());
    }
}
