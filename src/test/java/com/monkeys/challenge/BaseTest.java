package com.monkeys.challenge;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkeys.challenge.customer.domain.Customer;
import com.monkeys.challenge.customer.domain.CustomerRepository;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.config.http.MatcherType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class BaseTest {

    protected static final String CUSTOMER_ID = "caebae03-3ee9-4aef-b041-21a400fa1bb7";
    protected static final String CUSTOMER_NAME = "John";
    protected static final String CUSTOMER_SURNAME = "Doe";
    protected static final String CUSTOMER_AVATAR = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?f=y&d=mm";
    protected static final String CREATOR_USER = "creatorUser";
    protected final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    protected String AUTH_TOKEN;

    private void setAuthToken(String token) {
        this.AUTH_TOKEN = token;
    }

    protected void loggedUser(MockMvc mvc) throws Exception {
        if (AUTH_TOKEN != null) {
            return;
        }

        var response = mvc.perform(post("/login")
                .contentType("application/json")
                .content("{\"username\":\"test@email.com\",\"password\":\"Password!\"}")).andReturn();

        var token = mapper.readTree(response.getResponse().getContentAsString()).get("access_token").asText();
        setAuthToken(token);
    }

    protected void givenSavedCustomer(CustomerRepository repository) {
        var customer = new Customer(CUSTOMER_ID, CUSTOMER_NAME, CUSTOMER_SURNAME, CUSTOMER_AVATAR);
        var userId = repository.save(customer, CREATOR_USER);
    }

    protected void givenFixedUUID(String fixedUUID) {
        var uuid = UUID.fromString(fixedUUID);
        try (MockedStatic<UUID> uuidMockedStatic = Mockito.mockStatic(UUID.class)) {
            uuidMockedStatic.when(UUID::randomUUID).thenReturn(uuid);
        }
    }
}