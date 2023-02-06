package com.monkeys.challenge.customer.infrastructure.rest.find;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.shared.ChallengeApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class ITGetCustomerByIdControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void should_fail_with_status_code_401() throws Exception {
        // Given
        //? Not authenticated user

        // When - Controller is called
        // Then - Status code 401 is returned
        mvc.perform(get("/customers/" + CUSTOMER_ID)
                .contentType("application/json"))
                .andExpect(status().is(401));
    }

    @Test
    void should_return_saved_customer() throws Exception {
        // Given - Logged user and saved customer
        loggedUser(mvc);
        givenSavedCustomer(customerRepository);

        // When - Request is made
        // Then - Customer is returned
        mvc.perform(
                get("/customers/" + CUSTOMER_ID)
                        .header("Authorization", "Bearer " + AUTH_TOKEN)
                        .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(CUSTOMER_ID));

        customerRepository.deleteAll();
    }

    @Test
    void should_fail_with_not_found() throws Exception {
        // Given - Logged user
        loggedUser(mvc);

        // When - Request is made
        // Then - Customer Not Found
        mvc.perform(
                get("/customers/" + CUSTOMER_ID)
                        .header("Authorization", "Bearer " + AUTH_TOKEN)
                        .contentType("application/json")
                )
                .andExpect(status().isNotFound());
    }
}
