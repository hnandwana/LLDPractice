<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# Factory Pattern - Comprehensive Revision Notes 📚

## Overview

**Factory Patterns** are creational design patterns that provide an interface for creating objects without specifying their exact classes. They promote loose coupling and follow the Open/Closed Principle.

***

## 1. Simple Factory

### Definition

A class with a static method that creates and returns objects based on input parameters.

### Structure

```java
// Product Interface
interface Payment {
    void processPayment(double amount);
}

// Concrete Products
class CreditCardPayment implements Payment { ... }
class PayPalPayment implements Payment { ... }

// Simple Factory
class PaymentFactory {
    public static Payment createPayment(PaymentType type, String... details) {
        switch(type) {
            case CREDIT_CARD: return new CreditCardPayment(details);
            case PAYPAL: return new PayPalPayment(details);
            default: throw new IllegalArgumentException();
        }
    }
}

// Client Usage
Payment payment = PaymentFactory.createPayment(PaymentType.CREDIT_CARD, details);
```


### Characteristics

- **Static factory method**
- **Centralized creation logic**
- **Uses switch/if-else** to determine which object to create
- **Violates Open/Closed Principle** (must modify factory for new types)


### When to Use

✅ Simple object creation with minimal logic
✅ Few product types
✅ Centralized validation needed
✅ Quick prototyping

### Pros \& Cons

| Pros | Cons |
| :-- | :-- |
| Simple to implement | Violates OCP |
| Centralized logic | Single point of failure |
| Easy to understand | Not easily testable (static) |
| Type safety with enums | Factory grows with new types |


***

## 2. Factory Method

### Definition

An abstract class/interface with a factory method that subclasses override to create specific product instances. Often combined with Template Method pattern.

### Structure

```java
// Product Interface
interface Notification {
    void send();
}

// Concrete Products
class EmailNotification implements Notification { ... }
class SMSNotification implements Notification { ... }

// Abstract Factory
abstract class NotificationFactory {
    // Factory Method
    abstract Notification createNotification();
    
    // Template Method
    public void processNotification() {
        logStart();
        Notification notification = createNotification();
        validateNotification(notification);
        notification.send();
        logComplete();
    }
}

// Concrete Factories
class EmailNotificationFactory extends NotificationFactory {
    @Override
    Notification createNotification() {
        return new EmailNotification(subject, body);
    }
}

// Client Usage
NotificationFactory factory = new EmailNotificationFactory(params);
factory.processNotification();
```


### Characteristics

- **Abstract factory method** overridden by subclasses
- **One factory per product type**
- **Template method** provides common workflow
- **Follows Open/Closed Principle** (add new class, don't modify existing)


### When to Use

✅ Each product type has different creation logic
✅ Need extensibility (easy to add new types)
✅ Want to follow OCP strictly
✅ Need testable code (instance methods)
✅ Common workflow across products (template method)

### Pros \& Cons

| Pros | Cons |
| :-- | :-- |
| Follows OCP | More classes (one per type) |
| Highly extensible | More complex than Simple Factory |
| Easily testable | Overkill for simple cases |
| Template method support | Client must know concrete factory |


***

## 3. Abstract Factory

### Definition

An interface for creating **families of related objects** without specifying their concrete classes. Ensures all created objects belong to the same family.

### Structure

```java
// Product Interfaces
interface Button { void render(); }
interface Checkbox { void render(); }
interface TextField { void render(); }

// Concrete Products - Windows Family
class WindowsButton implements Button { ... }
class WindowsCheckbox implements Checkbox { ... }
class WindowsTextField implements TextField { ... }

// Concrete Products - MacOS Family
class MacOSButton implements Button { ... }
class MacOSCheckbox implements Checkbox { ... }
class MacOSTextField implements TextField { ... }

// Abstract Factory
interface UIComponentFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
}

// Concrete Factories
class WindowsFactory implements UIComponentFactory {
    public Button createButton() { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
    public TextField createTextField() { return new WindowsTextField(); }
}

class MacOSFactory implements UIComponentFactory {
    public Button createButton() { return new MacOSButton(); }
    public Checkbox createCheckbox() { return new MacOSCheckbox(); }
    public TextField createTextField() { return new MacOSTextField(); }
}

// Client Usage
UIComponentFactory factory = new WindowsFactory();
Button btn = factory.createButton();
Checkbox chk = factory.createCheckbox();
TextField txt = factory.createTextField();
// All components are guaranteed to be Windows-style
```


### Characteristics

- **Creates families of related products**
- **One method per product type** in factory interface
- **Ensures consistency** within product family
- **Two-dimensional extensibility** (add families OR products)


### When to Use

✅ Creating families of related objects that must work together
✅ Need to ensure consistency (no mixing from different families)
✅ System needs to be independent of product creation
✅ Multiple related products that belong together

### Pros \& Cons

| Pros | Cons |
| :-- | :-- |
| Family consistency guaranteed | Adding new product type is expensive |
| Follows OCP for families | Must modify all factories for new product |
| Easy theme/family switching | More complex structure |
| Prevents mixing incompatible products | Overkill if only one product type |


***

## Comparison: Simple vs Factory Method vs Abstract Factory

| Aspect | Simple Factory | Factory Method | Abstract Factory |
| :-- | :-- | :-- | :-- |
| **Intent** | Create one object | Create one object with extensibility | Create families of objects |
| **Structure** | Static method | Abstract method + subclasses | Interface with multiple create methods |
| **Products** | Single type | Single type | Multiple related types |
| **Extensibility** | Modify factory | Add new factory class | Add new factory + products |
| **OCP** | ❌ Violates | ✅ Follows | ✅ Follows |
| **Complexity** | Low | Medium | High |
| **Use Case** | Loggers, DB connections | Notifications, Reports | UI themes, Document formats |
| **Class Count** | Few | More (1 factory/type) | Most (factories × products) |


***

## When to Use Which Pattern? - Decision Tree

```
Do you need to create FAMILIES of related objects?
│
├─ YES → Abstract Factory
│   Example: UI themes (Button + Checkbox + TextField per theme)
│
└─ NO → Do you have complex creation logic per type?
    │
    ├─ YES → Factory Method
    │   Example: Notifications with different validation
    │
    └─ NO → Simple Factory
        Example: Basic logger instances
```


### Quick Rules

- **1 product type + Simple logic** → Simple Factory
- **1 product type + Complex/Different logic per type** → Factory Method
- **Multiple related products + Must work together** → Abstract Factory

***

## SOLID Principles Coverage

| Pattern | Principles Followed |
| :-- | :-- |
| **Simple Factory** | SRP (centralized creation) |
| **Factory Method** | **OCP** (extensible), SRP, DIP |
| **Abstract Factory** | **OCP** (for families), SRP, DIP |

**Key Insight:** Factory Method and Abstract Factory both follow OCP, but in different dimensions:

- **Factory Method:** Open for adding new **product types**
- **Abstract Factory:** Open for adding new **product families**

***

## Common Mistakes \& Best Practices

### ❌ Common Mistakes

**1. Mixing Factory Types**

```java
// Bad: Using Simple Factory when you need extensibility
class NotificationFactory {
    static Notification create(String type) { ... } // Violates OCP!
}
```

**2. Not Using Interface Types**

```java
// Bad
WindowsFactory factory = new WindowsFactory();

// Good
UIComponentFactory factory = new WindowsFactory();
```

**3. Forgetting Validation**

```java
// Bad: No validation
class PaymentFactory {
    static Payment create(PaymentType type, String... details) {
        return new CreditCardPayment(details); // What if details are invalid?
    }
}
```

**4. Abstract Factory - Adding Products Without Planning**

```java
// Adding Dropdown means modifying ALL factories
interface UIComponentFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
    Dropdown createDropdown(); // ← All 3 factories must implement!
}
```


### ✅ Best Practices

**1. Use Enums for Type Safety**

```java
enum PaymentType { CREDIT_CARD, PAYPAL, CRYPTO }
// Better than String - compile-time checking
```

**2. Validate in Factory**

```java
public static Payment createPayment(PaymentType type, String... details) {
    if (details == null || details.length < 3) {
        throw new IllegalArgumentException("Invalid payment details");
    }
    // ... create payment
}
```

**3. Factory Method: Use Template Method Pattern**

```java
abstract class NotificationFactory {
    abstract Notification createNotification();
    
    public void processNotification() {
        log();
        Notification n = createNotification();
        validate(n);
        n.send();
    }
}
```

**4. Abstract Factory: Keep Product Families Small**

- Don't add too many products to one family
- If you have 10+ products, reconsider your design

**5. Depend on Abstractions**

```java
// Always use interface types
UIComponentFactory factory = getFactory(); // Not: WindowsFactory factory
```


***

## Code Templates

### Simple Factory Template

```java
// 1. Product interface
public interface Product {
    void operation();
}

// 2. Concrete products
public class ConcreteProductA implements Product { }
public class ConcreteProductB implements Product { }

// 3. Enum for type safety
public enum ProductType { A, B }

// 4. Simple Factory
public class ProductFactory {
    public static Product createProduct(ProductType type) {
        switch(type) {
            case A: return new ConcreteProductA();
            case B: return new ConcreteProductB();
            default: throw new IllegalArgumentException();
        }
    }
}

// Usage
Product product = ProductFactory.createProduct(ProductType.A);
```


### Factory Method Template

```java
// 1. Product interface
public interface Product {
    void operation();
}

// 2. Concrete products
public class ConcreteProductA implements Product { }

// 3. Abstract factory with template method
public abstract class ProductFactory {
    // Factory method
    protected abstract Product createProduct();
    
    // Template method
    public void processProduct() {
        Product product = createProduct();
        product.operation();
    }
}

// 4. Concrete factory
public class ConcreteFactoryA extends ProductFactory {
    @Override
    protected Product createProduct() {
        return new ConcreteProductA();
    }
}

// Usage
ProductFactory factory = new ConcreteFactoryA();
factory.processProduct();
```


### Abstract Factory Template

```java
// 1. Product interfaces
public interface ProductA { void operationA(); }
public interface ProductB { void operationB(); }

// 2. Concrete products - Family 1
public class Product1A implements ProductA { }
public class Product1B implements ProductB { }

// 3. Concrete products - Family 2
public class Product2A implements ProductA { }
public class Product2B implements ProductB { }

// 4. Abstract factory
public interface AbstractFactory {
    ProductA createProductA();
    ProductB createProductB();
}

// 5. Concrete factories
public class ConcreteFactory1 implements AbstractFactory {
    public ProductA createProductA() { return new Product1A(); }
    public ProductB createProductB() { return new Product1B(); }
}

public class ConcreteFactory2 implements AbstractFactory {
    public ProductA createProductA() { return new Product2A(); }
    public ProductB createProductB() { return new Product2B(); }
}

// Usage
AbstractFactory factory = new ConcreteFactory1();
ProductA productA = factory.createProductA();
ProductB productB = factory.createProductB();
// Both products from same family
```


***

## Your Implementations - Quick Reference

### 1. Simple Factory (Payment System)

- **Factory:** `PaymentFactory.createPayment(PaymentType, String...)`
- **Products:** CreditCardPayment, PayPalPayment, CryptoPayment
- **Key Learning:** Varargs, enum type safety, validation in factory


### 2. Factory Method (Notification System)

- **Abstract Factory:** `NotificationFactory` with template method
- **Concrete Factories:** EmailFactory, SMSFactory, PushFactory, SlackFactory
- **Products:** EmailNotification, SMSNotification, etc.
- **Key Learning:** Template method, business rule validation, OCP


### 3. Abstract Factory (UI System)

- **Abstract Factory:** `UIComponentFactory`
- **Concrete Factories:** WindowsFactory, MacOSFactory, LinuxFactory
- **Product Families:** Button, Checkbox, TextField (3 per theme)
- **Key Learning:** Family consistency, dependency inversion, easy theme switching

***

## Interview Questions \& Answers

**Q: Difference between Factory Method and Abstract Factory?**
A: Factory Method creates **one type of product** with different creation logic per subclass. Abstract Factory creates **families of related products** to ensure they work together.

**Q: Why not just use `new` keyword?**
A: Direct instantiation couples code to concrete classes. Factories allow:

- Dependency Inversion (depend on abstractions)
- Easy extensibility (add new types without changing client)
- Centralized validation and logic
- Runtime decision of which class to instantiate

**Q: When would Simple Factory be better than Factory Method?**
A: When creation logic is simple, you have few types, and extensibility isn't a priority. Example: Creating logger instances with basic configuration.

**Q: Cost of Abstract Factory?**
A: Adding new product types (e.g., Dropdown) requires modifying ALL factory implementations. But adding new families (e.g., AndroidTheme) is easy.

***

## Summary Checklist

After studying Factory patterns, you should be able to:

- ✅ Identify when to use each factory pattern
- ✅ Implement all three patterns from scratch
- ✅ Explain trade-offs between patterns
- ✅ Apply SOLID principles (especially OCP)
- ✅ Choose appropriate pattern for real-world scenarios
- ✅ Understand extensibility implications
- ✅ Use enums for type safety
- ✅ Depend on abstractions, not concrete classes

***

