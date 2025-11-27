***

# **FACTORY PATTERN - REVISION NOTES**

## **(Simple Factory + Factory Method)**


***

## **1. WHAT IS FACTORY PATTERN?**

**Definition:** A creational design pattern that provides an interface for creating objects without exposing the instantiation logic to the client.

**Purpose:**

- Hide object creation complexity from clients
- Decouple client code from concrete classes
- Centralize object creation logic
- Make code more maintainable and extensible

**Category:** Creational Design Pattern

***

## **2. THE PROBLEM (Without Factory)**

### **Tight Coupling Issue:**

```java
public class PaymentProcessor {
    public void processPayment(String type, double amount) {
        Payment payment;
        
        // Client knows about all concrete classes ❌
        if (type.equals("CREDIT_CARD")) {
            payment = new CreditCardPayment();  // Direct instantiation
        } else if (type.equals("PAYPAL")) {
            payment = new PayPalPayment();
        } else if (type.equals("CRYPTO")) {
            payment = new CryptoPayment();
        }
        
        payment.process(amount);
    }
}
```

**Problems:**

- ❌ Client tightly coupled to concrete classes
- ❌ Must modify client code to add new payment types
- ❌ Violates Open/Closed Principle
- ❌ Creation logic scattered across codebase
- ❌ Hard to test and maintain

***

## **3. FACTORY PATTERN TYPES**

There are **3 types** of Factory patterns:

1. **Simple Factory** (Factory Idiom) - Not a true design pattern
2. **Factory Method Pattern** - True GoF design pattern ✅
3. **Abstract Factory Pattern** - True GoF design pattern (not covered yet)

***

## **4. SIMPLE FACTORY**

### **What is Simple Factory?**

A class with a **static method** that returns different implementations based on input parameters.

### **Structure:**

```java
// Product Interface
interface Payment {
    void processPayment(double amount);
    void refund(double amount);
    void validatePaymentDetails();
}

// Concrete Products
class CreditCardPayment implements Payment {
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;
    
    public CreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public void processPayment(double amount) {
        validatePaymentDetails();
        System.out.println("Processing Credit Card payment of $" + amount);
    }
    
    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to Credit Card");
    }
    
    @Override
    public void validatePaymentDetails() {
        System.out.println("Validating Credit Card details...");
        // Validation logic
    }
}

class PayPalPayment implements Payment {
    private final String email;
    
    public PayPalPayment(String email) {
        this.email = email;
    }
    
    @Override
    public void processPayment(double amount) {
        validatePaymentDetails();
        System.out.println("Processing PayPal payment of $" + amount);
    }
    
    @Override
    public void refund(double amount) {
        System.out.println("Refunding $" + amount + " to PayPal");
    }
    
    @Override
    public void validatePaymentDetails() {
        System.out.println("Validating PayPal details...");
    }
}

// Simple Factory
public class PaymentFactory {
    
    // Static factory method
    public static Payment createPayment(PaymentType type, String... details) {
        if (type == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        
        return switch (type) {
            case CREDIT_CARD -> {
                if (details.length < 3) {
                    throw new IllegalArgumentException("Credit Card requires 3 parameters");
                }
                yield new CreditCardPayment(details[0], details[1], details[2]);
            }
            case PAYPAL -> {
                if (details.length < 1) {
                    throw new IllegalArgumentException("PayPal requires 1 parameter");
                }
                yield new PayPalPayment(details[0]);
            }
            case CRYPTO -> {
                if (details.length < 1) {
                    throw new IllegalArgumentException("Crypto requires 1 parameter");
                }
                yield new CryptoPayment(details[0]);
            }
        };
    }
    
    // Type-specific factory methods (alternative approach)
    public static Payment createCreditCardPayment(String cardNumber, String cvv, String expiryDate) {
        return new CreditCardPayment(cardNumber, cvv, expiryDate);
    }
    
    public static Payment createPayPalPayment(String email) {
        return new PayPalPayment(email);
    }
    
    public static Payment createCryptoPayment(String walletAddress) {
        return new CryptoPayment(walletAddress);
    }
}

// Enum for type safety
public enum PaymentType {
    CREDIT_CARD,
    PAYPAL,
    CRYPTO
}

// Client Usage
public class Client {
    public static void main(String[] args) {
        // Approach 1: Varargs method
        Payment payment1 = PaymentFactory.createPayment(
            PaymentType.CREDIT_CARD, 
            "1234-5678-9012-3456", 
            "123", 
            "12/25"
        );
        payment1.processPayment(100.0);
        
        // Approach 2: Type-specific method
        Payment payment2 = PaymentFactory.createCreditCardPayment(
            "1234-5678-9012-3456", 
            "123", 
            "12/25"
        );
        payment2.processPayment(150.0);
    }
}
```


### **Key Characteristics:**

✅ **Static method** - Usually `public static`
✅ **Single factory class** - All creation logic in one place
✅ **Switch/if-else logic** - Decision based on parameters
✅ **Returns interface/abstract type** - Not concrete classes
✅ **Client doesn't know concrete classes** - Decoupled

***

### **Advantages of Simple Factory:**

✅ **Encapsulation** - Creation logic in one place
✅ **Loose coupling** - Client depends on interface, not concrete classes
✅ **Easy to use** - Simple static method call
✅ **Less code** - Fewer classes than Factory Method
✅ **Centralized control** - All creation logic in one place

### **Disadvantages of Simple Factory:**

❌ **Violates Open/Closed Principle** - Must modify factory to add new types
❌ **Not extensible** - Can't subclass static methods
❌ **Hard to test** - Static methods are difficult to mock
❌ **Single point of change** - Every new type requires factory modification
❌ **Not a true design pattern** - Just a programming idiom

***

## **5. FACTORY METHOD PATTERN**

### **What is Factory Method?**

A creational pattern that defines an interface for creating objects, but lets **subclasses decide** which class to instantiate.

### **Key Principle:**

**"Define an interface for creating an object, but let subclasses decide which class to instantiate. Factory Method lets a class defer instantiation to subclasses."**

***

### **Structure:**

```java
// Product Interface (same as Simple Factory)
interface Payment {
    void processPayment(double amount);
    void refund(double amount);
    void validatePaymentDetails();
}

// Concrete Products (same as Simple Factory)
class CreditCardPayment implements Payment { /* ... */ }
class PayPalPayment implements Payment { /* ... */ }
class CryptoPayment implements Payment { /* ... */ }

// Abstract Creator (Factory)
public abstract class PaymentFactory {
    
    // Factory Method (abstract - subclasses implement)
    public abstract Payment createPayment();
    
    // Template Method (uses factory method)
    public void processTransaction(double amount) {
        // Step 1: Log
        logPaymentCreation();
        
        // Step 2: Create payment (calls subclass implementation)
        Payment payment = createPayment();
        
        // Step 3: Validate
        payment.validatePaymentDetails();
        
        // Step 4: Process
        payment.processPayment(amount);
        
        // Step 5: Confirm
        sendConfirmation(amount);
    }
    
    private void logPaymentCreation() {
        System.out.println("[LOG] Creating payment via " + this.getClass().getSimpleName());
    }
    
    private void sendConfirmation(double amount) {
        System.out.println("[CONFIRMATION] Payment of $" + amount + " completed");
    }
}

// Concrete Creators (Factories)
public class CreditCardPaymentFactory extends PaymentFactory {
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;
    
    public CreditCardPaymentFactory(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public Payment createPayment() {
        return new CreditCardPayment(cardNumber, cvv, expiryDate);
    }
}

public class PayPalPaymentFactory extends PaymentFactory {
    private final String email;
    
    public PayPalPaymentFactory(String email) {
        this.email = email;
    }
    
    @Override
    public Payment createPayment() {
        return new PayPalPayment(email);
    }
}

public class CryptoPaymentFactory extends PaymentFactory {
    private final String walletAddress;
    
    public CryptoPaymentFactory(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    
    @Override
    public Payment createPayment() {
        return new CryptoPayment(walletAddress);
    }
}

// Client Usage
public class Client {
    public static void main(String[] args) {
        // Create factory instance
        PaymentFactory factory = new CreditCardPaymentFactory(
            "1234-5678-9012-3456", 
            "123", 
            "12/25"
        );
        
        // Option 1: Create payment explicitly
        Payment payment = factory.createPayment();
        payment.processPayment(100.0);
        
        // Option 2: Use template method
        factory.processTransaction(150.0);  // Includes logging, validation, confirmation
    }
}
```


***

### **Key Components:**

1. **Product** (`Payment`) - Interface for objects the factory creates
2. **Concrete Products** (`CreditCardPayment`, etc.) - Implementations of Product
3. **Creator** (`PaymentFactory`) - Abstract class declaring factory method
4. **Concrete Creators** (`CreditCardPaymentFactory`, etc.) - Override factory method

***

### **Key Characteristics:**

✅ **Abstract factory method** - Subclasses implement
✅ **Inheritance-based** - Uses class hierarchy
✅ **Instance methods** - Not static (can be overridden)
✅ **Each product has its own factory** - One factory per product type
✅ **Template Method Pattern** - Common algorithm in base class
✅ **Open/Closed Principle** - Add new types without modifying existing code

***

### **Advantages of Factory Method:**

✅ **Open/Closed Principle** - Extend by adding new subclasses
✅ **Highly extensible** - Easy to add new product types
✅ **Testable** - Can mock factory instances
✅ **Flexible** - Subclasses control instantiation
✅ **Single Responsibility** - Each factory creates one type
✅ **Template Method support** - Common logic in base class

### **Disadvantages of Factory Method:**

❌ **More classes** - Need a factory for each product type
❌ **More complex** - Additional abstraction layer
❌ **Overkill for simple cases** - Unnecessary if 2-3 static types
❌ **More code** - More boilerplate than Simple Factory

***

## **6. COMPARISON: SIMPLE FACTORY vs FACTORY METHOD**

| **Aspect** | **Simple Factory** | **Factory Method** |
| :-- | :-- | :-- |
| **True Design Pattern?** | No (idiom) | Yes (GoF pattern) |
| **Structure** | Single class with static method | Abstract class + concrete subclasses |
| **Creation Logic** | In one method (if/switch) | In subclass (override) |
| **Extensibility** | Modify factory class ❌ | Add new subclass ✅ |
| **Open/Closed** | Violates ❌ | Follows ✅ |
| **Number of Classes** | Fewer (1 factory) | More (1 factory per type) |
| **Complexity** | Simple | More complex |
| **Testability** | Hard (static methods) | Easy (instance methods) |
| **Use Case** | 2-5 types, rarely change | Many types, frequent additions |
| **Method Type** | Static | Instance (non-static) |
| **Inheritance** | Not used | Core mechanism |


***

### **When Adding New Product Type:**

**Simple Factory:**

```java
// Must MODIFY existing factory class ❌
public static Payment createPayment(PaymentType type, String... details) {
    return switch (type) {
        case CREDIT_CARD -> ...
        case PAYPAL -> ...
        case CRYPTO -> ...
        case BANK_TRANSFER -> ...  // ← Modification required
    };
}
```

**Factory Method:**

```java
// Just ADD new class, no modification ✅
public class BankTransferPaymentFactory extends PaymentFactory {
    @Override
    public Payment createPayment() {
        return new BankTransferPayment();
    }
}
```


***

## **7. YOUR IMPLEMENTATIONS**

### **Simple Factory - Payment System**

**What You Built:**

- `Payment` interface with 3 methods
- 3 concrete payments: CreditCard, PayPal, Crypto
- `PaymentFactory` with varargs method + type-specific methods
- `PaymentType` enum for type safety
- Validation in factory
- Masking sensitive data (card numbers)

**Key Decisions:**

- ✅ Used enum instead of String for type safety
- ✅ Provided both varargs and type-specific methods
- ✅ Validated parameters before creating objects
- ✅ Used switch expression (Java 17+)

***

### **Factory Method - Payment System**

**What You Built:**

- Refactored Simple Factory to Factory Method
- Abstract `PaymentFactory` with `createPayment()` and `processTransaction()`
- 3 concrete factories storing payment details as fields
- Template method with 5 steps: log → create → validate → process → confirm
- Immutable factory fields

**Key Decisions:**

- ✅ Payment details stored in factory (not passed to method)
- ✅ Template method for common workflow
- ✅ Each factory responsible for one payment type
- ✅ Easy to extend without modifying existing code

***

### **Factory Method - Notification System**

**What You Built:**

- Abstract `Notification` class with `send()` method
- 4 notifications: Email, SMS, Push, Slack
- Abstract `NotificationFactory` with template method
- 4 concrete factories with validation
- Business rules enforcement

**Validations Implemented:**

- Email: subject \& body not empty ✅
- SMS: message ≤ 160 characters ✅
- Push: title ≤ 50 characters ✅
- Slack: channel starts with '\#' ✅

**Key Decisions:**

- ✅ Validation in factory `createNotification()` method
- ✅ Immutable notifications with Lombok
- ✅ Template method in factory for reusable notifications
- ⚠️ Discussed: Logging placement (factory vs notification class)
- ⚠️ Discussed: Validation placement (factory vs constructor)

***

## **8. WHEN TO USE EACH PATTERN**

### **Use Simple Factory When:**

✅ You have **2-5 product types** that rarely change
✅ Creation logic is **simple**
✅ You want **minimal code** and classes
✅ Types are **static and well-defined**
✅ **Testability** is not a critical concern
✅ You're okay with **modifying** factory for new types

**Example Use Cases:**

- Configuration-based object creation
- Creating database connections (MySQL, PostgreSQL, MongoDB)
- Simple shape creation (Circle, Square, Triangle)
- Logger creation (FileLogger, ConsoleLogger, DatabaseLogger)

***

### **Use Factory Method When:**

✅ You expect **frequent addition** of new product types
✅ You need to follow **Open/Closed Principle** strictly
✅ **Extensibility** is more important than simplicity
✅ You need **testable** code (mocking factories)
✅ Product creation has **complex logic** per type
✅ You want **template method** support for common workflow
✅ Different products have **different initialization** requirements

**Example Use Cases:**

- Plugin architectures (new plugins added frequently)
- Payment gateways (frequently add new providers)
- Notification systems (email, SMS, push, Slack, WhatsApp, etc.)
- Document processors (PDF, Word, Excel - each with complex setup)
- UI component libraries (different themes/platforms)

***

## **9. REAL-WORLD EXAMPLES**

### **Simple Factory Examples:**

**Java Standard Library:**

```java
// NumberFormat
NumberFormat formatter = NumberFormat.getInstance();
NumberFormat currency = NumberFormat.getCurrencyInstance();

// Calendar
Calendar calendar = Calendar.getInstance();

// Logger
Logger logger = Logger.getLogger("MyLogger");
```

**Your Code:**

```java
Payment payment = PaymentFactory.createPayment(PaymentType.CREDIT_CARD, "1234", "123", "12/25");
```


***

### **Factory Method Examples:**

**Java Standard Library:**

```java
// Collection iterators
List<String> list = new ArrayList<>();
Iterator<String> iterator = list.iterator();  // ArrayList provides its iterator

// JDBC Connection
Connection conn = DriverManager.getConnection(url, user, pass);  // Driver-specific connection
```

**Frameworks:**

- **Spring Framework** - BeanFactory and ApplicationContext
- **Android** - Fragment factories
- **Hibernate** - SessionFactory

**Your Code:**

```java
PaymentFactory factory = new CreditCardPaymentFactory("1234", "123", "12/25");
Payment payment = factory.createPayment();
```


***

## **10. COMMON MISTAKES TO AVOID**

### **Simple Factory Mistakes:**

❌ **Not using enums** - Using strings leads to typos

```java
// Bad
createPayment("CREDTI_CARD");  // Typo! Runtime error

// Good
createPayment(PaymentType.CREDIT_CARD);  // Compile-time safety
```

❌ **Returning null** - Should throw exception

```java
// Bad
public static Payment createPayment(PaymentType type) {
    if (type == CREDIT_CARD) return new CreditCardPayment();
    return null;  // ❌ NullPointerException waiting to happen
}

// Good
public static Payment createPayment(PaymentType type) {
    return switch (type) {
        case CREDIT_CARD -> new CreditCardPayment();
        // Compiler ensures all cases covered with switch expression
    };
}
```

❌ **Not validating parameters** - Leads to invalid objects

```java
// Bad
public static Payment createPayment(String... details) {
    return new CreditCardPayment(details[0], details[1], details[2]);
    // ❌ ArrayIndexOutOfBounds if details.length < 3
}

// Good
if (details.length < 3) {
    throw new IllegalArgumentException("Credit Card requires 3 parameters");
}
```


***

### **Factory Method Mistakes:**

❌ **Making factory method final** - Can't override

```java
// Bad
public final Payment createPayment() { ... }  // ❌ Defeats the purpose

// Good
public abstract Payment createPayment();  // ✅ Subclasses override
```

❌ **Duplicating logic in subclasses** - Should use template method

```java
// Bad - Repeated in each factory
public class CreditCardPaymentFactory extends PaymentFactory {
    public void processTransaction(double amount) {
        log();        // Repeated
        validate();   // Repeated
        process();    // Repeated
        confirm();    // Repeated
    }
}

// Good - In abstract class
public abstract class PaymentFactory {
    public void processTransaction(double amount) {
        log();
        Payment p = createPayment();  // Only this varies
        validate();
        process();
    }
}
```

❌ **Not storing required data in factory** - Passing too many parameters

```java
// Bad
public abstract Payment createPayment(String card, String cvv, String expiry);
// All subclasses need same parameters

// Good
public class CreditCardPaymentFactory {
    private final String card, cvv, expiry;
    
    public CreditCardPaymentFactory(String card, String cvv, String expiry) {
        this.card = card; this.cvv = cvv; this.expiry = expiry;
    }
    
    public Payment createPayment() {
        return new CreditCardPayment(card, cvv, expiry);
    }
}
```


***

## **11. VALIDATION STRATEGIES**

### **Where to Validate?**

You explored three approaches:

**Option 1: Validation in Factory**

```java
public Payment createPayment() {
    if (cardNumber == null || cardNumber.isEmpty()) {
        throw new IllegalArgumentException("Card number required");
    }
    return new CreditCardPayment(cardNumber, cvv, expiry);
}
```

**Pros:** Fail-fast before object creation
**Cons:** Can bypass if object created directly (not via factory)

***

**Option 2: Validation in Constructor (Recommended)**

```java
public class CreditCardPayment {
    public CreditCardPayment(String cardNumber, String cvv, String expiry) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            throw new IllegalArgumentException("Card number required");
        }
        this.cardNumber = cardNumber;
    }
}
```

**Pros:** Impossible to create invalid object, even without factory ✅
**Cons:** Can't use Lombok `@AllArgsConstructor`

***

**Option 3: Validation in Method**

```java
public void processPayment(double amount) {
    if (cardNumber == null) {
        throw new IllegalArgumentException("Invalid card");
    }
    // Process payment
}
```

**Pros:** Object can exist before validation
**Cons:** Delayed validation, invalid objects can exist ❌

***

**Recommendation:** **Option 2 (Constructor)** for production code - guarantees object validity.

***

## **12. CODE TEMPLATES**

### **Simple Factory Template:**

```java
// Product Interface
public interface Product {
    void operation();
}

// Concrete Products
public class ConcreteProductA implements Product {
    @Override
    public void operation() {
        System.out.println("Product A operation");
    }
}

public class ConcreteProductB implements Product {
    @Override
    public void operation() {
        System.out.println("Product B operation");
    }
}

// Product Type Enum
public enum ProductType {
    PRODUCT_A,
    PRODUCT_B
}

// Simple Factory
public class ProductFactory {
    public static Product createProduct(ProductType type) {
        if (type == null) {
            throw new IllegalArgumentException("Product type cannot be null");
        }
        
        return switch (type) {
            case PRODUCT_A -> new ConcreteProductA();
            case PRODUCT_B -> new ConcreteProductB();
        };
    }
}

// Usage
Product product = ProductFactory.createProduct(ProductType.PRODUCT_A);
product.operation();
```


***

### **Factory Method Template:**

```java
// Product Interface
public interface Product {
    void operation();
}

// Concrete Products
public class ConcreteProductA implements Product {
    @Override
    public void operation() {
        System.out.println("Product A operation");
    }
}

public class ConcreteProductB implements Product {
    @Override
    public void operation() {
        System.out.println("Product B operation");
    }
}

// Abstract Creator
public abstract class ProductFactory {
    
    // Factory method (abstract - subclasses implement)
    public abstract Product createProduct();
    
    // Template method (uses factory method)
    public void doSomethingWithProduct() {
        Product product = createProduct();
        product.operation();
    }
}

// Concrete Creators
public class ConcreteProductAFactory extends ProductFactory {
    @Override
    public Product createProduct() {
        return new ConcreteProductA();
    }
}

public class ConcreteProductBFactory extends ProductFactory {
    @Override
    public Product createProduct() {
        return new ConcreteProductB();
    }
}

// Usage
ProductFactory factory = new ConcreteProductAFactory();
Product product = factory.createProduct();
product.operation();

// Or use template method
factory.doSomethingWithProduct();
```


***

## **13. DESIGN PRINCIPLES**

### **SOLID Principles Applied:**

**Single Responsibility Principle (SRP):**

- Each factory class creates only one type of product
- Product classes handle only their own logic

**Open/Closed Principle (OCP):**

- Factory Method: Open for extension (add new factory), closed for modification ✅
- Simple Factory: Violates OCP (must modify factory for new types) ❌

**Liskov Substitution Principle (LSP):**

- All concrete products can substitute Product interface
- All concrete factories can substitute abstract factory

**Dependency Inversion Principle (DIP):**

- Client depends on Product interface, not concrete classes
- High-level code doesn't depend on low-level details

***

## **14. INTERVIEW TIPS**

### **Common Questions:**

**Q: Why use Factory Pattern instead of direct instantiation?**

> "Factory Pattern decouples client code from concrete classes, making the code more flexible and maintainable. If product types change or new types are added, only the factory needs updating (Simple Factory) or we just add new factory classes (Factory Method), without touching client code."

***

**Q: What's the difference between Simple Factory and Factory Method?**

> "Simple Factory uses a static method with if/switch logic in a single class, requiring modification to add new types. Factory Method uses inheritance - each product type has its own factory subclass, allowing extension without modification, following the Open/Closed Principle."

***

**Q: When would you choose Factory Method over Simple Factory?**

> "Factory Method when extensibility is important and we expect frequent addition of new product types, especially in plugin architectures or systems with many product variants. Simple Factory when we have a small, stable set of products (2-5 types) that rarely change."

***

**Q: How does Factory Method support Open/Closed Principle?**

> "By using inheritance - to add a new product type, we create a new factory subclass without modifying existing code. The abstract factory defines the interface, and new concrete factories extend it, keeping existing code unchanged."

***

**Q: Can you give a real-world example of Factory Method?**

> "Java's Collection framework - ArrayList and LinkedList both provide iterators, but each returns its own iterator implementation. The List interface declares iterator() method (factory method), and each concrete list class implements it to return its specific iterator type."

***

**Q: What's the relationship between Factory Method and Template Method patterns?**

> "They often work together. Factory Method defines WHAT object to create (varies by subclass), while Template Method defines HOW to use that object (same algorithm for all subclasses). The abstract class's template method calls the factory method to get the right product, then performs common operations on it."

***

## **15. KEY TAKEAWAYS**

### **Simple Factory:**

1. **Single static method** creating different products
2. **Switch/if-else logic** based on parameters
3. **Simple to use** but **violates Open/Closed Principle**
4. **Good for 2-5 stable product types**
5. **Hard to test** (static methods)
6. **Must modify factory** to add new types

***

### **Factory Method:**

1. **Abstract factory method** in base class
2. **Subclasses override** to create specific products
3. **Follows Open/Closed Principle** - extend via new subclass
4. **Template Method support** - common workflow in base class
5. **Testable** - can mock factory instances
6. **More classes but more flexible**

***

### **Choosing Between Them:**

**Simple Factory:**

- Small number of types (2-5)
- Types rarely change
- Simplicity > Extensibility
- Quick prototypes

**Factory Method:**

- Many types or frequent additions
- Extensibility important
- Need testable code
- Production systems with evolving requirements

***

### **Your Learning Journey:**

✅ Built Simple Factory with Payment System
✅ Implemented type-safe enums
✅ Added varargs + type-specific methods
✅ Refactored to Factory Method with inheritance
✅ Added template method with 5-step workflow
✅ Built Notification System from scratch
✅ Explored validation strategies
✅ Quiz score: 9.5/11
✅ Strong understanding of Open/Closed Principle

***

## **16. WHAT'S NEXT**

**Abstract Factory Pattern** - Creating families of related products (e.g., UI themes with Button + Checkbox + TextField)

***
