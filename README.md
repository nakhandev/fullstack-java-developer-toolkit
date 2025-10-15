# 🧩 Full Stack Java Developer Toolkit

🚀 A complete open-source toolkit for Full Stack Java Developers — featuring Spring Boot, React, Docker, Kubernetes, and modern DevOps templates for rapid, production-grade app development.

## 🌐 Overview

The Full Stack Java Developer Toolkit provides a unified structure and curated open-source stack for building scalable Java applications. It helps you start fast with pre-configured templates for backend, frontend, database, testing, and DevOps.

## 🧱 Repository Structure

```
fullstack-java-toolkit/
│
├── README.md
├── context.md                  # Complete open-source tech stack reference
├── LICENSE
│
├── backend/
│   ├── spring-boot-template/    # Spring Boot starter project
│   ├── microservices/           # Modular service examples
│   └── quarkus-template/
│
├── frontend/
│   ├── react-vite-template/     # React + Vite + Tailwind starter
│   ├── angular-template/
│   └── vue-template/
│
├── database/
│   ├── postgres/
│   ├── mongo/
│   └── redis/
│
├── devops/
│   ├── docker/
│   ├── kubernetes/
│   └── jenkins/
│
├── testing/
│   ├── junit-examples/
│   ├── mockito-examples/
│   └── testcontainers/
│
└── docs/
    ├── setup-guide.md
    ├── api-guidelines.md
    ├── dev-workflow.md
    └── coding-standards.md
```

## ⚙️ Tech Stack

| Layer | Technologies |
|-------|-------------|
| Backend | Spring Boot, Micronaut, Quarkus |
| Frontend | React, TypeScript, TailwindCSS, Vite |
| Database | PostgreSQL, MongoDB, Redis |
| Build Tools | Gradle, Maven |
| Authentication | Keycloak (OAuth2, JWT) |
| Testing | JUnit 5, Mockito, Testcontainers |
| DevOps | Docker, Kubernetes, Jenkins |
| Monitoring | Prometheus, Grafana |
| Automation | JHipster, n8n, Airflow |

Full details in [context.md](context.md)

## 🚀 Quick Start

### 1️⃣ Clone the repository
```bash
git clone https://github.com/nakhandev/fullstack-java-developer-toolkit.git
cd fullstack-java-developer-toolkit
```

### 2️⃣ Run setup script
```bash
bash scripts/setup.sh
```

### 3️⃣ Start backend (Spring Boot)
```bash
cd backend/spring-boot-template
./gradlew bootRun
```

### 4️⃣ Start frontend (React + Vite)
```bash
cd frontend/react-vite-template
npm install
npm run dev
```

### 5️⃣ Run with Docker
```bash
docker-compose up --build
```

Access the app at:
- **Backend** → http://localhost:8080
- **Frontend** → http://localhost:3000

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run integration tests
docker-compose -f docker-compose.test.yml up --build
```

## ☁️ Deployment

Supports multiple deployment targets:
- 🐳 **Docker Compose** - Local development and single-server deployment
- ☸️ **Kubernetes** - Scalable container orchestration (Helm Charts included)
- 🔧 **Jenkins CI/CD** - Automated build and deployment pipelines

## 🧰 Included Templates

| Type | Folder | Description |
|------|--------|-------------|
| Spring Boot | `backend/spring-boot-template/` | REST API boilerplate |
| React + Vite | `frontend/react-vite-template/` | Frontend starter |
| PostgreSQL | `database/postgres/` | Pre-configured Docker setup |
| Jenkins | `devops/jenkins/` | CI/CD automation pipeline |
| K8s | `devops/kubernetes/` | Deployment manifests |

## 🧭 Roadmap

- [x] Setup core repository structure
- [x] Add Spring Boot + React templates
- [ ] Add microservices examples
- [ ] Implement comprehensive testing suite
- [ ] Create deployment automation
- [ ] Add monitoring and logging setup

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

Built with ❤️ for the Java developer community.

---

**Need help?** Check out the [documentation](docs/) or open an issue for support.
