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

    //    ------------------------------------------------- insert User
    @Test
    void saveNullUser() {
        assertThrows(ExeptionHandler.class, () -> userService.insertUser(null));
    }

    @Test
    void CreateUser() {
        // 1. Setup/Arrange
        User user = new User(1, "John", "john.doe@example.com", "password123");

        // 2. Mocking repository methods
        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.insertUser(user)).thenReturn(user);
        when(userRepository.getAllUsers()).thenReturn(Collections.singletonList(user));

        // 3. Act
        User insertUser = userService.insertUser(user);

        // 4. Assert
        assertNotNull(insertUser);
        assertEquals("john.doe@example.com", insertUser.getEmail());

        // 5. Verify repository methods were called
        verify(userRepository).insertUser(user);
        verify(userRepository).getAllUsers();
    }

    @Test
    void nullEmail() {
        // Arrange
        User user = new User(1, "John", null, "password123");
        // Act & Assert
        assertThrows(ExeptionHandler.class, () -> userService.insertUser(user));
    }

    @Test
    void emptyEmail() {
        User user = new User(1, "John", "", "password123");
        assertThrows(ExeptionHandler.class, () -> userService.insertUser(user));
    }

    @Test
    void emptyName() {
        User user = new User(1, null, "john.doe@example.com", "password123");  // Empty email here
        assertThrows(ExeptionHandler.class, () -> userService.insertUser(user));
    }

    @Test
    void emptyPassword() {
        User user = new User(1, "John", "john.doe@example.com", null);  // Empty email here
        assertThrows(ExeptionHandler.class, () -> userService.insertUser(user));
    }

    @Test
    void testDuplicateEmail() {
        // Arrange
        String email = "john.doe@example.com";
        User existingUser = new User(1, "John", email, "password123");
        User newUser = new User(2, "Jane", email, "password456");

        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        assertThrows(ExeptionHandler.class, () -> userService.insertUser(newUser));
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
        Token expectedToken = new Token();
        expectedToken.setUser(user);

//        Mok ---------------------------------------------------------------
        when(userRepository.insertUser(user)).thenReturn(user);
        when(userRepository.getAllUsers()).thenReturn(Collections.singletonList(user));
        when(tokenService.save(any(Token.class))).thenReturn(expectedToken);
//---------------------------------------------------------------------------
//        Act ---------------------------------------------------------------
        userService.insertUser(user);
//---------------------------------------------------------------------------
        // Assert
        verify(userRepository, times(1)).insertUser(user);
        ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);
        verify(tokenService, times(1)).save(tokenCaptor.capture());

        Token capturedToken = tokenCaptor.getValue();
        assertEquals(expectedToken.getUser(), capturedToken.getUser());
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

    //    ------------------------------------------------- delete User
    @Test
    void foundUserWithNullId() {
        int id = 0;
        assertThrows(ExeptionHandler.class, () -> userService.findById(id));
    }

    @Test
    void userIdNull() {
        User user = new User(0, "John", "john.doe@example.com", "password123");
        assertThrows(ExeptionHandler.class, () -> userService.deleteUser(user.getId()));
    }

    @Test
    void deleteUser() {
        User user = new User(1, "testuser", "test@example.com", "password123");
        when(userRepository.deleteUser(user.getId())).thenReturn(true);
        boolean result = userService.deleteUser(user.getId());
        assertTrue(result);
        verify(userRepository, times(1)).deleteUser(user.getId());
    }

    //    ------------------------------------------------- update User
    @Test
    void updateUser() {
        // 1. Setup/Arrange
        User user = new User(1, "John", "john.doe@example.com", "password123");

        // 2. Mocking repository methods
        when(userRepository.findById(user.getId())).thenReturn(user);
        when(userRepository.updateUser(user)).thenReturn(user);

        // 3. Act
        User updateUser = userService.updateUser(user);

        // 4. Assert
        assertNotNull(updateUser);
        assertEquals("john.doe@example.com", updateUser.getEmail());
        assertEquals("John", updateUser.getUsername());
        assertEquals("password123", updateUser.getPassword());

        // 5. Verify repository methods were called
        verify(userRepository).updateUser(user);
    }

}
