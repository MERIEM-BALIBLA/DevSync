package org.example.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.model.Token;
import org.example.model.User;
import org.example.repository.implementation.UserRepository;
import org.example.util.ExeptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
    }

    @Test
    void getAllUsersTest() {
        // Arrange
        User user1 = new User(1, "John Doe");
        User user2 = new User(2, "Jane Doe");
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(userRepository.getAllUsers()).thenReturn(expectedUsers); // Mock the repository method

        // Act
        List<User> actualUsers = userService.getAll(); // Call the service method

        // Assert
        assertNotNull(actualUsers); // Ensure the result is not null
        assertEquals(2, actualUsers.size()); // Check that we have 2 users
        assertEquals(expectedUsers, actualUsers); // Check that the returned list matches the expected list
        verify(userRepository).getAllUsers(); // Verify that the repository method was called
    }

    @Test
    void saveUser_WhenUserIsNull() {
        assertThrows(ExeptionHandler.class, () -> userService.insertUser(null));
    }

    @Test
    void testInsertUserCreatesTokenAndValidatesData() {
        User user = new User(1, "John", "john.doe@example.com", "john.doe@example.com");

        // Mock the insert
        when(userRepository.insertUser(user)).thenReturn(user);

        when(userRepository.getAllUsers()).thenReturn(Collections.singletonList(user));

        User insertedUser = userService.insertUser(user);

        verify(userRepository, times(1)).insertUser(user);

        ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
        verify(tokenService, times(1)).save(tokenCaptor.capture());

        Token capturedToken = tokenCaptor.getValue();

        assertEquals(1, userService.getAll().size(), "the new size of table should be 1");
    }

    @Test
    void testBasicUserInsertion() {
        // Arrange
        User user = new User(1, "John", "john.doe@example.com", "password123");
        when(userRepository.insertUser(user)).thenReturn(user);
        when(userRepository.getAllUsers()).thenReturn(Collections.singletonList(user));

        // Act
        User insertedUser = userService.insertUser(user);

        // Assert
        verify(userRepository, times(1)).insertUser(user);
        assertEquals(user, insertedUser);
        assertEquals(1, userService.getAll().size());
    }

    @Test
    void testUserInsertionWithTokenGeneration() {
        // Arrange
        User user = new User(1, "John", "john.doe@example.com", "password123");

        // Create expected token with the user
        Token expectedToken = new Token();
        expectedToken.setUser(user);  // Set the user in the expected token

//        Mok ---------------------------------------------------------------
        when(userRepository.insertUser(user)).thenReturn(user);
        when(tokenService.save(any(Token.class))).thenReturn(expectedToken);
//---------------------------------------------------------------------------
//        Act ---------------------------------------------------------------
        userService.insertUser(user);
//---------------------------------------------------------------------------

        // Assert
        ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
        verify(tokenService, times(1)).save(tokenCaptor.capture());

        Token capturedToken = tokenCaptor.getValue();
        assertEquals(expectedToken.getUser(), capturedToken.getUser());
        System.out.println("User name: " + user.getUsername());
        System.out.println("Monthly Tokens: " + capturedToken.getMonthlyTokens());

        System.out.println("Daily Tokens: " + capturedToken.getMonthlyTokens());
    }

    /*@Test
    void testEmailValidation() {
        // Arrange
        String validEmail = "john.doe@example.com";
        String invalidEmail1 = "invalid.email";  // Missing @
        String invalidEmail2 = "@example.com";   // Missing local part
        String invalidEmail3 = "john.doe@";      // Missing domain
        String invalidEmail4 = "john.doe@.com";  // Missing domain part
        String invalidEmail5 = "john.doe@example";  // Missing top-level domain
        String invalidEmail6 = "john..doe@example.com";  // Double dots
        String invalidEmail7 = "john doe@example.com";   // Contains space

        // Valid email test
        User validUser = new User(1, "John", validEmail, "password123");
        when(userRepository.insertUser(validUser)).thenReturn(validUser);

        // Act & Assert
        assertDoesNotThrow(() -> userService.insertUser(validUser),
                "Valid email should not throw exception");

        // Invalid email tests
        User invalidUser1 = new User(2, "John", invalidEmail1, "password123");
        assertThrows(ExeptionHandler.class,
                () -> userService.insertUser(invalidUser1),
                "Email without @ should throw exception");

        User invalidUser2 = new User(3, "John", invalidEmail2, "password123");
        assertThrows(ExeptionHandler.class,
                () -> userService.insertUser(invalidUser2),
                "Email without local part should throw exception");

        User invalidUser3 = new User(4, "John", invalidEmail3, "password123");
        assertThrows(ExeptionHandler.class,
                () -> userService.insertUser(invalidUser3),
                "Email without domain should throw exception");

        // Verify that invalid emails don't trigger repository save
        verify(userRepository, never()).insertUser(invalidUser1);
        verify(userRepository, never()).insertUser(invalidUser2);
        verify(userRepository, never()).insertUser(invalidUser3);
    }*/

    @Test
    void testEmailNormalization() {
        // Arrange
        String upperCaseEmail = "JOHN.DOE@EXAMPLE.COM";
        String mixedCaseEmail = "John.Doe@Example.Com";
        String expectedEmail = "john.doe@example.com";

        User user1 = new User(1, "John", upperCaseEmail, "password123");
        User user2 = new User(2, "John", mixedCaseEmail, "password123");

        // Act & Assert
        when(userRepository.insertUser(any(User.class))).thenReturn(user1);

        userService.insertUser(user1);
        userService.insertUser(user2);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(2)).insertUser(userCaptor.capture());

        List<User> capturedUsers = userCaptor.getAllValues();
        assertEquals(expectedEmail, capturedUsers.get(0).getEmail(),
                "Upper case email should be normalized to lower case");
        assertEquals(expectedEmail, capturedUsers.get(1).getEmail(),
                "Mixed case email should be normalized to lower case");
    }

    @Test
    void testDuplicateEmail() {
        // Arrange
        String email = "john.doe@example.com";
        User existingUser = new User(1, "John", email, "password123");
        User newUser = new User(2, "Jane", email, "password456");

        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        // Act & Assert
        ExeptionHandler exception = assertThrows(ExeptionHandler.class, () -> userService.insertUser(newUser),
                "Inserting user with duplicate email should throw exception");

        assertEquals("Email " + email + " is already registered", exception.getMessage());
        verify(userRepository, never()).insertUser(newUser);
    }

    /*@Test
    void testEmailWithSpecialCharacters() {
        // Arrange
        // Valid special characters in email
        String validSpecialEmail1 = "user+label@example.com";
        String validSpecialEmail2 = "user.name-123@sub.example.com";

        User user1 = new User(1, "User1", validSpecialEmail1, "password123");
        User user2 = new User(2, "User2", validSpecialEmail2, "password123");

        when(userRepository.insertUser(any(User.class)))
                .thenReturn(user1)
                .thenReturn(user2);

        // Act & Assert
        assertDoesNotThrow(() -> userService.insertUser(user1),
                "Email with + should be valid");
        assertDoesNotThrow(() -> userService.insertUser(user2),
                "Email with dots and hyphens should be valid");

        verify(userRepository, times(1)).insertUser(user1);
        verify(userRepository, times(1)).insertUser(user2);
    }
*/
   /* @Test
    void testEmailLengthValidation() {
        // Arrange
        String tooLongLocalPart = "a".repeat(65) + "@example.com";  // Local part > 64 chars
        String tooLongDomain = "user@" + "a".repeat(256) + ".com";  // Total length > 255 chars

        User userLongLocal = new User(1, "User1", tooLongLocalPart, "password123");
        User userLongDomain = new User(2, "User2", tooLongDomain, "password123");

        // Act & Assert
        assertThrows(InvalidEmailException.class,
                () -> userService.insertUser(userLongLocal),
                "Email with too long local part should be invalid");

        assertThrows(InvalidEmailException.class,
                () -> userService.insertUser(userLongDomain),
                "Email with too long domain should be invalid");

        verify(userRepository, never()).insertUser(any(User.class));
    }*/

}
