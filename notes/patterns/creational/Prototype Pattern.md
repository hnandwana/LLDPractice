# Prototype Pattern - Revision Notes 📚

## Overview

**Prototype Pattern** is a creational design pattern that creates new objects by **cloning existing objects** (prototypes) instead of creating from scratch using `new` keyword.

**Key Idea:** When object creation is expensive, create one prototype and clone it multiple times.

***

## When to Use Prototype Pattern

✅ **Use Prototype when:**

- Object creation is **expensive** (database queries, file I/O, network calls, complex initialization)
- Need many similar objects with **small variations**
- Want to avoid **complex class hierarchies** for object creation
- Object has **complex state** that's costly to rebuild
- Need to **preserve object state** at a specific point in time

❌ **Don't use when:**

- Object creation is simple and fast
- Objects are fundamentally different (not variations of same type)
- Deep copy is too complex or circular references exist


### Real-World Examples

- Game characters loaded from database
- Document templates (proposals, invoices)
- Complex configuration objects
- Cached objects that need customization

***

## Core Concepts

### 1. Shallow Copy vs Deep Copy

**Shallow Copy:**

- Copies primitive fields by value
- Copies object references (NOT the objects themselves)
- Both original and copy point to **same nested objects**
- Fast but dangerous if nested objects are mutable

**Deep Copy:**

- Copies primitive fields by value
- Creates **new objects** for all nested references
- Original and copy are completely independent
- Slower but safe


### Visual Example

```java
class Person {
    String name;        // Immutable
    int age;           // Primitive
    Address address;   // Mutable object
}
```

**Shallow Copy:**

```
original.address ──┐
                   ↓
                [Address Object]
                   ↑
   copy.address ──┘
// Both share same Address!
```

**Deep Copy:**

```
original.address ──→ [Address Object 1]

   copy.address ──→ [Address Object 2]
// Each has its own Address!
```


***

## Decision Table: What Needs Deep Copy?

| Field Type | Deep Copy Needed? | Why |
| :-- | :-- | :-- |
| Primitives (int, boolean) | No (N/A) | Always copied by value |
| Immutable Objects (String, Integer) | No | Safe to share - can't be modified |
| Mutable Objects (custom classes) | **Yes** | Changes affect both if shared |
| Collections (List, Map) | **Yes** | List itself needs new instance |
| Objects inside Collections | **Depends** | If mutable, yes; if immutable, no |


***

## Implementation Approaches

### Approach 1: Copy Constructor (Recommended ✅)

```java
class Weapon {
    private String name;
    private int damage;
    
    // Regular constructor
    public Weapon(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }
    
    // Copy constructor
    public Weapon(Weapon other) {
        this.name = other.name;
        this.damage = other.damage;
    }
}

class Character {
    private String name;
    private Weapon weapon;
    
    // Copy constructor with deep copy
    public Character(Character other) {
        this.name = other.name;
        this.weapon = new Weapon(other.weapon);  // Deep copy!
    }
}

// Usage
Character original = new Character("Hero", new Weapon("Sword", 50));
Character clone = new Character(original);  // Copy constructor
```

**Pros:**

- ✅ No checked exceptions
- ✅ Works with final fields
- ✅ Clear and explicit
- ✅ Type-safe (no casting)
- ✅ Recommended by "Effective Java"

**Cons:**

- ❌ More verbose
- ❌ Must implement for each class in hierarchy

***

### Approach 2: Cloneable Interface + clone()

```java
class Weapon implements Cloneable {
    private String name;
    private int damage;
    
    @Override
    public Weapon clone() {
        try {
            return (Weapon) super.clone();  // Shallow copy!
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Character implements Cloneable {
    private String name;
    private Weapon weapon;
    
    @Override
    public Character clone() {
        try {
            Character cloned = (Character) super.clone();
            cloned.weapon = this.weapon.clone();  // Manual deep copy!
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

// Usage
Character clone = original.clone();
```

**Pros:**

- ✅ Standard Java convention
- ✅ Recognized pattern

**Cons:**

- ❌ Throws checked exception (CloneNotSupportedException)
- ❌ Doesn't work with final fields
- ❌ Requires casting
- ❌ Easy to forget deep copy for nested objects

***

### Approach 3: Hybrid (Copy Constructor + clone()) ✨

**Your Implementation:**

```java
class Weapon {
    // Copy constructor (private)
    private Weapon(Weapon other) {
        this.name = other.name;
        this.damage = other.damage;
    }
    
    // Public clone method
    public Weapon clone() {
        return new Weapon(this);
    }
}

class Character {
    private Character(Character other) {
        this.name = other.name;
        this.weapon = other.weapon.clone();  // Calls clone()
    }
    
    public Character clone() {
        return new Character(this);
    }
}
```

**Pros:**

- ✅ Clean public API (clone())
- ✅ Encapsulation (private constructor)
- ✅ No checked exceptions
- ✅ Deep copy enforced

**Best of both worlds!** ✨

***

## Code Templates

### Template 1: Simple Class with Primitives

```java
class Product {
    private String name;
    private double price;
    
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    // Copy constructor
    public Product(Product other) {
        this.name = other.name;  // String is immutable - OK
        this.price = other.price; // Primitive - OK
    }
}
```


***

### Template 2: Class with Nested Objects

```java
class Order {
    private String orderId;
    private Customer customer;  // Mutable object
    
    public Order(Order other) {
        this.orderId = other.orderId;
        this.customer = new Customer(other.customer);  // Deep copy!
    }
}

class Customer {
    private String name;
    private Address address;
    
    public Customer(Customer other) {
        this.name = other.name;
        this.address = new Address(other.address);  // Deep copy!
    }
}
```


***

### Template 3: Class with Collections

```java
class Team {
    private String name;
    private List<Player> players;  // Mutable list
    
    public Team(Team other) {
        this.name = other.name;
        
        // Option 1: Mutable list (can add/remove later)
        this.players = new ArrayList<>(other.players);
        
        // Option 2: Immutable list (cannot modify)
        this.players = List.copyOf(other.players);
        
        // Note: Both options do shallow copy of Player objects!
        // If Player is mutable, do deep copy:
        this.players = other.players.stream()
            .map(Player::new)  // Assumes Player has copy constructor
            .collect(Collectors.toList());
    }
}
```


***

### Template 4: Static Factory for Templates

```java
class Character {
    private String name;
    private Weapon weapon;
    private Armor armor;
    
    // Private copy constructor
    private Character(Character other) {
        this.name = other.name;
        this.weapon = new Weapon(other.weapon);
        this.armor = new Armor(other.armor);
    }
    
    // Public clone method
    public Character clone() {
        return new Character(this);
    }
    
    // Static factory methods for templates
    public static Character createWarriorTemplate() {
        return new Character(
            "Warrior",
            new Weapon("Sword", 90),
            new Armor("Heavy", 80)
        );
    }
    
    public static Character createMageTemplate() {
        return new Character(
            "Mage",
            new Weapon("Staff", 70),
            new Armor("Robe", 40)
        );
    }
}

// Usage
Character warriorTemplate = Character.createWarriorTemplate();
Character player1 = warriorTemplate.clone();
Character player2 = warriorTemplate.clone();
```


***

## Common Mistakes \& How to Avoid

### ❌ Mistake 1: Shallow Copy When Deep Copy Needed

```java
// BAD
public Character(Character other) {
    this.weapon = other.weapon;  // Same object reference!
}

// GOOD
public Character(Character other) {
    this.weapon = new Weapon(other.weapon);  // New object!
}
```


***

### ❌ Mistake 2: Forgetting Nested Objects

```java
// BAD - Only copies first level
public Order(Order other) {
    this.customer = new Customer(other.customer);
    // Forgot to deep copy customer.address!
}

// GOOD
public Order(Order other) {
    this.customer = new Customer(other.customer);
    // Customer constructor must deep copy address!
}
```


***

### ❌ Mistake 3: Cloneable Without Deep Copy

```java
// BAD
public Character clone() {
    return (Character) super.clone();  // Shallow copy!
}

// GOOD
public Character clone() {
    Character c = (Character) super.clone();
    c.weapon = this.weapon.clone();  // Manual deep copy!
    c.armor = this.armor.clone();
    return c;
}
```


***

### ❌ Mistake 4: Instance Method for Template Creation

```java
// BAD
public Character createWarriorTemplate() {
    this.name = "Warrior";  // Modifies existing object!
    return this;
}

// GOOD
public static Character createWarriorTemplate() {
    return new Character("Warrior", ...);  // Creates new object!
}
```


***

## Pattern Comparisons

### Prototype vs Singleton

| Aspect | Singleton | Prototype |
| :-- | :-- | :-- |
| **Purpose** | Ensure only ONE instance | Create MULTIPLE copies |
| **Object Identity** | `s1 == s2` → true | `p1 == p2` → false |
| **Use Case** | Shared resource | Similar objects with variations |
| **Memory** | One object | Multiple objects |


***

### Prototype vs Factory Method

| Aspect | Prototype | Factory Method |
| :-- | :-- | :-- |
| **How Objects Created** | Cloning existing object | Calling factory method |
| **When to Use** | Expensive creation | Different creation logic per type |
| **Inheritance** | No subclassing needed | Subclass per type |
| **Performance** | Fast (cloning) | Depends on creation logic |

**Example:**

- **Factory Method:** EmailNotificationFactory, SMSNotificationFactory (different logic)
- **Prototype:** Clone game character template (avoid expensive loading)

***

### Prototype vs Builder

| Aspect | Prototype | Builder |
| :-- | :-- | :-- |
| **Starting Point** | Existing object | Empty object |
| **Customization** | Modify cloned object | Build step-by-step |
| **Use Case** | Similar objects | Complex objects with many options |


***

## Interview Questions \& Answers

**Q: What is Prototype Pattern?**
A: Creational pattern that creates new objects by cloning existing prototypes instead of creating from scratch. Used when object creation is expensive.

**Q: Difference between shallow and deep copy?**
A: Shallow copy copies references (shares nested objects). Deep copy creates new objects for all nested references (complete independence).

**Q: Why use Prototype over new keyword?**
A: When object creation is expensive (database, files, complex initialization), cloning is much faster than creating from scratch.

**Q: What's wrong with Cloneable interface?**
A: (1) Throws checked exception, (2) Doesn't work with final fields, (3) Easy to forget deep copy, (4) Requires casting. Copy constructors are better.

**Q: When would you use shallow copy?**
A: When nested objects are immutable (String, Integer) or when you intentionally want to share objects (Flyweight pattern).

**Q: How to handle circular references?**
A: Use a Map to track already-cloned objects. Check if object already cloned before creating new copy.

***

## Performance Analysis

### Scenario: Game Character Creation

**Without Prototype:**

```
Create Character 1: 9 seconds (load textures, sounds, animations)
Create Character 2: 9 seconds
Create Character 3: 9 seconds
...
Total for 100: 900 seconds = 15 minutes! ❌
```

**With Prototype:**

```
Create Template: 9 seconds (done once)
Clone Character 1: 0.01 seconds
Clone Character 2: 0.01 seconds
Clone Character 3: 0.01 seconds
...
Total for 100: 9 + 0.99 = ~10 seconds! ✅
```

**Speedup: 90x faster!** 🚀

***

## Best Practices ✅

1. **Use copy constructors** over Cloneable (Effective Java Item 13)
2. **Deep copy mutable objects**, shallow copy immutable
3. **Private copy constructor + public clone()** for clean API
4. **Static factory methods** for creating templates
5. **Document clone behavior** (shallow vs deep) in JavaDoc
6. **Test independence** - verify template unchanged after clone modification
7. **Consider immutability** - immutable objects don't need deep copy
8. **Use List.copyOf()** for immutable lists, `new ArrayList<>()` for mutable

***

## Your Implementation Checklist

After implementing Prototype, verify:

- ✅ Clone creates NEW object (`clone == original` is false)
- ✅ Modifying clone doesn't affect original
- ✅ All mutable nested objects are deep copied
- ✅ Collections are handled appropriately (new list created)
- ✅ Template creation uses static methods
- ✅ Copy constructors are clean and complete
- ✅ Demo code tests independence

***

## Summary

**Prototype Pattern creates objects by cloning prototypes.**

**Key Points:**

1. Use when object creation is **expensive**
2. **Deep copy** mutable objects, **shallow copy** immutable
3. **Copy constructors** better than Cloneable
4. **Static factories** for template creation
5. **Test independence** - verify clones don't affect original

***

