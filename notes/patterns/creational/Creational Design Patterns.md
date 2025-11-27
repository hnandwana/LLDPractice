<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# Complete Creational Design Patterns - Master Revision Notes 📚

## Table of Contents

1. Overview \& Comparison
2. Singleton Pattern
3. Builder Pattern
4. Factory Patterns (Simple, Method, Abstract)
5. Prototype Pattern
6. Decision Tree: When to Use Which?
7. Interview Questions
8. Quick Reference Cheat Sheet

***

# 1. Overview \& Comparison

## What are Creational Patterns?

**Creational patterns** deal with **object creation mechanisms**, hiding the creation logic rather than instantiating objects directly using `new`. This gives flexibility in deciding which objects to create.

## The 4 Creational Patterns You've Mastered

| Pattern | Purpose | Key Idea | Example Use Case |
| :-- | :-- | :-- | :-- |
| **Singleton** | Ensure only ONE instance | Global access point | Database connection, Logger, Config |
| **Builder** | Construct complex objects step-by-step | Separate construction from representation | Email builder, HTTP request, Complex objects |
| **Factory** | Create objects without specifying exact class | Defer instantiation to subclasses/methods | Payment types, Notifications, UI themes |
| **Prototype** | Create objects by cloning | Copy existing objects instead of new | Game characters, Document templates |


***

## Quick Comparison Table

| Aspect | Singleton | Builder | Factory | Prototype |
| :-- | :-- | :-- | :-- | :-- |
| **Instances** | 1 | Many | Many | Many (clones) |
| **Creation** | Private constructor | Step-by-step | Factory method | Cloning |
| **Complexity** | Low | High | Medium | Medium |
| **Use When** | Shared resource | Many parameters | Different types | Expensive creation |
| **Thread Safety** | Consider DCL/Bill Pugh | Not an issue | Not an issue | Not an issue |


***

# 2. Singleton Pattern

## Definition

Ensures a class has **only ONE instance** and provides a global access point to it.

## Core Concepts

### Problem It Solves

- Prevent multiple instances (waste resources)
- Provide global access point
- Control instance creation


### Key Characteristics

- ✅ Private constructor (prevents `new`)
- ✅ Static instance variable
- ✅ Static `getInstance()` method
- ✅ Thread-safe (if needed)

***

## Implementations

### 1. Eager Initialization

```java
public class Singleton {
    // Instance created at class loading
    private static final Singleton instance = new Singleton();
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        return instance;
    }
}
```

**Pros:** Thread-safe, simple
**Cons:** Created even if never used (wastes memory)
**Use When:** Always need instance, lightweight object

***

### 2. Lazy Initialization (NOT Thread-Safe)

```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();  // Multiple threads can reach here!
        }
        return instance;
    }
}
```

**Pros:** Created only when needed
**Cons:** NOT thread-safe
**Use When:** Single-threaded applications only

***

### 3. Bill Pugh (RECOMMENDED ✅)

```java
public class Singleton {
    private Singleton() {}
    
    // Inner static class - loaded only when getInstance() called
    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

**Pros:** Thread-safe, lazy, no synchronization overhead
**Cons:** None (best approach!)
**Use When:** Default choice for most cases

***

### 4. Double-Checked Locking (DCL)

```java
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {  // Check 1 (no lock)
            synchronized (Singleton.class) {
                if (instance == null) {  // Check 2 (with lock)
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**Pros:** Thread-safe, lazy, minimal synchronization
**Cons:** Complex, `volatile` keyword needed
**Use When:** High-concurrency scenarios

***

### 5. Enum Singleton (Breaking-Proof)

```java
public enum Singleton {
    INSTANCE;
    
    public void doSomething() {
        // Business logic
    }
}

// Usage
Singleton.INSTANCE.doSomething();
```

**Pros:** Reflection-proof, serialization-proof, thread-safe
**Cons:** Not lazy, enum syntax unusual
**Use When:** Need absolute protection from breaking

***

## Breaking Singleton

### 1. Reflection Attack

```java
Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
constructor.setAccessible(true);
Singleton instance2 = constructor.newInstance();  // Creates second instance!
```

**Defense:**

```java
private Singleton() {
    if (SingletonHelper.INSTANCE != null) {
        throw new RuntimeException("Use getInstance() method");
    }
}
```


### 2. Serialization Attack

```java
// Serialize
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
oos.writeObject(instance1);

// Deserialize - creates NEW instance!
ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singleton.ser"));
Singleton instance2 = (Singleton) ois.readObject();
```

**Defense:**

```java
protected Object readResolve() {
    return getInstance();
}
```


### 3. Cloning Attack

**Defense:**

```java
@Override
protected Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
}
```


***

## When to Use Singleton

✅ **Use When:**

- Database connection pool
- Logger
- Configuration manager
- Cache
- Thread pool
- Device drivers

❌ **Don't Use When:**

- Need multiple instances with different state
- Testing (hard to mock)
- Need flexibility in object creation

***

# 3. Builder Pattern

## Definition

Separates the construction of a complex object from its representation, allowing step-by-step creation.

## Core Concepts

### Problem It Solves

- Avoid telescoping constructors (too many parameters)
- Make object immutable
- Readable object creation


### Key Characteristics

- ✅ Private constructor (force builder usage)
- ✅ Builder class (usually static inner class)
- ✅ Fluent API (method chaining)
- ✅ `build()` method returns final object

***

## Implementation

### Example: Email Builder

```java
public class Email {
    // Required fields
    private final String to;
    private final String subject;
    
    // Optional fields
    private final String cc;
    private final String bcc;
    private final String body;
    private final List<String> attachments;
    
    // Private constructor - force builder usage
    private Email(Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.body = builder.body;
        this.attachments = builder.attachments;
    }
    
    // Static inner Builder class
    public static class Builder {
        // Required fields
        private final String to;
        private final String subject;
        
        // Optional fields
        private String cc;
        private String bcc;
        private String body;
        private List<String> attachments = new ArrayList<>();
        
        // Constructor with required fields only
        public Builder(String to, String subject) {
            this.to = to;
            this.subject = subject;
        }
        
        // Optional field setters - return this for chaining
        public Builder cc(String cc) {
            this.cc = cc;
            return this;
        }
        
        public Builder bcc(String bcc) {
            this.bcc = bcc;
            return this;
        }
        
        public Builder body(String body) {
            this.body = body;
            return this;
        }
        
        public Builder addAttachment(String attachment) {
            this.attachments.add(attachment);
            return this;
        }
        
        // Build method with validation
        public Email build() {
            if (to == null || to.isEmpty()) {
                throw new IllegalStateException("To is required");
            }
            return new Email(this);
        }
    }
    
    // Only getters, no setters (immutable)
    public String getTo() { return to; }
    public String getSubject() { return subject; }
}
```


### Usage

```java
// Readable and flexible!
Email email = new Email.Builder("user@example.com", "Hello")
    .cc("cc@example.com")
    .body("This is the body")
    .addAttachment("file.pdf")
    .build();
```


***

## When to Use Builder

✅ **Use When:**

- Many constructor parameters (4+)
- Many optional parameters
- Want immutable objects
- Need validation before creation
- Want readable object creation

❌ **Don't Use When:**

- Simple objects with few fields
- Mutable objects are fine
- Overkill for 2-3 parameters

***

## Builder vs Constructor

| Telescoping Constructor | Builder Pattern |
| :-- | :-- |
| `new Email(to, subject, null, null, body, null)` ❌ | `new Email.Builder(to, subject).body(body).build()` ✅ |
| Hard to read | Self-documenting |
| All parameters required | Only required ones mandatory |
| Parameter order matters | Named parameters (method names) |


***

# 4. Factory Patterns

Factory patterns provide an interface for creating objects without specifying their exact classes.

## 4.1 Simple Factory

### Definition

A class with a **static method** that creates and returns objects based on input parameters.

### Implementation

```java
// Product interface
interface Payment {
    void processPayment(double amount);
}

// Concrete products
class CreditCardPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment: " + amount);
    }
}

class PayPalPayment implements Payment {
    public void processPayment(double amount) {
        System.out.println("Processing PayPal payment: " + amount);
    }
}

// Enum for type safety
enum PaymentType {
    CREDIT_CARD, PAYPAL, CRYPTO
}

// Simple Factory
class PaymentFactory {
    public static Payment createPayment(PaymentType type, String... details) {
        if (type == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        
        switch (type) {
            case CREDIT_CARD:
                return new CreditCardPayment(details);
            case PAYPAL:
                return new PayPalPayment(details);
            case CRYPTO:
                return new CryptoPayment(details);
            default:
                throw new IllegalArgumentException("Unknown payment type");
        }
    }
}

// Usage
Payment payment = PaymentFactory.createPayment(PaymentType.CREDIT_CARD, details);
payment.processPayment(100.0);
```


### When to Use

✅ Simple object creation
✅ Few product types
✅ Centralized validation
❌ Violates Open/Closed Principle (must modify factory for new types)

***

## 4.2 Factory Method

### Definition

An **abstract class/interface** with a factory method that subclasses override to create specific products.

### Implementation

```java
// Product interface
interface Notification {
    void send();
}

// Concrete products
class EmailNotification implements Notification {
    public void send() {
        System.out.println("Sending email notification");
    }
}

class SMSNotification implements Notification {
    public void send() {
        System.out.println("Sending SMS notification");
    }
}

// Abstract factory with template method
abstract class NotificationFactory {
    // Factory method - subclasses override
    protected abstract Notification createNotification();
    
    // Template method - common workflow
    public void processNotification() {
        logStart();
        Notification notification = createNotification();
        validateNotification(notification);
        notification.send();
        logComplete();
    }
    
    private void logStart() {
        System.out.println("Starting notification process");
    }
    
    private void validateNotification(Notification n) {
        // Validation logic
    }
    
    private void logComplete() {
        System.out.println("Notification sent successfully");
    }
}

// Concrete factories
class EmailNotificationFactory extends NotificationFactory {
    @Override
    protected Notification createNotification() {
        return new EmailNotification();
    }
}

class SMSNotificationFactory extends NotificationFactory {
    @Override
    protected Notification createNotification() {
        return new SMSNotification();
    }
}

// Usage
NotificationFactory factory = new EmailNotificationFactory();
factory.processNotification();  // Handles creation + workflow
```


### When to Use

✅ Each type has different creation logic
✅ Want extensibility (add new types easily)
✅ Need template method (common workflow)
✅ Follows Open/Closed Principle

***

## 4.3 Abstract Factory

### Definition

An **interface for creating families of related objects** without specifying their concrete classes.

### Implementation

```java
// Product interfaces
interface Button {
    void render();
}

interface Checkbox {
    void render();
}

interface TextField {
    void render();
}

// Concrete products - Windows family
class WindowsButton implements Button {
    public void render() {
        System.out.println("Rendering Windows button");
    }
}

class WindowsCheckbox implements Checkbox {
    public void render() {
        System.out.println("Rendering Windows checkbox");
    }
}

class WindowsTextField implements TextField {
    public void render() {
        System.out.println("Rendering Windows textfield");
    }
}

// Concrete products - MacOS family
class MacOSButton implements Button {
    public void render() {
        System.out.println("Rendering MacOS button");
    }
}

class MacOSCheckbox implements Checkbox {
    public void render() {
        System.out.println("Rendering MacOS checkbox");
    }
}

class MacOSTextField implements TextField {
    public void render() {
        System.out.println("Rendering MacOS textfield");
    }
}

// Abstract factory interface
interface UIComponentFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
}

// Concrete factories
class WindowsFactory implements UIComponentFactory {
    public Button createButton() {
        return new WindowsButton();
    }
    
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
    
    public TextField createTextField() {
        return new WindowsTextField();
    }
}

class MacOSFactory implements UIComponentFactory {
    public Button createButton() {
        return new MacOSButton();
    }
    
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
    
    public TextField createTextField() {
        return new MacOSTextField();
    }
}

// Usage
UIComponentFactory factory = new WindowsFactory();  // Easy to switch themes!
Button button = factory.createButton();
Checkbox checkbox = factory.createCheckbox();
TextField textField = factory.createTextField();

// All components are guaranteed to be Windows-style
button.render();
checkbox.render();
textField.render();
```


### When to Use

✅ Creating families of related objects
✅ Need consistency (no mixing from different families)
✅ Want to switch entire product families easily
❌ Don't use if only one product type

***

## Factory Patterns Comparison

| Aspect | Simple Factory | Factory Method | Abstract Factory |
| :-- | :-- | :-- | :-- |
| **Structure** | Static method | Abstract method + subclasses | Interface with multiple methods |
| **Products** | Single type | Single type | Multiple related types |
| **Open/Closed** | ❌ Violates | ✅ Follows | ✅ Follows |
| **Extensibility** | Modify factory | Add new subclass | Add new factory + products |
| **Complexity** | Low | Medium | High |
| **Use Case** | Loggers, DB connections | Notifications, Reports | UI themes, Document formats |
| **Class Count** | Few | More (1 factory/type) | Most (factories × products) |


***

# 5. Prototype Pattern

## Definition

Creates new objects by **cloning existing prototypes** instead of creating from scratch.

## Core Concepts

### Problem It Solves

- Object creation is expensive (database, files, network)
- Need many similar objects with small variations
- Want to preserve object state


### Key Idea

Instead of `new Character()` → Clone `template.clone()`

***

## Shallow vs Deep Copy

### Shallow Copy

- Copies primitive fields by value
- Copies object **references** (shares same objects)
- Fast but dangerous


### Deep Copy

- Copies primitive fields by value
- Creates **new objects** for all nested references
- Slower but safe


### Visual Example

```java
class Character {
    String name;      // Immutable
    Weapon weapon;    // Mutable object
}
```

**Shallow Copy:**

```
original.weapon ──┐
                  ↓
               [Weapon]
                  ↑
   clone.weapon ──┘
// Both share same Weapon!
```

**Deep Copy:**

```
original.weapon ──→ [Weapon 1]

   clone.weapon ──→ [Weapon 2]
// Each has own Weapon!
```


***

## Implementation

### Approach: Copy Constructor + clone()

```java
// Weapon class
class Weapon {
    private String name;
    private int damage;
    
    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    
    // Private copy constructor
    private Weapon(Weapon other) {
        this.name = other.name;
        this.damage = other.damage;
    }
    
    // Public clone method
    public Weapon clone() {
        return new Weapon(this);
    }
}

// Character class
class Character {
    private String name;
    private int level;
    private Weapon weapon;
    private List<Skill> skills;
    
    public Character(String name, int level, Weapon weapon, List<Skill> skills) {
        this.name = name;
        this.level = level;
        this.weapon = weapon;
        this.skills = skills;
    }
    
    // Private copy constructor with deep copy
    private Character(Character other) {
        this.name = other.name;
        this.level = other.level;
        this.weapon = other.weapon.clone();  // Deep copy!
        this.skills = List.copyOf(other.skills);  // Immutable list
    }
    
    // Public clone method
    public Character clone() {
        return new Character(this);
    }
    
    // Static factory for templates
    public static Character createWarriorTemplate() {
        return new Character(
            "Warrior",
            1,
            new Weapon("Sword", 90),
            List.of(new Skill("High Health"))
        );
    }
}

// Usage
Character warriorTemplate = Character.createWarriorTemplate();  // Expensive (9 seconds)
Character player1 = warriorTemplate.clone();  // Fast (0.01 seconds)
Character player2 = warriorTemplate.clone();  // Fast (0.01 seconds)

player1.setName("Player1");
player1.getWeapon().setDamage(1000);

// Template unchanged!
System.out.println(warriorTemplate.getWeapon().getDamage());  // 90
System.out.println(player1.getWeapon().getDamage());          // 1000
```


***

## What Needs Deep Copy?

| Field Type | Deep Copy? | Why |
| :-- | :-- | :-- |
| Primitives (int, boolean) | N/A | Always value-copied |
| Immutable (String, Integer) | No | Safe to share |
| Mutable Objects | **Yes** | Changes affect both if shared |
| Collections (List, Map) | **Yes** | Need new instance |
| Objects inside Collections | Depends | If mutable, yes |


***

## When to Use Prototype

✅ **Use When:**

- Object creation is expensive (database, files, network)
- Need many similar objects with variations
- Complex object state to preserve
- Want to avoid subclassing for variations

❌ **Don't Use When:**

- Object creation is simple and fast
- Objects are fundamentally different
- Deep copy is too complex


### Real-World Examples

- Game characters (loaded from database)
- Document templates (complex formatting)
- Configuration objects (expensive to build)
- Cached objects needing customization

***

# 6. Decision Tree: When to Use Which?

```
Need to control object creation?
│
├─ Need only ONE instance globally?
│  └─ YES → SINGLETON
│     Examples: Logger, Config, DB Connection
│
├─ Object has many parameters (4+)?
│  └─ YES → BUILDER
│     Examples: Email, HTTP Request, Complex Config
│
├─ Object creation is expensive?
│  └─ YES → PROTOTYPE
│     Examples: Game characters, Document templates
│
└─ Need to create different types of objects?
   │
   ├─ Creating FAMILIES of related objects?
   │  └─ YES → ABSTRACT FACTORY
   │     Examples: UI themes, Document formats
   │
   ├─ Each type has different creation logic?
   │  └─ YES → FACTORY METHOD
   │     Examples: Notifications, Reports
   │
   └─ Simple creation, few types?
      └─ YES → SIMPLE FACTORY
         Examples: Loggers, DB connections
```


***

# 7. Pattern Interactions

## How Patterns Work Together

### Singleton + Factory

```java
class DatabaseFactory {
    // Singleton factory
    private static DatabaseFactory instance = new DatabaseFactory();
    
    public static DatabaseFactory getInstance() {
        return instance;
    }
    
    // Factory method
    public Database createDatabase(DatabaseType type) {
        switch (type) {
            case MYSQL: return new MySQLDatabase();
            case POSTGRES: return new PostgresDatabase();
        }
    }
}
```


### Builder + Factory

```java
// Factory creates builders
class EmailBuilderFactory {
    public static Email.Builder createBusinessEmailBuilder() {
        return new Email.Builder()
            .from("business@company.com")
            .signature("Best Regards, Company");
    }
}
```


### Prototype + Factory

```java
// Factory provides prototypes
class CharacterFactory {
    private static Character warriorTemplate = loadWarriorTemplate();
    
    public static Character createWarrior() {
        return warriorTemplate.clone();
    }
}
```


***

# 8. Interview Questions

## Singleton

**Q: Why use Singleton over static class?**
A: Singleton can implement interfaces, be passed as parameter, support lazy initialization, and be extended.

**Q: Is Singleton thread-safe?**
A: Depends on implementation. Bill Pugh and Enum are thread-safe. Lazy without synchronization is NOT.

**Q: How to break Singleton?**
A: Reflection, serialization, cloning. Defenses: check in constructor, implement readResolve(), throw exception in clone().

***

## Builder

**Q: Why make constructor private in Builder?**
A: Force users to use Builder, ensures validation before object creation, makes object immutable.

**Q: Builder vs Telescoping Constructor?**
A: Builder is readable (named parameters), flexible (optional fields), allows validation before creation.

**Q: When NOT to use Builder?**
A: Simple objects with 2-3 fields, mutable objects, when constructor is clear enough.

***

## Factory

**Q: Difference between Factory Method and Abstract Factory?**
A: Factory Method creates ONE type with different logic per subclass. Abstract Factory creates FAMILIES of related objects.

**Q: Why use Factory over new?**
A: Loose coupling (depend on interface), extensibility (add types without modifying client), centralized logic.

**Q: Simple Factory violates OCP. Why still use it?**
A: Good for simple cases, centralized validation, when types rarely change. For frequent changes, use Factory Method.

***

## Prototype

**Q: Shallow vs Deep copy?**
A: Shallow copies references (shares objects). Deep copies create new objects (independence).

**Q: When use Prototype over new?**
A: When creation is expensive (database, files), need variations of complex object, want to preserve state.

**Q: Cloneable interface necessary?**
A: No. Copy constructors are better (no exceptions, work with final fields, clearer).

***

# 9. Quick Reference Cheat Sheet

## One-Line Summary

| Pattern | One-Liner |
| :-- | :-- |
| **Singleton** | ONE instance, global access |
| **Builder** | Step-by-step construction, immutable result |
| **Simple Factory** | Static method creates objects based on input |
| **Factory Method** | Subclasses decide which class to instantiate |
| **Abstract Factory** | Families of related objects without concrete classes |
| **Prototype** | Clone existing objects instead of new |


***

## Code Signatures

### Singleton

```java
private static final Singleton INSTANCE = new Singleton();
public static Singleton getInstance() { return INSTANCE; }
```


### Builder

```java
private Email(Builder builder) { ... }
public static class Builder {
    public Builder field(Type val) { this.field = val; return this; }
    public Email build() { return new Email(this); }
}
```


### Simple Factory

```java
public static Product createProduct(ProductType type) {
    switch(type) { ... }
}
```


### Factory Method

```java
abstract class Factory {
    protected abstract Product createProduct();
    public void processProduct() { ... }
}
```


### Abstract Factory

```java
interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}
```


### Prototype

```java
private Class(Class other) { this.field = other.field.clone(); }
public Class clone() { return new Class(this); }
```


***

## Complexity Ranking

**Simplest → Most Complex:**

1. **Simple Factory** (static method)
2. **Singleton** (one instance)
3. **Prototype** (copy constructor)
4. **Builder** (step-by-step)
5. **Factory Method** (subclasses)
6. **Abstract Factory** (families)

***

## Performance Considerations

| Pattern | Performance Notes |
| :-- | :-- |
| Singleton | No overhead after first access |
| Builder | Slight overhead (extra object), but negligible |
| Simple Factory | Switch/if overhead minimal |
| Factory Method | Virtual method call overhead |
| Abstract Factory | Multiple virtual calls |
| Prototype | Clone faster than new + initialization |


***

# 10. Real-World Mapping

## By Industry

### Game Development

- **Singleton:** Game manager, Audio manager
- **Builder:** Character customization
- **Factory:** Enemy spawning
- **Prototype:** Clone game levels, NPCs


### E-Commerce

- **Singleton:** Shopping cart, User session
- **Builder:** Order, Invoice
- **Factory:** Payment methods
- **Prototype:** Product templates


### Enterprise Software

- **Singleton:** Logger, Config, DB pool
- **Builder:** Complex reports, Emails
- **Factory:** Document types, Notifications
- **Prototype:** Configuration templates

***

# Summary: Your Mastery Checklist

After mastering all 4 Creational patterns, you should be able to:

- ✅ Explain each pattern in 30 seconds
- ✅ Identify which pattern fits a given scenario
- ✅ Implement each pattern from scratch
- ✅ Explain trade-offs and alternatives
- ✅ Know when NOT to use each pattern
- ✅ Handle thread-safety (Singleton)
- ✅ Implement deep copy correctly (Prototype)
- ✅ Choose right Factory type (Simple vs Method vs Abstract)
- ✅ Make objects immutable (Builder)
- ✅ Answer interview questions confidently

***

