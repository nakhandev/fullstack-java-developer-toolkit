package org.nakhan.repository;

import org.nakhan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 * This interface demonstrates basic CRUD operations and custom query methods.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username.
     *
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by email address.
     *
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find users by active status.
     *
     * @param active the active status
     * @return list of users with the specified active status
     */
    List<User> findByActive(Boolean active);

    /**
     * Find users by first name containing (case-insensitive).
     *
     * @param firstName the first name to search for
     * @return list of users with matching first name
     */
    List<User> findByFirstNameContainingIgnoreCase(String firstName);

    /**
     * Check if username exists.
     *
     * @param username the username to check
     * @return true if username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists.
     *
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Custom query to find users by username or email.
     *
     * @param username the username to search for
     * @param email the email to search for
     * @return list of users matching either username or email
     */
    @Query("SELECT u FROM User u WHERE u.username = :username OR u.email = :email")
    List<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    /**
     * Count users by active status.
     *
     * @param active the active status
     * @return number of users with the specified active status
     */
    long countByActive(Boolean active);
}
