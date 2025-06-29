# Final Fantasy Project - PlantUML Class Diagrams

This directory contains PlantUML class diagrams that visualize the architecture and design of the Final Fantasy project.

## 📋 Available Diagrams

### 1. Overall Architecture (`overall-architecture.puml`)
**Purpose**: Complete system overview showing all layers and their relationships
- **Scope**: Entire application architecture
- **Shows**: Web layer, Infrastructure layer, Application layer, Domain layer
- **Key Features**:
  - Clean Architecture pattern
  - Dependency flow between layers
  - All major classes and interfaces
  - Cross-layer relationships

### 2. Domain Model (`domain-model.puml`)
**Purpose**: Detailed view of the core business domain
- **Scope**: Domain layer only
- **Shows**: Entities, Value Objects, Enums, Domain Services
- **Key Features**:
  - Hero aggregate with equipment management
  - Equipment and stats value objects
  - Factory patterns for object creation
  - Rich domain model with business logic

### 3. Application Layer (`application-layer.puml`)
**Purpose**: Hexagonal architecture implementation
- **Scope**: Application layer with port/adapter pattern
- **Shows**: Use cases, Ports (in/out), Application services
- **Key Features**:
  - Primary ports (driving adapters)
  - Secondary ports (driven adapters)
  - Dependency inversion principle
  - Clean separation of concerns

### 4. Infrastructure Layer (`infrastructure-layer.puml`)
**Purpose**: Technical implementation details
- **Scope**: Infrastructure layer adapters and configurations
- **Shows**: Web controllers, DTOs, Persistence, Configuration
- **Key Features**:
  - Primary adapters (web controllers)
  - Secondary adapters (repositories)
  - Data transfer objects
  - Framework configurations

## 🏗️ Architecture Overview

The project follows **Clean Architecture** principles with clear separation of concerns:

```
Web Layer (Entry Point)
    ↓
Infrastructure Layer (Adapters)
    ↓
Application Layer (Use Cases)
    ↓
Domain Layer (Business Logic)
```

### Key Architectural Patterns

1. **Hexagonal Architecture (Ports & Adapters)**
   - Primary ports: `HeroManagementUseCase`
   - Secondary ports: `HeroRepository`
   - Adapters: Controllers, Repository implementations

2. **Domain-Driven Design (DDD)**
   - Aggregates: `Hero`
   - Value Objects: `HeroStats`, `Equipment`
   - Domain Services: `HeroFactory`, `EquipmentFactory`

3. **Dependency Inversion**
   - High-level modules don't depend on low-level modules
   - Both depend on abstractions (interfaces)

## 🎮 Domain Model Highlights

### Core Entities
- **Hero**: Main aggregate root managing equipment and stats
- **Equipment**: Value object with stat bonuses and restrictions
- **HeroStats**: Immutable value object with stat calculations

### Business Rules
- Heroes can only equip compatible equipment
- Equipment provides stat bonuses
- Stats are calculated dynamically (base + equipment bonuses)
- Chinese localization for hero types and equipment slots

## 🔧 How to Use These Diagrams

### Prerequisites
- PlantUML plugin for your IDE/editor
- Or use online PlantUML editor: http://www.plantuml.com/plantuml/

### Viewing Diagrams
1. **IDE Integration**: Install PlantUML plugin for IntelliJ IDEA, VS Code, etc.
2. **Online**: Copy diagram content to PlantUML online editor
3. **Command Line**: Use PlantUML JAR to generate images

### Generating Images
```bash
# Generate PNG images
java -jar plantuml.jar *.puml

# Generate SVG images
java -jar plantuml.jar -tsvg *.puml
```

## 📚 Diagram Relationships

- **overall-architecture.puml**: Complete system view
- **domain-model.puml**: Subset focusing on business logic
- **application-layer.puml**: Subset focusing on use cases
- **infrastructure-layer.puml**: Subset focusing on technical details

Each diagram can be viewed independently or together for different perspectives of the same system.

## 🎯 Benefits of These Diagrams

1. **Documentation**: Visual representation of system architecture
2. **Onboarding**: Help new developers understand the codebase
3. **Design Validation**: Verify architectural decisions
4. **Communication**: Share design with stakeholders
5. **Maintenance**: Track architectural evolution over time

## 🔄 Keeping Diagrams Updated

These diagrams should be updated when:
- New classes or interfaces are added
- Relationships between components change
- Architectural patterns are modified
- New modules or layers are introduced

## 🤖 Automated Class Diagram Generation

### Auto-Generated Class Diagrams

In addition to the manually crafted architectural diagrams above, this project includes an automated script that generates PlantUML class diagrams for each module:

#### Available Auto-Generated Diagrams
- `finalfantasy-domain-classes.puml` - All domain classes, enums, and services
- `finalfantasy-application-classes.puml` - Application layer classes and interfaces
- `finalfantasy-infrastructure-classes.puml` - Infrastructure adapters and configurations
- `finalfantasy-web-classes.puml` - Web layer entry point

#### Usage

To generate/update the class diagrams automatically:

```bash
# Make the script executable (first time only)
chmod +x generate_class_diagrams.sh

# Run the script to generate class diagrams
./generate_class_diagrams.sh
```

#### What the Script Does

1. **Scans each module** for Java source files
2. **Extracts class information** including:
   - Class/Interface/Enum declarations
   - Private, protected, and public fields
   - Method signatures with visibility
3. **Generates PlantUML syntax** with proper formatting
4. **Creates separate diagram files** for each module in the `docs/` directory
5. **Adds timestamps** and module information

#### Script Features

- ✅ **Automatic discovery** of all Java files in each module
- ✅ **Proper PlantUML formatting** with consistent styling
- ✅ **Colored console output** for better user experience
- ✅ **Error handling** for missing directories or files
- ✅ **Timestamp tracking** to know when diagrams were last generated

#### When to Run

Run the script whenever:
- New Java classes are added to any module
- Existing classes are significantly modified
- You want to refresh the auto-generated documentation
- Before major releases or code reviews

#### Differences from Manual Diagrams

| Manual Diagrams | Auto-Generated Diagrams |
|----------------|-------------------------|
| Show architectural relationships | Show individual class structures |
| Include cross-layer dependencies | Focus on single module content |
| Manually curated and annotated | Automatically extracted from code |
| Architectural patterns emphasis | Complete class member listing |
| Design documentation focus | Code documentation focus |

Both types of diagrams complement each other - use manual diagrams for understanding architecture and auto-generated ones for detailed class reference.

---

*Generated for Final Fantasy Project - A demonstration of Clean Architecture with Domain-Driven Design*
