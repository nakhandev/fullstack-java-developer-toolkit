import { useState } from 'react'
import UserService from './services/UserService'
import UserForm from './components/UserForm'
import UserList from './components/UserList'

/**
 * Main App component for the React + Vite + TailwindCSS template.
 * This component demonstrates a complete user management interface.
 *
 * @author Full Stack Java Developer Toolkit
 * @version 1.0.0
 */
function App() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [showForm, setShowForm] = useState(false)

  // Fetch all users
  const fetchUsers = async () => {
    setLoading(true)
    setError(null)
    try {
      const response = await UserService.getAllUsers()
      setUsers(response.data)
    } catch (err) {
      setError('Failed to fetch users')
      console.error('Error fetching users:', err)
    } finally {
      setLoading(false)
    }
  }

  // Handle user creation
  const handleUserCreate = async (userData) => {
    try {
      await UserService.createUser(userData)
      setShowForm(false)
      fetchUsers() // Refresh the list
    } catch (err) {
      setError('Failed to create user')
      console.error('Error creating user:', err)
    }
  }

  // Handle user deletion
  const handleUserDelete = async (userId) => {
    try {
      await UserService.deleteUser(userId)
      fetchUsers() // Refresh the list
    } catch (err) {
      setError('Failed to delete user')
      console.error('Error deleting user:', err)
    }
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-6">
            <div>
              <h1 className="text-3xl font-bold text-gray-900">
                🧩 Full Stack Java Toolkit
              </h1>
              <p className="text-gray-600 mt-1">
                React + Vite + TailwindCSS Template
              </p>
            </div>
            <div className="flex space-x-4">
              <button
                onClick={fetchUsers}
                disabled={loading}
                className="btn-primary"
              >
                {loading ? 'Loading...' : 'Refresh Users'}
              </button>
              <button
                onClick={() => setShowForm(!showForm)}
                className="btn-secondary"
              >
                {showForm ? 'Cancel' : 'Add User'}
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Error Message */}
        {error && (
          <div className="mb-6 bg-red-50 border border-red-200 rounded-lg p-4">
            <p className="text-red-800">{error}</p>
          </div>
        )}

        {/* User Form */}
        {showForm && (
          <div className="mb-8">
            <UserForm onSubmit={handleUserCreate} />
          </div>
        )}

        {/* User List */}
        <div className="card">
          <h2 className="text-xl font-semibold text-gray-900 mb-6">
            Users ({users.length})
          </h2>
          <UserList
            users={users}
            loading={loading}
            onDelete={handleUserDelete}
          />
        </div>

        {/* API Information */}
        <div className="mt-8 card">
          <h3 className="text-lg font-medium text-gray-900 mb-4">
            🚀 API Integration
          </h3>
          <div className="bg-gray-50 rounded-lg p-4">
            <p className="text-sm text-gray-600 mb-2">
              This frontend is configured to work with the Spring Boot backend:
            </p>
            <ul className="text-sm text-gray-600 space-y-1">
              <li>• API Base URL: <code className="bg-white px-2 py-1 rounded">/api</code></li>
              <li>• Proxy to: <code className="bg-white px-2 py-1 rounded">http://localhost:8080</code></li>
              <li>• Axios configured for HTTP requests</li>
              <li>• Error handling and loading states</li>
            </ul>
          </div>
        </div>

        {/* Features Demo */}
        <div className="mt-8 card">
          <h3 className="text-lg font-medium text-gray-900 mb-4">
            ✨ Features Demonstrated
          </h3>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            <div className="bg-blue-50 rounded-lg p-4">
              <h4 className="font-medium text-blue-900">React 18</h4>
              <p className="text-sm text-blue-700 mt-1">Modern React with hooks</p>
            </div>
            <div className="bg-green-50 rounded-lg p-4">
              <h4 className="font-medium text-green-900">Vite</h4>
              <p className="text-sm text-green-700 mt-1">Fast build tool & dev server</p>
            </div>
            <div className="bg-purple-50 rounded-lg p-4">
              <h4 className="font-medium text-purple-900">TailwindCSS</h4>
              <p className="text-sm text-purple-700 mt-1">Utility-first CSS framework</p>
            </div>
            <div className="bg-yellow-50 rounded-lg p-4">
              <h4 className="font-medium text-yellow-900">Axios</h4>
              <p className="text-sm text-yellow-700 mt-1">HTTP client for API calls</p>
            </div>
            <div className="bg-indigo-50 rounded-lg p-4">
              <h4 className="font-medium text-indigo-900">Component Architecture</h4>
              <p className="text-sm text-indigo-700 mt-1">Reusable UI components</p>
            </div>
            <div className="bg-pink-50 rounded-lg p-4">
              <h4 className="font-medium text-pink-900">State Management</h4>
              <p className="text-sm text-pink-700 mt-1">React useState hooks</p>
            </div>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-white border-t mt-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="text-center text-gray-600">
            <p className="text-sm">
              Built with ❤️ using the{' '}
              <span className="font-medium text-gray-900">
                Full Stack Java Developer Toolkit
              </span>
            </p>
            <p className="text-xs mt-2">
              Ready for production with Spring Boot backend integration
            </p>
          </div>
        </div>
      </footer>
    </div>
  )
}

export default App
