***

# **OOP FUNDAMENTALS - COMPLETE REVISION NOTES**


***

## **1. RELATIONSHIPS IN OOP**

### **A. IS-A Relationship (Inheritance)**

**Definition**: Child class inherits from Parent class using `extends` keyword.

**Code Pattern**:

```java
class Animal {
    void eat() {
        System.out.println("Animal eats");
    }
}

class Dog extends Animal {  // Dog IS-A Animal
    void bark() {
        System.out.println("Bark!");
    }
}

// Valid
Dog dog = new Dog();
dog.eat();   // Inherited
dog.bark();  // Own method

Animal animal = new Dog();  // ✅ Upcasting (Parent = Child)
animal.eat();  // Works

Dog d = new Animal();  // ❌ ERROR (Child cannot = Parent)
```

**Key Points**:

- Uses `extends` or `implements` keyword
- Child inherits all parent methods
- Unidirectional: Dog IS-A Animal, but Animal IS-NOT-A Dog
- **Upcasting** (Child → Parent) is automatic
- **Downcasting** (Parent → Child) needs explicit cast
- Tightest coupling

**Examples**: Dog IS-A Animal, Car IS-A Vehicle, Manager IS-A Employee

***

### **B. HAS-A Relationship (Aggregation)**

**Definition**: Container "has" a Part, but Part exists independently. **Weak ownership**.

**Code Pattern**:

```java
class Player {
    String name;
    Player(String name) {
        this.name = name;
    }
}

class Team {
    String teamName;
    List<Player> players = new ArrayList<>();  // HAS-A
    
    Team(String teamName) {
        this.teamName = teamName;
    }
    
    void addPlayer(Player player) {  // Player passed from OUTSIDE
        players.add(player);
    }
}

// Usage
Player p1 = new Player("Virat");  // Created OUTSIDE
Team team = new Team("India");
team.addPlayer(p1);  // Passed in

// If Team deleted → Player still exists
```

**Key Points**:

- Object created **outside** and passed in
- Part can exist independently
- Stored as instance variable
- Loose coupling
- **UML**: Empty diamond ◇

**Examples**: Team HAS-A Player, Library HAS-A Book, University HAS-A Professor

***

### **C. PART-OF Relationship (Composition)**

**Definition**: Container "owns" Part. Part cannot exist without container. **Strong ownership**.

**Code Pattern**:

```java
class Room {
    String name;
    Room(String name) {
        this.name = name;
    }
}

class House {
    private Room livingRoom;  // PART-OF (private)
    private Room kitchen;
    
    House() {
        // Rooms created INSIDE constructor
        livingRoom = new Room("Living Room");
        kitchen = new Room("Kitchen");
    }
}

// Usage
House house = new House();  // Rooms auto-created

// If House deleted → Rooms also deleted
```

**Key Points**:

- Object created **inside** the container
- Part cannot exist independently
- Usually marked `private`
- Tight coupling
- **UML**: Filled diamond ◆

**Examples**: House OWNS Room, Car OWNS Engine, Human OWNS Heart

***

### **D. USES-A Relationship (Dependency)**

**Definition**: One class uses another temporarily (method parameter only). **No permanent connection**.

**Code Pattern**:

```java
class Printer {
    void print(String message) {
        System.out.println("Printing: " + message);
    }
}

class Document {
    String content;
    
    // Dependency - Printer is parameter only (NOT instance variable)
    void printDocument(Printer printer) {
        printer.print(content);  // Temporary usage
    }
    // After method ends, no reference to Printer
}

// Usage
Document doc = new Document();
Printer p = new Printer();
doc.printDocument(p);  // Passed temporarily
```

**Key Points**:

- Object passed as **method parameter only**
- **No instance variable** for the object
- Used only during method execution
- Loosest coupling
- **UML**: Dashed arrow ----→

**Examples**: Document USES Printer, Student USES Calculator, Car USES FuelStation

***

## **2. RELATIONSHIPS COMPARISON TABLE**

| **Feature** | **IS-A (Inheritance)** | **HAS-A (Aggregation)** | **PART-OF (Composition)** | **USES-A (Dependency)** |
| :-- | :-- | :-- | :-- | :-- |
| **Keyword** | `extends`, `implements` | Instance variable (passed) | Instance variable (created) | Method parameter |
| **Relationship** | "IS A TYPE OF" | "HAS A" (weak) | "OWNS/PART OF" (strong) | "USES A" (temporary) |
| **Coupling** | Tight | Loose | Tight | Loosest |
| **Lifetime** | N/A | Independent | Dependent | Only during method call |
| **Storage** | Inheritance hierarchy | Instance field | Instance field | No storage |
| **Creation** | Inherited | Outside, passed in | Inside constructor | Outside, passed temporarily |
| **Can survive alone?** | N/A | Yes | No | Yes |
| **UML** | ───▷ | ◇──── | ◆──── | ----→ |
| **Example** | Dog extends Animal | Team has Player | House owns Room | Document uses Printer |


***

## **3. QUICK IDENTIFICATION RULES**

| **If You See...** | **It's...** |
| :-- | :-- |
| `extends` or `implements` | IS-A (Inheritance) |
| Constructor parameter + stored as field | HAS-A (Aggregation) |
| `new` inside constructor + stored as field | PART-OF (Composition) |
| Method parameter only, no field | USES-A (Dependency) |
| Object passed from outside | HAS-A (Aggregation) |
| Object created inside class | PART-OF (Composition) |


***

## **4. METHOD OVERRIDING**

**Definition**: Child class provides its own implementation of a method already defined in parent class.

**Code Pattern**:

```java
class Animal {
    void makeSound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override  // Always use this annotation
    void makeSound() {
        System.out.println("Bark!");
    }
}

// Usage
Animal animal = new Dog();  // Polymorphism
animal.makeSound();  // Output: Bark! (calls Dog's version)
```

**Using `super` keyword**:

```java
class Dog extends Animal {
    @Override
    void makeSound() {
        super.makeSound();  // Call parent's method first
        System.out.println("And bark!");
    }
}
// Output: Animal sound
//         And bark!
```

**Rules**:

1. Same method signature (name + parameters)
2. Cannot reduce visibility (public → protected/private ❌)
3. Cannot override `private`, `static`, or `final` methods
4. Use `@Override` annotation (catches errors at compile time)

**Why it matters**: Foundation for runtime polymorphism and design patterns.

***

## **5. POLYMORPHISM**

**Definition**: "Many forms" - ability of an object to take different forms.

### **A. Compile-Time Polymorphism (Method Overloading)**

**Same method name, different parameters**. Decided at **compile time**.

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }
    
    int add(int a, int b, int c) {  // Different parameters
        return a + b + c;
    }
    
    double add(double a, double b) {  // Different types
        return a + b;
    }
}
```

**Key**: Same name, different signatures.

***

### **B. Runtime Polymorphism (Method Overriding)**

**Same method signature**. Decided at **runtime** based on actual object.

```java
class Animal {
    void makeSound() {
        System.out.println("Animal sound");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("Bark!");
    }
}

class Cat extends Animal {
    @Override
    void makeSound() {
        System.out.println("Meow!");
    }
}

// Runtime polymorphism
Animal animal;
animal = new Dog();
animal.makeSound();  // Bark!

animal = new Cat();
animal.makeSound();  // Meow!
```

**Key**: Same reference type, different object types.

***

### **Comparison**:

| **Compile-Time** | **Runtime** |
| :-- | :-- |
| Method Overloading | Method Overriding |
| Same class | Parent-Child classes |
| Different parameters | Same parameters |
| Faster | Slower |
| Static binding | Dynamic binding |


***

## **6. INTERFACE VS ABSTRACT CLASS**

### **A. Abstract Class**

Cannot be instantiated. Can have both abstract and concrete methods.

```java
abstract class Animal {
    String name;  // Can have instance variables
    
    Animal(String name) {  // Can have constructor
        this.name = name;
    }
    
    void sleep() {  // Concrete method
        System.out.println(name + " is sleeping");
    }
    
    abstract void makeSound();  // Abstract method
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }
    
    @Override
    void makeSound() {
        System.out.println("Bark!");
    }
}
```


***

### **B. Interface**

A contract defining what methods a class must implement.

```java
interface Flyable {
    void fly();  // Abstract by default
    void land();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {  // Multiple interfaces
    @Override
    public void fly() {
        System.out.println("Flying");
    }
    
    @Override
    public void land() {
        System.out.println("Landing");
    }
    
    @Override
    public void swim() {
        System.out.println("Swimming");
    }
}
```


***

### **Comparison Table**:

| **Abstract Class** | **Interface** |
| :-- | :-- |
| Can have abstract + concrete methods | All methods abstract (except default/static) |
| Can have instance variables | Only `public static final` constants |
| Can have constructors | No constructors |
| **Single inheritance** (`extends` one) | **Multiple inheritance** (`implements` many) |
| Any access modifiers | Methods `public` by default |
| Use `extends` | Use `implements` |


***

### **When to Use**:

**Abstract Class**:

- Share code among related classes
- Need instance variables or constructors
- Need non-public methods
- Clear "IS-A" relationship

**Interface**:

- Define capability for unrelated classes
- Need multiple inheritance
- Design a contract
- "CAN-DO" relationship

***

## **7. REAL-WORLD EXAMPLES BY CATEGORY**

### **IS-A (Inheritance)**:

- Dog IS-A Animal
- Car IS-A Vehicle
- Manager IS-A Employee
- Circle IS-A Shape


### **HAS-A (Aggregation)**:

- Team HAS Player (player exists independently)
- Library HAS Book (book can move)
- University HAS Professor (professor can change universities)
- Playlist HAS Song (song exists independently)


### **PART-OF (Composition)**:

- House OWNS Room (room dies with house)
- Car OWNS Engine (engine is part of car)
- Human OWNS Heart (heart cannot exist without human)
- Computer OWNS Motherboard (motherboard is integral)


### **USES-A (Dependency)**:

- Document USES Printer (temporary)
- Student USES Calculator (during calculation)
- Car USES FuelStation (during refuel)
- Chef USES Knife (while cooking)

***

## **8. DECISION FLOWCHART**

```
Question: What relationship should I use?

├─ Is Class A a TYPE OF Class B?
│  └─ YES → Use IS-A (Inheritance)
│     Example: Dog extends Animal
│
└─ NO → Does Class A need Class B?
    │
    ├─ Always needs it → Store as instance field
    │   │
    │   ├─ Can B exist WITHOUT A?
    │   │   ├─ YES → Use HAS-A (Aggregation)
    │   │   │  Example: Team has Player
    │   │   │
    │   │   └─ NO → Use PART-OF (Composition)
    │   │      Example: House owns Room
    │   │
    │   └─ Only needs temporarily in one method?
    │       └─ YES → Use USES-A (Dependency)
    │          Example: Document uses Printer
```


***

## **9. MEMORY TRICK**

**IS-A**: "A dog **IS A** animal" → Inheritance
**HAS-A**: "A team **HAS** players" → Aggregation (weak)
**PART-OF**: "A house **OWNS** rooms" → Composition (strong)
**USES-A**: "A document **USES** printer" → Dependency (temporary)

***

## **10. INTERVIEW QUICK TIPS**

1. **Favor Composition over Inheritance** - more flexible
2. **Program to interfaces, not implementations** - use Interface/Abstract class
3. **Polymorphism** is key to most design patterns
4. **Method Overriding** enables runtime polymorphism
5. **Aggregation vs Composition** = lifetime dependency difference

***

