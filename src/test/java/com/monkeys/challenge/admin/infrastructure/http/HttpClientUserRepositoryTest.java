package com.monkeys.challenge.admin.infrastructure.http;

import com.monkeys.challenge.admin.domain.UserRepository;
import com.monkeys.challenge.admin.domain.UserRole;
import com.monkeys.challenge.admin.domain.exceptions.*;
import com.monkeys.challenge.admin.infrastructure.rest.find.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class HttpClientUserRepositoryTest {

    public static final String BAD_REQUEST_RESPONSE = """
            {
                "statusCode": 400,
                "error": "Bad Request",
                "message": "User not found"
            }
            """;
    public static final String USER_JSON = """
            {
                "user_id": "123-456-789",
                "name": "John",
                "username": "Doe",
                "email": "johnd@email.com",
                "created_at": "test",
                "last_login": "test"
            }
            """;
    UserRepository userRepository;
    HttpResponse<String> response;

    @BeforeEach
    void setUp() {
        userRepository = new TestableHttpClientUserRepository();
        response = mock(HttpResponse.class);
    }

    @Test
    void should_create_user_successfully() throws IOException, InterruptedException {
        // Given - an email, username and password
        var email = "test@email.com";
        var username = "John";
        var password = "password123";

        when(response.statusCode()).thenReturn(201);
        when(response.body()).thenReturn(USER_JSON);
        // When/Then - the user is created not throwing an exception
        assertDoesNotThrow(() -> userRepository.createUser(email, username, password));
    }

    @Test
    void should_fail_creating_user() throws IOException, InterruptedException {
        // Given - an email, username and password
        var email = "test@email.com";
        var username = "John";
        var password = "password123";
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When/Then - throws an UserCreationException
        assertThrows(UserCreationException.class, () -> userRepository.createUser(email, username, password));
    }

    @Test
    void should_delete_user_successfully() {
        // Given - An user id to remove
        var userId = "123-456-789";
        when(response.statusCode()).thenReturn(204);

        // When/Then - the user is created not throwing an exception
        assertDoesNotThrow(() -> userRepository.delete(userId));
    }

    @Test
    void should_fail_deleting_user() {
        // Given - An user id to remove
        var userId = "123-456-789";

        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When/Then - throws an UserRemoveException
        assertThrows(UserRemoveException.class, () -> userRepository.delete(userId));
    }


    @Test
    void should_list_users() {
        // Given
        List<User> expectedList = List.of(
                new User("123-456-789", "John", "Doe", "johnd@email.com", "test", "test")
        );

        when(response.body()).thenReturn("""
                [
                        %s
                ]
                """.formatted(USER_JSON));


        // When
        var actualList = userRepository.findAll();

        // Then
        assertFalse(actualList.isEmpty());
        assertEquals(expectedList, actualList);
    }

    @Test
    void should_fail_listing_users() {
        // Given - a wrong formatted response
        when(response.body()).thenReturn("""
                {
                    "data": [
                        {
                            "id": "123-456-789",
                            "firstName": "John",
                            "lastName": "Doe",
                            "email": "john@email.com",
                            "created_at": "",
                            "last_login": ""
                         }
                    ]
                }
                """);


        // When - the list is requested
        // Then - a generic error is thrown
        assertThrows(GenericError.class, () -> userRepository.findAll());
    }

    @Test
    void should_update_user_successfully() {
        // Given - An user id, email, username and password
        var userId = "123-456-789";
        var name = "John";
        var username = "JohnDoe";
        User expectedResponse = new User("123-456-789",
                                    "John",
                                    "Doe",
                                    "johnd@email.com",
                                    "test",
                                    "test");
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn(USER_JSON);
        
        // When - The user is updated
        var actualResponse = userRepository.updateUser(userId, name, username);
        
        // Then -
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_add_role_successfully() {
        // Given - An userId and roleId
        var userId = "123-456-789";
        var roleId = "987-654";
        when(response.statusCode()).thenReturn(204);

        // When - The role is added
        // Then - No exception is thrown
        assertDoesNotThrow(() -> userRepository.addRole(userId, roleId));
    }

    @Test
    void should_fail_adding_role() {
        // Given - An userId and roleId
        var userId = "123-456-789";
        var roleId = "987-654";
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When - The role is added
        // Then - A GenericError is thrown
        assertThrows(GenericError.class, () -> userRepository.addRole(userId, roleId));
    }

    @Test
    void should_remove_role_successfully() {
        // Given - An userId and roleId
        var userId = "123-456-789";
        var roleId = "987-654";
        when(response.statusCode()).thenReturn(204);

        // When - The role is removed
        // Then - No exception is thrown
        assertDoesNotThrow(() -> userRepository.removeRole(userId, roleId));
    }

    @Test
    void should_fail_removing_role() {
        // Given - An userId and roleId
        var userId = "123-456-789";
        var roleId = "987-654";
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When - The role is removed
        // Then - A GenericError is thrown
        assertThrows(GenericError.class, () -> userRepository.removeRole(userId, roleId));
    }

    @Test
    void should_fail_updating_user_if_status_code_not_200() {
        // Given - An user id, email, username and password
        var userId = "123-456-789";
        var name = "John";
        var username = "JohnDoe";
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When - The user is updated
        // Then - A UserUpdateException is thrown
        assertThrows(UserUpdateException.class, () -> userRepository.updateUser(userId, name, username));
    }

    @Test
    void should_fail_updating_user_if_response_wrong_formatted() {
        // Given - An user id, email, username and password
        var userId = "123-456-789";
        var name = "John";
        var username = "JohnDoe";
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When - The user is updated
        // Then - A UserUpdateException is thrown
        assertThrows(GenericError.class, () -> userRepository.updateUser(userId, name, username));
    }

    @Test
    void should_get_user_roles() {
        // Given - An user id
        var userId = "123-456-789";
        List<UserRole> expectedRoles = List.of(new UserRole("123", "admin", "admin role"));
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
                [
                    {
                        "id": "123",
                        "name": "admin",
                        "description": "admin role"
                    }
                ]
                """);

        // When - The user roles are requested
        var actualRoles = userRepository.getUserRoles(userId);

        // Then - The roles are returned
        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void should_fail_getting_user_roles_if_status_code_not_200() {
        // Given - An user id
        var userId = "123-456-789";
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When - The user roles are requested
        // Then - A UserRolesException is thrown
        assertThrows(GenericError.class, () -> userRepository.getUserRoles(userId));
    }

    @Test
    void should_do_login_successfully() {
        // Given - An email and password
        var email = "johnd@email.com";
        var password = "password123";
        var expectedResponse = """
                {
                    "access_token": "token",
                    "expires_in": 86400,
                    "token_type": "Bearer"
                }
                """;
        when(response.statusCode()).thenReturn(200);
        when(response.body()).thenReturn("""
                {
                    "access_token": "token",
                    "expires_in": 86400,
                    "token_type": "Bearer"
                }
                """);

        // When - doLoginWithPassword is called
        var actualResponse = userRepository.doLoginWithPassword(email, password);

        // Then
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void should_fail_doing_login() {
        // Given - An email and password
        var email = "johnd@email.com";
        var password = "password123";
        var tokenResponse = """
                
                """;
        when(response.statusCode()).thenReturn(400);
        when(response.body()).thenReturn(BAD_REQUEST_RESPONSE);

        // When - doLoginWithPassword is called
        // Then - a UserLoginException is thrown
        assertThrows(UserAuthenticationException.class, () -> userRepository.doLoginWithPassword(email, password));

    }

    public class TestableHttpClientUserRepository extends HttpClientUserRepository {

        public TestableHttpClientUserRepository() {
            this.setIssuer("https://test-api.com/");
            this.setAudience("https://test-api.com/");
            this.setClientId("cliendId-test");
            this.setClientSecret("clientSecret-test");
        }

        @Override
        protected HttpResponse<String> sendRequest(HttpRequest request) {
            return response;
        }

        @Override
        protected String getManagementToken() {
            return "token";
        }
    }
}