# LLD Practice 2025

A comprehensive Java project implementing design patterns for Low-Level Design (LLD) interview preparation and practical software development.

[![Java](https://img.shields.io/badge/Java-22-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-4.0.0-blue.svg)](https://maven.apache.org/)
[![Progress](https://img.shields.io/badge/Progress-71%25-green.svg)](.)


## 🎯 Overview

This project is a practical learning repository that demonstrates:
- **Design Patterns** with real-world implementations
- **Interview-focused** preparation for SDE positions
- **Hands-on learning** using the Socratic method

**Purpose:**
- Prepare for software engineering interviews
- Master design patterns through implementation
- Create a personal reference guide
- Practice Low-Level Design (LLD) skills

## 🛠️ Technology Stack

| Technology | Version | Purpose |
|:-----------|:--------|:--------|
| **Java** | 22 | Core language with latest features |
| **Maven** | 4.0.0 | Build automation and dependency management |
| **Lombok** | 1.18.38 | Reduces boilerplate code (`@Builder`, `@Getter`) |
| **Apache Commons Lang3** | 3.19.0 | Utility functions and helpers |
| **SLF4J** | 2.0.12 | Logging facade |
| **Logback Classic** | 1.4.11 | Logging implementation |
| **Jackson Databind** | 2.13.3 | JSON processing |

**Build Configuration:**
- Compiler: Java 22
- Encoding: UTF-8
- Lombok annotation processing enabled

## 📚 Implemented Design Patterns

### Creational Patterns (5/5 Complete) ✅

#### ✅ Builder Pattern
**Examples:** Email Builder, HTTP Request Builder

**Key Features:**
- Immutable objects with `final` fields
- Method chaining for fluent API
- Defensive copying for collections
- Complex object construction made readable

**Use Cases:** HTTP clients, email builders, test data builders

```java
Email email = new Email.EmailBuilder("user@example.com", "Subject")
    .body("Email content")
    .cc(List.of("cc@example.com"))
    .priority(Priority.HIGH)
    .build();
```

#### ✅ Factory Method Pattern
**Examples:** Notification Factory, Payment Factory

**Implementations:**
- Simple Factory
- Factory Method

**Use Cases:** Notification systems (Email, Push, SMS, Slack), payment processing

```java
NotificationFactory factory = NotificationFactoryProvider.getFactory("EMAIL");
Notification notification = factory.createNotification();
```

#### ✅ Abstract Factory Pattern
**Examples:** UI Component Factory (cross-platform UI)

**Key Features:**
- Creates families of related objects
- Ensures compatibility between created objects
- Supports multiple platform variations

**Use Cases:** Cross-platform UI frameworks, theme systems, database abstraction layers

```java
UIFactory factory = UIFactoryProvider.getFactory("WINDOWS");
Button button = factory.createButton();
Checkbox checkbox = factory.createCheckbox();
```

#### ✅ Prototype Pattern
**Examples:** Game Character Cloning

**Key Features:**
- Deep copy of complex objects
- Character templates with weapons, armor, skills
- Efficient object creation

**Use Cases:** Game development, object cloning

#### ✅ Singleton Pattern
**Examples:** Logger (Bill Pugh Implementation)

**Key Features:**
- Thread-safe using static inner class
- Lazy initialization
- No synchronization overhead

**Use Cases:** Logging systems, configuration managers, connection pools

---

### Structural Patterns (7/7 Complete) ✅

#### ✅ Adapter Pattern
**Examples:** Logger Adapters (Console, File, Database), Weather API Adapter

**Key Features:**
- Makes incompatible interfaces work together
- Object adapter implementation
- Real-world API integration

**Use Cases:** Third-party API integration, legacy system compatibility

#### ✅ Bridge Pattern
**Key Features:**
- Separates abstraction from implementation
- Independent variation of both hierarchies

**Use Cases:** Cross-platform development, database drivers

#### ✅ Composite Pattern
**Key Features:**
- Tree structures for part-whole hierarchies
- Uniform treatment of individual and composite objects

**Use Cases:** File systems, organization hierarchies, UI components

#### ✅ Decorator Pattern
**Examples:** Notification Decorators (Timestamp, Encryption, Compression, Read Receipt)

**Key Features:**
- Dynamic addition of responsibilities
- Flexible alternative to subclassing
- Composable enhancements

**Use Cases:** Notification systems, I/O streams, UI enhancements

```java
Notification notification = new BasicNotification("Hello");
notification = new TimestampDecorator(notification);
notification = new EncryptionDecorator(notification);
notification.send();
```

#### ✅ Facade Pattern
**Key Features:**
- Simplified interface to complex subsystems
- Reduces client dependencies
- Provides convenient methods

**Use Cases:** Complex libraries, subsystem integration

#### ✅ Flyweight Pattern
**Examples:** Particle System for Game Engines

**Key Features:**
- Memory optimization through shared state
- Manages 50,000+ particles efficiently
- Intrinsic vs extrinsic state separation

**Use Cases:** Game engines, text editors, graphics rendering

```java
ParticleFactory factory = new ParticleFactory();
ParticleType explosionType = factory.getParticleType("EXPLOSION");
// Reused across 10,000+ particles
```

#### ✅ Proxy Pattern
**Examples:** Document Proxy (Virtual, Protection, Logging)

**Implementations:**
- Virtual Proxy (lazy loading)
- Protection Proxy (access control)
- Logging Proxy (method call tracking)

**Use Cases:** Document management, access control, caching

---

### Behavioral Patterns (0/5 - Planned)

The following patterns are planned for implementation:
- Chain of Responsibility
- State
- Command
- Observer
- Strategy

## 📁 Project Structure

```
LLDPractice/
├── src/main/java/com/lldpractice/designpatterns/
│   ├── creational/                    # 54 files
│   │   ├── builder/                   # Email, HttpRequest
│   │   ├── factory/                   # Notification, Payment, Abstract UI
│   │   ├── prototype/                 # Game characters
│   │   └── singleton/                 # Logger
│   │
│   └── structural/                    # 58 files
│       ├── adapter/                   # Logger, Weather
│       ├── bridge/
│       ├── composite/
│       ├── decorator/                 # Notification decorators
│       ├── facade/
│       ├── flyweight/                 # Particle system
│       └── proxy/                     # Document proxies
│
├── notes/                             # ~330KB documentation
│   ├── patterns/
│   │   ├── creational/               # 6 detailed guides
│   │   ├── structural/               # 8 detailed guides
│   │   └── behavioural/              # To be expanded
│   ├── OOPS/                         # OOP fundamentals
│   └── important.md                  # Quick reference
│
├── prompts/                           # AI learning prompts
│   ├── Master Prompt.md
│   ├── Latest prompt.md
│   └── Complete Learning Prompt.md
│
├── pom.xml                            # Maven configuration
└── README.md                          # This file
```

## 🎓 Learning Methodology

This project follows a **Socratic learning approach** with AI-guided instruction:

### Core Principles
- **No Direct Solutions:** Learn by implementing, not copying
- **Question-Based Learning:** AI asks guiding questions
- **Code-First Approach:** Write code, then review
- **Systematic Process:** 
  1. Concept explanation
  2. Understanding verification
  3. Problem implementation
  4. Code review and discussion
  5. Pattern applicability analysis

### Learning Style
✅ Concise explanations with examples  
✅ Comparison tables  
✅ Java code examples  
✅ Type safety using enums  
✅ Decision flowcharts  
❌ No complete solutions upfront  
❌ Review student's code instead of providing answers

## 🚀 Getting Started

### Prerequisites
- Java 22 or higher
- Maven 3.6+
- IntelliJ IDEA or VS Code (recommended)

### Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd LLDPractice
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run examples:**
   Navigate to any main class and run it:
   ```bash
   # Example: Run Builder pattern demo
   mvn exec:java -Dexec.mainClass="com.lldpractice.designpatterns.creational.builder.Builder"
   ```

4. **Explore patterns:**
   - Browse `src/main/java/com/lldpractice/designpatterns/`
   - Read detailed notes in `notes/patterns/`
   - Review real-world examples

## 📖 Documentation

Each pattern includes comprehensive documentation:

### Pattern Notes Include
- Pattern definition and purpose
- Problem statement
- Solution with code examples
- Core components and concepts
- Multiple implementation variations
- When to use / when to avoid
- Real-world examples
- Advantages & disadvantages
- Comparison with other patterns
- Common mistakes
- Code templates
- Interview tips

### Key Documents
- **Builder Pattern:** 16.6KB of detailed notes
- **Factory Pattern:** 29.4KB with multiple implementations
- **Flyweight Pattern:** 32.1KB with game engine example
- **Structural Patterns Master Notes:** 62KB comprehensive guide

## 📊 Progress Tracking

### Overall Progress: 71% Complete (12/17 patterns)

| Category | Completed | Total | Progress |
|:---------|:---------:|:-----:|:---------|
| **Creational** | 5 | 5 | 🟢🟢🟢🟢🟢 100% ✅ |
| **Structural** | 7 | 7 | 🟢🟢🟢🟢🟢🟢🟢 100% ✅ |
| **Behavioral** | 0 | 5 | ⚪⚪⚪⚪⚪  0% |


## 🎯 Next Steps

### To Complete the Project

1. **Implement Behavioral Patterns (Priority)**
   - Strategy Pattern
   - Observer Pattern
   - Command Pattern
   - State Pattern
   - Chain of Responsibility

2. **Add Test Coverage**
   - JUnit tests for each pattern
   - Thread safety tests for Singleton
   - Immutability validation for Builder

3. **Enhance Examples**
   - Pattern combinations
   - E-commerce system
   - Complete notification platform

4. **Create Summary Documents**
   - Quick reference card
   - Pattern comparison matrix
   - Interview cheat sheet

## 🎓 Interview Readiness

This project covers key interview topics:

✅ All creational patterns (100%)  
✅ All structural patterns (100%)  
✅ Thread safety considerations  
✅ Immutability patterns  
✅ Memory optimization  
✅ API design  
✅ Real-world applications  
✅ Pattern trade-offs  

**Ready for:** SDE interviews focusing on design patterns and LLD
