# Decorator Pattern - Revision Notes 📝


***

## Overview

**Decorator Pattern** is a structural design pattern that allows you to **add new behavior to objects dynamically** by wrapping them with decorator objects, without modifying their structure or using inheritance.

**Think of it as:** Layering features like dressing up - you add layers (jacket, scarf, hat) without changing the base clothing.

***

## The Problem Decorator Solves

### Without Decorator (Class Explosion ❌)

```java
// Need a class for every combination!
Coffee
├── SimpleCoffee
├── CoffeeWithMilk
├── CoffeeWithSugar
├── CoffeeWithMilkAndSugar
├── CoffeeWithMilkAndSugarAndWhippedCream
└── ... (exponential growth!)

// With 5 add-ons: 2^5 = 32 classes!
```


### With Decorator (Flexible ✅)

```java
Coffee coffee = new SimpleCoffee();           // Base
coffee = new MilkDecorator(coffee);          // Add milk
coffee = new SugarDecorator(coffee);         // Add sugar
coffee = new WhippedCreamDecorator(coffee);  // Add whipped cream

// 1 base + 3 decorators = 4 classes for infinite combinations!
```


***

## When to Use Decorator Pattern

✅ **Use Decorator When:**

1. Need to add responsibilities to objects **dynamically** at runtime
2. Want to add features **without modifying** existing code (Open/Closed Principle)
3. Extension by **subclassing is impractical** (class explosion)
4. Need **flexible combinations** of features
5. Want to add/remove features **at runtime**
6. Features are **independent** and can be mixed

❌ **Don't Use When:**

- Simple inheritance is sufficient
- Objects don't share common interface
- Order of decoration is complex and confusing
- Creating fundamentally different types (use inheritance)
- Only one combination needed

***

## Real-World Examples

| Scenario | Use Case |
| :-- | :-- |
| **Coffee Shop** ☕ | Plain coffee + milk + sugar + whipped cream |
| **Java I/O** | FileReader → BufferedReader → LineNumberReader |
| **GUI Components** | Window → BorderDecorator → ScrollbarDecorator |
| **Text Formatting** | PlainText → BoldDecorator → ItalicDecorator |
| **Notifications** | Message → EncryptionDecorator → CompressionDecorator |
| **Image Processing** | Image → BlurFilter → GrayscaleFilter |
| **Web Requests** | Request → AuthDecorator → LoggingDecorator |


***

## Decorator Pattern Structure

```
┌─────────────────┐
│   Component     │ ← Interface/Abstract class
│   (Coffee)      │
│   + operation() │
└────────┬────────┘
         │
    ┌────┴────────────────┐
    │                     │
    │                     │
┌───▼────────┐    ┌───────▼──────────┐
│  Concrete  │    │    Decorator     │
│ Component  │    │ (Base Decorator) │
│(SimpleCoffee)   │  - component     │
└────────────┘    │  + operation()   │
                  └────────┬─────────┘
                           │
                  ┌────────┴─────────┐
                  │                  │
          ┌───────▼────────┐  ┌──────▼──────┐
          │MilkDecorator   │  │SugarDecorator│
          │+ operation()   │  │+ operation() │
          └────────────────┘  └──────────────┘
```

**Key Components:**

1. **Component (Interface):** Defines operations for both concrete and decorated objects
2. **Concrete Component:** Base object without decoration
3. **Decorator (Abstract):** Base class for all decorators, wraps Component
4. **Concrete Decorators:** Add specific features

***

## Core Implementation Pattern

### 1. Component Interface

```java
public interface Coffee {
    String getDescription();
    double getCost();
}
```


### 2. Concrete Component (Base Object)

```java
public class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }
    
    @Override
    public double getCost() {
        return 2.0;
    }
}
```


### 3. Base Decorator (Abstract)

```java
@AllArgsConstructor
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;  // Wraps Component
    
    // Delegates to wrapped object by default
    public String getDescription() {
        return coffee.getDescription();
    }
    
    public double getCost() {
        return coffee.getCost();
    }
}
```

**Why abstract base decorator?**

- ✅ Avoids code duplication (field + constructor)
- ✅ Provides default delegation
- ✅ All decorators share common structure
- ✅ Follows DRY principle


### 4. Concrete Decorators

```java
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";  // Delegate + add
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.5;  // Delegate + add
    }
}

public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Sugar";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.3;
    }
}
```


### 5. Usage (Stacking Decorators)

```java
// Start with base
Coffee coffee = new SimpleCoffee();  // $2.00

// Add decorators
coffee = new MilkDecorator(coffee);    // +$0.50 = $2.50
coffee = new SugarDecorator(coffee);   // +$0.30 = $2.80

System.out.println(coffee.getDescription());  
// "Simple Coffee, Milk, Sugar"

System.out.println(coffee.getCost());         
// 2.80
```


***

## Key Characteristics

### 1. Same Interface (IS-A)

```java
Coffee coffee = new SimpleCoffee();           // Coffee
coffee = new MilkDecorator(coffee);          // Still Coffee
coffee = new SugarDecorator(coffee);         // Still Coffee

// All implement same interface!
```


### 2. Wrapping (HAS-A)

```java
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;  // Contains a Coffee
}
```


### 3. Delegation + Enhancement

```java
public double getCost() {
    return coffee.getCost() + 0.5;  // Delegate, then enhance
}
```


### 4. Stacking (Layering)

```java
// Can wrap decorators with other decorators infinitely!
coffee = new Decorator1(coffee);
coffee = new Decorator2(coffee);
coffee = new Decorator3(coffee);
```


### 5. Runtime Flexibility

```java
// Add/remove features at runtime
if (userIsPremium) {
    notification = new EncryptionDecorator(notification);
}

if (needsTimestamp) {
    notification = new TimestampDecorator(notification);
}
```


***

## IS-A + HAS-A Relationship 🔑

**Why BOTH are needed:**

### IS-A (Implements Interface)

```java
class CoffeeDecorator implements Coffee { }
```

**Purpose:** Decorator IS-A Coffee, so it can be used anywhere Coffee is expected!

**Enables:**

- ✅ Polymorphism
- ✅ Stacking decorators
- ✅ Uniform interface


### HAS-A (Contains Component)

```java
protected Coffee coffee;
```

**Purpose:** Decorator HAS-A Coffee, so it can wrap and delegate!

**Enables:**

- ✅ Delegation to wrapped object
- ✅ Adding behavior before/after delegation
- ✅ Flexible composition


### Why Not Inheritance Only?

```java
// Wrong approach
abstract class CoffeeDecorator extends SimpleCoffee {
    protected SimpleCoffee coffee;  // Can only wrap SimpleCoffee!
}
```

**Problems:**

- ❌ Can't wrap other decorators (type mismatch)
- ❌ Tight coupling to concrete class
- ❌ Can't wrap different implementations
- ❌ Redundant data (inherited + wrapped)

**Correct: Interface + Composition!**

```java
abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;  // Can wrap ANY Coffee!
}
```


***

## Order of Decoration Matters! ⚠️

### Visual: Call Chain

```java
Coffee c = new SimpleCoffee();
c = new MilkDecorator(c);
c = new SugarDecorator(c);

// Structure:
SugarDecorator
  └─ wraps → MilkDecorator
               └─ wraps → SimpleCoffee

// When calling c.getCost():
SugarDecorator.getCost()
  → calls coffee.getCost() (MilkDecorator)
     → MilkDecorator.getCost()
        → calls coffee.getCost() (SimpleCoffee)
           → SimpleCoffee.getCost() returns 2.0
        → adds 0.5, returns 2.5
  → adds 0.3, returns 2.8
```


### Order Example: Encryption + Timestamp

**Order 1: Encrypt THEN Timestamp**

```java
n = new EncryptionDecorator(n);      // Inner
n = new TimestampDecorator(n);       // Outer

// Output: [TIMESTAMP: 1234] [ENCRYPTED: xyz]
// Timestamp is VISIBLE (added after encryption)
```

**Order 2: Timestamp THEN Encrypt**

```java
n = new TimestampDecorator(n);       // Inner
n = new EncryptionDecorator(n);      // Outer

// Output: [ENCRYPTED: abc]
// Timestamp is ENCRYPTED (was added before encryption)
```


### Best Practices for Order

**Compression + Encryption:**

```java
// Good: Compress first (smaller data to encrypt)
n = new CompressionDecorator(n);
n = new EncryptionDecorator(n);

// Bad: Encrypt first (encrypted data doesn't compress)
n = new EncryptionDecorator(n);
n = new CompressionDecorator(n);  // Compression won't help!
```

**Why?** Encrypted data is pseudo-random with no patterns for compression!

***

## Code Templates

### Template 1: Basic Decorator

```java
// 1. Component interface
interface Component {
    String operation();
}

// 2. Concrete component
class ConcreteComponent implements Component {
    public String operation() {
        return "Base";
    }
}

// 3. Base decorator
@AllArgsConstructor
abstract class Decorator implements Component {
    protected Component component;
}

// 4. Concrete decorator
class ConcreteDecorator extends Decorator {
    public ConcreteDecorator(Component component) {
        super(component);
    }
    
    @Override
    public String operation() {
        return component.operation() + " + Extra";
    }
}

// 5. Usage
Component c = new ConcreteComponent();
c = new ConcreteDecorator(c);
System.out.println(c.operation());  // "Base + Extra"
```


***

### Template 2: Multiple Decorators

```java
interface Notification {
    String send();
    double getCost();
}

class BasicNotification implements Notification {
    private String message;
    
    public BasicNotification(String message) {
        this.message = message;
    }
    
    public String send() { return message; }
    public double getCost() { return 0.0; }
}

@AllArgsConstructor
abstract class NotificationDecorator implements Notification {
    protected Notification notification;
}

class EncryptionDecorator extends NotificationDecorator {
    public EncryptionDecorator(Notification notification) {
        super(notification);
    }
    
    public String send() {
        String msg = notification.send();
        return "[ENCRYPTED: " + encrypt(msg) + "]";
    }
    
    public double getCost() {
        return notification.getCost() + 0.5;
    }
}

class TimestampDecorator extends NotificationDecorator {
    public TimestampDecorator(Notification notification) {
        super(notification);
    }
    
    public String send() {
        return "[TIMESTAMP: " + getTime() + "] " + notification.send();
    }
    
    public double getCost() {
        return notification.getCost() + 0.3;
    }
}

// Usage: Stack multiple decorators
Notification n = new BasicNotification("Hello");
n = new EncryptionDecorator(n);
n = new TimestampDecorator(n);
System.out.println(n.send());       // [TIMESTAMP: ...] [ENCRYPTED: ...]
System.out.println(n.getCost());    // 0.8
```


***

### Template 3: With Lombok

```java
@AllArgsConstructor
public abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
}

@EqualsAndHashCode(callSuper = false)
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }
    
    @Override
    public String getDescription() {
        return coffee.getDescription() + ", Milk";
    }
    
    @Override
    public double getCost() {
        return coffee.getCost() + 0.5;
    }
}
```


***

## Common Mistakes \& Solutions

### ❌ Mistake 1: Using `private` Instead of `protected`

```java
// Bad
abstract class Decorator implements Component {
    private Component component;  // ❌ Child classes can't access!
}

class ConcreteDecorator extends Decorator {
    public String operation() {
        return component.operation();  // ❌ Compiler error!
    }
}
```

**Solution:** Use `protected`

```java
abstract class Decorator implements Component {
    protected Component component;  // ✅ Child classes can access
}
```


***

### ❌ Mistake 2: Forgetting to Delegate

```java
// Bad - doesn't delegate!
class MilkDecorator extends CoffeeDecorator {
    public double getCost() {
        return 0.5;  // ❌ Lost base cost!
    }
}
```

**Solution:** Always delegate to wrapped object

```java
class MilkDecorator extends CoffeeDecorator {
    public double getCost() {
        return coffee.getCost() + 0.5;  // ✅ Delegate + add
    }
}
```


***

### ❌ Mistake 3: Creating Decorator Inside Client

```java
// Bad - tight coupling
Coffee coffee = new MilkDecorator(new SimpleCoffee());  // OK
coffee = new SugarDecorator(new MilkDecorator(new SimpleCoffee()));  // Messy!
```

**Solution:** Build step by step or use builder/factory

```java
// Good - clear steps
Coffee coffee = new SimpleCoffee();
coffee = new MilkDecorator(coffee);
coffee = new SugarDecorator(coffee);
```


***

### ❌ Mistake 4: Wrong Order (Performance)

```java
// Bad - encrypt then compress (compression doesn't work!)
n = new EncryptionDecorator(n);
n = new CompressionDecorator(n);
```

**Solution:** Think about dependencies

```java
// Good - compress then encrypt
n = new CompressionDecorator(n);
n = new EncryptionDecorator(n);
```


***

## Pattern Comparisons

### Decorator vs Adapter

| Aspect | Decorator | Adapter |
| :-- | :-- | :-- |
| **Purpose** | Add behavior | Convert interface |
| **Interface** | Same interface | Different interface |
| **Number of objects** | Can stack infinitely | Usually wraps one |
| **When to use** | Extend functionality | Make incompatible things work |
| **Example** | Coffee + Milk + Sugar | Fahrenheit sensor → Celsius |

**Key:** Decorator keeps same interface, Adapter changes it!

***

### Decorator vs Inheritance

| Aspect | Decorator | Inheritance |
| :-- | :-- | :-- |
| **Flexibility** | Runtime composition | Compile-time hierarchy |
| **Combinations** | Infinite with N decorators | Exponential class explosion |
| **Modification** | Add without changing code | Requires subclassing |
| **Open/Closed** | ✅ Open for extension | ❌ Requires modification |

**Example:**

```java
// Inheritance: Need 8 classes for 3 toppings
CheesePizza, PepperoniPizza, MushroomPizza, 
CheesePepperoniPizza, CheeseMushroomPizza, etc.

// Decorator: Need 4 classes for infinite combinations
PlainPizza + CheeseDecorator + PepperoniDecorator + MushroomDecorator
```


***

### Decorator vs Proxy

| Aspect | Decorator | Proxy |
| :-- | :-- | :-- |
| **Purpose** | Add features | Control access |
| **Behavior** | Enhances functionality | Same functionality, controls when/how |
| **Stacking** | Multiple decorators | Usually single proxy |
| **Example** | Text + Bold + Italic | Lazy image loading |


***

## Real-World: Java I/O Streams

```java
BufferedReader reader = new BufferedReader(
    new InputStreamReader(
        new FileInputStream("file.txt")
    )
);
```

**Each layer adds functionality:**

- **FileInputStream:** Reads raw bytes from file
- **InputStreamReader:** Converts bytes → characters (also acts as Adapter!)
- **BufferedReader:** Adds buffering + `readLine()` method

**This is Decorator pattern in action!** ✅

***

## Testing Decorators

```java
@Test
public void testMilkDecorator() {
    // Arrange
    Coffee base = new SimpleCoffee();  // $2.00
    Coffee withMilk = new MilkDecorator(base);
    
    // Act
    double cost = withMilk.getCost();
    String description = withMilk.getDescription();
    
    // Assert
    assertEquals(2.5, cost, 0.01);
    assertEquals("Simple Coffee, Milk", description);
}

@Test
public void testStackedDecorators() {
    Coffee coffee = new SimpleCoffee();
    coffee = new MilkDecorator(coffee);
    coffee = new SugarDecorator(coffee);
    
    assertEquals(2.8, coffee.getCost(), 0.01);  // 2.0 + 0.5 + 0.3
}
```


***

## Interview Questions \& Answers

**Q: What is Decorator Pattern?**
A: Structural pattern that adds behavior to objects dynamically by wrapping them, without modifying their structure.

**Q: Decorator vs Adapter?**
A: Decorator keeps same interface and adds behavior. Adapter changes interface for compatibility.

**Q: Why use Decorator instead of inheritance?**
A: Avoids class explosion, allows runtime composition, follows Open/Closed Principle.

**Q: Can decorators be stacked?**
A: Yes! That's the power of Decorator - infinite combinations with finite decorators.

**Q: Does order of decoration matter?**
A: Yes! Last decorator applied is outermost layer. For example, compress before encrypt for efficiency.

**Q: Why use abstract base decorator?**
A: Reduces code duplication (field + constructor) across all concrete decorators.

**Q: Give real example of Decorator in Java?**
A: Java I/O streams - `BufferedReader(InputStreamReader(FileInputStream))`

***

## Best Practices ✅

1. **Use interface** for Component, not concrete class
2. **Use protected** for wrapped component field (not private)
3. **Always delegate** to wrapped object, then add behavior
4. **Create base decorator** to avoid code duplication
5. **Think about order** - some decorators depend on others
6. **Keep decorators focused** - single responsibility
7. **Use Lombok** for cleaner code (`@AllArgsConstructor`)
8. **Test combinations** - verify stacking works correctly

***

## Quick Reference Cheat Sheet

**One-Line Summary:** Decorator adds behavior dynamically by wrapping objects with same interface.

**When to use:** Need flexible, composable features; avoid class explosion

**Structure:**

```java
abstract class Decorator implements Component {
    protected Component component;
    // Delegate + enhance
}
```

**Key Principle:** IS-A + HAS-A with same interface

**Common Use Cases:** I/O streams, GUI components, text formatting, notifications, filters

***

## Summary

**Decorator Pattern allows adding features dynamically through wrapping.**

**Key Points:**

1. Same interface (IS-A) + Contains component (HAS-A)
2. Delegate to wrapped object, then add behavior
3. Can stack decorators infinitely
4. Avoids class explosion from inheritance
5. Order matters - consider dependencies
6. Use abstract base decorator to reduce duplication

***


