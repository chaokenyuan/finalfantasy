# Final Fantasy Game System - Project Architecture

## 📋 Architecture Overview

```yaml
id: "ARCH-FF6-001"
name: "Final Fantasy Hexagonal Architecture"
category: "架構設計"
tags: ["hexagonal", "ddd", "spring-boot", "clean-architecture", "ports-and-adapters"]
success_rate: 95%
last_updated: "2025-10-25"
status: "Active Development"
```

## 🎯 Architecture Pattern

### Hexagonal Architecture (Ports and Adapters)

This project follows **Hexagonal Architecture** (also known as Ports and Adapters pattern), which provides:
- Clear separation between business logic and technical concerns
- Framework-independent domain model
- Easy testing through dependency inversion
- Flexibility to swap adapters without affecting core logic

```
┌─────────────────────────────────────────────────────────┐
│                    Driving Adapters                     │
│         (HTTP/REST, WebSocket, CLI, Scheduled)          │
│                                                          │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐       │
│  │ Controllers│  │  Vert.x    │  │  Scheduled │       │
│  │   (REST)   │  │  Servers   │  │   Tasks    │       │
│  └────────────┘  └────────────┘  └────────────┘       │
└───────────────────────┬─────────────────────────────────┘
                        │ Input Ports (Use Cases)
┌───────────────────────┼─────────────────────────────────┐
│                       ↓                                  │
│              Application Layer                           │
│         ┌─────────────────────────┐                     │
│         │    BattleService        │                     │
│         │  CharacterActionService │                     │
│         └────────┬────────────────┘                     │
│                  │ uses                                  │
│                  ↓                                       │
│              Domain Layer                                │
│    ┌────────────────────────────────┐                  │
│    │  Battle  │ FF6Character │ Hero │                  │
│    │  Equipment │ MagicSpell  │ ... │                  │
│    └────────────────────────────────┘                  │
│                  │ needs                                 │
│                  ↓                                       │
│              Output Ports (Interfaces)                   │
│    ┌─────────────────────────────────┐                 │
│    │  BattleRepository               │                 │
│    │  CharacterRepository            │                 │
│    │  GameEventPublisher             │                 │
│    └─────────────┬───────────────────┘                 │
└──────────────────┼──────────────────────────────────────┘
                   │ Implementations
┌──────────────────┼──────────────────────────────────────┐
│                  ↓                                       │
│              Driven Adapters                             │
│  ┌────────────┐  ┌────────────┐  ┌────────────┐       │
│  │ InMemory   │  │   Events   │  │  External  │       │
│  │Repository  │  │ Publisher  │  │  Services  │       │
│  └────────────┘  └────────────┘  └────────────┘       │
│                                                          │
│        (Database, Messaging, External APIs)             │
└─────────────────────────────────────────────────────────┘
```

## 🏗️ Module Structure

### 1. finalfantasy-domain (Core Business Logic)

**Purpose**: Pure business logic, framework-independent

**Contents**:
- Domain Models (Entities, Value Objects)
- Domain Services
- Business Rules

**Key Classes**:
```java
// Battle System
- Battle                  // Aggregate Root for battle
- FF6Character           // Rich domain model for FF6 characters
- Turn                   // Battle turn representation

// Character System
- Hero                   // Simple hero model (API compatibility)
- Equipment              // Equipment with type system
- StatusEffect           // Character status effects

// Game Mechanics
- DamageCalculationService
- MagicCalculationService
- AtbCalculationService
```

**Dependencies**: NONE (only JDK and Lombok)

**Recent Changes** (2025-10-25):
- ✅ Added `EquipmentType` enum for type-safe equipment
- ✅ Enhanced `Equipment` with type property
- ✅ Improved `FF6Character` weapon detection logic

---

### 2. finalfantasy-application (Use Cases)

**Purpose**: Orchestrates domain logic, defines ports

**Contents**:
- Application Services
- Use Case Interfaces (Input Ports)
- Repository Interfaces (Output Ports)

**Key Components**:
```java
// Services
- BattleService           // Battle orchestration (REFACTORED ✅)

// Input Ports
- BattleUseCase          // Battle operations interface
- CharacterActionUseCase // Character action interface

// Output Ports
- BattleRepository       // Battle persistence interface (UPDATED ✅)
- CharacterRepository    // Character persistence interface
- GameEventPublisher     // Event publishing interface
```

**Dependencies**: finalfantasy-domain

**Recent Changes** (2025-10-25):
- ✅ **Major Refactoring**: BattleService now stateless
- ✅ Removed instance variables, uses ThreadLocal for context
- ✅ Added comprehensive input validation
- ✅ Updated BattleRepository to work with Battle objects

---

### 3. finalfantasy-infrastructure (Technical Adapters)

**Purpose**: Implementation of ports, framework integration

**Contents**:
- Web Controllers (Driving Adapters)
- Repository Implementations (Driven Adapters)
- External Service Integrations
- Configuration

**Structure**:
```
infrastructure/
├── adapter/
│   ├── in/                        # Driving Adapters
│   │   ├── vertx/
│   │   │   ├── HttpServerVerticle     # Vert.x HTTP server
│   │   │   ├── SocketServerVerticle   # Vert.x WebSocket
│   │   │   └── VertxService           # Vert.x lifecycle
│   │   ├── web/dto/
│   │   │   ├── CreateHeroRequest
│   │   │   ├── EquipItemRequest
│   │   │   └── HeroResponse
│   │   └── mapper/
│   │       └── HeroMapper              # NEW ✅ Hero ↔ FF6Character
│   ├── out/                       # Driven Adapters
│   │   ├── persistence/
│   │   │   ├── InMemoryBattleRepository    # UPDATED ✅
│   │   │   └── InMemoryCharacterRepository
│   │   └── event/
│   │       └── SimpleGameEventPublisher
│   └── config/
│       ├── ServerPortsConfig
│       ├── SwaggerConfig
│       └── VertxConfig
```

**Dependencies**: finalfantasy-domain, finalfantasy-application

**Recent Changes** (2025-10-25):
- ✅ Created `HeroMapper` for model transformation
- ✅ Updated `InMemoryBattleRepository` with validation
- ✅ Enhanced thread-safety in repositories

---

### 4. finalfantasy-web (Application Entry Point)

**Purpose**: Main application, integration, testing

**Contents**:
- Spring Boot Application
- Configuration Files
- Cucumber BDD Tests

**Structure**:
```
web/
├── src/main/
│   ├── java/
│   │   └── FinalFantasyApplication.java
│   └── resources/
│       ├── application.yml
│       ├── application-local.yml
│       ├── application-sit.yml
│       ├── application-uat.yml
│       └── application-prod.yml
└── src/test/
    ├── java/cucumber/
    │   ├── CucumberTestRunnerIT
    │   └── domain/
    │       ├── BattleSteps
    │       ├── CharacterModelSteps
    │       └── ...
    └── resources/features/
        ├── battle/
        │   ├── battle_core.feature
        │   └── physical_damage_multiplier.feature
        ├── character/
        │   └── (14 character test files)
        └── magic/
            ├── black_magic.feature
            ├── white_magic.feature
            ├── blue_magic.feature
            └── gray_magic.feature
```

**Dependencies**: All other modules

---

## 📦 Package Organization

### By Layer (Current Structure)

```
net.game.finalfantasy/
├── domain/                          # Domain Layer
│   ├── model/
│   │   ├── battle/
│   │   ├── character/
│   │   ├── equipment/
│   │   ├── esper/
│   │   ├── hero/
│   │   ├── magic/
│   │   └── stats/
│   └── service/
│       ├── AtbCalculationService
│       ├── DamageCalculationService
│       ├── MagicCalculationService
│       └── ...
├── application/                     # Application Layer
│   ├── port/
│   │   ├── in/                     # Use Cases
│   │   └── out/                    # Repository Interfaces
│   └── service/
│       ├── BattleService           # Orchestration
│       └── CharacterActionService
└── infrastructure/                  # Infrastructure Layer
    ├── adapter/
    │   ├── in/                     # Inbound
    │   ├── out/                    # Outbound
    │   └── mapper/                 # NEW ✅
    └── config/
```

### Dependency Rules

```
┌──────────────────┐
│   Web (Entry)    │ ───┐
└──────────────────┘    │
                        ↓
┌──────────────────┐    │
│ Infrastructure   │ ───┤
└──────────────────┘    │
                        ↓
┌──────────────────┐    │
│   Application    │ ───┤
└──────────────────┘    │
                        ↓
┌──────────────────┐    │
│     Domain       │ ←──┘
└──────────────────┘

✅ Dependencies flow INWARD only
❌ Inner layers NEVER depend on outer layers
```

## 🔧 Technology Stack

### Core Frameworks
- **Spring Boot 3.5.3** - Application framework
- **Vert.x 4.5.10** - High-performance event-driven toolkit
- **Java 17** - Programming language

### Data & Persistence
- **H2** - In-memory database (development)
- **MySQL** - Production database (via Docker)
- **Spring Data JPA** - Data access (future)

### Testing
- **Cucumber 7.18.1** - BDD testing
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework

### Build & Development
- **Maven** - Build tool
- **Lombok 1.18.30** - Code generation

### Documentation
- **SpringDoc OpenAPI** - API documentation
- **Swagger UI** - Interactive API explorer

## 🎮 Server Architecture

### Multi-Server Setup

The application runs multiple servers simultaneously:

| Server | Port | Purpose | Technology |
|--------|------|---------|------------|
| Spring Boot HTTP | 8080 | Main REST API | Spring MVC |
| Vert.x HTTP | 8081 | High-performance endpoints | Vert.x Web |
| Vert.x WebSocket | 8082 | Real-time communication | Vert.x |
| gRPC | 9090 | RPC services (future) | Spring gRPC |

### Server Responsibilities

**Spring Boot (8080)**:
- REST API endpoints
- Swagger documentation
- H2 console (dev)
- Business logic orchestration

**Vert.x HTTP (8081)**:
- `/vertx/health` - Health check
- `/vertx/game/status` - Game status
- `/vertx/game/action` - Game actions
- High-throughput operations

**Vert.x WebSocket (8082)**:
- Real-time battle updates
- Player notifications
- Live game events

## 🧪 Testing Strategy

### Test Pyramid

```
        ┌─────────────┐
        │   E2E (5%)  │
        └─────────────┘
      ┌───────────────────┐
      │ Integration (15%) │
      └───────────────────┘
    ┌───────────────────────────┐
    │   Unit Tests (80%)        │
    └───────────────────────────┘
```

### BDD with Cucumber

**Feature Files**: 46 feature files covering:
- Battle mechanics (ATB, damage calculation)
- Character abilities (14 FF6 characters)
- Magic systems (Black, White, Blue, Gray)
- Status effects
- Equipment system

**Example**:
```gherkin
Feature: FF6 戰鬥核心邏輯與行動流程

Scenario: ATB 基礎行動系統
  Given 角色的基礎速度為 40
  And ATB上限值為 65535
  When 計算ATB增量
  Then 基礎ATB增量公式為 "atb_increase = speed"
```

### Test Organization

```
finalfantasy-web/src/test/
├── java/
│   └── cucumber/
│       ├── CucumberSpringConfiguration.java
│       ├── CucumberTestRunnerIT.java
│       └── domain/
│           ├── BattleSteps.java
│           ├── BlackMagicSteps.java
│           ├── CharacterModelSteps.java
│           └── ...
└── resources/
    └── features/
        ├── battle/
        ├── character/ (14 characters)
        ├── magic/ (4 types)
        ├── esper/
        └── status/
```

## 📊 Recent Architecture Changes

### 2025-10-25 Refactoring

#### 1. Stateless BattleService ✅
**Problem**: Thread-safety issues, no concurrent battles support
**Solution**:
- Removed instance variables
- Introduced ThreadLocal for battle context
- All state in Battle domain object

**Impact**: High
**Status**: ✅ Completed, tests passing

#### 2. Type-Safe Equipment System ✅
**Problem**: String-based weapon detection
**Solution**:
- Created `EquipmentType` enum
- Added type property to `Equipment`
- Helper methods: `isWeapon()`, `isRelic()`, `isArmor()`

**Impact**: Medium
**Status**: ✅ Completed, tests passing

#### 3. Hero/FF6Character Unification ✅
**Problem**: Two parallel character systems
**Solution**:
- Created `HeroMapper` adapter
- Hero = API DTO
- FF6Character = Rich domain model

**Impact**: Medium
**Status**: ✅ Completed

## 🔗 Architecture Decision Records

See `.ai-docs/adr/` for detailed architectural decisions:
- [ADR-0001: Stateless BattleService Design](../adr/ADR-001-stateless-battle-service.md)
- [ADR-0002: Type-Safe Equipment System](../adr/ADR-002-equipment-type-system.md)

## ✅ Architecture Validation

### Dependency Check
```bash
# Verify dependencies flow inward only
mvn dependency:tree
```

### Module Independence
- ✅ Domain has zero framework dependencies
- ✅ Application depends only on Domain
- ✅ Infrastructure depends on Application & Domain
- ✅ Web integrates all modules

### Test Coverage
```
Domain: Unit tests (4 tests, all passing)
Application: Integration tests (pending)
Infrastructure: Configuration tests (3 tests, passing)
Web: BDD tests (46 feature files)
```

## 🎯 Architecture Goals

### Current State (2025-10-25)
- ✅ Clean separation of concerns
- ✅ Framework-independent domain
- ✅ Thread-safe services
- ✅ Comprehensive BDD tests
- ✅ Type-safe equipment system

### Future Improvements
- ⏳ Add JPA persistence layer
- ⏳ Implement REST controllers
- ⏳ Add CQRS for battle system
- ⏳ Implement event sourcing
- ⏳ Add caching layer
- ⏳ Performance optimization

## 📚 Related Documentation

- [Project Overview](./PROJECT-OVERVIEW.md)
- [Workflow Guide](./WORKFLOW-GUIDE.md)
- [Tech Stack Details](../tech-stacks.md)
- [Refactoring Summary](../../docs/REFACTORING_SUMMARY.md)
- [Main README](../../README.md)
