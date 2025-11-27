***

# Adapter Pattern - Revision Notes 📝

## Overview

**Adapter Pattern** is a structural design pattern that allows objects with **incompatible interfaces** to work together by wrapping one interface to match another.

**Think of it as:** A translator between two people who speak different languages, or a power plug adapter that makes your Indian device work in USA outlets.

***

## The Problem Adapter Solves

### Without Adapter ❌

```java
// Your system expects this
interface MediaPlayer {
    void play(String filename);
}

// Third-party library has different interface
class AdvancedMP4Player {
    void playMP4(String filename) { ... }
}

// Can't use directly!
MediaPlayer player = new AdvancedMP4Player();  // ❌ Doesn't implement MediaPlayer
```


### With Adapter ✅

```java
// Adapter wraps the incompatible interface
class MP4Adapter implements MediaPlayer {
    private AdvancedMP4Player mp4Player;
    
    public MP4Adapter(AdvancedMP4Player player) {
        this.mp4Player = player;
    }
    
    @Override
    public void play(String filename) {
        mp4Player.playMP4(filename);  // Converts the call!
    }
}

// Now it works!
MediaPlayer player = new MP4Adapter(new AdvancedMP4Player());
player.play("video.mp4");  // ✅ Works!
```


***

## When to Use Adapter Pattern

✅ **Use Adapter When:**

1. **Incompatible interfaces** - Two classes need to work together but have different interfaces
2. **Third-party libraries** - Can't modify external code but need to integrate it
3. **Legacy code** - Old system needs to work with new system
4. **Data format conversion** - Different units (Celsius/Fahrenheit), formats (JSON/XML)
5. **Multiple implementations** - Need unified interface for different APIs (payment gateways, weather services)

❌ **Don't Use When:**

- Can modify the original class (just change it directly)
- Interfaces are already compatible (no adapter needed)
- Adding unnecessary complexity
- Need to add behavior (use Decorator instead)

***

## Real-World Examples

| Scenario | Adapter Use |
| :-- | :-- |
| **Power Plug** | Indian 2-pin → USA 3-pin adapter |
| **Payment Gateways** | Stripe/PayPal/Razorpay → Unified interface |
| **Weather APIs** | Different services → Common format |
| **Database Drivers** | JDBC adapts different databases |
| **Media Players** | MP3/MP4/VLC → Unified player |
| **Temperature Sensors** | Fahrenheit → Celsius conversion |


***

## Two Types of Adapters

### 1. Object Adapter (Recommended ✅)

**Uses composition (HAS-A relationship)**

```java
class Adapter implements Target {
    private Adaptee adaptee;  // Composition
    
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public void request() {
        adaptee.specificRequest();  // Delegates to adaptee
    }
}
```

**Pros:**

- ✅ More flexible (can adapt multiple classes)
- ✅ Follows composition over inheritance
- ✅ Can adapt class and its subclasses
- ✅ Preferred in Java

**Cons:**

- ❌ Slightly more code (delegation)

***

### 2. Class Adapter

**Uses inheritance (IS-A relationship)**

```java
class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        specificRequest();  // Inherited method
    }
}
```

**Pros:**

- ✅ Less code (direct method access)
- ✅ Can override Adaptee methods

**Cons:**

- ❌ Less flexible (single inheritance in Java)
- ❌ Can't adapt multiple classes
- ❌ Tight coupling

**In Java: Always prefer Object Adapter!** (Java doesn't support multiple inheritance)

***

## Adapter Pattern Structure

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │ uses
       ↓
┌─────────────────┐           ┌─────────────┐
│  Target         │           │  Adaptee    │
│  (Interface)    │           │  (Legacy)   │
│  + request()    │           │  + specific │
└─────────────────┘           │    Request()│
       ↑                       └─────────────┘
       │ implements                   ↑
       │                              │ contains
┌──────────────────────────────────────────┐
│           Adapter                        │
│  - adaptee: Adaptee                     │
│  + request() {                          │
│      adaptee.specificRequest();         │
│    }                                    │
└──────────────────────────────────────────┘
```

**Key Components:**

1. **Target:** Interface your client expects
2. **Adaptee:** Existing class with incompatible interface
3. **Adapter:** Wraps Adaptee and implements Target
4. **Client:** Uses Target interface

***

## Implementation Patterns

### Pattern 1: Simple Interface Conversion

```java
// Target interface
interface PaymentProcessor {
    void processPayment(double amount);
}

// Adaptee (legacy/third-party)
class OldPaymentGateway {
    public void makePayment(int cents) {
        System.out.println("Processing " + cents + " cents");
    }
}

// Adapter
class PaymentAdapter implements PaymentProcessor {
    private OldPaymentGateway gateway;
    
    public PaymentAdapter(OldPaymentGateway gateway) {
        this.gateway = gateway;
    }
    
    @Override
    public void processPayment(double amount) {
        int cents = (int)(amount * 100);  // Convert dollars to cents
        gateway.makePayment(cents);
    }
}

// Usage
PaymentProcessor processor = new PaymentAdapter(new OldPaymentGateway());
processor.processPayment(10.50);  // Output: Processing 1050 cents
```


***

### Pattern 2: Data Format Conversion

```java
// Target
interface TemperatureSensor {
    double getTemperatureCelsius();
}

// Adaptee
class FahrenheitSensor {
    public double getTemperatureFahrenheit() {
        return 98.6;  // Body temperature
    }
}

// Adapter with unit conversion
class TempAdapter implements TemperatureSensor {
    private FahrenheitSensor sensor;
    
    public TempAdapter(FahrenheitSensor sensor) {
        this.sensor = sensor;
    }
    
    @Override
    public double getTemperatureCelsius() {
        double fahrenheit = sensor.getTemperatureFahrenheit();
        return (fahrenheit - 32) * 5.0 / 9.0;  // F to C conversion
    }
}

// Usage
TemperatureSensor sensor = new TempAdapter(new FahrenheitSensor());
System.out.println(sensor.getTemperatureCelsius());  // 37.0°C
```


***

### Pattern 3: Multiple Adapters for Unified Interface

```java
// Common interface
interface WeatherService {
    WeatherInfo getWeather(String city);
}

// Different third-party APIs
class OpenWeatherAPI {
    public WeatherData fetchWeather(String city) { ... }
}

class AccuWeatherService {
    public AccuWeatherResponse getWeatherInfo(String city) { ... }
}

// Adapter 1
class OpenWeatherAdapter implements WeatherService {
    private OpenWeatherAPI api;
    
    public OpenWeatherAdapter(OpenWeatherAPI api) {
        this.api = api;
    }
    
    @Override
    public WeatherInfo getWeather(String city) {
        WeatherData data = api.fetchWeather(city);
        return new WeatherInfo(
            data.getTemperature() - 273.15,  // Kelvin to Celsius
            data.getCity(),
            data.getCondition()
        );
    }
}

// Adapter 2
class AccuWeatherAdapter implements WeatherService {
    private AccuWeatherService service;
    
    public AccuWeatherAdapter(AccuWeatherService service) {
        this.service = service;
    }
    
    @Override
    public WeatherInfo getWeather(String city) {
        AccuWeatherResponse response = service.getWeatherInfo(city);
        return new WeatherInfo(
            response.getTempCelsius(),
            response.getLocation(),
            response.getWeather()
        );
    }
}

// Usage - unified interface for different services!
WeatherService service1 = new OpenWeatherAdapter(new OpenWeatherAPI());
WeatherService service2 = new AccuWeatherAdapter(new AccuWeatherService());

WeatherInfo info1 = service1.getWeather("Delhi");
WeatherInfo info2 = service2.getWeather("Delhi");
```


***

## Code Templates

### Template 1: Basic Object Adapter

```java
// 1. Target interface (what client expects)
interface Target {
    void request();
}

// 2. Adaptee class (incompatible interface)
class Adaptee {
    public void specificRequest() {
        System.out.println("Specific request");
    }
}

// 3. Adapter (wraps Adaptee, implements Target)
class Adapter implements Target {
    private Adaptee adaptee;
    
    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }
    
    @Override
    public void request() {
        adaptee.specificRequest();  // Adapt the call
    }
}

// 4. Client usage
Target target = new Adapter(new Adaptee());
target.request();
```


***

### Template 2: Adapter with Lombok

```java
@RequiredArgsConstructor
public class PaymentAdapter implements PaymentProcessor {
    private final OldPaymentGateway gateway;
    
    @Override
    public void processPayment(double amount) {
        gateway.makePayment((int)(amount * 100));
    }
}
```


***

### Template 3: Multiple Adapters Pattern

```java
// Common interface
interface Service {
    Result execute(String input);
}

// Adapter 1
@RequiredArgsConstructor
class ServiceAAdapter implements Service {
    private final ThirdPartyServiceA serviceA;
    
    @Override
    public Result execute(String input) {
        return convertToResult(serviceA.doSomething(input));
    }
}

// Adapter 2
@RequiredArgsConstructor
class ServiceBAdapter implements Service {
    private final ThirdPartyServiceB serviceB;
    
    @Override
    public Result execute(String input) {
        return convertToResult(serviceB.perform(input));
    }
}

// Usage with polymorphism
List<Service> services = List.of(
    new ServiceAAdapter(new ThirdPartyServiceA()),
    new ServiceBAdapter(new ThirdPartyServiceB())
);

for (Service service : services) {
    Result result = service.execute("data");
}
```


***

## Common Mistakes \& Solutions

### ❌ Mistake 1: Validation in Adapter

```java
// BAD - Too much logic
class MP4Adapter implements MediaPlayer {
    public void play(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("Invalid filename");
        }
        if (!filename.endsWith(".mp4")) {
            throw new IllegalArgumentException("Not MP4 file");
        }
        mp4Player.playMP4(filename);
    }
}
```

**Problem:** Adapter is doing too much (validation + adaptation)

**Solution:** Adapter should only adapt, validation is caller's responsibility

```java
// GOOD - Just adapts
class MP4Adapter implements MediaPlayer {
    public void play(String filename) {
        mp4Player.playMP4(filename);  // Just adapt
    }
}
```


***

### ❌ Mistake 2: Creating Adaptee Inside Adapter

```java
// BAD - Tight coupling
class PaymentAdapter implements PaymentProcessor {
    private OldPaymentGateway gateway = new OldPaymentGateway();  // ❌
}
```

**Problem:** Hard to test, tight coupling

**Solution:** Inject via constructor (Dependency Injection)

```java
// GOOD - Loose coupling
@RequiredArgsConstructor
class PaymentAdapter implements PaymentProcessor {
    private final OldPaymentGateway gateway;  // Injected
}
```


***

### ❌ Mistake 3: Not Handling Data Conversion

```java
// BAD - Direct pass-through without conversion
class TempAdapter implements TemperatureSensor {
    public double getTemperatureCelsius() {
        return sensor.getTemperatureFahrenheit();  // ❌ Wrong unit!
    }
}
```

**Solution:** Always convert data formats

```java
// GOOD - Proper conversion
class TempAdapter implements TemperatureSensor {
    public double getTemperatureCelsius() {
        double f = sensor.getTemperatureFahrenheit();
        return (f - 32) * 5.0 / 9.0;  // ✅ Convert F to C
    }
}
```


***

### ❌ Mistake 4: Using Concrete Class Instead of Interface

```java
// BAD - Depends on concrete class
class DatabaseAdapter {
    private MySQLDatabase database;  // ❌ Concrete class
}
```

**Problem:** Hard to test (can't mock), tight coupling

**Solution:** Depend on interface

```java
// GOOD - Depends on interface
class DatabaseAdapter {
    private Database database;  // ✅ Interface
}
```


***

## Pattern Comparisons

### Adapter vs Decorator

| Aspect | Adapter | Decorator |
| :-- | :-- | :-- |
| **Purpose** | Convert interface | Add behavior |
| **Interface** | Changes to different interface | Same interface |
| **Number of objects** | Wraps 1 object | Can wrap multiple layers |
| **When to use** | Incompatible interfaces | Extend functionality |
| **Example** | Old printer → New system | Plain coffee → Add milk → Add sugar |

**Key Difference:** Adapter changes interface, Decorator keeps same interface.

***

### Adapter vs Facade

| Aspect | Adapter | Facade |
| :-- | :-- | :-- |
| **Purpose** | Make incompatible things work | Simplify complex system |
| **Relationship** | 1-to-1 (one adaptee) | Many-to-1 (multiple subsystems) |
| **Interface** | Converts existing interface | Creates new simple interface |
| **When to use** | Two systems don't match | System too complex |
| **Example** | Power plug adapter | Home theater remote |

**Key Difference:** Adapter connects 2 things, Facade simplifies many things.

***

### Adapter vs Proxy

| Aspect | Adapter | Proxy |
| :-- | :-- | :-- |
| **Purpose** | Interface conversion | Control access |
| **Interface** | Different interface | Same interface |
| **Adds** | Compatibility | Access control, lazy loading |
| **Example** | Currency converter | Image loading proxy |

**Key Difference:** Adapter changes interface, Proxy controls access with same interface.

***

## Testing Adapters

### Good Test Structure

```java
@Test
public void testPaymentAdapter() {
    // 1. Create mock adaptee
    OldPaymentGateway mockGateway = mock(OldPaymentGateway.class);
    
    // 2. Create adapter with mock
    PaymentProcessor adapter = new PaymentAdapter(mockGateway);
    
    // 3. Call adapter method
    adapter.processPayment(10.50);
    
    // 4. Verify adaptee method called correctly
    verify(mockGateway).makePayment(1050);  // Dollars converted to cents
}
```


### Why Dependency Injection Helps Testing

```java
// With DI - Easy to test
@RequiredArgsConstructor
class Adapter {
    private final Adaptee adaptee;  // Injected
}

@Test
public void test() {
    Adaptee mock = mock(Adaptee.class);  // Create mock
    Adapter adapter = new Adapter(mock);  // Inject mock
    // Easy to test!
}
```


***

## Interview Questions \& Answers

**Q: What is Adapter Pattern?**
A: Structural pattern that allows incompatible interfaces to work together by wrapping one interface to match another.

**Q: Object Adapter vs Class Adapter?**
A: Object Adapter uses composition (HAS-A), Class Adapter uses inheritance (IS-A). Object Adapter is preferred in Java because it's more flexible and Java doesn't support multiple inheritance.

**Q: When to use Adapter?**
A: When integrating third-party libraries, working with legacy code, converting data formats, or unifying multiple APIs with different interfaces.

**Q: Adapter vs Decorator?**
A: Adapter changes interface for compatibility. Decorator keeps same interface but adds behavior.

**Q: Can Adapter change behavior?**
A: Adapter should only convert interfaces and data formats, not add business logic. Keep adapters thin!

**Q: How many adapters needed for N incompatible systems?**
A: N adapters - one for each system. Each adapter converts that system's interface to your target interface.

***

## Best Practices ✅

1. **Use Object Adapter** (composition) over Class Adapter (inheritance)
2. **Inject dependencies** via constructor (testability)
3. **Keep adapters thin** - only convert, don't add logic
4. **One adapter per incompatible system** - follow Single Responsibility Principle
5. **Convert data formats** properly (units, types, structures)
6. **Depend on interfaces** not concrete classes (loose coupling)
7. **Use Lombok** for cleaner code (`@RequiredArgsConstructor`)
8. **Test with mocks** to verify conversions

***

## Quick Reference Cheat Sheet

**One-Line Summary:** Adapter converts incompatible interface to expected interface.

**When to use:** Third-party libraries, legacy code, data conversion, unified interface

**Structure:**

```java
class Adapter implements Target {
    private Adaptee adaptee;
    public void request() { adaptee.specificRequest(); }
}
```

**Key Principle:** Composition over inheritance (Object Adapter)

**Common Use Cases:** Payment gateways, weather APIs, media players, database drivers

***

## Summary

**Adapter Pattern allows incompatible interfaces to work together.**

**Key Points:**

1. Wraps incompatible interface to match expected interface
2. Use Object Adapter (composition) in Java
3. One adapter per incompatible system
4. Converts interfaces AND data formats
5. Keep adapters thin - only adapt, don't add logic
6. Test with dependency injection and mocks

***


