package com.monkeys.challenge.customer.infrastructure.rest.create;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.CustomerRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class ITPostCreateCustomerControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;



    @Test
    void should_create_customer_successfully() throws Exception {
        // Given - Logged user
        loggedUser(mvc);
        String createCustomerBody = """
            {
                "name" : "James Doe",
                "surname" : "jdoe",
                "avatar" : "imageUrl"
            }
            """;

        // When - Request is made
        // Then - Customer is created
        var response = mvc.perform(
                post("/customers")
                        .header("Authorization", "Bearer " + AUTH_TOKEN)
                        .contentType("application/json")
                        .content(createCustomerBody)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").isNotEmpty())
                .andReturn().getResponse().getContentAsString();
        var customerId = mapper.readTree(response).get("customerId").asText();
        assertNotNull(customerRepository.findById(customerId));
    }

    @Test
    void should_fail_if_customer_name_not_valid() throws Exception {
        // Given - Logged user
        loggedUser(mvc);
        String createCustomerBody = """
            {
                "name" : "   ",
                "surname" : "jdoe",
                "avatar" : "imageUrl"
            }
            """;

        // When - Request is made
        // Then - InvalidCustomerNameException is thrown
        mvc.perform(
                        post("/customers")
                                .header("Authorization", "Bearer " + AUTH_TOKEN)
                                .contentType("application/json")
                                .content(createCustomerBody)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_fail_if_customer_surname_not_valid() throws Exception {
        // Given - Logged user
        loggedUser(mvc);
        String createCustomerBody = """
            {
                "name" : "James",
                "surname" : "   ",
                "avatar" : "imageUrl"
            }
            """;

        // When - Request is made
        // Then - Exception is thrown
        mvc.perform(
                        post("/customers")
                                .header("Authorization", "Bearer " + AUTH_TOKEN)
                                .contentType("application/json")
                                .content(createCustomerBody)
                )
                .andExpect(status().isBadRequest());
    }
}
