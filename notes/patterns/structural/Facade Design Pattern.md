# Facade Pattern - Revision Notes 📝


***

## Overview

**Facade Pattern** is a structural design pattern that provides a **simplified, unified interface** to a complex subsystem. It hides the complexity of the system and provides a higher-level API that makes the subsystem easier to use.

**Think of it as:** A hotel receptionist—instead of calling the chef, housekeeper, room service, and valet separately, you just call the front desk who coordinates everything for you.

***

## The Problem Facade Solves

### Without Facade (Complex ❌)

```java
// Client must know about ALL subsystems and their interactions!
public class Client {
    public void startMovie() {
        TV tv = new TV();
        DVDPlayer dvd = new DVDPlayer();
        Amplifier amp = new Amplifier();
        Lights lights = new Lights();
        Projector projector = new Projector();
        
        // Client must know the EXACT sequence!
        lights.dim(10);
        projector.on();
        projector.setInput(dvd);
        amp.on();
        amp.setInput(dvd);
        amp.setVolume(5);
        dvd.on();
        dvd.play("Inception");
    }
}
```

**Problems:**

- ❌ Client depends on 5 different classes
- ❌ Client must know exact workflow
- ❌ Code duplication across clients
- ❌ Hard to maintain (workflow change breaks all clients)


### With Facade (Simple ✅)

```java
// Facade hides complexity
public class HomeTheaterFacade {
    private TV tv;
    private DVDPlayer dvd;
    private Amplifier amp;
    private Lights lights;
    private Projector projector;
    
    public HomeTheaterFacade(...) {
        // Initialize subsystems
    }
    
    public void watchMovie(String movie) {
        lights.dim(10);
        projector.on();
        projector.setInput(dvd);
        amp.on();
        amp.setInput(dvd);
        amp.setVolume(5);
        dvd.on();
        dvd.play(movie);
    }
    
    public void endMovie() {
        dvd.stop();
        dvd.eject();
        amp.off();
        projector.off();
        lights.on();
    }
}

// Client code - SIMPLE!
public class Client {
    public void startMovie() {
        HomeTheaterFacade theater = new HomeTheaterFacade(...);
        theater.watchMovie("Inception");  // ONE method call!
    }
}
```

**Benefits:**

- ✅ Client depends on 1 class (Facade)
- ✅ Simple API (1-2 method calls)
- ✅ Workflow encapsulated in Facade
- ✅ Easy to maintain

***

## When to Use Facade Pattern

✅ **Use Facade When:**

1. You have a **complex subsystem** with many interdependent classes
2. You want to provide a **simple interface** for common use cases
3. You want to **reduce coupling** between clients and subsystems
4. You want to **layer your system** (facade can be entry point to a layer)
5. Subsystems are **difficult to use** due to complexity
6. You want to **hide implementation details** from clients

❌ **Don't Use When:**

- Subsystem is already simple
- You need fine-grained control (facade might be too restrictive)
- You're just adding a wrapper with no simplification
- You want to add behavior (use Decorator instead)
- You want to adapt interfaces (use Adapter instead)

***

## Real-World Examples

| Scenario | Facade Use |
| :-- | :-- |
| **E-Commerce Checkout** | Single checkout method wraps inventory, payment, shipping, email |
| **Computer Startup** | One "power on" button starts CPU, memory, hard drive, OS |
| **Banking Portal** | Customer dashboard hides account, loan, transaction, credit card systems |
| **API Gateway** | Single entry point to multiple microservices |
| **JDBC** | `DataSource` facade simplifies database connection management |
| **Compiler** | Single compile command coordinates lexer, parser, optimizer, code generator |
| **Spring Boot** | `@SpringBootApplication` facade hides complex configuration |


***

## Facade Pattern Structure

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │ uses
       ↓
┌──────────────────┐
│     Facade       │  ← Simplified interface
│                  │
│ + operation1()   │
│ + operation2()   │
└────┬─────┬───┬───┘
     │     │   │
     │     │   └──────┐
     ↓     ↓          ↓
┌─────────┬─────────┬──────────┐
│SubsystemA│SubsystemB│SubsystemC│  ← Complex subsystems
└──────────┴──────────┴──────────┘
```

**Key Components:**

1. **Facade:** Provides simple interface, delegates to subsystems
2. **Subsystems:** Complex classes with detailed functionality
3. **Client:** Uses Facade instead of subsystems directly

**Important:** Clients can still access subsystems directly if needed! Facade doesn't prevent it, just simplifies common cases.

***

## Core Implementation Pattern

### 1. Subsystem Classes (Complex)

```java
// Multiple complex subsystems
public class InventoryService {
    public boolean checkStock(String productId, int quantity) { ... }
    public void reserveStock(String productId, int quantity) { ... }
    public void updateStock(String productId, int quantity) { ... }
    public void releaseStock(String productId, int quantity) { ... }
}

public class PaymentService {
    public boolean processPayment(String customerId, double amount) { ... }
    public void refund(String customerId, double amount) { ... }
}

public class ShippingService {
    public String scheduleShipping(String productId, String address, int quantity) { ... }
    public void cancelShipping(String trackingNumber) { ... }
}

public class EmailService {
    public void sendConfirmationEmail(String customerId, String orderId) { ... }
    public void sendCancellationEmail(String customerId, String reason) { ... }
}

public class InvoiceService {
    public String generateInvoice(String customerId, String productId, int quantity, double amount) { ... }
}
```


### 2. Facade (Simplifies)

```java
public class OrderFacade {
    // Encapsulate all subsystems
    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;
    private final EmailService email;
    private final InvoiceService invoice;
    
    // Initialize subsystems
    public OrderFacade() {
        this.inventory = new InventoryService();
        this.payment = new PaymentService();
        this.shipping = new ShippingService();
        this.email = new EmailService();
        this.invoice = new InvoiceService();
    }
    
    // Simplified interface - ONE method for complex workflow
    public boolean placeOrder(String customerId, String productId, 
                             int quantity, double amount, String address) {
        // 1. Check stock
        if (!inventory.checkStock(productId, quantity)) {
            email.sendCancellationEmail(customerId, "Out of stock");
            return false;
        }
        
        // 2. Reserve stock
        inventory.reserveStock(productId, quantity);
        
        // 3. Process payment
        if (!payment.processPayment(customerId, amount)) {
            inventory.releaseStock(productId, quantity);  // ROLLBACK!
            email.sendCancellationEmail(customerId, "Payment failed");
            return false;
        }
        
        // 4. Update inventory
        inventory.updateStock(productId, quantity);
        
        // 5. Schedule shipping
        String trackingNumber = shipping.scheduleShipping(productId, address, quantity);
        
        // 6. Generate invoice
        String invoiceId = invoice.generateInvoice(customerId, productId, quantity, amount);
        
        // 7. Send confirmation
        email.sendConfirmationEmail(customerId, invoiceId);
        
        return true;
    }
}
```


### 3. Client Usage (Simple)

```java
public class Client {
    public static void main(String[] args) {
        OrderFacade facade = new OrderFacade();
        
        // ONE simple method call!
        boolean success = facade.placeOrder(
            "CUST123", 
            "PROD456", 
            2, 
            99.99, 
            "123 Main St, Delhi"
        );
        
        System.out.println(success ? "Success!" : "Failed!");
    }
}
```


***

## Key Characteristics

### 1. Loose Coupling

**Without Facade:**

```
Client ──┬──> InventoryService
         ├──> PaymentService
         ├──> ShippingService
         ├──> EmailService
         └──> InvoiceService
(Client depends on 5 classes)
```

**With Facade:**

```
Client ────> Facade ──┬──> InventoryService
                      ├──> PaymentService
                      ├──> ShippingService
                      ├──> EmailService
                      └──> InvoiceService
(Client depends on 1 class)
```


### 2. Simplified Interface

Facade exposes **only essential operations**, hiding complex details:

```java
// Client doesn't need to know:
// - How many subsystems exist
// - What order to call them
// - How to handle errors
// - What rollback logic is needed

// Just calls:
facade.placeOrder(...);
```


### 3. Workflow Encapsulation

Complex workflows hidden inside facade methods:

```java
// 7-step workflow hidden from client
public boolean placeOrder(...) {
    // Step 1: Check
    // Step 2: Reserve
    // Step 3: Pay
    // Step 4: Update
    // Step 5: Ship
    // Step 6: Invoice
    // Step 7: Email
}
```


### 4. Error Handling \& Rollback

Facade handles error recovery transparently:

```java
if (!payment.processPayment(...)) {
    inventory.releaseStock(...);  // Rollback
    email.sendCancellationEmail(...);
    return false;
}
```

Client doesn't need to know about rollback logic!

***

## Code Templates

### Template 1: Basic Facade

```java
// Subsystems
class SubsystemA {
    public void operationA() { ... }
}

class SubsystemB {
    public void operationB() { ... }
}

class SubsystemC {
    public void operationC() { ... }
}

// Facade
public class Facade {
    private SubsystemA subsystemA;
    private SubsystemB subsystemB;
    private SubsystemC subsystemC;
    
    public Facade() {
        this.subsystemA = new SubsystemA();
        this.subsystemB = new SubsystemB();
        this.subsystemC = new SubsystemC();
    }
    
    // Simplified operation
    public void simplifiedOperation() {
        subsystemA.operationA();
        subsystemB.operationB();
        subsystemC.operationC();
    }
}

// Client
Facade facade = new Facade();
facade.simplifiedOperation();  // Easy!
```


***

### Template 2: Facade with Dependency Injection

```java
public class OrderFacade {
    private final InventoryService inventory;
    private final PaymentService payment;
    private final ShippingService shipping;
    
    // Constructor injection (better for testing)
    public OrderFacade(InventoryService inventory, 
                      PaymentService payment,
                      ShippingService shipping) {
        this.inventory = inventory;
        this.payment = payment;
        this.shipping = shipping;
    }
    
    public boolean placeOrder(...) {
        // Implementation
    }
}

// Usage
OrderFacade facade = new OrderFacade(
    new InventoryService(),
    new PaymentService(),
    new ShippingService()
);

// Or with Spring:
@Service
public class OrderFacade {
    @Autowired
    public OrderFacade(InventoryService inventory, ...) { ... }
}
```


***

### Template 3: Multiple Facades for Same Subsystem

```java
// Subsystems
class AccountService { ... }
class LoanService { ... }
class TransactionService { ... }

// Facade 1: For customers
public class CustomerBankingFacade {
    private AccountService account;
    private TransactionService transaction;
    
    public double checkBalance(String accountId) { ... }
    public void transferMoney(String from, String to, double amount) { ... }
}

// Facade 2: For admins
public class AdminBankingFacade {
    private AccountService account;
    private LoanService loan;
    private TransactionService transaction;
    
    public void freezeAccount(String accountId) { ... }
    public void auditTransactions() { ... }
    public void approveLoan(String loanId) { ... }
}

// Different facades for different user roles!
```


***

### Template 4: Facade with Rich Return Type

```java
// Result object
public class OrderResult {
    private final boolean success;
    private final String orderId;
    private final String trackingNumber;
    private final String errorMessage;
    
    // Constructors
    public static OrderResult success(String orderId, String trackingNumber) {
        return new OrderResult(true, orderId, trackingNumber, null);
    }
    
    public static OrderResult failure(String errorMessage) {
        return new OrderResult(false, null, null, errorMessage);
    }
    
    // Getters
}

// Facade
public class OrderFacade {
    public OrderResult placeOrder(...) {
        if (!inventory.checkStock(...)) {
            return OrderResult.failure("Out of stock");
        }
        
        // ... workflow ...
        
        return OrderResult.success(invoiceId, trackingNumber);
    }
}

// Client
OrderResult result = facade.placeOrder(...);
if (result.isSuccess()) {
    System.out.println("Order: " + result.getOrderId());
    System.out.println("Track: " + result.getTrackingNumber());
} else {
    System.out.println("Error: " + result.getErrorMessage());
}
```


***

## Common Mistakes \& Solutions

### ❌ Mistake 1: God Object Facade

```java
// Bad - Facade does EVERYTHING!
public class SystemFacade {
    public void manageUsers() { ... }
    public void processPayments() { ... }
    public void handleInventory() { ... }
    public void sendEmails() { ... }
    public void generateReports() { ... }
    public void manageProducts() { ... }
    public void trackOrders() { ... }
    // ... 30 more methods!
}
```

**Problem:** Violates Single Responsibility Principle, becomes unmaintainable.

**Solution:** Multiple focused facades

```java
// Good - Separate facades
public class OrderFacade {
    public boolean placeOrder(...) { ... }
    public boolean cancelOrder(...) { ... }
}

public class InventoryFacade {
    public void updateStock(...) { ... }
    public int checkAvailability(...) { ... }
}

public class ReportFacade {
    public void generateSalesReport(...) { ... }
    public void generateInventoryReport(...) { ... }
}
```


***

### ❌ Mistake 2: Missing Rollback Logic

```java
// Bad - No rollback on failure!
public boolean placeOrder(...) {
    inventory.reserveStock(productId, quantity);
    
    if (!payment.processPayment(...)) {
        return false;  // ❌ Stock still reserved!
    }
    
    // Continue...
}
```

**Problem:** Reserved stock never released if payment fails.

**Solution:** Proper rollback

```java
// Good - Rollback on failure
public boolean placeOrder(...) {
    inventory.reserveStock(productId, quantity);
    
    if (!payment.processPayment(...)) {
        inventory.releaseStock(productId, quantity);  // ✅ Rollback!
        email.sendCancellationEmail(...);
        return false;
    }
    
    // Continue...
}
```


***

### ❌ Mistake 3: Facade Doing Business Logic

```java
// Bad - Facade contains business logic!
public class OrderFacade {
    public boolean placeOrder(...) {
        // Business logic in facade!
        double discount = amount > 1000 ? amount * 0.1 : 0;
        double tax = amount * 0.18;
        double finalAmount = amount - discount + tax;
        
        payment.processPayment(customerId, finalAmount);
    }
}
```

**Problem:** Facade should coordinate, not contain business logic.

**Solution:** Delegate to subsystems

```java
// Good - Business logic in subsystem
public class PricingService {
    public double calculateFinalPrice(double amount) {
        double discount = amount > 1000 ? amount * 0.1 : 0;
        double tax = amount * 0.18;
        return amount - discount + tax;
    }
}

public class OrderFacade {
    private PricingService pricing;
    
    public boolean placeOrder(...) {
        double finalAmount = pricing.calculateFinalPrice(amount);
        payment.processPayment(customerId, finalAmount);
    }
}
```


***

### ❌ Mistake 4: Exposing Subsystem Types

```java
// Bad - Returns subsystem type!
public class OrderFacade {
    public Invoice getInvoice(String orderId) {
        return invoiceService.findById(orderId);  // Returns Invoice from subsystem
    }
}

// Client
Invoice invoice = facade.getInvoice("123");  // Client depends on Invoice class!
```

**Problem:** Client still couples to subsystem types.

**Solution:** Return facade-specific types or primitives

```java
// Good - Returns facade type
public class InvoiceDTO {
    private String invoiceId;
    private double amount;
    // Facade-specific type
}

public class OrderFacade {
    public InvoiceDTO getInvoice(String orderId) {
        Invoice invoice = invoiceService.findById(orderId);
        return new InvoiceDTO(invoice.getId(), invoice.getAmount());
    }
}
```


***

## Pattern Comparisons

### Facade vs Adapter

| Aspect | Facade | Adapter |
| :-- | :-- | :-- |
| **Purpose** | Simplify complex subsystem | Make incompatible interfaces work |
| **Number of classes** | Works with multiple subsystems | Usually wraps one adaptee |
| **Interface** | Creates new simplified interface | Converts existing interface |
| **Client awareness** | Client knows it's using facade | Client thinks it's using target interface |
| **Example** | Home theater "Movie Mode" | Celsius sensor adapter for Fahrenheit system |

**Key:** Facade simplifies many things, Adapter converts one thing.

***

### Facade vs Decorator

| Aspect | Facade | Decorator |
| :-- | :-- | :-- |
| **Purpose** | Simplify subsystem | Add features dynamically |
| **Interface** | Different (simplified) | Same interface |
| **Wrapping** | Wraps multiple subsystems | Wraps single component |
| **Stacking** | Usually single facade | Can stack decorators |
| **Example** | Order checkout workflow | Coffee + Milk + Sugar |

**Key:** Facade simplifies, Decorator enhances.

***

### Facade vs Proxy

| Aspect | Facade | Proxy |
| :-- | :-- | :-- |
| **Purpose** | Simplify complex system | Control access to object |
| **Subsystems** | Multiple subsystems | Single subject |
| **Interface** | Simplified interface | Same interface as subject |
| **Adds** | Convenience methods | Access control, lazy loading, caching |
| **Example** | Banking portal | Virtual proxy for large image |

**Key:** Facade simplifies many, Proxy controls one.

***

## Testing Facades

### Option 1: Test with Real Subsystems (Integration Test)

```java
@Test
public void testOrderPlacement() {
    OrderFacade facade = new OrderFacade();
    
    boolean success = facade.placeOrder("C1", "P1", 2, 100, "Address");
    
    assertTrue(success);
}
```

**Pros:** Tests real integration
**Cons:** Slow, needs database/services

***

### Option 2: Test with Mocks (Unit Test)

```java
@Test
public void testOrderRollbackOnPaymentFailure() {
    // Create mocks
    InventoryService mockInventory = mock(InventoryService.class);
    PaymentService mockPayment = mock(PaymentService.class);
    EmailService mockEmail = mock(EmailService.class);
    
    // Inject mocks
    OrderFacade facade = new OrderFacade(mockInventory, mockPayment, mockEmail, ...);
    
    // Setup behavior
    when(mockInventory.checkStock(any(), anyInt())).thenReturn(true);
    when(mockPayment.processPayment(any(), anyDouble())).thenReturn(false);  // Fail!
    
    // Execute
    boolean result = facade.placeOrder("C1", "P1", 2, 100, "Address");
    
    // Verify rollback
    assertFalse(result);
    verify(mockInventory).reserveStock("P1", 2);
    verify(mockInventory).releaseStock("P1", 2);  // Rollback called!
    verify(mockEmail).sendCancellationEmail("C1", "Payment failed");
}
```

**Pros:** Fast, isolated, tests logic
**Cons:** Doesn't test real integration

***

## Interview Questions \& Answers

**Q: What is Facade Pattern?**
A: Structural pattern that provides a simplified, unified interface to a complex subsystem, hiding its complexity.

**Q: When to use Facade?**
A: When you have complex subsystems with many interdependent classes and want to provide a simple interface for common operations.

**Q: Facade vs Adapter?**
A: Facade simplifies multiple subsystems with new interface. Adapter converts one incompatible interface to another.

**Q: Can clients access subsystems directly?**
A: Yes! Facade doesn't prevent it, just simplifies common use cases. Advanced users can still use subsystems directly.

**Q: Should Facade contain business logic?**
A: No! Facade should coordinate subsystems, not contain business logic. Logic belongs in subsystems.

**Q: How many facades can one subsystem have?**
A: Multiple! Different facades for different user roles or use cases (CustomerFacade, AdminFacade).

**Q: What's the main benefit of Facade?**
A: Loose coupling - client depends on facade, not all subsystems. Changes in subsystems don't affect client.

***

## Best Practices ✅

1. **Keep facade methods focused** - expose common operations only
2. **One facade per user role/use case** - avoid god objects
3. **Use dependency injection** - easier to test
4. **Handle errors properly** - rollback on failure
5. **Don't add business logic** - coordinate, don't compute
6. **Return facade-specific types** - avoid leaking subsystem types
7. **Allow direct subsystem access** - facade is convenience, not restriction
8. **Use rich return types** - `OrderResult` over `boolean` for production

***

## Quick Reference Cheat Sheet

**One-Line Summary:** Facade provides simplified interface to complex subsystem.

**When to use:** Complex subsystems need simple entry point

**Structure:**

```java
class Facade {
    private SubsystemA a;
    private SubsystemB b;
    
    public void simplifiedOperation() {
        a.doA();
        b.doB();
    }
}
```

**Key Principle:** Loose coupling through simplified interface

**Common Use Cases:** E-commerce checkout, API gateways, system layers, banking portals

***

## Summary

**Facade Pattern simplifies complex subsystems with unified interface.**

**Key Points:**

1. Client depends on facade, not all subsystems (loose coupling)
2. Facade coordinates workflow, doesn't contain logic
3. Multiple facades for different use cases (avoid god object)
4. Proper error handling with rollback
5. Rich return types for production systems
6. Dependency injection for testability
***
