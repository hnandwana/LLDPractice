# Encapsulation & Abstraction — Interview Revision Notes

> These two are the **most commonly confused OOP concepts** in interviews.  
> They look similar but solve different problems. Master the distinction.

---

## Quick Distinction (The Most Important Table)

| | **Encapsulation** | **Abstraction** |
|---|---|---|
| **What is it?** | **Hiding the DATA** | **Hiding the COMPLEXITY** |
| **How?** | `private` fields + getters/setters | Interfaces & abstract classes |
| **Why?** | Protect internal state from misuse | Show only what's relevant, hide how it works |
| **Focus** | **How** data is stored and accessed | **What** an object can do |
| **Analogy** | Capsule/pill — ingredients hidden inside | TV remote — you press buttons, don't see circuits |
| **Level** | Class level | Design level |

> **One-line memory trick:**  
> **Encapsulation** = "You can't touch my data directly" (data hiding)  
> **Abstraction** = "You don't need to know how I work" (complexity hiding)

---

---

## Encapsulation

### Definition

> **"Bundling data (fields) and the methods that operate on that data into a single unit (class), and restricting direct access to the internal state."**

In simple words: **Make fields `private`. Control access through methods.** The outside world can only interact with your object through the interface you expose.

### Why It Matters

- **Prevents invalid state** — can validate in setters (`age` can't be negative)
- **Hides implementation details** — internal structure can change without breaking external code
- **Controlled access** — some fields might be read-only (getter only, no setter)
- **Makes code maintainable** — internal changes don't ripple to all callers

---

### ❌ Violation — Public Fields (No Encapsulation)

```java
class BankAccount {
    public String owner;
    public double balance;  // ❌ Anyone can set balance to -99999
    public String pin;      // ❌ PIN is exposed to the world!
}
```

```java
// Caller can do ANYTHING — no protection
BankAccount account = new BankAccount();
account.balance = -50000;   // ❌ Negative balance — invalid!
account.pin = "0000";       // ❌ PIN is exposed and directly mutable
account.owner = null;       // ❌ Null owner — invalid!
```

**What's wrong?**
- No validation — invalid states can be set freely
- `pin` is completely exposed — security risk
- Internal representation is locked in — can't change `balance` to `BigDecimal` without breaking all callers

---

### ✅ Correct Design — Encapsulated BankAccount

```java
class BankAccount {
    private final String owner;   // Can't change owner after creation
    private double balance;       // Only accessible via controlled methods
    private String pin;           // Never exposed outside the class

    public BankAccount(String owner, double initialBalance, String pin) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.owner = owner;
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Read-only — no setter (balance changes only via deposit/withdraw)
    public double getBalance() {
        return balance;
    }

    // Read-only for owner
    public String getOwner() {
        return owner;
    }

    // Controlled operation with validation
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    // Controlled operation with PIN check
    public void withdraw(double amount, String enteredPin) {
        if (!this.pin.equals(enteredPin)) {
            throw new SecurityException("Invalid PIN");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }
        this.balance -= amount;
    }

    // PIN is never returned — not even a getter for it
    public boolean verifyPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Arjun", 1000.0, "1234");

        account.deposit(500);                      // ✅ Balance: 1500
        account.withdraw(200, "1234");             // ✅ Balance: 1300
        // account.balance = -99999;              // ❌ Compile error — field is private
        // account.withdraw(200, "wrong");        // ❌ SecurityException at runtime
        System.out.println(account.getBalance()); // ✅ 1300.0 — controlled read access
    }
}
```

---

### Access Modifiers — The 4 Levels

| Modifier | Same Class | Same Package | Subclass | Everywhere |
|----------|-----------|-------------|----------|------------|
| `private` | ✅ | ❌ | ❌ | ❌ |
| `package-private` (default) | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

**Rule of thumb:** Start with `private`. Only open up access if there's a specific reason.

---

### Encapsulation Levels

#### Level 1 — Basic (Getter + Setter)
```java
class Person {
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

#### Level 2 — Validated Setter (Better)
```java
class Person {
    private int age;

    public int getAge() { return age; }

    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        this.age = age;
    }
}
```

#### Level 3 — Immutable Object (Best for value objects)
```java
// Once created, cannot be changed — thread-safe, no defensive copies needed
final class Money {
    private final double amount;
    private final String currency;

    public Money(double amount, String currency) {
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        this.amount = amount;
        this.currency = currency;
    }

    public double getAmount() { return amount; }
    public String getCurrency() { return currency; }

    // No setters — to "change" money, create a new object
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        return new Money(this.amount + other.amount, this.currency);
    }
}
```

---

### Encapsulation in Design Patterns

| Pattern | Encapsulation Role |
|---------|-------------------|
| **Builder** | Fields are `private final` — only set via builder, immutable after build |
| **Singleton** | Constructor is `private` — creation controlled by the class itself |
| **Proxy** | Real object is `private` inside proxy — access controlled by proxy |
| **Facade** | Complex subsystem objects are `private` — exposed only via simplified methods |

---

### How to Spot Encapsulation Violations

| Smell | Problem |
|-------|---------|
| `public` fields | Direct access — no validation possible |
| Getter returns mutable collection directly | Caller can modify internal state |
| No validation in setters | Invalid state possible |
| `public` mutable list/array field | Structural exposure |

```java
// ❌ Classic collection exposure mistake
class Team {
    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return players;  // ❌ Caller can do team.getPlayers().clear()!
    }
}

// ✅ Return defensive copy or unmodifiable view
public List<Player> getPlayers() {
    return Collections.unmodifiableList(players);  // ✅ Read-only view
}
```

> **Interview One-Liner:** "Encapsulation is about data hiding — making fields private and controlling access through methods with validation. It protects internal state from being set to invalid values. For example, a BankAccount's balance field is private — you can only change it via deposit() and withdraw() which enforce business rules."

---

---

## Abstraction

### Definition

> **"The process of hiding complex implementation details and showing only the essential features of an object."**

In simple words: **You define WHAT something does, not HOW it does it.** The caller works with a simple interface without caring about the underlying complexity.

### Why It Matters

- **Reduces complexity** — users work with a simple contract, not internals
- **Flexibility** — implementation can change without affecting callers
- **Separation of concerns** — what to do vs. how to do it
- **Foundation for OCP and DIP** — depend on abstractions, not concretions

---

### The Classic Analogy — Car

```
What you see (Abstraction):     What's hidden (Implementation):
  - Steering wheel                - Hydraulic steering system
  - Accelerator pedal             - Fuel injection, combustion engine
  - Brake pedal                   - ABS, brake fluid system
  - Gear shift                    - Transmission, clutch system

You drive using the abstraction. You don't need to know HOW brakes work.
Change the brake system (drum → disc) — your driving experience is the same.
```

---

### Abstraction in Java — Two Tools

#### Tool 1: Interface (100% Abstraction — Pure Contract)

```java
// WHAT a payment processor does — no HOW
interface PaymentProcessor {
    boolean processPayment(double amount);
    void refund(String transactionId);
    String getTransactionStatus(String transactionId);
}
```

```java
// HOW Stripe does it — hidden from caller
class StripeProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        // Stripe API calls, error handling, retries — all hidden
        System.out.println("Processing ₹" + amount + " via Stripe");
        return true;
    }

    @Override
    public void refund(String transactionId) {
        System.out.println("Refunding transaction: " + transactionId + " via Stripe");
    }

    @Override
    public String getTransactionStatus(String transactionId) {
        return "SUCCESS"; // Stripe API call hidden
    }
}
```

```java
// HOW PayPal does it — hidden from caller
class PayPalProcessor implements PaymentProcessor {
    @Override
    public boolean processPayment(double amount) {
        // PayPal SDK calls — completely different, but same interface
        System.out.println("Processing ₹" + amount + " via PayPal");
        return true;
    }
    // ...
}
```

```java
// Caller only sees the ABSTRACTION — doesn't care if it's Stripe or PayPal
class OrderService {
    private final PaymentProcessor processor;

    public OrderService(PaymentProcessor processor) {
        this.processor = processor;
    }

    public void checkout(double amount) {
        boolean success = processor.processPayment(amount);  // HOW? Don't care.
        if (success) System.out.println("Order confirmed!");
    }
}
```

---

#### Tool 2: Abstract Class (Partial Abstraction — Template)

Use when you want to define a **template with some shared implementation** but force subclasses to fill in the specifics.

```java
abstract class Report {
    // Template method — defines the SKELETON (abstraction)
    public final void generate() {
        fetchData();         // Step 1 — shared
        processData();       // Step 2 — subclass fills in
        formatOutput();      // Step 3 — subclass fills in
        saveToFile();        // Step 4 — shared
    }

    // Shared implementation — same for all reports
    private void fetchData() {
        System.out.println("Fetching data from DB...");
    }

    private void saveToFile() {
        System.out.println("Saving report to file...");
    }

    // Abstract — WHAT to do is defined here, HOW is in the subclass
    protected abstract void processData();
    protected abstract void formatOutput();
}
```

```java
class PDFReport extends Report {
    @Override
    protected void processData() {
        System.out.println("Processing data for PDF...");
    }

    @Override
    protected void formatOutput() {
        System.out.println("Formatting as PDF with fonts and margins...");
    }
}

class ExcelReport extends Report {
    @Override
    protected void processData() {
        System.out.println("Processing data for Excel...");
    }

    @Override
    protected void formatOutput() {
        System.out.println("Formatting as Excel with cells and formulas...");
    }
}
```

```java
// Caller uses the abstraction — just calls generate()
Report report = new PDFReport();
report.generate();
// Output:
// Fetching data from DB...     ← shared
// Processing data for PDF...   ← subclass
// Formatting as PDF...         ← subclass
// Saving report to file...     ← shared
```

---

### Interface vs Abstract Class — When to Use Which

| Scenario | Use |
|----------|-----|
| **Pure contract** — just define WHAT, not HOW | Interface |
| **Multiple inheritance needed** | Interface (a class can implement many) |
| **Unrelated classes share a capability** | Interface (`Flyable` for Bird and Plane) |
| **Shared code** among related classes | Abstract class |
| **Template/skeleton** — some steps fixed, some variable | Abstract class (Template Method pattern) |
| **Constructor or instance variables needed** | Abstract class |
| Clear **IS-A** relationship with partial behavior | Abstract class |

---

### How Abstraction and Encapsulation Work Together

They almost always appear together — but they are different concepts:

```java
class ATM {
    // ENCAPSULATION: balance is hidden, validated access
    private double balance;
    private String pin;

    // ABSTRACTION: caller sees simple operations
    // doesn't know about DB calls, network requests, PIN hashing inside
    public void withdraw(double amount, String enteredPin) {
        // Complex logic hidden:
        // 1. Validate PIN (hashed comparison)
        // 2. Check balance
        // 3. Log transaction to DB
        // 4. Update account via bank API
        // 5. Dispense cash signal
        System.out.println("Withdrawn: ₹" + amount);
    }

    public double checkBalance(String enteredPin) {
        // Complex verification hidden
        return balance;
    }
}
```

```
Encapsulation → balance and pin are PRIVATE (data hidden)
Abstraction   → withdraw() hides the 5-step complexity behind one method call
```

---

### Encapsulation vs Abstraction — Side by Side

```java
// ENCAPSULATION in action:
private double balance;          // Field is hidden
public void deposit(double amt)  // Controlled access with validation
                                 // → Protects DATA

// ABSTRACTION in action:
interface PaymentProcessor {
    boolean processPayment(double amount);
}                                // HOW it processes is hidden behind the interface
                                 // → Hides COMPLEXITY
```

| | Encapsulation | Abstraction |
|---|---|---|
| **Mechanism** | `private` access modifier | `interface` / `abstract class` |
| **What's hidden** | Fields / internal data | Implementation details / complexity |
| **Achieved at** | Class level | Design level (interfaces, hierarchy) |
| **Goal** | Protect state integrity | Reduce cognitive load, enable flexibility |
| **Broken when** | Public fields, no validation | Exposing implementation details to caller |
| **Example** | `private double balance` | `interface PaymentProcessor` |

---

### Real-World Abstraction Examples

| You Use | You Don't See |
|---------|--------------|
| `List.add()` | Array resizing, memory allocation |
| `Collections.sort()` | TimSort algorithm internals |
| `JDBC Connection` | TCP/IP socket management |
| `@Transactional` in Spring | Begin/commit/rollback logic |
| `HttpClient.post()` | HTTP protocol, TCP handshake |
| Your `ShippingCalculator` | Internal formula of each strategy |

---

### How to Spot Missing Abstraction

| Smell | Problem |
|-------|---------|
| Caller knows internal implementation | Coupled to details |
| Changing internals breaks callers | No abstraction layer |
| `instanceof` checks everywhere | No common abstraction |
| Copy-paste of the same logic in multiple places | No abstract template |
| Business logic mixed with low-level details | No separation of concerns |

> **Interview One-Liner:** "Abstraction hides complexity behind a simple interface. When I call `processor.processPayment(amount)`, I don't care if it's Stripe or PayPal — the abstraction lets me work at the right level. Abstraction is about WHAT an object does. Encapsulation is about protecting the data INSIDE it. They're different: abstraction hides complexity, encapsulation hides data."

---

---

## 📌 Interview Quick-Fire — Encapsulation & Abstraction

| Question | Answer |
|----------|--------|
| What is encapsulation? | Hiding internal data using `private` fields and exposing controlled access via methods |
| What is abstraction? | Hiding implementation complexity and showing only essential behaviour via interfaces/abstract classes |
| What's the difference? | Encapsulation hides DATA; Abstraction hides COMPLEXITY |
| How do you achieve encapsulation? | `private` fields, getters/setters with validation, immutable objects |
| How do you achieve abstraction? | Interfaces and abstract classes |
| Can a class have both? | Yes — almost always. e.g., ATM has private fields (encapsulation) AND implements an interface (abstraction) |
| Which SOLID principles relate to abstraction? | OCP (extend via abstractions), DIP (depend on abstractions), ISP (focused abstractions) |
| Real example of abstraction in your project? | `ShippingStrategy` interface — caller doesn't know if it's Express or Overnight internally |
| Real example of encapsulation in your project? | Builder pattern — all fields are `private final`, only settable via builder methods |
| What's an immutable class? | All fields are `private final`, no setters, state set only in constructor |

---

> **Day 3 Status:** ✅ DIP + Encapsulation & Abstraction complete  
> **SOLID notes are 100% done!**  
> **Next:** Day 4 — Quick revision of all 14 patterns + Chain of Responsibility

