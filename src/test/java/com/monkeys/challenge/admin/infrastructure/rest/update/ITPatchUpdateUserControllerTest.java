package com.monkeys.challenge.admin.infrastructure.rest.update;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class ITPatchUpdateUserControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void should_update_user_successfully() throws Exception {
        // given logged user
        loggedUser(mvc);
        var userId = givenSavedTestUser(userRepository);
        String updatebody = """
                {
                    "username": "updated"
                }
                """;

        // When - Request is made
        // Then - User is updated
        mvc.perform(
                patch("/users/%s".formatted(userId))
                        .header("Authorization", "Bearer " + AUTH_TOKEN)
                        .contentType("application/json")
                        .content(updatebody)
                ).andExpect(status().isOk());

        // After all
        removeCreatedUser(userRepository, userId);

    }
}
