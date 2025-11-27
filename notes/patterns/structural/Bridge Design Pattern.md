# 📚 Bridge Pattern - Comprehensive Revision Notes


***

## 🎯 Pattern Summary

**Definition:** Decouple an abstraction from its implementation so that the two can vary independently.

**Category:** Structural Pattern

**Problem Solved:** Prevents class explosion when you have multiple dimensions of variation. Allows abstraction and implementation to evolve independently without affecting each other.

**Key Insight:** Use composition (HAS-A) to connect two separate class hierarchies - one for "what to do" (Abstraction) and one for "how to do it" (Implementation). The abstraction holds a reference to the implementation, forming a "bridge" between them.

**Alternative Names:** Handle/Body pattern

***

## 🏗️ Structure \& Participants

### 1. Abstraction (Abstract Class/Interface)

- Defines high-level control logic
- Maintains reference to Implementation (the "bridge")
- Delegates actual work to Implementation
- Example: `VideoQuality`, `RemoteControl`, `Payment`


### 2. Refined Abstraction (Concrete Classes)

- Extends Abstraction with specific features
- Uses inherited Implementation reference
- Example: `BasicOnDevice`, `HdOnDevice`, `UltraOnDevice`


### 3. Implementation (Interface)

- Defines interface for implementation classes
- Provides primitive operations
- Not necessarily matching Abstraction's interface
- Example: `Device`, `DrawingAPI`, `PaymentGateway`


### 4. Concrete Implementation

- Implements the Implementation interface
- Platform/technology specific code
- Example: `SmartTV`, `MobilePhone`, `Laptop`


### 5. Client

- Creates Abstraction with desired Implementation
- Works through Abstraction interface

***

## 💻 Code Templates

### Template 1: Basic Bridge Structure

```java
// IMPLEMENTATION INTERFACE - "How to do it"
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

// ABSTRACTION - "What to do"
@Getter
public abstract class Abstraction {
    protected final Implementation implementation;
    
    public Abstraction(Implementation implementation) {
        this.implementation = Objects.requireNonNull(implementation, 
            "Implementation cannot be null");
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
        implementation.operationImpl();  // Delegates to implementation
    }
}

public class RefinedAbstractionB extends Abstraction {
    public RefinedAbstractionB(Implementation implementation) {
        super(implementation);
    }
    
    @Override
    public void operation() {
        System.out.print("Refined B: ");
        implementation.operationImpl();  // Delegates to implementation
    }
}

// CLIENT USAGE
public class BridgeDemo {
    public static void main(String[] args) {
        // Mix and match abstraction with implementation
        Abstraction abs1 = new RefinedAbstractionA(new ConcreteImplementationA());
        abs1.operation();  // Refined A: Implementation A
        
        Abstraction abs2 = new RefinedAbstractionA(new ConcreteImplementationB());
        abs2.operation();  // Refined A: Implementation B
        
        Abstraction abs3 = new RefinedAbstractionB(new ConcreteImplementationA());
        abs3.operation();  // Refined B: Implementation A
    }
}
```


***

### Template 2: Remote Control \& Device Example

```java
// IMPLEMENTATION - Device
public interface Device {
    void powerOn();
    void powerOff();
    void setVolume(int volume);
    int getVolume();
    void setChannel(int channel);
    int getChannel();
}

// CONCRETE IMPLEMENTATIONS
@Data
public class TV implements Device {
    private boolean on = false;
    private int volume = 30;
    private int channel = 1;
    
    @Override
    public void powerOn() {
        on = true;
        System.out.println("TV is now ON");
    }
    
    @Override
    public void powerOff() {
        on = false;
        System.out.println("TV is now OFF");
    }
    
    @Override
    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
        System.out.println("TV volume: " + this.volume);
    }
    
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("TV channel: " + this.channel);
    }
}

@Data
public class Radio implements Device {
    private boolean on = false;
    private int volume = 20;
    private int channel = 1;
    
    @Override
    public void powerOn() {
        on = true;
        System.out.println("Radio is now ON");
    }
    
    @Override
    public void powerOff() {
        on = false;
        System.out.println("Radio is now OFF");
    }
    
    @Override
    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
        System.out.println("Radio volume: " + this.volume);
    }
    
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
        System.out.println("Radio frequency: " + this.channel + " MHz");
    }
}

// ABSTRACTION - Remote Control
@Getter
@RequiredArgsConstructor
public abstract class RemoteControl {
    protected final Device device;
    
    public void togglePower() {
        // High-level operation using device primitives
        System.out.println("Toggle power");
        if (device.getVolume() > 0) {
            device.powerOff();
        } else {
            device.powerOn();
        }
    }
    
    public void volumeUp() {
        device.setVolume(device.getVolume() + 10);
    }
    
    public void volumeDown() {
        device.setVolume(device.getVolume() - 10);
    }
    
    public void channelUp() {
        device.setChannel(device.getChannel() + 1);
    }
    
    public void channelDown() {
        device.setChannel(device.getChannel() - 1);
    }
}

// REFINED ABSTRACTIONS
public class BasicRemote extends RemoteControl {
    public BasicRemote(Device device) {
        super(device);
    }
}

public class AdvancedRemote extends RemoteControl {
    public AdvancedRemote(Device device) {
        super(device);
    }
    
    // Additional features
    public void mute() {
        System.out.println("Mute");
        device.setVolume(0);
    }
    
    public void setChannel(int channel) {
        System.out.println("Direct channel selection: " + channel);
        device.setChannel(channel);
    }
}

// USAGE
public class RemoteControlDemo {
    public static void main(String[] args) {
        Device tv = new TV();
        Device radio = new Radio();
        
        // Basic remote with TV
        RemoteControl basicRemote = new BasicRemote(tv);
        basicRemote.togglePower();
        basicRemote.volumeUp();
        basicRemote.channelUp();
        
        // Advanced remote with Radio
        AdvancedRemote advancedRemote = new AdvancedRemote(radio);
        advancedRemote.togglePower();
        advancedRemote.mute();
        advancedRemote.setChannel(95);
        
        // Switch device at runtime
        advancedRemote = new AdvancedRemote(tv);
        advancedRemote.setChannel(10);
    }
}
```


***

### Template 3: Payment System Example

```java
// IMPLEMENTATION - Payment Gateway
public interface PaymentGateway {
    boolean authenticate(String credentials);
    boolean processTransaction(double amount, String currency);
    String getTransactionId();
}

// CONCRETE IMPLEMENTATIONS
public class Razorpay implements PaymentGateway {
    private String transactionId;
    
    @Override
    public boolean authenticate(String credentials) {
        System.out.println("Razorpay: Authenticating...");
        return true;
    }
    
    @Override
    public boolean processTransaction(double amount, String currency) {
        System.out.println("Razorpay: Processing ₹" + amount);
        transactionId = "RZP_" + System.currentTimeMillis();
        return true;
    }
    
    @Override
    public String getTransactionId() {
        return transactionId;
    }
}

public class PayU implements PaymentGateway {
    private String transactionId;
    
    @Override
    public boolean authenticate(String credentials) {
        System.out.println("PayU: Authenticating...");
        return true;
    }
    
    @Override
    public boolean processTransaction(double amount, String currency) {
        System.out.println("PayU: Processing ₹" + amount);
        transactionId = "PAYU_" + System.currentTimeMillis();
        return true;
    }
    
    @Override
    public String getTransactionId() {
        return transactionId;
    }
}

// ABSTRACTION - Payment Type
@Getter
public abstract class Payment {
    protected final PaymentGateway gateway;
    protected final double amount;
    protected final String currency;
    
    public Payment(PaymentGateway gateway, double amount, String currency) {
        this.gateway = Objects.requireNonNull(gateway, "Gateway cannot be null");
        this.amount = amount;
        this.currency = currency;
    }
    
    public abstract boolean pay();
}

// REFINED ABSTRACTIONS
public class CardPayment extends Payment {
    private final String cardNumber;
    private final String cvv;
    
    public CardPayment(PaymentGateway gateway, double amount, String currency,
                      String cardNumber, String cvv) {
        super(gateway, amount, currency);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }
    
    @Override
    public boolean pay() {
        System.out.println("=== Card Payment ===");
        if (gateway.authenticate(cardNumber + ":" + cvv)) {
            if (gateway.processTransaction(amount, currency)) {
                System.out.println("Card payment successful! TxnID: " + 
                                 gateway.getTransactionId());
                return true;
            }
        }
        return false;
    }
}

public class UPIPayment extends Payment {
    private final String upiId;
    
    public UPIPayment(PaymentGateway gateway, double amount, String currency,
                     String upiId) {
        super(gateway, amount, currency);
        this.upiId = upiId;
    }
    
    @Override
    public boolean pay() {
        System.out.println("=== UPI Payment ===");
        if (gateway.authenticate(upiId)) {
            if (gateway.processTransaction(amount, currency)) {
                System.out.println("UPI payment successful! TxnID: " + 
                                 gateway.getTransactionId());
                return true;
            }
        }
        return false;
    }
}

// USAGE
public class PaymentDemo {
    public static void main(String[] args) {
        PaymentGateway razorpay = new Razorpay();
        PaymentGateway payu = new PayU();
        
        // Card payment via Razorpay
        Payment cardPayment = new CardPayment(razorpay, 1500, "INR",
                                             "1234-5678-9012-3456", "123");
        cardPayment.pay();
        
        // UPI payment via PayU
        Payment upiPayment = new UPIPayment(payu, 2500, "INR", 
                                           "user@paytm");
        upiPayment.pay();
        
        // Same payment type, different gateway (runtime switch)
        Payment cardViaPayU = new CardPayment(payu, 3000, "INR",
                                             "1234-5678-9012-3456", "123");
        cardViaPayU.pay();
    }
}
```


***

## 🔑 Key Concepts \& Relationships

### IS-A and HAS-A Relationships

```
ABSTRACTION HIERARCHY:
VideoQuality (abstract)
├─ IS-A: BasicOnDevice
├─ IS-A: HdOnDevice
└─ IS-A: UltraOnDevice

IMPLEMENTATION HIERARCHY:
Device (interface)
├─ IS-A: SmartTV
├─ IS-A: MobilePhone
└─ IS-A: Laptop

THE BRIDGE (Composition):
VideoQuality HAS-A Device (protected final Device device)
```


### Two Independent Hierarchies

```
Abstraction Side          Implementation Side
(What to do)              (How to do it)

VideoQuality    ←─bridge─→    Device
    ↓                            ↓
BasicOnDevice              SmartTV
HdOnDevice                 MobilePhone
UltraOnDevice              Laptop

Can add to either side without affecting the other!
```


### Delegation Pattern

```java
// Abstraction delegates to Implementation
public void play() {
    // Uses implementation's primitives
    System.out.println("[" + device.getDeviceName() + "] ...");
    //                      ↑
    //              Delegates to implementation
}
```


***

## ⚠️ Common Mistakes \& Solutions

### Mistake 1: Confusing Bridge with Adapter

```java
❌ // This looks like Bridge but is actually Adapter
public class PaymentAdapter implements NewPaymentInterface {
    private OldPaymentAPI oldAPI;  // Wrapping legacy API
    
    public void newMethod() {
        oldAPI.oldMethod();  // Converting interface
    }
}

✅ // Real Bridge - both hierarchies designed together
public abstract class Payment {
    protected PaymentGateway gateway;  // Both are new designs
    
    public abstract boolean pay();
}
```

**Key Difference:** Bridge is designed upfront; Adapter wraps existing code.

***

### Mistake 2: Using Inheritance Instead of Composition

```java
❌ // Wrong - Using inheritance
public class BasicOnSmartTV extends SmartTV {
    // Locked to SmartTV, need separate class for each device
}

✅ // Correct - Using composition
public class BasicOnDevice extends VideoQuality {
    public BasicOnDevice(Device device) {
        super(device);  // Can work with ANY device
    }
}
```

**Solution:** Always use composition (HAS-A) to bridge abstraction and implementation.

***

### Mistake 3: No Null Check in Constructor

```java
❌ public VideoQuality(Device device) {
    this.device = device;  // What if device is null?
}

✅ public VideoQuality(Device device) {
    this.device = Objects.requireNonNull(device, "Device cannot be null");
}
```


***

### Mistake 4: Implementation Doing Too Much

```java
❌ // Implementation controls behavior (wrong)
public interface Device {
    void playVideo(String quality);  // Too high-level
    void displayVideoInfo(String quality);
}

✅ // Implementation provides primitives (correct)
public interface Device {
    String getDeviceName();  // Low-level data
    String getDeviceSpecs();
}

// Abstraction controls behavior
public void play() {
    System.out.println("[" + device.getDeviceName() + "] Playing...");
}
```

**Principle:** Implementation provides primitives; Abstraction builds complex operations.

***

### Mistake 5: Not Making Implementation Final

```java
❌ protected Device device;  // Can be reassigned!

✅ protected final Device device;  // Immutable reference
```

**Why final?** Device shouldn't change after construction. Prevents bugs and ensures consistency.

***

### Mistake 6: Creating Redundant Hierarchies

```java
❌ // Don't do this - unnecessary subclasses
public class BasicOnSmartTV extends BasicOnDevice {
    // Just passes SmartTV to super - pointless!
}

✅ // Do this - use constructor directly
VideoQuality basic = new BasicOnDevice(new SmartTV());
```


***

## 🆚 Pattern Comparisons

### Bridge vs Adapter

| Aspect | Bridge | Adapter |
| :-- | :-- | :-- |
| **Intent** | Decouple abstraction from implementation | Make incompatible interfaces compatible |
| **When** | Designed upfront (proactive) | Used after-the-fact (reactive) |
| **Structure** | Two hierarchies designed together | Wraps existing incompatible code |
| **Purpose** | Prevent class explosion | Fix compatibility issues |
| **Flexibility** | Both hierarchies vary independently | Adapts one interface to another |
| **Example** | RemoteControl + Device (planned) | StripeAPI wrapper (fixing existing) |

**Quote:** "Adapter makes things work **after** they're designed; Bridge makes them work **before** they are."

***

### Bridge vs Strategy

| Aspect | Bridge | Strategy |
| :-- | :-- | :-- |
| **Type** | Structural pattern | Behavioral pattern |
| **Focus** | Structure (two hierarchies) | Behavior (algorithm selection) |
| **Abstraction** | Multiple refined abstractions | Single context class |
| **Implementation** | Multiple implementations | Multiple strategy algorithms |
| **Operations** | Different operations per abstraction | Same operation, different algorithms |
| **Example** | BasicRemote, AdvancedRemote + TV, Radio | SortContext + BubbleSort, QuickSort |


***

### Bridge vs Decorator

| Aspect | Bridge | Decorator |
| :-- | :-- | :-- |
| **Intent** | Separate what from how | Add responsibilities dynamically |
| **Structure** | Two hierarchies (abstraction + impl) | Single hierarchy with wrappers |
| **Relationship** | Abstraction HAS-A Implementation | Decorator HAS-A Component (same type) |
| **Purpose** | Vary independently | Stack behaviors |
| **Example** | RemoteControl + Device | Coffee + Milk + Sugar decorators |


***

### Bridge vs Composite

| Aspect | Bridge | Composite |
| :-- | :-- | :-- |
| **Intent** | Decouple hierarchies | Represent tree structures |
| **Children** | Single implementation reference | Multiple children (List) |
| **Recursion** | No recursion | Recursive operations |
| **Focus** | Connecting two hierarchies | Part-whole hierarchy |
| **Example** | Remote + Device | Folder containing Files/Folders |


***

## 🎯 When to Use Bridge Pattern

### ✅ Use When:

1. **Multiple Dimensions of Variation**
    - Shapes × Colors = Many combinations
    - Remote types × Device types
    - Payment methods × Gateways
2. **Avoid Class Explosion**
    - Without Bridge: M × N classes
    - With Bridge: M + N classes
3. **Runtime Binding**
    - Switch implementation at runtime
    - Same abstraction, different implementations
4. **Independent Evolution**
    - Add abstractions without modifying implementations
    - Add implementations without modifying abstractions
5. **Share Implementation Across Abstractions**
    - Multiple remotes control same device
    - Multiple payment types use same gateway
6. **Platform-Specific Implementations**
    - Cross-platform apps (Windows, Mac, Linux)
    - Multiple database drivers
    - Different rendering engines

### ❌ Don't Use When:

1. **Single Dimension of Variation**
    - Only one aspect changes
    - Simple inheritance is enough
2. **No Need for Runtime Switching**
    - Implementation fixed at compile-time
    - Use simpler patterns
3. **Tight Coupling is Acceptable**
    - Small system, won't change
    - Over-engineering risk
4. **After-the-Fact Integration**
    - Use Adapter for existing incompatible code
    - Bridge is for upfront design

***

## 🧠 Interview Questions \& Answers

### Q1: Explain Bridge pattern in 2 sentences.

**A:** Bridge pattern decouples an abstraction from its implementation so both can vary independently. It uses composition to connect two separate class hierarchies - one for high-level operations (abstraction) and one for platform-specific details (implementation).

***

### Q2: How does Bridge prevent class explosion?

**A:** Without Bridge, every combination of abstraction and implementation requires a separate class (M × N classes). With Bridge, you have M abstraction classes + N implementation classes = M + N total classes, connected through composition.

Example: 3 remotes × 4 devices = 12 classes ❌ → Bridge: 3 + 4 = 7 classes ✅

***

### Q3: What's the difference between Bridge and Adapter?

**A:** Bridge is designed upfront to decouple two hierarchies that will evolve independently. Adapter is used after-the-fact to make existing incompatible interfaces work together. Bridge prevents problems; Adapter fixes problems.

***

### Q4: What is the "bridge" in Bridge pattern?

**A:** The bridge is the composition relationship - the reference field in the abstraction that holds the implementation object. For example, `protected final Device device` in VideoQuality class is the bridge connecting abstraction to implementation.

***

### Q5: Can you add new abstractions without modifying implementations?

**A:** Yes! That's the core benefit. Add a new VideoQuality subclass (e.g., Quality8K), and it automatically works with all existing Device implementations without modifying any Device code. Both hierarchies evolve independently.

***

### Q6: Why use composition instead of inheritance in Bridge?

**A:** Inheritance locks you to a specific implementation at compile-time, causing class explosion (need BasicOnTV, BasicOnMobile, etc.). Composition allows runtime flexibility - same Basic quality can work with any Device by passing different implementations to the constructor.

***

### Q7: When should abstraction control behavior vs implementation?

**A:** In Bridge, the abstraction should control high-level behavior, and the implementation should provide low-level primitives. The abstraction uses implementation's primitives to build complex operations. This maintains proper separation of concerns.

***

### Q8: Can you switch implementation at runtime?

**A:** Yes, if you don't make the implementation field final and provide a setter. However, best practice is to create a new abstraction object with the new implementation rather than mutating existing objects. This follows immutability principles.

```java
// Better approach
VideoQuality hd1 = new HdOnDevice(mobile);
VideoQuality hd2 = new HdOnDevice(tv);  // New object, new device
```


***

### Q9: Real-world examples of Bridge pattern?

**A:**

- JDBC drivers (Java abstraction + database-specific implementations)
- GUI frameworks (UI controls + platform renderers: Windows, Mac, Linux)
- Payment systems (payment types + gateway providers)
- Graphics libraries (shapes + rendering APIs: OpenGL, DirectX)
- Messaging systems (message types + delivery channels: Email, SMS, Push)

***

### Q10: How is Bridge different from Strategy pattern?

**A:** Both use composition, but Bridge has two hierarchies (multiple abstractions + multiple implementations) with structural focus, while Strategy has one context with multiple algorithms (behavioral focus). Bridge varies the implementation; Strategy varies the algorithm.

***

## 🎨 Decision Tree: When to Use Bridge

```
Do you have TWO dimensions that vary?
│
├─ YES → Will combinations cause class explosion?
│   │
│   ├─ YES → Do both dimensions need independent evolution?
│   │   │
│   │   ├─ YES → Do you need runtime switching?
│   │   │   │
│   │   │   ├─ YES → ✅ USE BRIDGE PATTERN
│   │   │   │
│   │   │   └─ NO → Consider simple factory or strategy
│   │   │
│   │   └─ NO → Use inheritance or composition as needed
│   │
│   └─ NO → Simple inheritance might be enough
│
└─ NO → Single dimension → Use simpler patterns
```


***

## ✅ Best Practices

### 1. Always Use Composition (HAS-A)

```java
✅ protected final Device device;  // Composition
❌ extends SmartTV  // Inheritance locks to one type
```


### 2. Make Implementation Reference Final

```java
✅ protected final Device device;  // Immutable reference
❌ protected Device device;  // Can be changed, breaks consistency
```


### 3. Null Check in Constructor

```java
✅ this.device = Objects.requireNonNull(device, "Device cannot be null");
❌ this.device = device;  // NPE waiting to happen
```


### 4. Abstraction Controls Behavior

```java
✅ // Abstraction builds complex operations
public void play() {
    System.out.println("[" + device.getDeviceName() + "] Playing...");
}

❌ // Don't let implementation control high-level behavior
device.playVideo();  // Too much responsibility in implementation
```


### 5. Implementation Provides Primitives

```java
✅ // Low-level data methods
String getDeviceName();
String getDeviceSpecs();

❌ // High-level operations (belongs in abstraction)
void playVideoWithQuality(String quality);
```


### 6. Use Abstract Base Class for Abstraction

```java
✅ public abstract class VideoQuality {
    protected final Device device;
    public abstract void play();
}

// Concrete classes extend and implement
public class BasicOnDevice extends VideoQuality { ... }
```


### 7. Reduce Code Duplication in Abstractions

```java
✅ // Store common data in base class
public abstract class VideoQuality {
    private final String resolution;
    private final int bitrate;
    
    // Common implementation
    public void play() {
        System.out.println("Playing " + resolution);
    }
}

❌ // Don't duplicate logic in every subclass
```


### 8. Use Lombok Wisely

```java
✅ @Getter  // Only getters, no setters
   @RequiredArgsConstructor  // Constructor for final fields

❌ @Data  // Generates setters for non-final fields (might not want)
```


### 9. Interface for Implementation Side

```java
✅ public interface Device { ... }  // Flexible, supports multiple implementations

// Abstract class only if shared code exists
public abstract class BaseDevice implements Device { ... }
```


### 10. Document the Bridge

```java
/**
 * Bridge pattern: VideoQuality (abstraction) is bridged to Device (implementation).
 * This allows adding new quality levels or devices independently.
 */
public abstract class VideoQuality {
    protected final Device device;  // The bridge
}
```


***

## 🚀 Quick Reference

**Intent:** Decouple abstraction from implementation for independent variation

**Structure:**

- Abstraction HAS-A Implementation (the bridge)
- Two separate hierarchies connected by composition

**Key Participants:**

- Abstraction (abstract class with implementation reference)
- Refined Abstraction (concrete subclasses)
- Implementation (interface defining primitives)
- Concrete Implementation (platform-specific code)

**When to Use:**

- Multiple dimensions of variation
- Avoid class explosion (M × N → M + N)
- Runtime implementation switching
- Independent evolution of hierarchies

**When NOT to Use:**

- Single dimension (use simple inheritance)
- No runtime switching needed
- After-the-fact (use Adapter instead)

**Key Benefits:**

- Reduces classes from M × N to M + N
- Both hierarchies evolve independently
- Runtime flexibility
- Better maintainability

**Related Patterns:**

- Adapter (after-the-fact vs upfront)
- Strategy (behavioral vs structural)
- Decorator (stacking vs bridging)

***

## 📌 Copy-Paste Template for Quick Use

```java
// IMPLEMENTATION INTERFACE
public interface Implementation {
    void primitiveOperation();
}

// CONCRETE IMPLEMENTATION
public class ConcreteImplementation implements Implementation {
    @Override
    public void primitiveOperation() {
        System.out.println("Concrete implementation");
    }
}

// ABSTRACTION
@Getter
public abstract class Abstraction {
    protected final Implementation implementation;
    
    public Abstraction(Implementation implementation) {
        this.implementation = Objects.requireNonNull(implementation);
    }
    
    public abstract void operation();
}

// REFINED ABSTRACTION
public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementation implementation) {
        super(implementation);
    }
    
    @Override
    public void operation() {
        implementation.primitiveOperation();  // Delegate
    }
}

// USAGE
Implementation impl = new ConcreteImplementation();
Abstraction abs = new RefinedAbstraction(impl);
abs.operation();
```


***

