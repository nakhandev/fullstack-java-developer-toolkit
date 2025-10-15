# 📋 API Guidelines

This document outlines the API design principles, patterns, and best practices used in the Full Stack Java Developer Toolkit.

## 🎯 Design Principles

### RESTful API Design
- **Resource-based URLs** - Use nouns, not verbs
- **HTTP methods** - Proper use of GET, POST, PUT, DELETE, PATCH
- **HTTP status codes** - Appropriate status codes for different scenarios
- **Consistent naming** - Use kebab-case for URLs, camelCase for JSON properties

### API Versioning
```
https://api.example.com/v1/users
https://api.example.com/v2/users
```

## 🛠️ Standard Patterns

### Resource Identification
```http
GET    /api/users           # List all users
GET    /api/users/{id}      # Get specific user
POST   /api/users           # Create new user
PUT    /api/users/{id}      # Update user (full)
PATCH  /api/users/{id}      # Update user (partial)
DELETE /api/users/{id}      # Delete user
```

### Filtering and Pagination
```http
GET /api/users?active=true&page=0&size=20&sort=createdAt,desc
GET /api/users?firstName=John&lastName=Doe
GET /api/users?createdAfter=2024-01-01
```

### Search
```http
GET /api/users/search?query=john&fields=username,email
```

## 📊 HTTP Status Codes

| Code | Description | Usage |
|------|-------------|-------|
| `200 OK` | Success | Standard success response |
| `201 Created` | Resource created | After successful POST |
| `204 No Content` | Success, no response body | After successful DELETE/PUT |
| `400 Bad Request` | Invalid request | Validation errors, malformed data |
| `401 Unauthorized` | Authentication required | Missing/invalid credentials |
| `403 Forbidden` | Access denied | Valid credentials but insufficient permissions |
| `404 Not Found` | Resource not found | Invalid ID or resource doesn't exist |
| `409 Conflict` | Resource conflict | Unique constraint violations |
| `422 Unprocessable Entity` | Validation failed | Business rule violations |
| `500 Internal Server Error` | Server error | Unexpected server-side errors |

## 🔐 Authentication & Authorization

### JWT Token Authentication
```http
Authorization: Bearer <jwt-token>
```

### Request Headers
```http
Content-Type: application/json
Accept: application/json
Authorization: Bearer <token>
X-Request-ID: <unique-request-id>
```

## 📝 Request/Response Format

### Request Body (POST/PUT)
```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "active": true
}
```

### Success Response
```json
{
  "success": true,
  "data": {
    "id": 1,
    "username": "johndoe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "active": true,
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-15T10:30:00Z"
  },
  "message": "User created successfully"
}
```

### Error Response
```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Validation failed",
    "details": {
      "username": ["Username is required", "Username must be at least 3 characters"],
      "email": ["Email is required", "Email format is invalid"]
    }
  },
  "timestamp": "2024-01-15T10:30:00Z",
  "path": "/api/users"
}
```

## ✅ Validation

### Field Validation
- **Required fields** - Mark with `required: true` or use `@NotBlank`
- **String length** - Specify min/max lengths
- **Email format** - Use proper email validation
- **Numeric ranges** - Specify min/max values
- **Date formats** - Use ISO 8601 format

### Business Rules Validation
- **Unique constraints** - Username and email uniqueness
- **Business logic** - Custom validation rules
- **Cross-field validation** - Dependent field validation

## 🔄 Pagination

### Request Parameters
```http
GET /api/users?page=0&size=20&sort=createdAt,desc
```

### Response Format
```json
{
  "success": true,
  "data": {
    "content": [
      // Array of items
    ],
    "pagination": {
      "page": 0,
      "size": 20,
      "totalElements": 100,
      "totalPages": 5,
      "first": true,
      "last": false,
      "numberOfElements": 20
    }
  }
}
```

## 🔍 Filtering & Sorting

### Filtering
```http
GET /api/users?active=true
GET /api/users?createdAfter=2024-01-01
GET /api/users?firstName=John
```

### Sorting
```http
GET /api/users?sort=createdAt,desc
GET /api/users?sort=username,asc
GET /api/users?sort=active,desc&sort=createdAt,asc
```

## 🚨 Error Handling

### Validation Errors
```json
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Input validation failed",
    "details": {
      "field": "error message"
    }
  }
}
```

### Business Logic Errors
```json
{
  "success": false,
  "error": {
    "code": "BUSINESS_RULE_VIOLATION",
    "message": "Cannot delete user with active orders",
    "details": {
      "userId": 123,
      "activeOrders": 5
    }
  }
}
```

### System Errors
```json
{
  "success": false,
  "error": {
    "code": "SYSTEM_ERROR",
    "message": "An unexpected error occurred",
    "details": {
      "errorId": "uuid-here",
      "timestamp": "2024-01-15T10:30:00Z"
    }
  }
}
```

## 📚 API Documentation

### OpenAPI/Swagger
```yaml
openapi: 3.0.3
info:
  title: Full Stack Java Developer Toolkit API
  description: REST API for user management
  version: 1.0.0
servers:
  - url: https://api.example.com/v1
    description: Production server
  - url: http://localhost:8080/api
    description: Development server
```

## 🧪 Testing APIs

### Tools
- **Postman** - GUI for API testing
- **curl** - Command-line tool
- **REST Client** - VS Code extension
- **Insomnia** - API testing platform

### Example Requests

#### Create User
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "firstName": "Test",
    "lastName": "User"
  }'
```

#### Get User
```bash
curl http://localhost:8080/api/users/1
```

#### Update User
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "updateduser",
    "email": "updated@example.com",
    "firstName": "Updated",
    "lastName": "User",
    "active": true
  }'
```

#### Delete User
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

## 🔒 Security Best Practices

### Input Validation
- ✅ Validate all input data
- ✅ Sanitize user inputs
- ✅ Use parameterized queries
- ✅ Implement rate limiting

### Authentication
- ✅ Use strong JWT secrets
- ✅ Implement token expiration
- ✅ Use HTTPS in production
- ✅ Secure password storage

### Authorization
- ✅ Role-based access control (RBAC)
- ✅ Method-level security
- ✅ Resource ownership validation
- ✅ API key management

## 📈 Performance Considerations

### Caching
```http
Cache-Control: public, max-age=3600
ETag: "unique-resource-identifier"
Last-Modified: Wed, 15 Jan 2024 10:30:00 GMT
```

### Compression
```http
Content-Encoding: gzip
Accept-Encoding: gzip, deflate
```

### Pagination
- ✅ Implement pagination for large datasets
- ✅ Use cursor-based pagination for real-time data
- ✅ Provide total count when possible
- ✅ Include pagination metadata

## 🔄 API Evolution

### Backward Compatibility
- ✅ Support multiple API versions
- ✅ Deprecate old endpoints gradually
- ✅ Maintain consistent response formats
- ✅ Document breaking changes

### API Changelog
```markdown
## Version 1.1.0 (2024-01-15)
- Added `active` field to User model
- Added `/api/users/search` endpoint
- Improved error response format

## Version 1.0.0 (2024-01-01)
- Initial API release
- Basic CRUD operations for users
```

## 🛠️ Development Guidelines

### Code Organization
```
src/main/java/org/nakhan/
├── controller/     # REST controllers
├── service/        # Business logic
├── repository/     # Data access
├── entity/         # JPA entities
├── dto/           # Data transfer objects
├── config/        # Configuration classes
└── security/      # Security configuration
```

### Best Practices
- ✅ Use DTOs for API responses
- ✅ Implement proper exception handling
- ✅ Add comprehensive logging
- ✅ Write unit and integration tests
- ✅ Document API endpoints

## 📊 Monitoring & Analytics

### Metrics to Track
- Response times
- Error rates
- API usage patterns
- Popular endpoints
- Authentication failures

### Health Checks
```http
GET /actuator/health
GET /actuator/info
GET /actuator/metrics
```

## 🤝 Contributing

### API Changes
1. Discuss changes in issues/PRs
2. Update API documentation
3. Add tests for new endpoints
4. Update this guidelines document
5. Communicate breaking changes

### Review Process
- ✅ Code review for all API changes
- ✅ Security review for authentication/authorization
- ✅ Performance review for new endpoints
- ✅ Documentation review

---

*This API follows industry best practices and is designed for scalability, maintainability, and developer experience.*
