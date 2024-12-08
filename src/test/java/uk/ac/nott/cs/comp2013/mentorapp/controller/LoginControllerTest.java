package uk.ac.nott.cs.comp2013.mentorapp.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.nott.cs.comp2013.mentorapp.model.Repository;
import uk.ac.nott.cs.comp2013.mentorapp.model.user.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private LoginController loginController;

    @Mock
    private Repository<User, String> mockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(mockRepository);
    }

    @Test
    public void testOnLoginClick_UserExistsAndPasswordMatches() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);
        when(mockUser.getPassword()).thenReturn(password);

        when(mockRepository.selectById(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = loginController.onLoginClick(username, password);

        // Assert
        assertTrue(result);
        verify(mockRepository, times(1)).selectById(username);
    }

    @Test
    public void testOnLoginClick_UserDoesNotExist() {
        // Arrange
        String username = "nonexistentuser";
        String password = "password123";

        when(mockRepository.selectById(username)).thenReturn(Optional.empty());

        // Act
        boolean result = loginController.onLoginClick(username, password);

        // Assert
        assertFalse(result);
        verify(mockRepository, times(1)).selectById(username);
    }

    @Test
    public void testOnLoginClick_PasswordDoesNotMatch() {
        // Arrange
        String username = "testuser";
        String correctPassword = "password123";
        String wrongPassword = "wrongpassword";
        User mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn(username);
        when(mockUser.getPassword()).thenReturn(correctPassword);

        when(mockRepository.selectById(username)).thenReturn(Optional.of(mockUser));

        // Act
        boolean result = loginController.onLoginClick(username, wrongPassword);

        // Assert
        assertFalse(result);
        verify(mockRepository, times(1)).selectById(username);
    }
}