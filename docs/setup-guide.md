# 🚀 Setup Guide

This guide provides detailed instructions for setting up and running the Full Stack Java Developer Toolkit in various environments.

## 📋 Prerequisites

Before you begin, ensure you have the following installed:

### Required Software
- **Java 17+** - For Spring Boot backend
- **Node.js 16+** - For React frontend
- **Docker & Docker Compose** - For containerized deployment
- **Git** - For version control

### Optional but Recommended
- **Maven 3.9+** - For Java dependency management
- **PostgreSQL 15+** - For local database development
- **MongoDB 7.0+** - For NoSQL development
- **Redis 7.0+** - For caching development

## 🏃‍♂️ Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/<your-username>/fullstack-java-toolkit.git
cd fullstack-java-toolkit
```

### 2. Run Setup Script
```bash
bash scripts/setup.sh
```

This script will:
- ✅ Check all prerequisites
- ✅ Validate Java, Node.js, and Docker installations
- ✅ Set up environment configuration
- ✅ Create necessary directories and files

### 3. Start with Docker Compose (Recommended)

#### Full Stack (All Services)
```bash
docker-compose up --build
```

This starts:
- **Backend** (Spring Boot) → http://localhost:8080
- **Frontend** (React) → http://localhost:3000
- **PostgreSQL** → localhost:5432
- **MongoDB** → localhost:27017
- **Redis** → localhost:6379
- **PgAdmin** → http://localhost:5050
- **Mongo Express** → http://localhost:8081
- **Redis Commander** → http://localhost:8082

#### Individual Services
```bash
# Backend only
docker-compose up backend --build

# Frontend only
docker-compose up frontend --build

# Databases only
docker-compose up postgres mongodb redis --build
```

### 4. Manual Development Setup

#### Backend (Spring Boot)
```bash
cd backend/spring-boot-template

# Install dependencies
mvn clean install

# Run in development mode
mvn spring-boot:run

# Access H2 console (development)
# http://localhost:8080/h2-console
```

#### Frontend (React + Vite)
```bash
cd frontend/react-vite-template

# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build
```

## 🔧 Configuration

### Environment Variables

Create a `.env` file in the root directory:

```bash
# Database Configuration
POSTGRES_DB=fullstack_dev
POSTGRES_USER=fullstack_user
POSTGRES_PASSWORD=your-secure-password

# Application Configuration
SPRING_PROFILES_ACTIVE=dev
JWT_SECRET=your-jwt-secret-key

# Redis Configuration
REDIS_PASSWORD=your-redis-password

# Frontend Configuration
VITE_API_URL=http://localhost:8080
```

### Application Properties

#### Spring Boot Configuration
Edit `backend/spring-boot-template/src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/fullstack_dev
spring.datasource.username=fullstack_user
spring.datasource.password=your-password

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=your-redis-password
```

## 🗄️ Database Setup

### PostgreSQL

#### Using Docker (Recommended)
```bash
cd database/postgres
docker-compose up -d
```

#### Manual Installation
```bash
# Create database
createdb fullstack_dev

# Create user
createuser fullstack_user --pwprompt

# Grant permissions
psql -c "GRANT ALL PRIVILEGES ON DATABASE fullstack_dev TO fullstack_user;"
```

### MongoDB

#### Using Docker (Recommended)
```bash
cd database/mongo
docker-compose up -d
```

#### Manual Installation
```bash
# Start MongoDB service
sudo systemctl start mongod

# Connect to MongoDB
mongosh

# Create database and user
use fullstack_dev
db.createUser({
  user: "app_user",
  pwd: "your-password",
  roles: ["readWrite"]
})
```

### Redis

#### Using Docker (Recommended)
```bash
cd database/redis
docker-compose up -d
```

#### Manual Installation
```bash
# Install Redis
sudo apt install redis-server

# Configure password in /etc/redis/redis.conf
requirepass your-redis-password

# Start Redis
sudo systemctl start redis-server
```

## 🧪 Testing

### Run All Tests
```bash
# Backend tests
cd backend/spring-boot-template
mvn test

# Frontend tests (if configured)
cd frontend/react-vite-template
npm test
```

### Testing with Testcontainers
```bash
cd testing/junit-examples
mvn test -Dtest="*IntegrationTest"
```

### Database Testing
```bash
# Run tests with real databases
mvn test -Dspring.profiles.active=test
```

## 🚢 Deployment

### Production Deployment

#### Using Docker Compose
```bash
# Build for production
docker-compose -f docker-compose.prod.yml up --build -d

# Or use the main docker-compose.yml with production environment
DOCKER_ENV=production docker-compose up --build -d
```

#### Using Kubernetes
```bash
# Apply Kubernetes manifests
kubectl apply -f devops/kubernetes/

# Check deployment status
kubectl get pods -n fullstack-java-toolkit
kubectl get services -n fullstack-java-toolkit
```

### Environment-Specific Configuration

#### Development
- Hot reload enabled
- Debug logging
- H2 console available
- CORS configured for frontend

#### Production
- Optimized JVM settings
- Security headers
- Health checks
- Logging configuration

## 🔍 Monitoring and Debugging

### Health Checks
- **Backend Health**: http://localhost:8080/actuator/health
- **Frontend Health**: http://localhost:3000/health

### Database Management
- **PgAdmin**: http://localhost:5050 (admin@fullstack.local / admin123)
- **Mongo Express**: http://localhost:8081
- **Redis Commander**: http://localhost:8082

### Logs
```bash
# View application logs
docker-compose logs backend
docker-compose logs frontend

# Follow logs in real-time
docker-compose logs -f backend

# View specific service logs
docker-compose logs postgres
```

## 🛠️ Development Workflow

### Code Organization
```
src/
├── main/java/org/nakhan/
│   ├── config/     # Configuration classes
│   ├── controller/ # REST controllers
│   ├── dto/        # Data Transfer Objects
│   ├── entity/     # JPA entities
│   ├── repository/ # Data repositories
│   ├── security/   # Security configuration
│   └── service/    # Business logic
└── test/java/org/nakhan/
    └── testing/    # Test classes
```

### Best Practices

#### Backend Development
- Use `@Transactional` for database operations
- Implement proper exception handling
- Use DTOs for API responses
- Add validation annotations
- Write unit and integration tests

#### Frontend Development
- Use functional components with hooks
- Implement proper error boundaries
- Use TypeScript for type safety
- Follow component composition patterns
- Write tests for critical functionality

## 🔒 Security Considerations

### Production Security
1. **Change default passwords** in all services
2. **Configure HTTPS** for production deployment
3. **Set up proper CORS** policies
4. **Implement rate limiting**
5. **Use environment variables** for sensitive data
6. **Enable security headers**

### Database Security
1. **Use strong passwords**
2. **Limit database user permissions**
3. **Enable SSL/TLS** for database connections
4. **Regular backup strategies**

## 📊 Performance Optimization

### Backend Performance
- Connection pooling configuration
- Query optimization
- Caching strategies
- JVM tuning

### Frontend Performance
- Code splitting
- Lazy loading
- Image optimization
- Bundle analysis

## 🆘 Troubleshooting

### Common Issues

#### Port Conflicts
```bash
# Check used ports
sudo lsof -i :8080
sudo lsof -i :3000

# Kill process using port
sudo kill -9 <PID>
```

#### Docker Issues
```bash
# Clean up Docker resources
docker system prune -a

# Rebuild containers
docker-compose down
docker-compose up --build --force-recreate
```

#### Database Connection Issues
```bash
# Test database connectivity
docker-compose exec postgres psql -U fullstack_user -d fullstack_dev

# Check database logs
docker-compose logs postgres
```

#### Build Issues
```bash
# Clean and rebuild
mvn clean install -U

# Clear npm cache
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

## 📞 Support

### Getting Help
1. Check the [documentation](README.md)
2. Review [context.md](context.md) for detailed tech stack information
3. Open an issue on GitHub for bugs or feature requests
4. Join our community discussions

### Contributing
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

---

*For more detailed information about specific components, refer to the individual README files in each module directory.*
