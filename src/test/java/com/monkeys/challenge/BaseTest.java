package com.monkeys.challenge;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.mockStatic;

public class BaseTest {

    protected static final String CUSTOMER_ID = "caebae03-3ee9-4aef-b041-21a400fa1bb7";
    protected static final String CUSTOMER_NAME = "John";
    protected static final String CUSTOMER_SURNAME = "Doe";
    protected static final String CUSTOMER_AVATAR = "https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?f=y&d=mm";
    protected static final String CREATOR_USER = "creatorUser";

    protected void givenFixedUUID(String fixedUUID) {
        var uuid = UUID.fromString(fixedUUID);
        try (MockedStatic<UUID> uuidMockedStatic = Mockito.mockStatic(UUID.class)) {
            uuidMockedStatic.when(UUID::randomUUID).thenReturn(uuid);
        }
    }
}