# 📚 Master Structural Patterns - Complete Revision Guide

**Your Achievement: 7/7 Structural Patterns Mastered! 🎉**

***

## 📖 Table of Contents

1. [All 7 Patterns Quick Summary](#quick-summary)
2. [Pattern Comparison Table](#pattern-comparison-table)
3. [When to Use Which Pattern](#when-to-use-guide)
4. [Code Templates for Each Pattern](#code-templates)
5. [Real-World Examples](#real-world-examples)
6. [Interview Questions (50+ Questions)](#interview-questions)
7. [Quick Reference Cards](#quick-reference)
8. [Pattern Selection Decision Tree](#decision-tree)
9. [Common Mistakes Across Patterns](#common-mistakes)
10. [Best Practices Summary](#best-practices)

***

```
<a name="quick-summary"></a>
```


## 1️⃣ All 7 Patterns Quick Summary

### 1. Adapter Pattern 🔌

**Intent:** Convert interface of a class into another interface clients expect. Makes incompatible interfaces work together.

**Key:** Wraps existing class with new interface (like power adapter)

**Structure:** Client → Adapter → Adaptee

**When:** Legacy code integration, third-party library compatibility

***

### 2. Decorator Pattern 🎁

**Intent:** Attach additional responsibilities to an object dynamically. Flexible alternative to subclassing.

**Key:** Wraps objects to add behavior (like gift wrapping)

**Structure:** Component ← ConcreteComponent, Decorator (wraps Component)

**When:** Add features dynamically, avoid class explosion from combinations

***

### 3. Facade Pattern 🎭

**Intent:** Provide unified interface to a set of interfaces in a subsystem. Simplifies complex system.

**Key:** Single entry point to complex subsystem (like hotel receptionist)

**Structure:** Client → Facade → ComplexSubsystem (many classes)

**When:** Simplify complex API, decouple clients from subsystem

***

### 4. Proxy Pattern 🚪

**Intent:** Provide surrogate/placeholder to control access to another object.

**Key:** Controls access to real object (like security guard)

**Structure:** Client → Proxy → RealSubject (same interface)

**When:** Lazy loading, access control, logging, caching

***

### 5. Composite Pattern 🌲

**Intent:** Compose objects into tree structures. Treat individual objects and compositions uniformly.

**Key:** Part-whole hierarchy (like files/folders)

**Structure:** Component ← Leaf, Composite (has List<Component>)

**When:** Tree structures, uniform treatment of individual/group

***

### 6. Bridge Pattern 🌉

**Intent:** Decouple abstraction from implementation so both vary independently.

**Key:** Separates "what" from "how" (like remote control + TV)

**Structure:** Abstraction (has Implementation) + Implementation hierarchy

**When:** Avoid class explosion, runtime binding, independent evolution

***

### 7. Flyweight Pattern 🪶

**Intent:** Minimize memory by sharing as much data as possible among similar objects.

**Key:** Share common state, pass unique state (like shared fonts)

**Structure:** Flyweight (intrinsic) + Client (extrinsic) + Factory (pool)

**When:** Thousands of similar objects, memory optimization

***

```
<a name="pattern-comparison-table"></a>
```


## 2️⃣ Pattern Comparison Table

### Complete Comparison Matrix

| Pattern | Intent | Structure | Key Relationship | Main Benefit | Complexity |
| :-- | :-- | :-- | :-- | :-- | :-- |
| **Adapter** | Interface conversion | Wrapper | Adapter wraps Adaptee | Compatibility | Low |
| **Decorator** | Add behavior | Wrapper chain | Decorator wraps Component | Dynamic features | Medium |
| **Facade** | Simplification | Single interface | Facade aggregates subsystem | Simplified API | Low |
| **Proxy** | Access control | Placeholder | Proxy delegates to Real | Controlled access | Low-Medium |
| **Composite** | Tree hierarchy | Recursive | Composite has List<Component> | Uniform treatment | Medium |
| **Bridge** | Decouple abstraction/impl | Two hierarchies | Abstraction has Implementation | Independent variation | High |
| **Flyweight** | Memory optimization | Shared objects | Factory manages pool | Memory savings | Medium-High |


***

### Detailed Comparison: Similar Patterns

#### Adapter vs Decorator vs Proxy

| Aspect | Adapter | Decorator | Proxy |
| :-- | :-- | :-- | :-- |
| **Intent** | Interface conversion | Add behavior | Control access |
| **When** | After-the-fact (fix compatibility) | Design-time (enhance features) | Access control/optimization |
| **Changes Interface** | Yes (converts) | No (same interface) | No (same interface) |
| **Multiple Wrapping** | Usually one layer | Multiple layers (chain) | Usually one layer |
| **Client Awareness** | Knows adapter exists | May not know decorators | Transparent to client |
| **Example** | Stripe API → PaymentProcessor | Coffee + Milk + Sugar | ImageProxy → RealImage |

**Key Difference:**

- **Adapter:** "I need to make X work with Y" (compatibility)
- **Decorator:** "I want to add features to X" (enhancement)
- **Proxy:** "I need to control access to X" (protection/optimization)

***

#### Composite vs Decorator

| Aspect | Composite | Decorator |
| :-- | :-- | :-- |
| **Structure** | Tree (one-to-many) | Chain (one-to-one) |
| **Purpose** | Represent part-whole | Add responsibilities |
| **Children** | Multiple children (List) | Single wrapped object |
| **Recursion** | Yes (traverse tree) | No (delegate chain) |
| **Focus** | Aggregation | Behavior enhancement |
| **Example** | Folder with Files/Folders | Coffee with Milk/Sugar |

**When to use:**

- **Composite:** Need tree structure, groups contain individuals/groups
- **Decorator:** Need to stack features, no hierarchical containment

***

#### Bridge vs Strategy vs Adapter

| Aspect | Bridge | Strategy | Adapter |
| :-- | :-- | :-- | :-- |
| **Type** | Structural | Behavioral | Structural |
| **When** | Designed upfront | Runtime algorithm selection | After-the-fact |
| **Hierarchies** | Two (abstraction + impl) | One (context + strategies) | One (adapter + adaptee) |
| **Focus** | Separate what/how | Algorithm variation | Interface compatibility |
| **Example** | Remote + Device | SortContext + algorithms | Legacy API wrapper |


***

#### Flyweight vs Singleton vs Prototype

| Aspect | Flyweight | Singleton | Prototype |
| :-- | :-- | :-- | :-- |
| **Instances** | Multiple shared (pool) | One global | Many cloned |
| **Purpose** | Memory optimization | Global access | Object creation |
| **Sharing** | Yes (concurrent) | Yes (one instance) | No (copies) |
| **State** | Immutable intrinsic | Can be mutable | Usually mutable |
| **Example** | Characters in text | Logger | Complex object cloning |


***

```
<a name="when-to-use-guide"></a>
```


## 3️⃣ When to Use Which Pattern - Decision Guide

### Problem → Pattern Mapping

| Your Problem | Use This Pattern | Why |
| :-- | :-- | :-- |
| Legacy code incompatible with new system | **Adapter** | Converts interfaces without modifying legacy code |
| Need to add features without modifying class | **Decorator** | Wraps objects to add behavior dynamically |
| Complex subsystem with many classes | **Facade** | Provides simple unified interface |
| Need lazy loading or access control | **Proxy** | Controls access, delays initialization |
| File/folder system, org chart, menu | **Composite** | Represents tree structures uniformly |
| Multiple dimensions causing class explosion | **Bridge** | Separates abstraction from implementation |
| Thousands of similar objects using too much memory | **Flyweight** | Shares common state among objects |


***

### Scenario-Based Selection

#### Scenario 1: Third-Party Library Integration

**Problem:** You need to use Stripe payment API but your system expects `PaymentProcessor` interface.

**Solution:** **Adapter**

```java
public class StripeAdapter implements PaymentProcessor {
    private StripeAPI stripe;
    
    public void processPayment(double amount) {
        stripe.charge(amount);  // Adapts charge() to processPayment()
    }
}
```


***

#### Scenario 2: Coffee Shop Beverage System

**Problem:** Basic beverages (Espresso, Tea) with optional add-ons (Milk, Sugar, Whip). Avoid creating 12+ classes.

**Solution:** **Decorator**

```java
Beverage beverage = new Espresso();
beverage = new Milk(beverage);
beverage = new Sugar(beverage);
System.out.println(beverage.cost());  // Dynamically added features
```


***

#### Scenario 3: Smart Home System

**Problem:** Complex subsystems (lights, thermostat, security, entertainment) with many classes.

**Solution:** **Facade**

```java
public class SmartHomeFacade {
    public void leaveHome() {
        lights.turnOffAll();
        thermostat.setAway();
        security.arm();
        // One simple method for complex operations
    }
}
```


***

#### Scenario 4: Large Image Loading

**Problem:** Loading 100 high-res images at startup crashes app.

**Solution:** **Proxy**

```java
public class ImageProxy implements Image {
    private RealImage realImage;
    
    public void display() {
        if (realImage == null) {
            realImage = new RealImage();  // Lazy load
        }
        realImage.display();
    }
}
```


***

#### Scenario 5: Company Organization Chart

**Problem:** Employees and departments, departments contain employees/departments.

**Solution:** **Composite**

```java
Component ceo = new Employee("CEO");
Component engineering = new Department("Engineering");
engineering.add(new Employee("Dev1"));
engineering.add(new Employee("Dev2"));
ceo.add(engineering);  // Uniform treatment
```


***

#### Scenario 6: Multi-Platform Media Player

**Problem:** 3 player types (Basic, Advanced, Pro) × 4 platforms (Windows, Mac, Linux, Mobile) = 12 classes.

**Solution:** **Bridge**

```java
// Only 7 classes instead of 12
Player player = new AdvancedPlayer(new WindowsPlatform());
player.play();  // Can switch platform at runtime
```


***

#### Scenario 7: Game with 1 Million Particles

**Problem:** 1M particle objects with mostly shared properties (texture, color).

**Solution:** **Flyweight**

```java
// Only 4 particle types shared by 1M particles
ParticleType type = factory.getParticleType("FIRE");
Particle p = new Particle(type, x, y, velocity);  // 95% memory saved
```


***

```
<a name="code-templates"></a>
```


## 4️⃣ Code Templates for Each Pattern

### Template 1: Adapter Pattern

```java
// TARGET INTERFACE (what client expects)
public interface Target {
    void request();
}

// ADAPTEE (existing incompatible class)
public class Adaptee {
    public void specificRequest() {
        System.out.println("Adaptee specific request");
    }
}

// ADAPTER (converts Adaptee to Target)
public class Adapter implements Target {
    private final Adaptee adaptee;
    
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public void request() {
        adaptee.specificRequest();  // Delegates and converts
    }
}

// CLIENT
public class Client {
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new Adapter(adaptee);
        target.request();  // Client uses Target interface
    }
}
```

**Use Case Example:**

```java
// Real-world: Payment gateway adapter
public class StripeAdapter implements PaymentProcessor {
    private StripeAPI stripe = new StripeAPI();
    
    @Override
    public void processPayment(double amount) {
        stripe.charge(amount);
    }
}
```


***

### Template 2: Decorator Pattern

```java
// COMPONENT INTERFACE
public interface Component {
    String getDescription();
    double getCost();
}

// CONCRETE COMPONENT (base object)
public class ConcreteComponent implements Component {
    @Override
    public String getDescription() {
        return "Base Component";
    }
    
    @Override
    public double getCost() {
        return 10.0;
    }
}

// DECORATOR BASE CLASS
@AllArgsConstructor
public abstract class Decorator implements Component {
    protected final Component wrappedComponent;
    
    @Override
    public String getDescription() {
        return wrappedComponent.getDescription();
    }
    
    @Override
    public double getCost() {
        return wrappedComponent.getCost();
    }
}

// CONCRETE DECORATORS
public class DecoratorA extends Decorator {
    public DecoratorA(Component component) {
        super(component);
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + Feature A";
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 5.0;
    }
}

public class DecoratorB extends Decorator {
    public DecoratorB(Component component) {
        super(component);
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + Feature B";
    }
    
    @Override
    public double getCost() {
        return super.getCost() + 3.0;
    }
}

// CLIENT
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        component = new DecoratorA(component);
        component = new DecoratorB(component);
        
        System.out.println(component.getDescription());  // Base + A + B
        System.out.println(component.getCost());  // 10 + 5 + 3 = 18
    }
}
```

**Use Case Example:**

```java
// Real-world: Coffee shop
Beverage coffee = new Espresso();
coffee = new Milk(coffee);
coffee = new Sugar(coffee);
System.out.println(coffee.getDescription() + " = $" + coffee.cost());
```


***

### Template 3: Facade Pattern

```java
// COMPLEX SUBSYSTEM CLASSES
class SubsystemA {
    public void operationA() {
        System.out.println("Subsystem A operation");
    }
}

class SubsystemB {
    public void operationB() {
        System.out.println("Subsystem B operation");
    }
}

class SubsystemC {
    public void operationC() {
        System.out.println("Subsystem C operation");
    }
}

// FACADE (simplified interface)
public class Facade {
    private final SubsystemA subsystemA;
    private final SubsystemB subsystemB;
    private final SubsystemC subsystemC;
    
    public Facade() {
        this.subsystemA = new SubsystemA();
        this.subsystemB = new SubsystemB();
        this.subsystemC = new SubsystemC();
    }
    
    // Simplified method hiding complexity
    public void simpleOperation() {
        System.out.println("Facade: Coordinating subsystems");
        subsystemA.operationA();
        subsystemB.operationB();
        subsystemC.operationC();
    }
}

// CLIENT
public class Client {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.simpleOperation();  // One call instead of three
    }
}
```

**Use Case Example:**

```java
// Real-world: Home theater
public class HomeTheaterFacade {
    public void watchMovie(String movie) {
        lights.dim();
        projector.on();
        soundSystem.setVolume(5);
        dvdPlayer.play(movie);
    }
}
```


***

### Template 4: Proxy Pattern

```java
// SUBJECT INTERFACE
public interface Subject {
    void request();
}

// REAL SUBJECT (expensive/controlled object)
public class RealSubject implements Subject {
    public RealSubject() {
        System.out.println("RealSubject: Expensive initialization");
    }
    
    @Override
    public void request() {
        System.out.println("RealSubject: Handling request");
    }
}

// PROXY (controls access to RealSubject)
public class Proxy implements Subject {
    private RealSubject realSubject;
    
    @Override
    public void request() {
        if (realSubject == null) {
            System.out.println("Proxy: Lazy initialization");
            realSubject = new RealSubject();
        }
        
        System.out.println("Proxy: Logging/checking access");
        realSubject.request();
    }
}

// CLIENT
public class Client {
    public static void main(String[] args) {
        Subject proxy = new Proxy();
        proxy.request();  // Initializes RealSubject only when needed
    }
}
```

**Use Case Example:**

```java
// Real-world: Image proxy
public class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    
    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);  // Lazy load
        }
        realImage.display();
    }
}
```


***

### Template 5: Composite Pattern

```java
// COMPONENT INTERFACE
public interface Component {
    void operation();
    
    default void add(Component component) {
        throw new UnsupportedOperationException("Cannot add");
    }
    
    default void remove(Component component) {
        throw new UnsupportedOperationException("Cannot remove");
    }
}

// LEAF (individual object, no children)
public class Leaf implements Component {
    private final String name;
    
    public Leaf(String name) {
        this.name = name;
    }
    
    @Override
    public void operation() {
        System.out.println("Leaf: " + name);
    }
}

// COMPOSITE (container for Components)
@Getter
public class Composite implements Component {
    private final String name;
    private final List<Component> children = new ArrayList<>();
    
    public Composite(String name) {
        this.name = name;
    }
    
    @Override
    public void operation() {
        System.out.println("Composite: " + name);
        for (Component child : children) {
            child.operation();  // Recursion
        }
    }
    
    @Override
    public void add(Component component) {
        children.add(component);
    }
    
    @Override
    public void remove(Component component) {
        children.remove(component);
    }
}

// CLIENT
public class Client {
    public static void main(String[] args) {
        Component leaf1 = new Leaf("Leaf1");
        Component leaf2 = new Leaf("Leaf2");
        
        Composite composite = new Composite("Composite1");
        composite.add(leaf1);
        composite.add(leaf2);
        
        Composite root = new Composite("Root");
        root.add(composite);
        root.add(new Leaf("Leaf3"));
        
        root.operation();  // Recursive traversal
    }
}
```

**Use Case Example:**

```java
// Real-world: File system
Component file1 = new File("document.txt");
Component folder = new Folder("Documents");
folder.add(file1);
folder.add(new File("image.png"));
folder.getSize();  // Sum of all children
```


***

### Template 6: Bridge Pattern

```java
// IMPLEMENTATION INTERFACE (how to do it)
public interface Implementation {
    void operationImpl();
}

// CONCRETE IMPLEMENTATIONS
public class ConcreteImplementationA implements Implementation {
    @Override
    public void operationImpl() {
        System.out.println("Implementation A");
    }
}

public class ConcreteImplementationB implements Implementation {
    @Override
    public void operationImpl() {
        System.out.println("Implementation B");
    }
}

// ABSTRACTION (what to do)
@Getter
public abstract class Abstraction {
    protected final Implementation implementation;
    
    public Abstraction(Implementation implementation) {
        this.implementation = Objects.requireNonNull(implementation);
    }
    
    public abstract void operation();
}

// REFINED ABSTRACTIONS
public class RefinedAbstractionA extends Abstraction {
    public RefinedAbstractionA(Implementation implementation) {
        super(implementation);
    }
    
    @Override
    public void operation() {
        System.out.print("Refined A: ");
        implementation.operationImpl();  // Delegates
    }
}

public class RefinedAbstractionB extends Abstraction {
    public RefinedAbstractionB(Implementation implementation) {
        super(implementation);
    }
    
    @Override
    public void operation() {
        System.out.print("Refined B: ");
        implementation.operationImpl();  // Delegates
    }
}

// CLIENT
public class Client {
    public static void main(String[] args) {
        Implementation implA = new ConcreteImplementationA();
        Implementation implB = new ConcreteImplementationB();
        
        Abstraction abs1 = new RefinedAbstractionA(implA);
        Abstraction abs2 = new RefinedAbstractionB(implB);
        
        abs1.operation();  // Refined A: Implementation A
        abs2.operation();  // Refined B: Implementation B
    }
}
```

**Use Case Example:**

```java
// Real-world: Remote control + devices
Device tv = new TV();
Device radio = new Radio();

RemoteControl basic = new BasicRemote(tv);
RemoteControl advanced = new AdvancedRemote(radio);

basic.togglePower();
advanced.mute();
```


***

### Template 7: Flyweight Pattern

```java
// FLYWEIGHT INTERFACE
public interface Flyweight {
    void operation(String extrinsicState);
}

// CONCRETE FLYWEIGHT (stores intrinsic state)
@Getter
@AllArgsConstructor
public class ConcreteFlyweight implements Flyweight {
    private final String intrinsicState;  // Shared, immutable
    
    @Override
    public void operation(String extrinsicState) {
        System.out.println("Intrinsic: " + intrinsicState + 
                         " | Extrinsic: " + extrinsicState);
    }
}

// FLYWEIGHT FACTORY (manages pool)
public class FlyweightFactory {
    private final Map<String, Flyweight> flyweights = new HashMap<>();
    
    public Flyweight getFlyweight(String key) {
        Flyweight flyweight = flyweights.get(key);
        
        if (flyweight == null) {
            flyweight = new ConcreteFlyweight(key);
            flyweights.put(key, flyweight);
            System.out.println("Created new flyweight: " + key);
        } else {
            System.out.println("Reusing flyweight: " + key);
        }
        
        return flyweight;
    }
    
    public int getFlyweightCount() {
        return flyweights.size();
    }
}

// CLIENT (stores extrinsic state)
@AllArgsConstructor
public class Client {
    private final Flyweight flyweight;
    private final String extrinsicState;
    
    public void execute() {
        flyweight.operation(extrinsicState);
    }
}

// USAGE
public class FlyweightDemo {
    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        
        Flyweight fw1 = factory.getFlyweight("TypeA");  // Created
        Flyweight fw2 = factory.getFlyweight("TypeA");  // Reused
        
        Client c1 = new Client(fw1, "Data1");
        Client c2 = new Client(fw2, "Data2");
        
        c1.execute();
        c2.execute();
        
        System.out.println("Flyweights created: " + factory.getFlyweightCount());
    }
}
```

**Use Case Example:**

```java
// Real-world: Particle system
ParticleType fire = factory.getParticleType("FIRE");
Particle p1 = new Particle(fire, 100, 200, 5.0);  // Shares fire type
Particle p2 = new Particle(fire, 150, 250, 6.0);  // Shares fire type
```


***

```
<a name="real-world-examples"></a>
```


## 5️⃣ Real-World Examples

### Adapter Pattern Examples

| Domain | Adaptee | Target | Use Case |
| :-- | :-- | :-- | :-- |
| **Payment** | Stripe API | PaymentProcessor | Integrate Stripe with existing payment system |
| **Database** | MongoDB | JDBC interface | Use MongoDB with JDBC-based frameworks |
| **Logging** | java.util.logging | SLF4J | Migrate legacy logging to SLF4J |
| **Graphics** | Legacy drawing API | Modern graphics API | Support old plugins |
| **APIs** | XML API | JSON interface | Convert XML responses to JSON |

**Code Example:**

```java
// Adapting Stripe to existing payment interface
public class StripeAdapter implements PaymentProcessor {
    private StripeAPI stripe = new StripeAPI();
    
    @Override
    public boolean processPayment(String userId, double amount) {
        String token = stripe.createToken(userId);
        return stripe.charge(token, amount);
    }
}
```


***

### Decorator Pattern Examples

| Domain | Base | Decorators | Use Case |
| :-- | :-- | :-- | :-- |
| **Beverages** | Coffee | Milk, Sugar, Whip | Coffee shop ordering |
| **I/O Streams** | FileInputStream | BufferedInputStream, GZIPInputStream | Java I/O |
| **UI** | Window | ScrollDecorator, BorderDecorator | GUI enhancements |
| **Notifications** | EmailNotifier | SMSDecorator, SlackDecorator | Multi-channel alerts |
| **Text** | PlainText | BoldDecorator, ItalicDecorator | Text formatting |

**Code Example:**

```java
// Java I/O uses Decorator extensively
InputStream input = new FileInputStream("data.txt");
input = new BufferedInputStream(input);  // Add buffering
input = new GZIPInputStream(input);      // Add compression
```


***

### Facade Pattern Examples

| Domain | Subsystems | Facade | Use Case |
| :-- | :-- | :-- | :-- |
| **Home Theater** | Projector, Lights, Sound, DVD | HomeTheaterFacade | One-click movie watching |
| **Compiler** | Lexer, Parser, Optimizer, CodeGen | CompilerFacade | Simplified compilation |
| **E-commerce** | Inventory, Payment, Shipping, Email | OrderFacade | Place order workflow |
| **Database** | Connection, Transaction, Query | DatabaseFacade | Simplified DB operations |
| **Smart Home** | Lights, Thermostat, Security | SmartHomeFacade | Scenes (leaving, arriving) |

**Code Example:**

```java
// Order processing facade
public class OrderFacade {
    public void placeOrder(Order order) {
        inventory.checkStock(order);
        payment.charge(order.getAmount());
        shipping.schedule(order);
        email.sendConfirmation(order);
    }
}
```


***

### Proxy Pattern Examples

| Type | Use Case | Example |
| :-- | :-- | :-- |
| **Virtual Proxy** | Lazy loading expensive objects | ImageProxy delays loading |
| **Protection Proxy** | Access control | AdminProxy checks permissions |
| **Remote Proxy** | Remote object access | RMI, Web Services |
| **Cache Proxy** | Cache expensive operations | QueryProxy caches DB results |
| **Smart Reference** | Reference counting | C++ smart pointers |

**Code Example:**

```java
// Image proxy for lazy loading
public class ImageProxy implements Image {
    private String filename;
    private RealImage realImage;
    
    @Override
    public void display() {
        if (realImage == null) {
            System.out.println("Loading image: " + filename);
            realImage = new RealImage(filename);
        }
        realImage.display();
    }
}
```


***

### Composite Pattern Examples

| Domain | Leaf | Composite | Use Case |
| :-- | :-- | :-- | :-- |
| **File System** | File | Folder | Files and folders |
| **Graphics** | Shape | Group | Graphic editors |
| **Organization** | Employee | Department | Company structure |
| **UI** | Button | Panel | GUI hierarchies |
| **Menu** | MenuItem | Menu | Nested menus |

**Code Example:**

```java
// Swing GUI uses Composite
JPanel panel = new JPanel();
panel.add(new JButton("OK"));
panel.add(new JLabel("Name:"));

JFrame frame = new JFrame();
frame.add(panel);  // Panel contains components
```


***

### Bridge Pattern Examples

| Domain | Abstraction | Implementation | Use Case |
| :-- | :-- | :-- | :-- |
| **Media Player** | BasicPlayer, AdvancedPlayer | Windows, Mac, Linux | Cross-platform media |
| **Drawing** | Shape (Circle, Square) | DrawingAPI (OpenGL, DirectX) | Multiple rendering |
| **Messaging** | MessageType (Email, SMS) | MessageSender (AWS, Twilio) | Multi-provider messaging |
| **Database** | Query abstraction | JDBC drivers (MySQL, PostgreSQL) | Database independence |
| **Payment** | PaymentType (Card, UPI) | Gateway (Razorpay, Stripe) | Multi-gateway support |

**Code Example:**

```java
// JDBC is a Bridge pattern
Connection conn = DriverManager.getConnection(url);  // Abstraction
// MySQL, PostgreSQL provide different implementations
```


***

### Flyweight Pattern Examples

| Domain | Flyweight (Intrinsic) | Extrinsic | Use Case |
| :-- | :-- | :-- | :-- |
| **Text Editor** | Character glyph (font, style) | Position in document | Word processors |
| **Games** | Particle type (texture, model) | Position, velocity | Particle effects |
| **Forest** | TreeType (texture, mesh) | Position, age, health | Game terrain |
| **String Pool** | String value | Variable references | Java String.intern() |
| **Icons** | Icon image | Position on screen | GUI toolkits |

**Code Example:**

```java
// Java String pool uses Flyweight
String s1 = "hello";
String s2 = "hello";
System.out.println(s1 == s2);  // true - same object shared
```


***

```
<a name="interview-questions"></a>
```


## 6️⃣ Interview Questions (50+ Questions)

### General Structural Patterns (10 Questions)

**Q1:** What are structural design patterns? How do they differ from creational and behavioral patterns?

**A:** Structural patterns deal with object composition and relationships, defining how classes and objects can be combined to form larger structures. Creational focus on object creation mechanisms, behavioral on communication between objects. Structural patterns simplify design by identifying relationships between entities.

***

**Q2:** Name all 7 structural patterns and their one-line purpose.

**A:**

1. **Adapter** - Convert interface for compatibility
2. **Decorator** - Add behavior dynamically
3. **Facade** - Simplify complex subsystem
4. **Proxy** - Control access to object
5. **Composite** - Tree structure, uniform treatment
6. **Bridge** - Decouple abstraction/implementation
7. **Flyweight** - Share objects to save memory

***

**Q3:** What's the difference between Adapter and Decorator?

**A:** Adapter changes interface to make incompatible classes work together (compatibility), while Decorator keeps same interface but adds new behavior (enhancement). Adapter is reactive (fixes existing problem), Decorator is proactive (designed enhancement).

***

**Q4:** When should you use composition over inheritance? Which patterns use composition?

**A:** Use composition when: need runtime flexibility, want to avoid class explosion, favor loose coupling. All 7 structural patterns use composition (HAS-A relationship). Examples: Decorator wraps object, Bridge has implementation reference, Composite has list of components.

***

**Q5:** Explain the difference between Proxy and Decorator with an example.

**A:** Both wrap objects, but intent differs. **Proxy** controls access (lazy loading, permissions, caching) - example: ImageProxy delays expensive image loading. **Decorator** adds features - example: Milk decorator adds milk to coffee. Proxy often creates real object internally, Decorator wraps externally provided object.

***

**Q6:** What's the "Wrapper" pattern? Which patterns are wrappers?

**A:** Wrapper is informal term for patterns that wrap objects. Three wrappers: **Adapter** (wraps to convert interface), **Decorator** (wraps to add behavior), **Proxy** (wraps to control access). Key difference is intent, not structure.

***

**Q7:** How does Bridge pattern prevent class explosion?

**A:** Without Bridge, M abstractions × N implementations = M×N classes. With Bridge, separate hierarchies: M abstraction classes + N implementation classes = M+N classes. Example: 3 remotes × 4 devices = 12 classes → Bridge: 3+4=7 classes.

***

**Q8:** What's the difference between Facade and Adapter?

**A:** **Facade** simplifies a complex subsystem by providing unified interface (one-to-many relationship). **Adapter** converts one interface to another for compatibility (one-to-one). Facade hides complexity, Adapter fixes incompatibility.

***

**Q9:** Which patterns use recursion?

**A:** Primarily **Composite** - operations on composite recursively call operations on children (tree traversal). Example: `folder.getSize()` sums sizes of all files/subfolders recursively.

***

**Q10:** What's intrinsic vs extrinsic state in Flyweight?

**A:** **Intrinsic** = shared, immutable, context-independent state stored in flyweight (e.g., font, texture). **Extrinsic** = unique, context-dependent state stored externally or passed as parameter (e.g., position, age). Flyweight shares intrinsic, clients provide extrinsic.

***

### Adapter Pattern (5 Questions)

**Q11:** When should you use Adapter over modifying existing code?

**A:** Use Adapter when: can't modify legacy/third-party code, want to avoid breaking existing functionality, need to support multiple versions, want loose coupling. Adapter wraps without modification.

***

**Q12:** What's Object Adapter vs Class Adapter?

**A:** **Object Adapter** uses composition (HAS-A) - adapter wraps adaptee instance, works in languages without multiple inheritance. **Class Adapter** uses multiple inheritance - adapter extends both target and adaptee, only works in C++. Java uses Object Adapter.

***

**Q13:** Can Adapter change method signatures?

**A:** Yes! That's the point. Adapter converts `adaptee.specificRequest()` to `target.request()`, changing method names, parameters, return types as needed.

***

**Q14:** Real-world example of Adapter pattern?

**A:** Payment gateways: `StripeAdapter` converts Stripe API's `charge(token, amount)` to your system's `processPayment(userId, amount)` interface, allowing integration without modifying either.

***

**Q15:** How is Adapter pattern different from Bridge?

**A:** **Adapter** is after-the-fact (fixes existing incompatibility). **Bridge** is designed upfront (prevents future explosion). Adapter converts interfaces, Bridge separates abstraction/implementation.

***

### Decorator Pattern (5 Questions)

**Q16:** Why use Decorator instead of subclassing?

**A:** Decorator avoids class explosion from combinations (5 features = 32 subclasses), allows runtime composition, enables stacking features dynamically. Subclassing is static, inflexible.

***

**Q17:** Can you stack multiple decorators?

**A:** Yes! That's the power. Example: `new Sugar(new Milk(new Espresso()))` stacks Sugar and Milk decorators. Order matters for behavior and cost calculation.

***

**Q18:** Real-world Java API using Decorator?

**A:** **Java I/O Streams**: `BufferedInputStream`, `GZIPInputStream`, `DataInputStream` are decorators. Example: `new BufferedInputStream(new FileInputStream("file.txt"))` adds buffering to file reading.

***

**Q19:** What's the difference between Decorator and Inheritance?

**A:** **Decorator**: runtime composition, flexible, avoids explosion. **Inheritance**: compile-time, static, causes explosion. Decorator favors composition over inheritance.

***

**Q20:** How does Decorator maintain same interface?

**A:** Decorator implements same interface as wrapped object, delegates to it, then adds behavior. Client sees same interface, unaware of decorators.

***

### Facade Pattern (5 Questions)

**Q21:** When is Facade pattern most useful?

**A:** When subsystem is complex with many classes/dependencies, clients need simple operations, want to decouple from subsystem internals. Example: ordering system coordinating inventory, payment, shipping.

***

**Q22:** Can clients still access subsystem directly?

**A:** Yes! Facade doesn't hide subsystem, just provides convenience. Advanced clients can bypass facade for fine-grained control.

***

**Q23:** Difference between Facade and Mediator?

**A:** **Facade** simplifies subsystem (one-way, top-down). **Mediator** coordinates communication between peers (bidirectional, peer-to-peer). Facade is structural, Mediator is behavioral.

***

**Q24:** Real-world example of Facade?

**A:** **Hotel reception**: Guest requests "check in" (facade), reception coordinates room service, housekeeping, billing (subsystems). Guest doesn't interact with each subsystem.

***

**Q25:** Can you have multiple Facades for same subsystem?

**A:** Yes! Different facades for different client needs. Example: `AdminFacade` with full access, `CustomerFacade` with limited operations.

***

### Proxy Pattern (5 Questions)

**Q26:** What are the 4 main types of Proxy?

**A:**

1. **Virtual Proxy** - Lazy loading (ImageProxy)
2. **Protection Proxy** - Access control (AdminProxy)
3. **Remote Proxy** - Remote object access (RMI)
4. **Cache Proxy** - Cache results (QueryProxy)

***

**Q27:** How does Virtual Proxy optimize performance?

**A:** Delays expensive object creation until actually needed. Example: `ImageProxy` creates `RealImage` only when `display()` called, not at construction.

***

**Q28:** What's the difference between Proxy and Decorator?

**A:** **Proxy** controls access (protection, optimization), creates real object itself. **Decorator** adds features, wraps externally provided object. Both have same structure, different intent.

***

**Q29:** Can Proxy be transparent to client?

**A:** Yes! Client uses proxy thinking it's the real object (same interface). Transparency is key feature - client doesn't know it's using proxy.

***

**Q30:** Real-world example of Protection Proxy?

**A:** **AdminProxy**: checks user role before allowing admin operations like `deleteUser()` or `changeConfig()`. Denies access for non-admin users.

***

### Composite Pattern (5 Questions)

**Q31:** What's the "safety vs transparency" trade-off in Composite?

**A:** **Transparency**: `add()`/`remove()` in Component interface (all nodes same interface, but leaves throw exception). **Safety**: Only in Composite (type-safe, but need casting). Most implementations choose transparency.

***

**Q32:** How does Composite achieve uniform treatment?

**A:** Both Leaf and Composite implement same Component interface. Client calls `component.operation()` without knowing if it's leaf or composite. Composite recursively delegates to children.

***

**Q33:** Real-world example of Composite?

**A:** **File system**: `getSize()` on file returns size, on folder recursively sums all files/subfolders. Same operation, uniform treatment.

***

**Q34:** What's the risk with Composite pattern?

**A:** **Circular references**: Folder A contains Folder B, B contains A → infinite recursion, StackOverflowError. Solution: validate before adding to prevent cycles.

***

**Q35:** When NOT to use Composite?

**A:** When structure is flat (no nesting), operations differ significantly between leaf/composite, or type safety is critical (can't afford runtime exceptions from leaves).

***

### Bridge Pattern (5 Questions)

**Q36:** Explain Bridge pattern with real-world analogy.

**A:** **Remote control + TV devices**: Remote (abstraction) works with any TV brand (implementation). Can use any remote with any TV. Separates "control operations" (what) from "device specifics" (how).

***

**Q37:** How does Bridge differ from Adapter?

**A:** **Bridge** designed upfront to decouple hierarchies (proactive). **Adapter** fixes existing incompatibility (reactive). Bridge prevents explosion, Adapter fixes integration.

***

**Q38:** What's the "bridge" in Bridge pattern?

**A:** The composition relationship - reference field in abstraction pointing to implementation. Example: `protected final Device device` in `RemoteControl` class. This reference bridges two hierarchies.

***

**Q39:** Can you switch implementation at runtime?

**A:** Yes! That's a key benefit. Example: `RemoteControl remote = new BasicRemote(tv)` → switch device → `remote = new BasicRemote(radio)`. Same remote, different device.

***

**Q40:** Real-world example of Bridge?

**A:** **JDBC**: `Connection` (abstraction) works with any database driver (MySQL, PostgreSQL implementations). Application code doesn't change when switching databases.

***

### Flyweight Pattern (5 Questions)

**Q41:** What problem does Flyweight solve?

**A:** Memory exhaustion from thousands of similar objects. Example: 1M characters in document would consume huge memory; Flyweight shares 100 character types, saving 99.99% memory.

***

**Q42:** Why must flyweight be immutable?

**A:** Because it's shared by many contexts. If one context changes shared state, all other contexts see unexpected changes. Immutability prevents bugs and ensures thread-safety.

***

**Q43:** When should you NOT use Flyweight?

**A:** When no shared state exists (all objects unique), few objects (memory not a concern), or reuse factor is low (<3x). Complexity not worth it.

***

**Q44:** Real-world example of Flyweight in Java?

**A:** **String pool**: `String s1 = "hello"` and `String s2 = "hello"` share same object in pool. `s1 == s2` is true. `String.intern()` forces flyweight behavior.

***

**Q45:** Calculate savings: 100K trees, 5 types, intrinsic=120 bytes, extrinsic=24 bytes.

**A:**

- Without: 100K × 144 = 14.4 MB
- With: (5 × 120) + (100K × 24) = 2.4 MB
- Saved: 12 MB (83%)

***

### Advanced/Comparison Questions (10 Questions)

**Q46:** You need to integrate Stripe, add logging, and implement lazy loading. Which patterns?

**A:** **Adapter** for Stripe integration, **Decorator** for logging (wrap adapter), **Proxy** for lazy loading (wrap decorator). Stack: `Proxy → Decorator → Adapter → Stripe`.

***

**Q47:** Company org chart with thousands of employees. Which pattern and why?

**A:** **Composite** for tree structure (departments contain employees/departments). Consider **Flyweight** if employees share job titles/roles to save memory.

***

**Q48:** 5 shapes × 3 colors × 2 sizes = 30 classes. Which pattern prevents this?

**A:** **Bridge**: Separate shape hierarchy (5) from properties hierarchy (colors/sizes = 5). Total: 5+5=10 classes. Or **Decorator**: Stack Color and Size decorators on shapes.

***

**Q49:** Explain the relationship between Proxy, Decorator, and Adapter.

**A:** All use composition (HAS-A), wrap objects, maintain same interface. Differ in **intent**: Adapter converts interface, Decorator adds behavior, Proxy controls access. Structure similar, purpose different.

***

**Q50:** Which patterns can be combined? Give example.

**A:** Many combinations:

- **Adapter + Decorator**: Adapt legacy API, then add logging
- **Facade + Singleton**: Single facade instance
- **Flyweight + Factory**: Factory manages flyweight pool
- **Composite + Flyweight**: Share leaf nodes in tree
- **Bridge + Abstract Factory**: Create implementations via factory

***

**Q51:** What's the trade-off between flexibility and complexity in structural patterns?

**A:** More flexible patterns (Bridge, Decorator, Composite) add complexity - more classes, indirection, runtime overhead. Simpler patterns (Facade, Adapter) less flexible but easier to understand. Choose based on needs.

***

**Q52:** How do you decide between Decorator and Subclassing?

**A:** **Decorator** when: need runtime composition, many feature combinations (explosion), stack features dynamically. **Subclassing** when: static hierarchy, few variations, compile-time binding sufficient.

***

**Q53:** What's the common theme across all structural patterns?

**A:** **Composition over inheritance**. All 7 patterns favor HAS-A over IS-A relationships, enabling flexibility, loose coupling, runtime behavior changes.

***

**Q54:** Interview question: "Design a cross-platform UI framework." Which patterns?

**A:**

- **Bridge**: Separate UI controls (abstraction) from platform rendering (implementation)
- **Composite**: UI hierarchy (panels contain buttons/labels)
- **Decorator**: Add borders, scrollbars dynamically
- **Facade**: Simplified API for complex UI operations

***

**Q55:** How do structural patterns support SOLID principles?

**A:**

- **Single Responsibility**: Each pattern has clear purpose
- **Open/Closed**: Add features (Decorator) without modifying existing
- **Liskov Substitution**: All patterns use interface/inheritance correctly
- **Interface Segregation**: Small, focused interfaces (Component, Subject)
- **Dependency Inversion**: Depend on abstractions (interfaces), not concrete classes

***

```
<a name="quick-reference"></a>
```


## 7️⃣ Quick Reference Cards

### One-Page Reference: All Patterns

```
┌────────────────────────────────────────────────────────────────┐
│                    STRUCTURAL PATTERNS                          │
├────────────────────────────────────────────────────────────────┤
│ ADAPTER 🔌                                                      │
│ Intent: Interface conversion                                    │
│ Structure: Client → Adapter → Adaptee                          │
│ Use: Legacy integration, third-party APIs                       │
│ Key: Wraps to convert interface                                │
├────────────────────────────────────────────────────────────────┤
│ DECORATOR 🎁                                                    │
│ Intent: Add behavior dynamically                                │
│ Structure: Component ← ConcreteComponent, Decorator (wraps)    │
│ Use: Avoid subclass explosion, stack features                   │
│ Key: Wraps to enhance functionality                            │
├────────────────────────────────────────────────────────────────┤
│ FACADE 🎭                                                       │
│ Intent: Simplify complex subsystem                              │
│ Structure: Client → Facade → Subsystem (many classes)          │
│ Use: Complex API simplification                                 │
│ Key: Unified interface to subsystem                            │
├────────────────────────────────────────────────────────────────┤
│ PROXY 🚪                                                        │
│ Intent: Control access to object                                │
│ Structure: Client → Proxy → RealSubject (same interface)       │
│ Use: Lazy loading, access control, caching                      │
│ Key: Surrogate/placeholder                                     │
├────────────────────────────────────────────────────────────────┤
│ COMPOSITE 🌲                                                    │
│ Intent: Tree structure, uniform treatment                       │
│ Structure: Component ← Leaf, Composite (List<Component>)       │
│ Use: File systems, org charts, menus                            │
│ Key: Part-whole hierarchy                                      │
├────────────────────────────────────────────────────────────────┤
│ BRIDGE 🌉                                                       │
│ Intent: Decouple abstraction/implementation                     │
│ Structure: Abstraction (has Implementation) + Impl hierarchy   │
│ Use: Avoid M×N explosion, runtime binding                       │
│ Key: Separates "what" from "how"                               │
├────────────────────────────────────────────────────────────────┤
│ FLYWEIGHT 🪶                                                    │
│ Intent: Memory optimization via sharing                         │
│ Structure: Flyweight (intrinsic) + Client (extrinsic) + Factory│
│ Use: Thousands of similar objects                               │
│ Key: Share intrinsic, pass extrinsic                           │
└────────────────────────────────────────────────────────────────┘
```


***

### Memory Aids \& Mnemonics

**Remember all 7 patterns: "A Decorated Facade Provides Complete Bridge to Flyweight"**

- **A** - Adapter
- **Decorated** - Decorator
- **Facade** - Facade
- **Provides** - Proxy
- **Complete** - Composite
- **Bridge** - Bridge
- **Flyweight** - Flyweight

***

**Pattern Purposes Mnemonic: "Can Add Simplify Control Tree Separate Share"**

- **C**onvert - Adapter
- **A**dd - Decorator
- **S**implify - Facade
- **C**ontrol - Proxy
- **T**ree - Composite
- **S**eparate - Bridge
- **S**hare - Flyweight

***

### Visual Relationship Map

```
COMPOSITION-BASED (all use HAS-A)
│
├─ WRAPPERS (wraps single object)
│  ├─ Adapter (converts interface)
│  ├─ Decorator (adds behavior)
│  └─ Proxy (controls access)
│
├─ AGGREGATION (manages multiple/complex)
│  ├─ Facade (aggregates subsystem)
│  └─ Composite (tree of components)
│
├─ SEPARATION (two hierarchies)
│  └─ Bridge (abstraction + implementation)
│
└─ SHARING (reuses objects)
   └─ Flyweight (shares via factory pool)
```


***

```
<a name="decision-tree"></a>
```


## 8️⃣ Pattern Selection Decision Tree

```
START: What problem are you solving?
│
├─ Need to integrate existing incompatible code?
│  └─ YES → ADAPTER 🔌
│
├─ Need to add features without modifying class?
│  └─ YES → DECORATOR 🎁
│
├─ Complex subsystem needs simplification?
│  └─ YES → FACADE 🎭
│
├─ Need to control/delay object access?
│  └─ YES → PROXY 🚪
│
├─ Need tree structure with uniform operations?
│  └─ YES → COMPOSITE 🌲
│
├─ Multiple dimensions causing class explosion?
│  └─ YES → BRIDGE 🌉
│
├─ Thousands of similar objects using too much memory?
│  └─ YES → FLYWEIGHT 🪶
│
└─ None of above?
   └─ Reconsider problem or use combination of patterns
```


***

### Detailed Decision Questions

**Q: Do you need to change an interface?**

- YES → Adapter or Facade
    - Converting one-to-one? → **Adapter**
    - Simplifying many-to-one? → **Facade**

**Q: Do you need to add functionality?**

- YES → Decorator or Proxy
    - Adding features (enhancements)? → **Decorator**
    - Adding control (restrictions)? → **Proxy**

**Q: Do you have hierarchical structure?**

- YES → Composite or Bridge
    - Tree with uniform operations? → **Composite**
    - Two separate hierarchies to connect? → **Bridge**

**Q: Do you have memory constraints?**

- YES → Flyweight
    - Thousands of similar objects? → **Flyweight**

***

```
<a name="common-mistakes"></a>
```


## 9️⃣ Common Mistakes Across Patterns

### Mistake 1: Confusing Similar Patterns

| Often Confused | Key Difference | Remember This |
| :-- | :-- | :-- |
| Adapter vs Decorator | Adapter changes interface, Decorator adds behavior | Adapter = compatibility, Decorator = enhancement |
| Decorator vs Proxy | Decorator adds, Proxy controls | Decorator = more, Proxy = guard |
| Facade vs Adapter | Facade simplifies many, Adapter converts one | Facade = simplify, Adapter = convert |
| Composite vs Decorator | Composite = tree (many children), Decorator = chain (one wrapped) | Composite = contains, Decorator = wraps |
| Bridge vs Strategy | Bridge = structural (two hierarchies), Strategy = behavioral (algorithms) | Bridge = structure, Strategy = behavior |
| Flyweight vs Singleton | Flyweight = multiple shared, Singleton = one global | Flyweight = pool, Singleton = one |


***

### Mistake 2: Breaking Immutability in Flyweight

```java
❌ Wrong: Mutable flyweight
@Data  // Generates setters!
public class CharacterFlyweight {
    private String font;  // Not final
}

✅ Correct: Immutable flyweight
@Getter
@AllArgsConstructor
public class CharacterFlyweight {
    private final String font;  // Final, no setters
}
```


***

### Mistake 3: Not Using Factory for Flyweight

```java
❌ Wrong: Direct instantiation
CharacterFlyweight a = new CharacterFlyweight('a');
CharacterFlyweight a2 = new CharacterFlyweight('a');
// a != a2, no sharing!

✅ Correct: Factory ensures sharing
CharacterFlyweight a = factory.getCharacter('a');
CharacterFlyweight a2 = factory.getCharacter('a');
// a == a2, shared object!
```


***

### Mistake 4: Circular References in Composite

```java
❌ Wrong: No validation
Composite folderA = new Composite("A");
Composite folderB = new Composite("B");
folderA.add(folderB);
folderB.add(folderA);  // Circular reference!
folderA.operation();  // StackOverflowError!

✅ Correct: Validate before adding
public void add(Component component) {
    if (component == this) {
        throw new IllegalArgumentException("Cannot add self");
    }
    // Additional: Check if component contains this in hierarchy
    children.add(component);
}
```


***

### Mistake 5: Adapter Changing More Than Interface

```java
❌ Wrong: Adapter adds business logic
public class PaymentAdapter implements PaymentProcessor {
    public void processPayment(double amount) {
        // ❌ Don't add validation here
        if (amount < 0) throw new Exception();
        
        // ❌ Don't add new features
        logTransaction(amount);
        
        stripe.charge(amount);
    }
}

✅ Correct: Adapter only converts
public class PaymentAdapter implements PaymentProcessor {
    public void processPayment(double amount) {
        stripe.charge(amount);  // Just convert interface
    }
}
```


***

### Mistake 6: Decorator Breaking Chain

```java
❌ Wrong: Decorator doesn't call wrapped object
public class MilkDecorator extends Decorator {
    public double cost() {
        return 2.0;  // ❌ Forgot to add wrapped.cost()
    }
}

✅ Correct: Always delegate to wrapped
public class MilkDecorator extends Decorator {
    public double cost() {
        return wrapped.cost() + 2.0;  // ✅ Chain preserved
    }
}
```


***

### Mistake 7: Proxy Not Implementing Same Interface

```java
❌ Wrong: Different interface
public class ImageProxy {
    private RealImage realImage;
    
    public void show() {  // Different method name!
        realImage.display();
    }
}

✅ Correct: Same interface as RealSubject
public class ImageProxy implements Image {
    private RealImage realImage;
    
    public void display() {  // Same interface
        realImage.display();
    }
}
```


***

### Mistake 8: Bridge with Wrong State in Wrong Hierarchy

```java
❌ Wrong: Implementation has abstraction-level data
public class WindowsImplementation {
    private String documentTitle;  // ❌ This is abstraction data!
}

✅ Correct: Implementation only has platform data
public class WindowsImplementation {
    private String platformAPI;  // ✅ Platform-specific
}

// Abstraction has document-level data
public class Document {
    private String title;  // ✅ Abstraction data
    private Implementation platform;
}
```


***

```
<a name="best-practices"></a>
```


## 🔟 Best Practices Summary

### Universal Best Practices (All Patterns)

1. **Favor Composition Over Inheritance**
    - All structural patterns use HAS-A relationships
    - More flexible, runtime-configurable
2. **Program to Interface, Not Implementation**
    - Use interfaces/abstract classes for type declarations
    - Example: `Component component = new Leaf();`
3. **Dependency Inversion**
    - Depend on abstractions, not concrete classes
    - Example: Adapter depends on Target interface
4. **Single Responsibility**
    - Each pattern has one clear purpose
    - Don't mix concerns (Adapter shouldn't decorate)
5. **Null Safety**
    - Always validate parameters in constructors
    - Use `Objects.requireNonNull()` or manual checks

***

### Pattern-Specific Best Practices

#### Adapter

- ✅ Only convert interface, don't add logic
- ✅ Use Object Adapter (composition) in Java
- ✅ Keep adapter thin (delegation only)


#### Decorator

- ✅ Always delegate to wrapped object first
- ✅ Maintain chain (don't break it)
- ✅ Keep decorators focused (single feature each)


#### Facade

- ✅ Don't hide subsystem completely (allow direct access)
- ✅ Keep facade stateless (delegates to subsystem)
- ✅ One facade per client need (AdminFacade, UserFacade)


#### Proxy

- ✅ Implement same interface as RealSubject
- ✅ Make proxy transparent to client
- ✅ Clear proxy type (Virtual, Protection, Cache)


#### Composite

- ✅ Use default methods for add/remove exceptions
- ✅ Validate to prevent circular references
- ✅ Document recursion depth limits


#### Bridge

- ✅ Make implementation reference final
- ✅ Clear separation: what (abstraction) vs how (implementation)
- ✅ Use null checks in constructor


#### Flyweight

- ✅ Always make flyweight immutable (final fields)
- ✅ Use factory to ensure sharing
- ✅ Clear documentation of intrinsic vs extrinsic
- ✅ Calculate and log memory savings

***

### Lombok Usage Guidelines

```java
// ✅ Good Lombok usage per pattern

// Adapter
@AllArgsConstructor
public class Adapter implements Target {
    private final Adaptee adaptee;
}

// Decorator
@AllArgsConstructor
public abstract class Decorator implements Component {
    protected final Component wrapped;
}

// Facade (no Lombok needed usually)
public class Facade {
    private final SubsystemA a = new SubsystemA();
}

// Proxy
public class Proxy implements Subject {
    private RealSubject real;  // Not final (lazy init)
}

// Composite
@Getter
@RequiredArgsConstructor
public class Composite implements Component {
    private final String name;
    private final List<Component> children = new ArrayList<>();
}

// Bridge
@Getter
public abstract class Abstraction {
    protected final Implementation impl;
    
    public Abstraction(Implementation impl) {
        this.impl = Objects.requireNonNull(impl);
    }
}

// Flyweight
@Getter  // Only getters, no setters!
@AllArgsConstructor
public class Flyweight {
    private final String intrinsicState;
}
```


***

## 🎓 Final Tips for Mastery

### 1. Practice Pattern Recognition

When reading code, ask:

- "Is this wrapping something?" → Adapter, Decorator, or Proxy
- "Is this a tree structure?" → Composite
- "Is this connecting two hierarchies?" → Bridge
- "Is this sharing objects?" → Flyweight
- "Is this simplifying complexity?" → Facade

***

### 2. Design Pattern Combinations

Real systems use multiple patterns:

```java
// Example: Payment system
// 1. Facade for simplification
public class PaymentFacade {
    
    // 2. Adapter for third-party integration
    private PaymentProcessor processor = new StripeAdapter();
    
    // 3. Decorator for logging
    processor = new LoggingDecorator(processor);
    
    // 4. Proxy for caching
    processor = new CachingProxy(processor);
    
    public void processPayment(double amount) {
        processor.process(amount);
    }
}
```


***

### 3. Interview Preparation

**Top 5 Questions to Practice:**

1. "Design a file system" → **Composite**
2. "Integrate legacy API" → **Adapter**
3. "Add features to coffee ordering" → **Decorator**
4. "Optimize image loading in gallery" → **Proxy + Flyweight**
5. "Cross-platform media player" → **Bridge**

***

### 4. Code Review Checklist

When implementing any structural pattern:

- [ ] Is composition used correctly (HAS-A)?
- [ ] Are interfaces/abstractions used for type declarations?
- [ ] Is the pattern solving the right problem?
- [ ] Are there null checks where needed?
- [ ] Is immutability maintained where required (Flyweight)?
- [ ] Are circular references prevented (Composite)?
- [ ] Is delegation happening correctly (Decorator, Proxy)?
- [ ] Is the interface conversion clean (Adapter)?

***

