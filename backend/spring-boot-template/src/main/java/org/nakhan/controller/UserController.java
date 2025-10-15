package org.nakhan.controller;

import org.nakhan.dto.UserDto;
import org.nakhan.entity.User;
import org.nakhan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for User operations.
 * This controller demonstrates standard REST API patterns with proper HTTP methods and status codes.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Configure appropriately for production
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param userDto the user data to create
     * @return ResponseEntity with created user
     */
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setActive(userDto.getActive());

            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Get user by ID.
     *
     * @param id the user ID
     * @return ResponseEntity with user data
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user by username.
     *
     * @param username the username
     * @return ResponseEntity with user data
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user by email.
     *
     * @param email the email address
     * @return ResponseEntity with user data
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get all users.
     *
     * @return ResponseEntity with list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Get active users only.
     *
     * @return ResponseEntity with list of active users
     */
    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userService.getActiveUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Search users by first name.
     *
     * @param firstName the first name to search for
     * @return ResponseEntity with matching users
     */
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByFirstName(@RequestParam String firstName) {
        List<User> users = userService.searchUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    /**
     * Update user.
     *
     * @param id the user ID
     * @param userDto the updated user data
     * @return ResponseEntity with updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        try {
            User userDetails = new User();
            userDetails.setUsername(userDto.getUsername());
            userDetails.setEmail(userDto.getEmail());
            userDetails.setFirstName(userDto.getFirstName());
            userDetails.setLastName(userDto.getLastName());
            userDetails.setActive(userDto.getActive());

            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete user.
     *
     * @param id the user ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Activate user.
     *
     * @param id the user ID
     * @return ResponseEntity with activated user
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable Long id) {
        try {
            User user = userService.activateUser(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deactivate user.
     *
     * @param id the user ID
     * @return ResponseEntity with deactivated user
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable Long id) {
        try {
            User user = userService.deactivateUser(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get user count by status.
     *
     * @param active the active status
     * @return ResponseEntity with user count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getUserCount(@RequestParam Boolean active) {
        long count = userService.getUserCountByStatus(active);
        return ResponseEntity.ok(count);
    }
}
