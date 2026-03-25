# SOLID Principles — Complete Interview Revision Notes

> **SOLID** = 5 principles that make your code **maintainable, flexible, and scalable**.  
> Every design pattern is built on top of one or more SOLID principles.

---

## Quick Reference Table

| Principle | One-Liner | Violation Smell |
|-----------|-----------|----------------|
| **S** – Single Responsibility | A class should have only **one reason to change** | God class doing everything |
| **O** – Open/Closed | Open for **extension**, closed for **modification** | `if-else` / `switch` chains for new types |
| **L** – Liskov Substitution | Subtypes must be **substitutable** for their base types | Child class breaks parent's contract |
| **I** – Interface Segregation | Clients shouldn't depend on methods they **don't use** | Fat interface forcing empty implementations |
| **D** – Dependency Inversion | Depend on **abstractions**, not concretions | `new ConcreteClass()` inside high-level code |

---

---

## S — Single Responsibility Principle (SRP)

### Definition

> **"A class should have only one reason to change."**  
> — Robert C. Martin

In simple words: **One class = One job**. If a class is doing multiple unrelated things, split it.

### Why It Matters

- Easier to **understand** (small, focused classes)
- Easier to **test** (test one thing at a time)
- Easier to **modify** (change in billing logic doesn't touch email logic)
- Reduces **merge conflicts** in teams

---

### ❌ Violation Example — God Class

```java
class Employee {
    private String name;
    private double salary;

    // Responsibility 1: Employee data management
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Responsibility 2: Salary calculation (Business Logic)
    public double calculateBonus() {
        return salary * 0.1;
    }

    // Responsibility 3: Database operations (Persistence)
    public void saveToDatabase() {
        // JDBC code to insert employee into DB
        System.out.println("Saving " + name + " to database...");
    }

    // Responsibility 4: Report generation (Presentation)
    public String generatePaySlip() {
        return "PaySlip for " + name + ": Salary=" + salary + ", Bonus=" + calculateBonus();
    }

    // Responsibility 5: Sending emails (Notification)
    public void sendPaySlipEmail() {
        String paySlip = generatePaySlip();
        // SMTP code to send email
        System.out.println("Emailing payslip to " + name);
    }
}
```

**What's wrong?** This class has **5 reasons to change**:
1. Employee fields change → modify data section
2. Bonus formula changes → modify calculation
3. Database changes (MySQL → MongoDB) → modify persistence
4. PaySlip format changes → modify report
5. Email provider changes → modify notification

**Any one change risks breaking unrelated functionality.**

---

### ✅ Correct Design — Single Responsibility Each

```java
// Responsibility 1: Only holds employee data
class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() { return name; }
    public double getSalary() { return salary; }
}
```

```java
// Responsibility 2: Only handles salary calculations
class SalaryCalculator {
    public double calculateBonus(Employee employee) {
        return employee.getSalary() * 0.1;
    }

    public double calculateTax(Employee employee) {
        return employee.getSalary() * 0.3;
    }
}
```

```java
// Responsibility 3: Only handles persistence
class EmployeeRepository {
    public void save(Employee employee) {
        // JDBC / JPA code
        System.out.println("Saving " + employee.getName() + " to database");
    }

    public Employee findByName(String name) {
        // Query DB
        return null;
    }
}
```

```java
// Responsibility 4: Only handles report generation
class PaySlipGenerator {
    private final SalaryCalculator calculator;

    public PaySlipGenerator(SalaryCalculator calculator) {
        this.calculator = calculator;
    }

    public String generate(Employee employee) {
        double bonus = calculator.calculateBonus(employee);
        return "PaySlip for " + employee.getName() +
               ": Salary=" + employee.getSalary() +
               ", Bonus=" + bonus;
    }
}
```

```java
// Responsibility 5: Only handles notifications
class EmailService {
    public void sendEmail(String to, String content) {
        // SMTP logic
        System.out.println("Sending email to " + to + ": " + content);
    }
}
```

**Now each class has exactly ONE reason to change.**

---

### How to Identify SRP Violations

| Smell | Example |
|-------|---------|
| Class name has "And" or "Manager" | `UserAndOrderManager` |
| Class has 500+ lines | God class |
| You say "this class **also** does..." | Multiple responsibilities |
| A change in UI breaks DB logic | Tangled concerns |
| Class imports unrelated packages | `java.sql` + `javax.mail` + `java.io` in one class |

### SRP in the Real World

| Layer | Responsibility |
|-------|---------------|
| **Controller** | Handle HTTP request/response |
| **Service** | Business logic |
| **Repository** | Database operations |
| **DTO/Model** | Data transfer/storage |
| **Validator** | Input validation |

> **Interview One-Liner:** "SRP means a class should have only one reason to change. I follow this by splitting concerns into separate classes — like separating business logic from persistence and notification."

---

---

## O — Open/Closed Principle (OCP)

### Definition

> **"Software entities should be open for extension, but closed for modification."**  
> — Bertrand Meyer

In simple words: **Add new features by writing NEW code, not by changing EXISTING code.**

### Why It Matters

- Adding a new feature **shouldn't break existing features**
- Existing code is already **tested and working** — don't touch it
- Reduces **regression bugs**
- This is the principle that **Strategy, Factory, and most patterns** are built on

---

### ❌ Violation Example — The `if-else` / `switch` Chain

```java
class NotificationService {

    public void sendNotification(String type, String message) {
        if (type.equals("EMAIL")) {
            // Send email
            System.out.println("Sending EMAIL: " + message);
            // ... SMTP logic
        } else if (type.equals("SMS")) {
            // Send SMS
            System.out.println("Sending SMS: " + message);
            // ... Twilio logic
        } else if (type.equals("PUSH")) {
            // Send Push notification
            System.out.println("Sending PUSH: " + message);
            // ... Firebase logic
        }
        // ❌ To add SLACK notification, you MUST modify this class
        // ❌ To add WHATSAPP, modify again
        // ❌ Every new type = modify existing tested code
    }
}
```

**What's wrong?**
- Every new notification type requires **modifying** `NotificationService`
- The `if-else` chain keeps **growing**
- Risk of **breaking** existing EMAIL/SMS logic when adding SLACK
- Violates OCP — class is NOT closed for modification

---

### ✅ Correct Design — Open for Extension, Closed for Modification

**Step 1: Define an abstraction (interface)**

```java
interface Notification {
    void send(String message);
}
```

**Step 2: Each type implements the interface (extensions)**

```java
class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending EMAIL: " + message);
        // SMTP logic here
    }
}

class SmsNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
        // Twilio logic here
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending PUSH: " + message);
        // Firebase logic here
    }
}
```

**Step 3: Service depends on the abstraction**

```java
class NotificationService {
    private final Notification notification;

    // Inject the abstraction — NOT a concrete class
    public NotificationService(Notification notification) {
        this.notification = notification;
    }

    public void notifyUser(String message) {
        notification.send(message);  // Works for ANY notification type
    }
}
```

**Adding SLACK? Just create a new class — ZERO changes to existing code:**

```java
// ✅ NEW class — no existing code modified
class SlackNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SLACK: " + message);
        // Slack API logic here
    }
}
```

```java
// Usage — choose at runtime
NotificationService service = new NotificationService(new SlackNotification());
service.notifyUser("Hello!");
```

---

### Before vs After — Visual Comparison

```
BEFORE (Violates OCP):                     AFTER (Follows OCP):

NotificationService                        «interface» Notification
├── if EMAIL → send email                       ↑ implements
├── if SMS → send SMS                    ┌───────┼────────┬──────────┐
├── if PUSH → send push                 Email  SMS    Push    Slack ← NEW
├── if SLACK → ❌ MODIFY!               (each class is independent)
└── if WHATSAPP → ❌ MODIFY AGAIN!
                                         NotificationService
                                         └── uses Notification (interface)
                                             → works with ANY implementation
```

---

### OCP + Strategy Pattern Connection

OCP is the **foundation** of the Strategy Pattern. Look at your own Shipping Calculator:

```java
// Your existing code follows OCP perfectly!
interface ShippingStrategy {
    ShippingResult calculate(Parcel parcel);
}

class ExpressShipping implements ShippingStrategy { ... }
class OvernightShipping implements ShippingStrategy { ... }
class FreeShipping implements ShippingStrategy { ... }

// Adding a new shipping method? Just create a new class!
class DroneShipping implements ShippingStrategy {  // ✅ Extension
    @Override
    public ShippingResult calculate(Parcel parcel) {
        // New logic — no existing code touched
    }
}
```

**The ShippingCalculator is CLOSED for modification, OPEN for extension.**

---

### OCP + Factory Pattern Connection

Your Notification Factory also follows OCP:

```java
// Adding a new notification type?
// 1. Create the new class (WhatsAppNotification)
// 2. Register it in the factory
// No change to existing notification classes!
```

---

### How to Achieve OCP

| Technique | How |
|-----------|-----|
| **Interfaces** | Define contracts, implement variants |
| **Abstract classes** | Base behavior + extension points |
| **Strategy Pattern** | Swap algorithms without modifying context |
| **Factory Pattern** | Create objects without knowing concrete types |
| **Decorator Pattern** | Add behavior without modifying existing class |
| **Composition** | Inject behavior via constructor/setter |

### How to Spot OCP Violations

| Smell | Problem |
|-------|---------|
| `if-else` / `switch` on type | New type = modify existing code |
| `instanceof` checks | Fragile type checking |
| Adding a feature requires editing 5 files | Tight coupling |
| "I just added X and Y broke" | Modification side-effects |

---

### Real-World OCP Examples

| Scenario | Extension (New Class) | Closed (No Modification) |
|----------|----------------------|--------------------------|
| Payment Methods | Add `UPIPayment` | `PaymentService` unchanged |
| Shipping | Add `DroneShipping` | `ShippingCalculator` unchanged |
| Logging | Add `CloudLogger` | `LoggingService` unchanged |
| Discount Rules | Add `FestivalDiscount` | `DiscountCalculator` unchanged |
| Export Formats | Add `XMLExporter` | `ReportService` unchanged |

> **Interview One-Liner:** "OCP means I can add new features by creating new classes without modifying existing tested code. I achieve this using interfaces and patterns like Strategy and Factory — which is exactly how I built the notification and shipping systems in my projects."

---

---

## 🔗 How S and O Work Together

```
SRP: Split the class into focused pieces
         ↓
OCP: Make each piece extensible via interfaces
         ↓
Result: Clean, maintainable, scalable code
```

**Example Flow:**
1. **SRP** says: Don't put notification logic inside `OrderService`  
   → Extract `NotificationService`
2. **OCP** says: Don't use if-else for notification types  
   → Create `Notification` interface + separate implementations

**They complement each other.** SRP gives you small focused classes. OCP makes those classes extensible without modification.

---

## 📌 Interview Quick-Fire Answers

| Question | Answer |
|----------|--------|
| What is SRP? | A class should have only one reason to change |
| Give an example of SRP violation | A `UserService` that handles login, email, and database operations |
| What is OCP? | Open for extension, closed for modification |
| How do you achieve OCP? | Using interfaces, Strategy pattern, Factory pattern |
| Which design patterns use OCP? | Strategy, Factory, Decorator, Observer — almost all of them |
| What's the connection between SRP and OCP? | SRP splits responsibilities; OCP makes each piece extensible |
| Real project example? | Payment system — new payment method = new class, no existing code changes |

---

> **Day 1 Status:** ✅ SRP + OCP complete

---

---

## L — Liskov Substitution Principle (LSP)

### Definition

> **"If S is a subtype of T, then objects of type T should be replaceable with objects of type S without breaking the program."**  
> — Barbara Liskov

In simple words: **If you replace a parent class with ANY of its child classes, everything should still work correctly.** The child must honour the parent's contract — no surprises.

### Why It Matters

- Guarantees **polymorphism works safely**
- Prevents subtle bugs where a subclass behaves unexpectedly
- Ensures **inheritance is used correctly** (not just for code reuse)
- Foundation for trusting interfaces in design patterns

---

### ❌ Classic Violation — Rectangle / Square Problem

This is the **most famous LSP violation** and a guaranteed interview question.

```java
class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }
}
```

```java
// Square IS-A Rectangle... right? Mathematically yes, but in code — NO!
class Square extends Rectangle {

    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;  // ⚠️ Forces height = width
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
        this.width = height;  // ⚠️ Forces width = height
    }
}
```

```java
// Client code that works with Rectangle
class AreaCalculator {

    public void printArea(Rectangle rect) {
        rect.setWidth(5);
        rect.setHeight(4);
        // Expectation: area = 5 * 4 = 20
        System.out.println("Expected area: 20, Actual: " + rect.getArea());
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        AreaCalculator calc = new AreaCalculator();

        calc.printArea(new Rectangle());  // ✅ Output: Expected area: 20, Actual: 20
        calc.printArea(new Square());     // ❌ Output: Expected area: 20, Actual: 16
        //                                   Square set height=4, which also set width=4 → 4*4=16
    }
}
```

**What went wrong?**
- `Rectangle` contract says: `setWidth` changes ONLY width, `setHeight` changes ONLY height
- `Square` **breaks** this contract — setting height also changes width
- Substituting `Square` where `Rectangle` is expected produces **wrong results**
- The child **changed the behaviour** the parent promised → **LSP violated**

---

### ✅ Correct Design — Use Interfaces/Abstraction

**Don't force Square to extend Rectangle.** Instead, model what they have in common:

```java
interface Shape {
    int getArea();
}
```

```java
class Rectangle implements Shape {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}
```

```java
class Square implements Shape {
    private final int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}
```

```java
class AreaCalculator {
    public void printArea(Shape shape) {
        System.out.println("Area: " + shape.getArea());  // ✅ Works for ANY shape
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        AreaCalculator calc = new AreaCalculator();
        calc.printArea(new Rectangle(5, 4));  // ✅ Area: 20
        calc.printArea(new Square(5));         // ✅ Area: 25
        // No broken expectations — each shape correctly computes its own area
    }
}
```

**Why this works:**
- `Shape` contract says: `getArea()` returns the correct area — both classes honour this
- No mutable setters that could break invariants
- Objects are **immutable** — safer and more predictable
- Substituting any `Shape` implementation works correctly

---

### ❌ Another Violation — Bird That Can't Fly

```java
class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly!");  // ❌ LSP VIOLATION
    }
}
```

```java
// Client expects ALL birds to fly
public void makeBirdFly(Bird bird) {
    bird.fly();  // 💥 Crashes when Ostrich is passed!
}
```

### ✅ Fix — Separate the Capability

```java
interface Bird {
    void eat();
}

interface Flyable {
    void fly();
}

class Sparrow implements Bird, Flyable {
    @Override
    public void eat() { System.out.println("Sparrow eating"); }

    @Override
    public void fly() { System.out.println("Sparrow flying"); }
}

class Ostrich implements Bird {
    @Override
    public void eat() { System.out.println("Ostrich eating"); }
    // No fly() — Ostrich doesn't implement Flyable. Honest design!
}
```

Now the client can safely work with `Flyable` — only birds that CAN fly will be passed. No surprises.

---

### LSP Rules — The Checklist

| Rule | Meaning | Violation Example |
|------|---------|-------------------|
| **No strengthening preconditions** | Child can't demand MORE than parent | Parent accepts any int → Child rejects negatives |
| **No weakening postconditions** | Child must deliver AT LEAST what parent promises | Parent guarantees non-null → Child returns null |
| **No new exceptions** | Child shouldn't throw exceptions parent doesn't | Parent never throws → Child throws `UnsupportedOperationException` |
| **Preserve invariants** | Child must maintain parent's rules | Rectangle: w & h are independent → Square breaks this |
| **History constraint** | Child shouldn't allow state changes parent doesn't | Parent is immutable → Child adds a setter |

### How to Spot LSP Violations

| Smell | Problem |
|-------|---------|
| `throw new UnsupportedOperationException()` in child | Child can't fulfil parent's contract |
| `instanceof` checks before calling a method | You don't trust the substitution |
| Child method that does **nothing** (empty override) | Child is faking the capability |
| "Works with parent but breaks with child" | Substitution fails |
| Override changes the **meaning** of the method | Contract broken |

---

### LSP Connection to Design Patterns

| Pattern | LSP Connection |
|---------|---------------|
| **Strategy** | All strategies implement the same interface → any strategy is substitutable |
| **Factory** | Factory returns parent type → any child must work in place of parent |
| **Observer** | All observers implement `update()` → any observer is substitutable |
| **Decorator** | Decorator wraps the same interface → substitutable with the original |

> **Interview One-Liner:** "LSP means any child class should be safely substitutable for its parent without breaking the code. The classic violation is Rectangle-Square — Square overrides setters and breaks Rectangle's contract. The fix is to use interfaces and model shared behavior instead of forcing inheritance."

---

---

## I — Interface Segregation Principle (ISP)

### Definition

> **"Clients should not be forced to depend on methods they do not use."**  
> — Robert C. Martin

In simple words: **Don't create fat interfaces.** Split them into smaller, focused ones so that implementing classes only need to deal with methods that are relevant to them.

### Why It Matters

- No forced **empty/dummy implementations**
- Classes are **focused** — implement only what they need
- **Easier to change** — modifying one interface doesn't affect unrelated implementors
- Better **readability** — small interfaces are self-documenting

---

### ❌ Violation Example — Fat Interface

```java
interface Worker {
    void work();
    void eat();
    void sleep();
    void attendMeeting();
    void writeReport();
}
```

```java
// Full-time employee — uses all methods, no problem
class FullTimeEmployee implements Worker {
    @Override public void work() { System.out.println("Working full-time"); }
    @Override public void eat() { System.out.println("Eating lunch"); }
    @Override public void sleep() { System.out.println("Going home to sleep"); }
    @Override public void attendMeeting() { System.out.println("In a meeting"); }
    @Override public void writeReport() { System.out.println("Writing report"); }
}
```

```java
// Robot worker — FORCED to implement eat/sleep/meeting? Makes no sense!
class RobotWorker implements Worker {
    @Override public void work() { System.out.println("Robot working 24/7"); }

    @Override public void eat() {
        // ❌ Robots don't eat! But FORCED to implement this
        throw new UnsupportedOperationException("Robots don't eat");
    }

    @Override public void sleep() {
        // ❌ Robots don't sleep!
        throw new UnsupportedOperationException("Robots don't sleep");
    }

    @Override public void attendMeeting() {
        // ❌ Robots don't attend meetings!
        throw new UnsupportedOperationException("Robots don't attend meetings");
    }

    @Override public void writeReport() {
        // ❌ Not applicable
        throw new UnsupportedOperationException("Robots don't write reports");
    }
}
```

**What's wrong?**
- `RobotWorker` is forced to implement **4 methods it doesn't need**
- Throws `UnsupportedOperationException` everywhere → also violates **LSP**
- If we add `takeVacation()` to `Worker`, EVERY class must be updated
- The interface is **too fat** — mixing unrelated responsibilities

---

### ✅ Correct Design — Segregated Interfaces

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Sleepable {
    void sleep();
}

interface Meetable {
    void attendMeeting();
}

interface Reportable {
    void writeReport();
}
```

```java
// Full-time employee — implements all relevant interfaces
class FullTimeEmployee implements Workable, Eatable, Sleepable, Meetable, Reportable {
    @Override public void work() { System.out.println("Working full-time"); }
    @Override public void eat() { System.out.println("Eating lunch"); }
    @Override public void sleep() { System.out.println("Going home to sleep"); }
    @Override public void attendMeeting() { System.out.println("In a meeting"); }
    @Override public void writeReport() { System.out.println("Writing report"); }
}
```

```java
// Intern — works and eats, but doesn't write reports or attend meetings
class Intern implements Workable, Eatable, Sleepable {
    @Override public void work() { System.out.println("Intern working on tasks"); }
    @Override public void eat() { System.out.println("Eating at cafeteria"); }
    @Override public void sleep() { System.out.println("Going home to rest"); }
    // No meeting or report methods — not forced!
}
```

```java
// Robot — only works. Clean and honest!
class RobotWorker implements Workable {
    @Override public void work() { System.out.println("Robot working 24/7"); }
    // No eat, sleep, meeting, report — NOT FORCED. ✅
}
```

**Now each class implements ONLY what it needs. No fake implementations.**

---

### ❌ Another Real-World Violation — Multifunction Printer

```java
// Fat interface — not every device can do everything
interface Machine {
    void print(String doc);
    void scan(String doc);
    void fax(String doc);
    void staple(String doc);
}
```

```java
// Old printer that can ONLY print
class OldPrinter implements Machine {
    @Override public void print(String doc) { System.out.println("Printing: " + doc); }
    @Override public void scan(String doc) { /* ❌ Can't scan! */ }
    @Override public void fax(String doc) { /* ❌ Can't fax! */ }
    @Override public void staple(String doc) { /* ❌ Can't staple! */ }
}
```

### ✅ Fix — Split by Capability

```java
interface Printer {
    void print(String doc);
}

interface Scanner {
    void scan(String doc);
}

interface FaxMachine {
    void fax(String doc);
}
```

```java
// Old printer — only implements what it can do
class OldPrinter implements Printer {
    @Override public void print(String doc) { System.out.println("Printing: " + doc); }
}

// Modern all-in-one — implements everything it supports
class MultiFunctionPrinter implements Printer, Scanner, FaxMachine {
    @Override public void print(String doc) { System.out.println("Printing: " + doc); }
    @Override public void scan(String doc) { System.out.println("Scanning: " + doc); }
    @Override public void fax(String doc) { System.out.println("Faxing: " + doc); }
}
```

**Client code depends only on what it needs:**

```java
class PrintClient {
    private final Printer printer;  // Only cares about printing

    public PrintClient(Printer printer) {
        this.printer = printer;
    }

    public void printDocument(String doc) {
        printer.print(doc);  // Works with OldPrinter OR MultiFunctionPrinter
    }
}
```

---

### Before vs After — Visual Comparison

```
BEFORE (Fat Interface):                 AFTER (Segregated):

     «interface» Worker                Workable   Eatable   Sleepable   Meetable   Reportable
  ┌──────────────────────┐                ↑          ↑         ↑           ↑          ↑
  │ work()               │            implements (pick what you need)
  │ eat()                │
  │ sleep()              │         FullTimeEmployee: Workable, Eatable, Sleepable, Meetable, Reportable
  │ attendMeeting()      │         Intern:           Workable, Eatable, Sleepable
  │ writeReport()        │         RobotWorker:      Workable
  └──────────────────────┘
         ↑ implements              Each class implements ONLY relevant interfaces
  ALL classes must                 No dummy methods, no exceptions
  implement ALL methods
```

---

### ISP Connection to Design Patterns

| Pattern | ISP Connection |
|---------|---------------|
| **Strategy** | Each strategy interface has ONE method (`calculate()`) — very focused |
| **Observer** | Observer interface has only `update()` — not bloated with unrelated methods |
| **Adapter** | Adapter creates a focused interface for the client, hiding the fat adaptee |
| **Decorator** | Decorator implements the SAME small interface — wraps without extra baggage |

### ISP + LSP Connection

Fat interfaces **cause** LSP violations:
1. Fat interface forces class to implement irrelevant methods
2. Class throws `UnsupportedOperationException` or does nothing
3. Client calls the method expecting it to work → **LSP violation**

**Fix the ISP violation → LSP violation goes away automatically.**

---

### How to Spot ISP Violations

| Smell | Problem |
|-------|---------|
| Empty method implementations `{}` | Class doesn't need this method |
| `throw new UnsupportedOperationException()` | Forced to implement something irrelevant |
| Interface with 8+ methods | Probably mixing responsibilities |
| "I only use 2 of the 10 methods" | Client depends on too much |
| Adding a method to interface breaks 5 classes | Interface is too broad |

### When to Split vs Keep Together

| Keep Together (Same Interface) | Split Apart |
|-------------------------------|-------------|
| Methods are **always used together** | Methods used **independently** |
| Methods share the **same concept** | Methods serve **different clients** |
| Removing one breaks the other | Can exist without the other |
| Example: `Iterator` (hasNext + next) | Example: `Printer` + `Scanner` |

> **Interview One-Liner:** "ISP means don't force classes to implement methods they don't use. I split fat interfaces into smaller focused ones — like separating Printable, Scannable, Faxable instead of one giant Machine interface. This also prevents LSP violations since classes won't need dummy implementations."

---

---

## 🔗 How L and I Work Together

```
ISP: Split fat interfaces into focused ones
         ↓
LSP: Each implementation can now fully honour its interface contract
         ↓
Result: Safe polymorphism — any implementation is truly substitutable
```

**The chain reaction:**
1. **Fat interface** → forces `RobotWorker` to implement `eat()` → throws exception → **LSP violated**
2. **Split interface** → `RobotWorker` only implements `Workable` → no fake methods → **LSP safe**

**ISP prevents LSP violations. They are best friends.**

---

## 🔗 S + O + L + I — How They Build on Each Other

| Step | Principle | What It Does |
|------|-----------|-------------|
| 1 | **SRP** | Split god class into focused classes |
| 2 | **ISP** | Split fat interfaces into focused interfaces |
| 3 | **LSP** | Each implementation safely honours its interface |
| 4 | **OCP** | Add new implementations without modifying existing code |

---

## 📌 Interview Quick-Fire Answers (L + I)

| Question | Answer |
|----------|--------|
| What is LSP? | Subtypes must be substitutable for their base types without breaking the program |
| Classic LSP violation? | Rectangle-Square: Square overrides setWidth/setHeight and breaks area calculation |
| How to fix LSP violation? | Use interfaces to model shared behavior instead of forcing inheritance |
| What is ISP? | Clients shouldn't depend on methods they don't use — split fat interfaces |
| Classic ISP violation? | Machine interface with print/scan/fax — OldPrinter forced to implement scan/fax |
| How are LSP and ISP related? | Fat interfaces (ISP violation) force dummy implementations, which cause LSP violations |
| Which patterns follow ISP? | Strategy (focused interface), Observer (single update method) |
| Real project example? | Notification system — `Notification` interface has only `send()`, not `send() + schedule() + retry()` |

---

> **Day 2 Status:** ✅ LSP + ISP complete

---

---

## D — Dependency Inversion Principle (DIP)

### Definition

> **"High-level modules should not depend on low-level modules. Both should depend on abstractions."**  
> **"Abstractions should not depend on details. Details should depend on abstractions."**  
> — Robert C. Martin

In simple words:
- **High-level** = the class with the important business logic (e.g., `OrderService`)
- **Low-level** = the class that does the grunt work (e.g., `MySQLDatabase`, `EmailSender`)
- Neither should know the concrete details of the other — they should talk through an **interface**

### Why It Matters

- Swap low-level implementations **without touching business logic** (MySQL → MongoDB)
- Makes code **testable** — inject a mock instead of a real DB in unit tests
- **Loose coupling** between layers
- Foundation of **Dependency Injection (DI)** frameworks (Spring `@Autowired`)

---

### ❌ Violation Example — High-Level Depends on Low-Level Directly

```java
// Low-level class — concrete implementation
class MySQLDatabase {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

// Low-level class
class EmailSender {
    public void sendEmail(String message) {
        System.out.println("Sending email: " + message);
    }
}
```

```java
// ❌ High-level class — DIRECTLY depends on concrete low-level classes
class OrderService {
    private MySQLDatabase database = new MySQLDatabase();  // ❌ Hardcoded dependency
    private EmailSender emailSender = new EmailSender();   // ❌ Hardcoded dependency

    public void placeOrder(String order) {
        database.save(order);              // ❌ Tightly coupled to MySQL
        emailSender.sendEmail(order);      // ❌ Tightly coupled to Email
        System.out.println("Order placed: " + order);
    }
}
```

**What's wrong?**
- `OrderService` (high-level) is **tightly coupled** to `MySQLDatabase` and `EmailSender` (low-level)
- Want to switch to MongoDB? → **Modify** `OrderService`
- Want to switch to SMS instead of Email? → **Modify** `OrderService`
- Want to unit test `OrderService` without a real DB? → **Impossible** — can't inject a mock
- Adding a new notification type → **Modify** `OrderService` (also violates OCP!)

---

### ✅ Correct Design — Both Depend on Abstractions

**Step 1: Define abstractions (interfaces)**

```java
// Abstraction for persistence
interface Database {
    void save(String data);
}

// Abstraction for notifications
interface NotificationSender {
    void send(String message);
}
```

**Step 2: Low-level classes implement the abstractions**

```java
class MySQLDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class MongoDatabase implements Database {
    @Override
    public void save(String data) {
        System.out.println("Saving to MongoDB: " + data);
    }
}
```

```java
class EmailSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class SmsSender implements NotificationSender {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}
```

**Step 3: High-level class depends on abstractions — inject via constructor**

```java
// ✅ OrderService depends on interfaces, not concrete classes
class OrderService {
    private final Database database;               // Interface — not MySQLDatabase
    private final NotificationSender notifier;     // Interface — not EmailSender

    // Dependencies are INJECTED — not created inside
    public OrderService(Database database, NotificationSender notifier) {
        this.database = database;
        this.notifier = notifier;
    }

    public void placeOrder(String order) {
        database.save(order);       // ✅ Works with MySQL, MongoDB, or any DB
        notifier.send(order);       // ✅ Works with Email, SMS, or any notifier
        System.out.println("Order placed: " + order);
    }
}
```

**Usage — wire dependencies at the top level (composition root)**

```java
public class Main {
    public static void main(String[] args) {
        // Using MySQL + Email
        OrderService service1 = new OrderService(new MySQLDatabase(), new EmailSender());
        service1.placeOrder("Laptop");

        // Switching to MongoDB + SMS — ZERO changes to OrderService
        OrderService service2 = new OrderService(new MongoDatabase(), new SmsSender());
        service2.placeOrder("Phone");

        // Testing with mocks — no real DB or email needed
        OrderService testService = new OrderService(new MockDatabase(), new MockNotifier());
        testService.placeOrder("Test Order");
    }
}
```

**Switching implementations = just change what you pass in. `OrderService` never changes.**

---

### Before vs After — Visual Comparison

```
BEFORE (Violates DIP):                   AFTER (Follows DIP):

OrderService                             OrderService
    │ new MySQLDatabase()                    │ depends on «interface» Database
    │ new EmailSender()                      │ depends on «interface» NotificationSender
    ▼                                        ▼
MySQLDatabase  EmailSender            Database          NotificationSender
                                       ↑    ↑              ↑       ↑
High-level KNOWS low-level        MySQL  Mongo        Email    SMS
→ Tightly coupled                 
→ Can't swap, can't test          Low-level classes implement interfaces
                                   → Loosely coupled, swappable, testable
```

---

### DIP vs Dependency Injection (DI) — Clearing the Confusion

These two are very commonly confused. Here's the clearest way to think about them:

> **DIP** = the **WHY** and **WHAT** (the rule/goal)  
> **DI** = the **HOW** (the technique to achieve the goal)

---

#### The Analogy — Plug and Socket

Think of a **wall socket** and a **plug**:
- The socket (high-level) doesn't care if you plug in a fan, phone charger, or laptop
- It just exposes a **standard interface** (the socket shape)
- Any device that fits the interface works — the socket never changes

**DIP** says: "Design your socket to accept any device via a standard interface"  
**DI** says: "Hand the device to the socket from outside, don't hardcode a fan inside the wall"

---

#### They Are Different Things — You Can Have One Without the Other

```java
// ❌ DIP followed, but DI NOT used
// (depends on interface ✅, but creates it internally ❌)
class OrderService {
    private Database database = new MySQLDatabase();  // interface used, but hardcoded internally
}

// ❌ DI used, but DIP NOT followed
// (passed from outside ✅, but it's a concrete class ❌)
class OrderService {
    private MySQLDatabase database;  // concrete class, just injected
    public OrderService(MySQLDatabase database) { this.database = database; }
}

// ✅ BOTH DIP + DI together — the correct approach
class OrderService {
    private Database database;  // interface (DIP ✅)
    public OrderService(Database database) { this.database = database; }  // injected (DI ✅)
}
```

**Only when BOTH are used together do you get the full benefit.**

---

#### The 3 Types of Dependency Injection

**Type 1 — Constructor Injection ✅ (Preferred)**
```java
class OrderService {
    private final Database database;
    private final NotificationSender notifier;

    // Dependency injected at construction time — mandatory, explicit, immutable
    public OrderService(Database database, NotificationSender notifier) {
        this.database = database;
        this.notifier = notifier;
    }
}

// Usage: caller decides WHICH implementation to pass
new OrderService(new MySQLDatabase(), new EmailSender());   // Production
new OrderService(new MongoDatabase(), new SmsSender());     // Different setup
new OrderService(new MockDatabase(), new MockNotifier());   // Unit test — no real DB!
```
✅ Dependencies are **mandatory and clear**  
✅ Field can be `final` — **immutable** after construction  
✅ Easy to see what a class **needs** just by looking at the constructor  

---

**Type 2 — Setter Injection (For Optional Dependencies)**
```java
class OrderService {
    private Database database;
    private NotificationSender notifier;

    // Optional dependency — can be set or changed later
    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setNotifier(NotificationSender notifier) {
        this.notifier = notifier;
    }
}

// Usage
OrderService service = new OrderService();
service.setDatabase(new MySQLDatabase());
service.setNotifier(new EmailSender());
```
⚠️ Object can be in **incomplete state** before setters are called  
✅ Useful when dependency is truly **optional**  

---

**Type 3 — Method Injection (For Single-Use Dependency)**
```java
class OrderService {
    // Dependency only needed for THIS method — passed directly
    public void placeOrder(String order, Database database) {
        database.save(order);
    }
}
```
✅ When you need a different implementation for **each method call**  
✅ Lightweight — no stored reference  

---

#### How Spring Boot Does It (Same DIP + DI, framework handles the wiring)

Without Spring, YOU manually wire the dependencies:
```java
// You decide which implementation to use
OrderService service = new OrderService(new MySQLDatabase(), new EmailSender());
```

With Spring, the **framework** reads your config and does the wiring automatically:
```java
@Service
class OrderService {
    private final Database database;
    private final NotificationSender notifier;

    @Autowired  // Spring finds the Database bean and injects it automatically
    public OrderService(Database database, NotificationSender notifier) {
        this.database = database;
        this.notifier = notifier;
    }
}

@Repository
class MySQLDatabase implements Database { ... }  // Spring registers this as the Database bean

@Component
class EmailSender implements NotificationSender { ... }  // Spring registers this as the NotificationSender bean
```

Spring is just **automating the manual wiring** you see in `Main.java`.  
The DIP principle is the same — `OrderService` still depends on the `Database` interface, not on `MySQLDatabase`.

---

#### Complete Summary Table

| | DIP (Principle) | Dependency Injection (Technique) |
|---|---|---|
| **What is it?** | Design rule / guideline | Implementation pattern / technique |
| **What does it say?** | "Depend on abstractions, not concretions" | "Pass dependencies from outside, don't create them inside" |
| **How achieved?** | Use interfaces / abstract classes | Constructor / setter / method injection |
| **Goal** | Loose coupling | Flexibility + testability |
| **Without the other** | Can follow DIP but still hardcode `new MySQLDatabase()` | Can inject but still pass concrete classes |
| **Together** | ✅ Maximum benefit — swap implementations freely, mock in tests |
| **Framework** | — | Spring `@Autowired` automates DI |

---

### DIP Connection to Design Patterns

| Pattern | DIP Connection |
|---------|---------------|
| **Strategy** | Context depends on `Strategy` interface, not concrete strategies |
| **Factory** | Client depends on the factory interface, not on `new ConcreteProduct()` |
| **Observer** | Subject depends on `Observer` interface, not concrete observers |
| **Adapter** | Client depends on `Target` interface, Adapter bridges to the adaptee |
| **Decorator** | Wraps a `Component` interface — not a concrete class |

> **Almost every design pattern is an application of DIP** — they all introduce an interface between a high-level class and its dependencies.

---

### How to Spot DIP Violations

| Smell | Problem |
|-------|---------|
| `new ConcreteClass()` inside a service/business class | High-level knows low-level |
| `import com.db.MySQL...` in a service class | Concrete dependency pulled in |
| Can't unit test without real DB / email / file system | Dependencies are hardcoded |
| Changing DB requires modifying business logic | Tight coupling |
| Constructor has 5+ `new` calls | God constructor creating all dependencies |

---

### Real-World DIP Examples

| High-Level Module | Abstraction (Interface) | Low-Level Implementations |
|-------------------|------------------------|--------------------------|
| `OrderService` | `Database` | `MySQLDatabase`, `MongoDatabase` |
| `PaymentService` | `PaymentGateway` | `StripeGateway`, `PayPalGateway` |
| `NotificationService` | `NotificationSender` | `EmailSender`, `SmsSender`, `PushSender` |
| `ReportService` | `ReportExporter` | `PDFExporter`, `ExcelExporter` |
| `AuthService` | `UserRepository` | `DBUserRepository`, `InMemoryUserRepository` |

> **Interview One-Liner:** "DIP means high-level business logic shouldn't depend on low-level details. Both should depend on interfaces. In my projects, OrderService depends on a Database interface, not directly on MySQL — so I can swap implementations or inject mocks in tests without touching business logic."

---

---

## 🔗 All 5 Principles — The Complete Picture

```
SRP  → Each class has ONE job
OCP  → Extend via new classes, never modify existing ones
LSP  → Any subtype is safely substitutable for its parent
ISP  → Split fat interfaces; implement only what you need
DIP  → Depend on interfaces (abstractions), not concrete classes
```

**How they chain together:**

```
SRP  splits the code into focused classes
        ↓
ISP  gives each class focused interfaces to implement
        ↓
LSP  ensures each implementation honours its interface contract
        ↓
OCP  lets you add new implementations without changing existing code
        ↓
DIP  wires it all together — high-level depends on interfaces,
     low-level provides the implementations
```

---

## 📌 Complete SOLID Interview Quick-Fire

| Question | Answer |
|----------|--------|
| What does SOLID stand for? | Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion |
| Which principle prevents `if-else` chains? | OCP — add new types via new classes |
| Which principle prevents `UnsupportedOperationException` in children? | LSP — children must honour parent contracts |
| Which principle prevents fat interfaces? | ISP — split into focused interfaces |
| Which principle makes unit testing easy? | DIP — inject mocks via interfaces |
| Which principle separates Controller/Service/Repository? | SRP |
| Which patterns apply DIP? | Almost all — Strategy, Factory, Observer, Decorator, Adapter |
| Classic LSP violation? | Rectangle-Square |
| Classic ISP violation? | Fat Machine interface (print/scan/fax) |
| Classic DIP violation? | `new MySQLDatabase()` inside `OrderService` |

---

> **Day 3 (SOLID) Status:** ✅ DIP complete — All 5 SOLID principles done!  
> **Next:** Day 3 continued — Encapsulation & Abstraction → see `notes/OOPS/Encapsulation_and_Abstraction.md`

