package org.nakhan.testing.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nakhan.entity.User;
import org.nakhan.repository.UserRepository;
import org.nakhan.service.UserService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Example test class demonstrating Mockito usage with JUnit 5.
 * This class shows how to mock dependencies and test service layer logic.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Mockito Tests")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password123");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setActive(true);
    }

    @Test
    @DisplayName("Should create user successfully when username and email are unique")
    void shouldCreateUserSuccessfully() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.createUser(testUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUsername()).isEqualTo("testuser");

        // Verify interactions
        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void shouldThrowExceptionWhenUsernameExists() {
        // Given
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.createUser(testUser))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Username already exists");

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.createUser(testUser))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("Email already exists");

        verify(userRepository).existsByUsername("testuser");
        verify(userRepository).existsByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should get user by ID successfully")
    void shouldGetUserByIdSuccessfully() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // When
        Optional<User> result = userService.getUserById(1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getUsername()).isEqualTo("testuser");

        verify(userRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when user not found by ID")
    void shouldReturnEmptyWhenUserNotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<User> result = userService.getUserById(999L);

        // Then
        assertThat(result).isEmpty();
        verify(userRepository).findById(999L);
    }

    @Test
    @DisplayName("Should get all users successfully")
    void shouldGetAllUsersSuccessfully() {
        // Given
        List<User> users = Arrays.asList(testUser, createAnotherUser());
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<User> result = userService.getAllUsers();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(testUser);

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        // Given
        User updatedUser = new User();
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updated@example.com");
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setActive(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // When
        User result = userService.updateUser(1L, updatedUser);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("updateduser");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
        assertThat(result.getActive()).isFalse();

        verify(userRepository).findById(1L);
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent user")
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        User updatedUser = new User();
        updatedUser.setUsername("updateduser");

        // When & Then
        assertThatThrownBy(() -> userService.updateUser(999L, updatedUser))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("User not found with id: 999");

        verify(userRepository).findById(999L);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);

        // When
        userService.deleteUser(1L);

        // Then
        verify(userRepository).existsById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent user")
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        // Given
        when(userRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> userService.deleteUser(999L))
            .isInstanceOf(RuntimeException.class)
            .hasMessage("User not found with id: 999");

        verify(userRepository).existsById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should activate user successfully")
    void shouldActivateUserSuccessfully() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // When
        User result = userService.activateUser(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getActive()).isTrue();

        verify(userRepository).findById(1L);
        verify(userRepository).save(testUser);
    }

    @Test
    @DisplayName("Should search users by first name")
    void shouldSearchUsersByFirstName() {
        // Given
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findByFirstNameContainingIgnoreCase("Test")).thenReturn(users);

        // When
        List<User> result = userService.searchUsersByFirstName("Test");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).contains(testUser);

        verify(userRepository).findByFirstNameContainingIgnoreCase("Test");
    }

    @Test
    @DisplayName("Should get user count by status")
    void shouldGetUserCountByStatus() {
        // Given
        when(userRepository.countByActive(true)).thenReturn(5L);

        // When
        long result = userService.getUserCountByStatus(true);

        // Then
        assertThat(result).isEqualTo(5L);

        verify(userRepository).countByActive(true);
    }

    // Helper method to create another user for testing
    private User createAnotherUser() {
        User anotherUser = new User();
        anotherUser.setId(2L);
        anotherUser.setUsername("anotheruser");
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword("password123");
        anotherUser.setFirstName("Another");
        anotherUser.setLastName("User");
        anotherUser.setActive(true);
        return anotherUser;
    }
}
