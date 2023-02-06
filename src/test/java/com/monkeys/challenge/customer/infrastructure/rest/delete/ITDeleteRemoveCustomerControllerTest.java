package com.monkeys.challenge.customer.infrastructure.rest.delete;

import com.monkeys.challenge.BaseTest;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import com.monkeys.challenge.customer.domain.exceptions.CustomerNotFoundException;
import com.monkeys.challenge.shared.ChallengeApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class ITDeleteRemoveCustomerControllerTest  extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    void should_delete_customer_successfully()  throws Exception {
        // Given - Logged user and saved customer
        loggedUser(mvc);
        givenSavedCustomer(customerRepository);

        // When - Request is made
        // Then - Customer is deleted
        mvc.perform(
                delete("/customers/{customerId}", CUSTOMER_ID)
                        .header("Authorization", "Bearer " + AUTH_TOKEN)
                        .contentType("application/json")
                )
                .andExpect(status().isNoContent());

        assertThrows(CustomerNotFoundException.class, () -> customerRepository.findById(CUSTOMER_ID));
    }
}
