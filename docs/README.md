# Final Fantasy Project Documentation

Welcome to the Final Fantasy game system documentation.

## 📚 Documentation Index

### Getting Started
- [Main README](../README.md) - Project overview and quick start
- [Developer Guide](../Developer.md) - Development setup and guidelines
- [Architecture Overview](../project_architecture_overview.md) - Hexagonal architecture explanation

### Refactoring & Changes
- [Refactoring Summary](REFACTORING_SUMMARY.md) - Latest refactoring changes and improvements

### Architecture Decision Records (ADR)

ADRs document significant architectural decisions made during the project's development.

- [ADR Index](adr/README.md) - Complete list of all ADRs (繁體中文)
- [ADR-001: Stateless BattleService Design](adr/ADR-001-stateless-battle-service.md) - Thread-safe battle state management
- [ADR-002: Equipment Type System](adr/ADR-002-equipment-type-system.md) - Type-safe equipment categorization

### PlantUML Diagrams
- [PlantUML Diagrams Guide](PlantUML-Diagrams-README.md) - Visual architecture documentation
  - Overall Architecture
  - Domain Model
  - Application Layer (Ports & Adapters)
  - Infrastructure Layer

### API Documentation
- Swagger UI: `http://localhost:8080/swagger-ui/index.html` (when running)
- OpenAPI Spec: `http://localhost:8080/v3/api-docs` (when running)

### Testing
- BDD Feature Files: `finalfantasy-web/src/test/resources/features/`
- Unit Tests: Various `*Test.java` files across modules

## 🏗️ Project Structure

```
finalfantasy/
├── docs/                          # 📖 Documentation
│   ├── README.md                  # This file
│   ├── REFACTORING_SUMMARY.md     # Refactoring log
│   └── adr/                       # Architecture decisions
│       ├── ADR-001-stateless-battle-service.md
│       └── ADR-002-equipment-type-system.md
│
├── finalfantasy-domain/           # 🎯 Core business logic
│   └── src/main/java/
│       └── net/game/finalfantasy/domain/
│           ├── model/             # Domain models
│           │   ├── battle/        # Battle system
│           │   ├── character/     # FF6 characters
│           │   ├── equipment/     # Equipment items
│           │   ├── hero/          # Hero models
│           │   ├── magic/         # Magic spells
│           │   └── stats/         # Character stats
│           └── service/           # Domain services
│
├── finalfantasy-application/      # 🎮 Use cases
│   └── src/main/java/
│       └── net/game/finalfantasy/application/
│           ├── port/
│           │   ├── in/            # Input ports (use cases)
│           │   └── out/           # Output ports (repositories)
│           └── service/           # Application services
│
├── finalfantasy-infrastructure/   # 🔌 Technical adapters
│   └── src/main/java/
│       └── net/game/finalfantasy/infrastructure/
│           ├── adapter/
│           │   ├── in/            # Inbound adapters (HTTP, WebSocket)
│           │   ├── out/           # Outbound adapters (Database, Events)
│           │   └── mapper/        # DTO mappers
│           └── config/            # Configuration
│
└── finalfantasy-web/              # 🌐 Main application
    └── src/
        ├── main/
        │   ├── java/              # Spring Boot app
        │   └── resources/         # Config files
        └── test/
            └── resources/features/ # Cucumber BDD tests

```

## 🔍 Quick Links

### For Developers
- [Setting Up Development Environment](../Developer.md#setup)
- [Running Tests](../README.md#testing)
- [Building the Project](../README.md#build)

### For Architects
- [Hexagonal Architecture](../project_architecture_overview.md)
- [Project Architecture (Detailed)](../.ai-docs/project-info/PROJECT-ARCHITECTURE.md)
- [All ADRs](adr/README.md)
- [PlantUML Diagrams](PlantUML-Diagrams-README.md)
- [Refactoring Log](REFACTORING_SUMMARY.md)

### For Operators
- [Server Endpoints](../README.md#server-endpoints)
- [Configuration Guide](../README.md#configuration)
- [Health Checks](../README.md#monitoring-and-health-checks)

## 📝 How to Contribute Documentation

### Adding a New ADR

1. Create a new file: `docs/adr/NNNN-title.md` (use next number)
2. Use the ADR template:
   ```markdown
   # ADR NNNN: Title

   **Status**: Proposed | Accepted | Deprecated | Superseded
   **Date**: YYYY-MM-DD
   **Decision Makers**: Names

   ## Context
   ## Decision
   ## Consequences
   ## Alternatives Considered
   ```
3. Update this index file with a link

### Updating Refactoring Log

When making significant code changes:
1. Update `REFACTORING_SUMMARY.md`
2. Document what changed, why, and the impact
3. Include before/after examples

### API Documentation

For REST endpoints:
1. Add Swagger annotations to controllers
2. Document request/response examples
3. Include error codes

## 🎯 Recent Changes

### Latest (2025-10-25)
- ✅ Refactored BattleService to be stateless and thread-safe
- ✅ Implemented type-safe equipment system
- ✅ Added comprehensive input validation
- ✅ Created HeroMapper for model unification

See [REFACTORING_SUMMARY.md](REFACTORING_SUMMARY.md) for full details.

## 📖 Additional Resources

### Project Documentation (.ai-docs)
- [Project Overview](../.ai-docs/project-info/PROJECT-OVERVIEW.md) - Comprehensive project summary
- [Project Architecture](../.ai-docs/project-info/PROJECT-ARCHITECTURE.md) - Detailed architecture guide
- [Workflow Guide](../.ai-docs/project-info/WORKFLOW-GUIDE.md) - Development best practices
- [Tech Stack Configuration](../.ai-docs/tech-stacks.md) - Complete technology stack details
- [Refactoring Changelog](../.ai-docs/CHANGELOG-REFACTORING.md) - Detailed change history

### Final Fantasy VI Game Mechanics
- [FF6 Wiki](https://finalfantasy.fandom.com/wiki/Final_Fantasy_VI)
- [Battle System](https://finalfantasy.fandom.com/wiki/Battle_system_(Final_Fantasy_VI))
- [Equipment System](https://finalfantasy.fandom.com/wiki/Equipment_(Final_Fantasy_VI))

### Architecture Patterns
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [CQRS Pattern](https://martinfowler.com/bliki/CQRS.html)

### Spring Boot & Java
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Vert.x Documentation](https://vertx.io/docs/)
- [Effective Java - Best Practices](https://www.pearson.com/en-us/subject-catalog/p/effective-java/P200000000138)

---

**Last Updated**: 2025-10-25
**Maintainers**: Development Team
