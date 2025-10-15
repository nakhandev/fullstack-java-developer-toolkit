#!/bin/bash

# Full Stack Java Developer Toolkit - Setup Script
# This script initializes the development environment and validates all components

set -e

echo "🚀 Full Stack Java Developer Toolkit - Setup Script"
echo "=================================================="

# Function to check if a command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
echo "🔍 Checking prerequisites..."

# Check Java
if ! command_exists java; then
    echo "❌ Java is not installed. Please install Java 11 or higher."
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n1 | cut -d'"' -f2 | sed 's/^1\.//' | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 11 ]; then
    echo "❌ Java 11 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi
echo "✅ Java $JAVA_VERSION found"

# Check Docker
if ! command_exists docker; then
    echo "❌ Docker is not installed. Please install Docker."
    exit 1
fi
echo "✅ Docker found"

# Check Node.js (for frontend)
if ! command_exists node; then
    echo "❌ Node.js is not installed. Please install Node.js 16 or higher."
    exit 1
fi

NODE_VERSION=$(node -v | cut -d'v' -f2 | cut -d'.' -f1)
if [ "$NODE_VERSION" -lt 16 ]; then
    echo "❌ Node.js 16 or higher is required. Current version: $(node -v)"
    exit 1
fi
echo "✅ Node.js $(node -v) found"

# Check Maven (for Java projects)
if ! command_exists mvn; then
    echo "❌ Maven is not installed. Please install Maven."
    exit 1
fi
echo "✅ Maven found"

echo ""
echo "📦 Setting up backend components..."

# Setup Spring Boot template
echo "🔧 Setting up Spring Boot template..."
cd backend/spring-boot-template
if [ ! -f pom.xml ]; then
    echo "❌ Spring Boot template not found. Please ensure all templates are in place."
    exit 1
fi

# Install dependencies if needed
if [ ! -d "target" ] || [ ! -f "target/classes/application.properties" ]; then
    echo "📥 Installing Spring Boot dependencies..."
    mvn clean install -DskipTests
fi
cd ../..

echo ""
echo "📦 Setting up frontend components..."

# Setup React template
echo "⚛️ Setting up React + Vite template..."
cd frontend/react-vite-template
if [ ! -f package.json ]; then
    echo "❌ React template not found. Please ensure all templates are in place."
    exit 1
fi

# Install dependencies if needed
if [ ! -d "node_modules" ]; then
    echo "📥 Installing React dependencies..."
    npm install
fi
cd ../..

echo ""
echo "🐳 Setting up Docker environment..."

# Check if docker-compose is available
if command_exists docker-compose; then
    DOCKER_COMPOSE_CMD="docker-compose"
elif docker --version | grep -q "Docker Compose"; then
    DOCKER_COMPOSE_CMD="docker compose"
else
    echo "❌ Docker Compose not found. Please install Docker Compose."
    exit 1
fi

# Create .env file if it doesn't exist
if [ ! -f .env ]; then
    echo "📝 Creating .env file..."
    cat > .env << EOF
# Environment Configuration
SPRING_PROFILES_ACTIVE=dev
DATABASE_URL=jdbc:postgresql://localhost:5432/fullstack_dev
REDIS_URL=redis://localhost:6379
JWT_SECRET=your-secret-key-here

# Frontend Configuration
VITE_API_URL=http://localhost:8080
EOF
    echo "✅ .env file created"
else
    echo "✅ .env file already exists"
fi

echo ""
echo "🔐 Setting up authentication (Keycloak)..."

# Create Keycloak directories if needed
mkdir -p devops/docker/keycloak/data

echo ""
echo "✅ Setup completed successfully!"
echo ""
echo "🚀 Quick Start Commands:"
echo "   Backend:   cd backend/spring-boot-template && mvn spring-boot:run"
echo "   Frontend:  cd frontend/react-vite-template && npm run dev"
echo "   Full Stack: $DOCKER_COMPOSE_CMD up --build"
echo ""
echo "📖 For more information, see README.md"
echo ""
echo "🎉 Happy coding!"
