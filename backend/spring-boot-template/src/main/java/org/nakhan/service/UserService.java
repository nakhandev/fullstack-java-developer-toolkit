package org.nakhan.service;

import org.nakhan.entity.User;
import org.nakhan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Service class for User entity operations.
 * This class demonstrates business logic layer with transaction management.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Create a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    /**
     * Get user by ID.
     *
     * @param id the user ID
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Get user by username.
     *
     * @param username the username
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Get user by email.
     *
     * @param email the email
     * @return Optional containing the user if found
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Get all users.
     *
     * @return list of all users
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get active users only.
     *
     * @return list of active users
     */
    @Transactional(readOnly = true)
    public List<User> getActiveUsers() {
        return userRepository.findByActive(true);
    }

    /**
     * Update user.
     *
     * @param id the user ID
     * @param userDetails the updated user details
     * @return the updated user
     */
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setActive(userDetails.getActive());

        return userRepository.save(user);
    }

    /**
     * Delete user by ID.
     *
     * @param id the user ID
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Deactivate user.
     *
     * @param id the user ID
     * @return the deactivated user
     */
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(false);
        return userRepository.save(user);
    }

    /**
     * Activate user.
     *
     * @param id the user ID
     * @return the activated user
     */
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(true);
        return userRepository.save(user);
    }

    /**
     * Search users by first name.
     *
     * @param firstName the first name to search for
     * @return list of users with matching first name
     */
    @Transactional(readOnly = true)
    public List<User> searchUsersByFirstName(String firstName) {
        return userRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    /**
     * Get user count by active status.
     *
     * @param active the active status
     * @return number of users with the specified status
     */
    @Transactional(readOnly = true)
    public long getUserCountByStatus(Boolean active) {
        return userRepository.countByActive(active);
    }
}
