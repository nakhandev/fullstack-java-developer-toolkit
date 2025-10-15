import axios from 'axios'

// Create axios instance with base configuration
const apiClient = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

/**
 * User service for handling API communications.
 * This service demonstrates how to interact with the Spring Boot backend.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
const UserService = {
  /**
   * Get all users
   */
  async getAllUsers() {
    return await apiClient.get('/users')
  },

  /**
   * Get user by ID
   */
  async getUserById(id) {
    return await apiClient.get(`/users/${id}`)
  },

  /**
   * Get user by username
   */
  async getUserByUsername(username) {
    return await apiClient.get(`/users/username/${username}`)
  },

  /**
   * Get user by email
   */
  async getUserByEmail(email) {
    return await apiClient.get(`/users/email/${email}`)
  },

  /**
   * Create new user
   */
  async createUser(userData) {
    return await apiClient.post('/users', userData)
  },

  /**
   * Update user
   */
  async updateUser(id, userData) {
    return await apiClient.put(`/users/${id}`, userData)
  },

  /**
   * Delete user
   */
  async deleteUser(id) {
    return await apiClient.delete(`/users/${id}`)
  },

  /**
   * Activate user
   */
  async activateUser(id) {
    return await apiClient.patch(`/users/${id}/activate`)
  },

  /**
   * Deactivate user
   */
  async deactivateUser(id) {
    return await apiClient.patch(`/users/${id}/deactivate`)
  },

  /**
   * Search users by first name
   */
  async searchUsers(firstName) {
    return await apiClient.get('/users/search', {
      params: { firstName }
    })
  },

  /**
   * Get user count by status
   */
  async getUserCount(active) {
    return await apiClient.get('/users/count', {
      params: { active }
    })
  }
}

export default UserService
