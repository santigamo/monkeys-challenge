package com.monkeys.challenge.customer.infrastructure.rest.update;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.shared.ChallengeApplication;
import org.junit.jupiter.api.AfterEach;
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
class ITPatchUpdateCustomerControllerTest  extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void should_update_customer_successfully() throws Exception {
        // Given - Logged user and saved customer
        loggedUser(mvc);
        givenSavedCustomer(customerRepository);
        String updateBody = """
                {
                    "name": "New name"
                }
                """;

        // When - Request is made
        // Then - Customer is updated
        mvc.perform(patch("/customers/" + CUSTOMER_ID)
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .contentType("application/json")
                .content(updateBody))
                .andExpect(status().isOk());
    }
}
