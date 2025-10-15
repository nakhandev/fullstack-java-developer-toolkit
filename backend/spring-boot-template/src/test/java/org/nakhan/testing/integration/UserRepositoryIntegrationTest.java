package org.nakhan.testing.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.nakhan.entity.User;
import org.nakhan.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

/**
 * Integration test example using Testcontainers with PostgreSQL.
 * This class demonstrates how to test database operations with real containers.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
@SpringBootTest
@Testcontainers
@DisplayName("UserRepository Integration Tests with Testcontainers")
class UserRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test")
        .withReuse(true);

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @Test
    @DisplayName("Should save and retrieve user successfully")
    @Transactional
    void shouldSaveAndRetrieveUserSuccessfully() {
        // Given
        User user = new User();
        user.setUsername("integrationtest");
        user.setEmail("integration@test.com");
        user.setPassword("password123");
        user.setFirstName("Integration");
        user.setLastName("Test");
        user.setActive(true);

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("integrationtest");
        assertThat(savedUser.getEmail()).isEqualTo("integration@test.com");
        assertThat(savedUser.getActive()).isTrue();

        // Verify we can retrieve it
        Optional<User> retrievedUser = userRepository.findById(savedUser.getId());
        assertThat(retrievedUser).isPresent();
        assertThat(retrievedUser.get().getUsername()).isEqualTo("integrationtest");
    }

    @Test
    @DisplayName("Should find user by username")
    @Transactional
    void shouldFindUserByUsername() {
        // Given
        User user = new User();
        user.setUsername("testusername");
        user.setEmail("test@email.com");
        user.setPassword("password123");
        user.setActive(true);

        User savedUser = userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByUsername("testusername");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.get().getUsername()).isEqualTo("testusername");
    }

    @Test
    @DisplayName("Should find user by email")
    @Transactional
    void shouldFindUserByEmail() {
        // Given
        User user = new User();
        user.setUsername("emailtest");
        user.setEmail("findbyemail@test.com");
        user.setPassword("password123");
        user.setActive(true);

        User savedUser = userRepository.save(user);

        // When
        Optional<User> foundUser = userRepository.findByEmail("findbyemail@test.com");

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.get().getEmail()).isEqualTo("findbyemail@test.com");
    }

    @Test
    @DisplayName("Should find users by active status")
    @Transactional
    void shouldFindUsersByActiveStatus() {
        // Given
        User activeUser = new User();
        activeUser.setUsername("activeuser");
        activeUser.setEmail("active@test.com");
        activeUser.setPassword("password123");
        activeUser.setActive(true);

        User inactiveUser = new User();
        inactiveUser.setUsername("inactiveuser");
        inactiveUser.setEmail("inactive@test.com");
        inactiveUser.setPassword("password123");
        inactiveUser.setActive(false);

        userRepository.save(activeUser);
        userRepository.save(inactiveUser);

        // When
        List<User> activeUsers = userRepository.findByActive(true);
        List<User> inactiveUsers = userRepository.findByActive(false);

        // Then
        assertThat(activeUsers).hasSize(1);
        assertThat(activeUsers.get(0).getUsername()).isEqualTo("activeuser");

        assertThat(inactiveUsers).hasSize(1);
        assertThat(inactiveUsers.get(0).getUsername()).isEqualTo("inactiveuser");
    }

    @Test
    @DisplayName("Should check if username exists")
    @Transactional
    void shouldCheckIfUsernameExists() {
        // Given
        User user = new User();
        user.setUsername("existstest");
        user.setEmail("exists@test.com");
        user.setPassword("password123");
        user.setActive(true);

        userRepository.save(user);

        // When & Then
        assertThat(userRepository.existsByUsername("existstest")).isTrue();
        assertThat(userRepository.existsByUsername("nonexistent")).isFalse();
    }

    @Test
    @DisplayName("Should check if email exists")
    @Transactional
    void shouldCheckIfEmailExists() {
        // Given
        User user = new User();
        user.setUsername("emailcheck");
        user.setEmail("emailcheck@test.com");
        user.setPassword("password123");
        user.setActive(true);

        userRepository.save(user);

        // When & Then
        assertThat(userRepository.existsByEmail("emailcheck@test.com")).isTrue();
        assertThat(userRepository.existsByEmail("nonexistent@test.com")).isFalse();
    }

    @Test
    @DisplayName("Should search users by first name")
    @Transactional
    void shouldSearchUsersByFirstName() {
        // Given
        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@test.com");
        user1.setPassword("password123");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setActive(true);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@test.com");
        user2.setPassword("password123");
        user2.setFirstName("Johnny");
        user2.setLastName("Smith");
        user2.setActive(true);

        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<User> johnUsers = userRepository.findByFirstNameContainingIgnoreCase("John");

        // Then
        assertThat(johnUsers).hasSize(2);
        assertThat(johnUsers).extracting("username").contains("user1", "user2");
    }

    @Test
    @DisplayName("Should count users by active status")
    @Transactional
    void shouldCountUsersByActiveStatus() {
        // Given
        User activeUser1 = new User();
        activeUser1.setUsername("active1");
        activeUser1.setEmail("active1@test.com");
        activeUser1.setPassword("password123");
        activeUser1.setActive(true);

        User activeUser2 = new User();
        activeUser2.setUsername("active2");
        activeUser2.setEmail("active2@test.com");
        activeUser2.setPassword("password123");
        activeUser2.setActive(true);

        User inactiveUser = new User();
        inactiveUser.setUsername("inactive");
        inactiveUser.setEmail("inactive@test.com");
        inactiveUser.setPassword("password123");
        inactiveUser.setActive(false);

        userRepository.save(activeUser1);
        userRepository.save(activeUser2);
        userRepository.save(inactiveUser);

        // When
        long activeCount = userRepository.countByActive(true);
        long inactiveCount = userRepository.countByActive(false);

        // Then
        assertThat(activeCount).isEqualTo(2);
        assertThat(inactiveCount).isEqualTo(1);
    }

    @Test
    @DisplayName("Should delete user successfully")
    @Transactional
    void shouldDeleteUserSuccessfully() {
        // Given
        User user = new User();
        user.setUsername("deletetest");
        user.setEmail("delete@test.com");
        user.setPassword("password123");
        user.setActive(true);

        User savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        // Verify user exists
        assertThat(userRepository.existsById(userId)).isTrue();

        // When
        userRepository.deleteById(userId);

        // Then
        assertThat(userRepository.existsById(userId)).isFalse();
        assertThat(userRepository.findById(userId)).isEmpty();
    }

    @Test
    @DisplayName("Should handle custom query with parameters")
    @Transactional
    void shouldHandleCustomQueryWithParameters() {
        // Given
        User user = new User();
        user.setUsername("querytest");
        user.setEmail("query@test.com");
        user.setPassword("password123");
        user.setActive(true);

        userRepository.save(user);

        // When
        List<User> results = userRepository.findByUsernameOrEmail("querytest", "query@test.com");

        // Then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUsername()).isEqualTo("querytest");
    }
}
