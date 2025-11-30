# 📚 Observer Pattern - Comprehensive Revision Notes


***

## 🎯 Pattern Summary

**Definition:** Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

**Category:** Behavioral Pattern

**Problem Solved:** Establishes automatic notification mechanism for state changes without tight coupling. Eliminates need for polling. Enables event-driven architecture where multiple objects need to stay synchronized with a subject's state.

**Key Insight:** Instead of objects constantly checking (polling) for changes, they register as observers and get automatically notified when something changes. The subject broadcasts changes to all registered observers without knowing their concrete types.

**Alternative Names:** Publish-Subscribe (Pub-Sub), Dependents, Event-Subscriber, Listener Pattern

**Core Principle:** "Don't call us, we'll call you" (Hollywood Principle) - Observers register interest and subject calls them when state changes.

***

## 🏗️ Structure \& Participants

### 1. Subject (Observable/Publisher) Interface

- Declares methods to attach/detach observers
- Declares method to notify all observers
- Maintains list of observers
- Example: `Subject`, `Observable`, `EventSource`


### 2. ConcreteSubject

- Implements Subject interface
- Stores state of interest to observers
- Sends notification to observers when state changes
- Provides methods for observers to access state (pull model)
- Example: `WeatherStation`, `Stock`, `NewsAgency`


### 3. Observer (Subscriber/Listener) Interface

- Declares update method called by subject
- Simple interface (often just one method)
- Example: `Observer`, `Listener`, `Subscriber`


### 4. ConcreteObserver

- Implements Observer interface
- Maintains reference to ConcreteSubject (for pull model)
- Updates itself based on subject's notification
- Can query subject for specific data it needs
- Example: `CurrentConditionsDisplay`, `StatisticsDisplay`, `Dashboard`

***

## 💻 Code Templates

### Template 1: Basic Observer Pattern (Pull Model)

```java
// OBSERVER INTERFACE
public interface Observer {
    void update();  // Called by subject when state changes
}

// SUBJECT INTERFACE
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// CONCRETE SUBJECT
public class ConcreteSubject implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private int state;  // State that observers care about
    
    // Getters for pull model
    public int getState() {
        return state;
    }
    
    public void setState(int state) {
        this.state = state;
        notifyObservers();  // Notify when state changes
    }
    
    @Override
    public void registerObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null");
        }
        observers.add(observer);
        System.out.println("[Subject] Observer registered");
    }
    
    @Override
    public void removeObserver(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("[Subject] Observer removed");
        }
    }
    
    @Override
    public void notifyObservers() {
        // Exception handling prevents one failure from stopping others
        for (Observer observer : observers) {
            try {
                observer.update();
            } catch (Exception e) {
                System.err.println("[ERROR] Observer failed: " + e.getMessage());
                // Continue with other observers
            }
        }
    }
}

// CONCRETE OBSERVER
public class ConcreteObserver implements Observer {
    private final ConcreteSubject subject;  // private final - best practice
    private int observerState;
    
    public ConcreteObserver(ConcreteSubject subject) {
        this.subject = subject;
        subject.registerObserver(this);  // Self-register
    }
    
    @Override
    public void update() {
        this.observerState = subject.getState();  // Pull data from subject
        display();
    }
    
    private void display() {
        System.out.println("Observer state: " + observerState);
    }
    
    public void cleanup() {
        subject.removeObserver(this);  // Prevent memory leaks
    }
}

// CLIENT USAGE
public class ObserverDemo {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        
        // Observers self-register in constructor
        ConcreteObserver observer1 = new ConcreteObserver(subject);
        ConcreteObserver observer2 = new ConcreteObserver(subject);
        
        // Change subject state - observers notified automatically
        subject.setState(10);  // Both observers update
        subject.setState(20);  // Both observers update again
        
        // Remove observer
        observer1.cleanup();
        
        subject.setState(30);  // Only observer2 updates
    }
}
```


***

### Template 2: Weather Station Example (Complete)

```java
// OBSERVER INTERFACE
public interface Observer {
    void update();
}

// SUBJECT INTERFACE
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// CONCRETE SUBJECT
public class WeatherStation implements Subject {
    private float temperature;
    private float humidity;
    private float pressure;
    private final List<Observer> observers = new ArrayList<>();
    
    // Getters for pull model
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
    public float getPressure() { return pressure; }
    
    public void setMeasurements(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        System.out.println("[WeatherStation] Measurements updated");
        notifyObservers();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("[WeatherStation] Observer registered: " + 
                         observer.getClass().getSimpleName());
    }
    
    @Override
    public void removeObserver(Observer observer) {
        if (observers.remove(observer)) {
            System.out.println("[WeatherStation] Observer removed: " + 
                             observer.getClass().getSimpleName());
        }
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            try {
                observer.update();
            } catch (Exception e) {
                System.err.println("[ERROR] Observer failed: " + e.getMessage());
            }
        }
    }
}

// CONCRETE OBSERVER 1
public class CurrentConditionsDisplay implements Observer {
    private final WeatherStation weatherStation;
    
    public CurrentConditionsDisplay(WeatherStation station) {
        this.weatherStation = station;
        station.registerObserver(this);
    }
    
    @Override
    public void update() {
        System.out.println("Current Conditions:");
        System.out.println("Temperature: " + weatherStation.getTemperature() + "°C");
        System.out.println("Humidity: " + weatherStation.getHumidity() + "%");
        System.out.println("Pressure: " + weatherStation.getPressure() + " hPa");
        System.out.println("---");
    }
    
    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}

// CONCRETE OBSERVER 2
public class StatisticsDisplay implements Observer {
    private final WeatherStation weatherStation;
    private float minTemp = Float.POSITIVE_INFINITY;
    private float maxTemp = Float.NEGATIVE_INFINITY;
    private float tempSum = 0.0f;
    private int numReadings = 0;
    
    public StatisticsDisplay(WeatherStation station) {
        this.weatherStation = station;
        station.registerObserver(this);
    }
    
    @Override
    public void update() {
        float temp = weatherStation.getTemperature();
        tempSum += temp;
        numReadings++;
        
        if (temp < minTemp) minTemp = temp;
        if (temp > maxTemp) maxTemp = temp;
        
        System.out.println("Temperature Stats:");
        System.out.printf("  Avg: %.1f°C, Max: %.1f°C, Min: %.1f°C%n",
                        tempSum/numReadings, maxTemp, minTemp);
        System.out.println("---");
    }
    
    public void cleanup() {
        weatherStation.removeObserver(this);
    }
}

// USAGE
public class WeatherDemo {
    public static void main(String[] args) {
        WeatherStation station = new WeatherStation();
        
        CurrentConditionsDisplay current = new CurrentConditionsDisplay(station);
        StatisticsDisplay stats = new StatisticsDisplay(station);
        
        station.setMeasurements(25.5f, 65f, 1013.2f);
        station.setMeasurements(28.0f, 70f, 1012.0f);
        
        // Cleanup
        current.cleanup();
        stats.cleanup();
    }
}
```


***

### Template 3: Stock Market Example

```java
// OBSERVER INTERFACE
public interface StockObserver {
    void priceChanged(String symbol, double newPrice);
}

// SUBJECT
public class Stock {
    private final String symbol;
    private double price;
    private final List<StockObserver> observers = new ArrayList<>();
    
    public Stock(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
    }
    
    public void attach(StockObserver observer) {
        observers.add(observer);
    }
    
    public void detach(StockObserver observer) {
        observers.remove(observer);
    }
    
    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }
    
    private void notifyObservers() {
        for (StockObserver observer : observers) {
            try {
                observer.priceChanged(symbol, price);
            } catch (Exception e) {
                System.err.println("Observer notification failed: " + e);
            }
        }
    }
    
    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }
}

// CONCRETE OBSERVERS
public class StockDashboard implements StockObserver {
    @Override
    public void priceChanged(String symbol, double newPrice) {
        System.out.println("[Dashboard] " + symbol + " now at $" + newPrice);
    }
}

public class PriceAlert implements StockObserver {
    private final double threshold;
    
    public PriceAlert(double threshold) {
        this.threshold = threshold;
    }
    
    @Override
    public void priceChanged(String symbol, double newPrice) {
        if (newPrice > threshold) {
            System.out.println("[ALERT] " + symbol + " exceeded $" + threshold + 
                             " (now $" + newPrice + ")");
        }
    }
}

// USAGE
Stock appleStock = new Stock("AAPL", 150.0);
appleStock.attach(new StockDashboard());
appleStock.attach(new PriceAlert(200.0));

appleStock.setPrice(180.0);  // Dashboard updates
appleStock.setPrice(205.0);  // Dashboard updates + Alert triggers
```


***

## 🔑 Key Concepts \& Relationships

### Push vs Pull Models

**Most Important Design Decision!**

#### Push Model (Data Pushed to Observers)

```java
// PUSH MODEL - Subject sends data
public interface Observer {
    void update(float temp, float humidity, float pressure);  // Data as params
}

public class WeatherStation {
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(temperature, humidity, pressure);  // Push all data
        }
    }
}

public class Display implements Observer {
    @Override
    public void update(float temp, float humidity, float pressure) {
        // Data provided, don't need subject reference
        System.out.println("Temp: " + temp);
    }
}
```

**Pros:**

- ✅ Observers don't need subject reference
- ✅ More efficient (no getter calls)
- ✅ Clear what data is available

**Cons:**

- ❌ Subject must know what data observers need
- ❌ All observers get all data (even if not needed)
- ❌ Changing data structure requires changing interface
- ❌ Tight coupling to data format

***

#### Pull Model (Observers Pull Data)

```java
// PULL MODEL - Observers query subject
public interface Observer {
    void update();  // No parameters
}

public class WeatherStation {
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();  // Just notify, no data
        }
    }
    
    // Getters for observers to pull data
    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
}

public class Display implements Observer {
    private final WeatherStation station;  // Need subject reference
    
    @Override
    public void update() {
        float temp = station.getTemperature();  // Pull what you need
        System.out.println("Temp: " + temp);
    }
}
```

**Pros:**

- ✅ More flexible - observers choose what data to get
- ✅ Loose coupling - subject doesn't know what observers need
- ✅ Easy to add new data without changing interface
- ✅ Observers can pull different data

**Cons:**

- ❌ Observers need reference to subject
- ❌ Multiple getter calls (slight overhead)
- ❌ Observers must know available methods

**Recommendation:** **Pull model is generally preferred** for flexibility and maintainability.

***

### Memory Leak Prevention

**Critical Issue: Observers Not Detached**

```java
// MEMORY LEAK SCENARIO
Subject subject = new Subject();
Observer observer = new Observer();
subject.attach(observer);

// Later, try to clean up
observer = null;  // Variable cleared

// Problem: Subject's list still holds reference!
// Observer cannot be garbage collected → MEMORY LEAK
```

**Solution 1: Always Detach**

```java
public void cleanup() {
    subject.removeObserver(this);  // Remove from subject's list
    // Now can be garbage collected
}
```

**Solution 2: WeakReferences (Advanced)**

```java
public class Subject {
    private List<WeakReference<Observer>> observers = new ArrayList<>();
    
    public void attach(Observer observer) {
        observers.add(new WeakReference<>(observer));
    }
    
    public void notifyObservers() {
        // Clean dead references
        observers.removeIf(ref -> ref.get() == null);
        
        for (WeakReference<Observer> ref : observers) {
            Observer obs = ref.get();
            if (obs != null) {
                obs.update();
            }
        }
    }
}
// Observers can be GC'd even if not explicitly detached
```


***

## ⚠️ Common Mistakes \& Solutions

### Mistake 1: No Exception Handling in notifyObservers()

```java
❌ // Wrong - one failure stops all notifications
public void notifyObservers() {
    for (Observer obs : observers) {
        obs.update();  // If throws, loop stops!
    }
}

✅ // Correct - all observers get notified
public void notifyObservers() {
    for (Observer obs : observers) {
        try {
            obs.update();
        } catch (Exception e) {
            System.err.println("Observer failed: " + e.getMessage());
            // Continue with other observers
        }
    }
}
```


***

### Mistake 2: Forgetting to Detach Observers

```java
❌ // Memory leak
public void closeWindow() {
    window = null;  // Observer still registered with subject!
}

✅ // Proper cleanup
public void closeWindow() {
    subject.removeObserver(this);  // Detach first
    window = null;
}
```


***

### Mistake 3: Manual Registration (Two-Step Process)

```java
❌ // Easy to forget registration
Observer obs = new Observer(subject);
subject.registerObserver(obs);  // Separate call - can forget!

✅ // Self-registration in constructor
public Observer(Subject subject) {
    this.subject = subject;
    subject.registerObserver(this);  // Automatic
}
```


***

### Mistake 4: Not Making Subject Reference Final

```java
❌ // Can be reassigned
private WeatherStation station;

public void switchStation(WeatherStation newStation) {
    this.station = newStation;  // Now pulling from wrong subject!
}

✅ // Immutable reference
private final WeatherStation station;
// Cannot reassign, observer committed to one subject
```


***

### Mistake 5: Using forEach Instead of for Loop

```java
❌ // forEach stops on exception
observers.forEach(Observer::update);  // If one throws, stops

✅ // Traditional for loop with try-catch
for (Observer obs : observers) {
    try {
        obs.update();
    } catch (Exception e) { /* handle */ }
}
```


***

## 🆚 Pattern Comparisons

### Observer vs Pub-Sub

| Aspect | Observer Pattern | Pub-Sub Pattern |
| :-- | :-- | :-- |
| **Coupling** | Subject knows observers (direct) | Publisher/subscriber don't know each other |
| **Intermediary** | None | Message broker (Kafka, RabbitMQ) |
| **Communication** | Direct method calls | Indirect via topics/channels |
| **Synchrony** | Usually synchronous | Usually asynchronous |
| **Scope** | Within application | Across applications/services |
| **Type safety** | Compile-time checking | Runtime (string topics) |
| **Example** | GUI event listeners | Microservices messaging |

**Key Difference:** Observer has direct coupling; Pub-Sub decoupled via broker.

***

### Observer vs Mediator

| Aspect | Observer | Mediator |
| :-- | :-- | :-- |
| **Communication** | One-to-many broadcast | Many-to-many coordination |
| **Direction** | One-way (subject → observers) | Bidirectional (peers ↔ mediator) |
| **Awareness** | Subject aware of observers | Peers aware of mediator |
| **Purpose** | Notify dependents of changes | Coordinate complex interactions |
| **Example** | Newsletter subscription | Chat room |


***

### Observer vs Strategy

| Aspect | Observer | Strategy |
| :-- | :-- | :-- |
| **Type** | Behavioral (notification) | Behavioral (algorithm selection) |
| **Relationship** | One-to-many | One-to-one |
| **Purpose** | Maintain consistency | Select algorithm |
| **Notification** | Automatic on state change | Manual invocation |
| **Example** | Event listeners | Payment methods |


***

## 🎯 When to Use Observer Pattern

### ✅ Use When:

1. **One-to-Many Dependencies**
    - One object's state affects multiple objects
    - Don't know how many dependents at compile time
    - Example: Newsletter with dynamic subscribers
2. **Event-Driven Architecture**
    - GUI event handling (button clicks, key presses)
    - Real-time updates (stock prices, weather, feeds)
    - Game events (player actions affecting multiple systems)
3. **Loose Coupling Required**
    - Subject shouldn't know concrete observer classes
    - Observers added/removed dynamically
    - Example: Plugin systems, extensible frameworks
4. **Broadcast Communication**
    - Same message needs to go to multiple recipients
    - Recipients can process independently
    - Example: Logging system with multiple outputs
5. **MVC Pattern**
    - Model notifies multiple views when data changes
    - Views update automatically
    - Foundation of reactive UIs

**Classic Use Cases:**

- GUI frameworks (Swing, JavaFX listeners)
- Model-View-Controller (MVC)
- Event handling systems
- Real-time notifications (chat, feeds)
- Reactive programming (RxJava, Reactor)
- Property change listeners

***

### ❌ Don't Use When:

1. **Simple One-to-One Relationship**
    - Just two objects communicating
    - Direct method call is simpler
    - Observer adds unnecessary complexity
2. **Order-Dependent Notifications**
    - If notification order matters critically
    - Observer doesn't guarantee specific order
    - Use Chain of Responsibility instead
3. **Performance Critical with Many Observers**
    - Thousands of observers = slow notifications
    - Each notification is synchronous
    - Consider async alternatives or batching
4. **Complex Update Coordination**
    - Observers need to coordinate with each other
    - Dependencies between observers
    - Use Mediator pattern instead
5. **Guaranteed Delivery Required**
    - Observer doesn't guarantee delivery
    - If observer fails or removed, misses updates
    - Use message queue with persistence

***

## 🧠 Interview Questions \& Answers

### Q1: Explain Observer pattern in 2 sentences.

**A:** Observer pattern defines a one-to-many dependency where observers register with a subject and are automatically notified when the subject's state changes. It enables event-driven architecture by decoupling the subject from its observers through a common interface.

***

### Q2: What's the difference between push and pull models?

**A:** Push model: subject sends all data as parameters in update() call - efficient but tightly couples observers to data structure. Pull model: subject just calls update() with no parameters; observers query subject for data they need - more flexible and loosely coupled. Pull is generally preferred.

***

### Q3: How do you prevent memory leaks in Observer pattern?

**A:** Always call removeObserver() in cleanup/destroy methods to remove observer from subject's list. Alternatively, use WeakReferences in subject's observer list so observers can be garbage collected even if not explicitly detached. Never rely on just setting observer variable to null.

***

### Q4: Why is exception handling critical in notifyObservers()?

**A:** Without try-catch, if one observer throws exception during update(), the notification loop terminates and subsequent observers never get notified. Wrap each update() call in try-catch to ensure all observers receive notifications even if some fail.

***

### Q5: Observer vs Pub-Sub - what's the key difference?

**A:** Observer pattern has direct coupling - subject holds references to observers and calls their methods directly. Pub-Sub pattern is decoupled via message broker - publisher and subscribers don't know each other, communicate through topics/channels in a broker like Kafka or RabbitMQ.

***

### Q6: Should observers self-register in constructor?

**A:** Yes, best practice is self-registration in constructor. This ensures observers can't forget to register (common bug), reduces client code to one line, and encapsulates registration as internal detail. Alternative (manual registration) is more error-prone.

***

### Q7: Why should subject reference be private final in observers?

**A:** Private for encapsulation (hide internal dependency). Final for immutability (observer committed to one subject for lifetime, can't accidentally reassign). This prevents bugs where observer registered with one subject but pulls data from another.

***

### Q8: Real-world examples of Observer pattern?

**A:**

- **Java Swing/JavaFX** - Button listeners, event handlers
- **Spring Framework** - ApplicationEvent/ApplicationListener
- **Android** - LiveData, Observable fields
- **JavaScript** - addEventListener, event emitters
- **RxJava/Reactor** - Reactive streams (advanced Observer)
- **Stock trading apps** - Price update notifications
- **Social media** - News feed updates

***

### Q9: How to initialize min/max tracking in statistics observer?

**A:** Use Float.POSITIVE_INFINITY for minTemp and Float.NEGATIVE_INFINITY for maxTemp. Any real temperature will be less than positive infinity and greater than negative infinity, so first reading will properly initialize both. Don't use Float.MAX_VALUE/MIN_VALUE (MIN_VALUE is smallest positive, not negative).

***

### Q10: What if observers need to update in specific order?

**A:** Observer pattern doesn't guarantee notification order (uses list insertion order). If order is critical, either:

1. Use priority queue instead of list
2. Assign priority to observers and sort before notifying
3. Consider Chain of Responsibility pattern instead
4. Use separate notification phases

***

## ✅ Best Practices

### 1. Always Use Exception Handling

```java
public void notifyObservers() {
    for (Observer obs : observers) {
        try {
            obs.update();
        } catch (Exception e) {
            logger.error("Observer failed", e);
        }
    }
}
```


### 2. Self-Register in Constructor

```java
public ConcreteObserver(Subject subject) {
    this.subject = subject;
    subject.registerObserver(this);  // Automatic
}
```


### 3. Provide Cleanup Method

```java
public void cleanup() {
    subject.removeObserver(this);
}
```


### 4. Use private final for Subject Reference

```java
private final Subject subject;  // Encapsulated and immutable
```


### 5. Log Registration/Removal

```java
public void registerObserver(Observer obs) {
    observers.add(obs);
    logger.info("Observer registered: {}", obs.getClass().getSimpleName());
}
```


### 6. Null Checks

```java
public void registerObserver(Observer observer) {
    if (observer == null) {
        throw new IllegalArgumentException("Observer cannot be null");
    }
    observers.add(observer);
}
```


### 7. Use Pull Model for Flexibility

```java
// Observer pulls what it needs
public void update() {
    float temp = subject.getTemperature();  // Pull specific data
}
```


### 8. Consider Thread Safety if Needed

```java
private final List<Observer> observers = 
    new CopyOnWriteArrayList<>();  // Thread-safe
```


### 9. Document Notification Timing

```java
/**
 * Sets temperature and notifies all observers immediately.
 * Observers are notified synchronously in registration order.
 */
public void setTemperature(float temp) {
    this.temperature = temp;
    notifyObservers();
}
```


### 10. Avoid Circular Updates

```java
// Prevent infinite loops
private boolean notifying = false;

public void notifyObservers() {
    if (notifying) return;
    notifying = true;
    try {
        for (Observer obs : observers) {
            obs.update();
        }
    } finally {
        notifying = false;
    }
}
```


***

## 🚀 Quick Reference

**Intent:** Define one-to-many dependency; notify dependents automatically when state changes

**Structure:**

- Subject interface (register, remove, notify)
- ConcreteSubject (state + observer list)
- Observer interface (update method)
- ConcreteObserver (implements update, pulls data)

**Key Participants:**

- **Subject** - Knows observers, notifies on state change
- **Observer** - Defines update interface
- **ConcreteSubject** - Stores state, implements notification
- **ConcreteObserver** - Maintains subject reference, responds to updates

**When to Use:**

- One-to-many dependencies
- Event-driven systems
- Loose coupling needed
- Broadcast communication
- MVC pattern

**When NOT to Use:**

- Simple one-to-one relationship
- Order-dependent notifications
- Performance critical (many observers)
- Complex coordination needed

**Benefits:**

- Loose coupling between subject and observers
- Dynamic relationships (add/remove runtime)
- Broadcast communication
- Open/Closed Principle (add observers without modifying subject)

**Trade-offs:**

- Memory leaks if not detached properly
- Unexpected updates (observers don't know when)
- No notification order guarantee
- Can be slow with many observers
- Debugging complexity

**Related Patterns:**

- Pub-Sub (decoupled via broker)
- Mediator (many-to-many vs one-to-many)
- Command (encapsulate events as objects)
- Strategy (one-to-one vs one-to-many)

***

## 📌 Copy-Paste Template

```java
// OBSERVER INTERFACE
public interface Observer {
    void update();
}

// SUBJECT INTERFACE
public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}

// CONCRETE SUBJECT
public class ConcreteSubject implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private int state;
    
    public int getState() { return state; }
    
    public void setState(int state) {
        this.state = state;
        notifyObservers();
    }
    
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            try {
                obs.update();
            } catch (Exception e) {
                System.err.println("Observer failed: " + e);
            }
        }
    }
}

// CONCRETE OBSERVER
public class ConcreteObserver implements Observer {
    private final ConcreteSubject subject;
    
    public ConcreteObserver(ConcreteSubject subject) {
        this.subject = subject;
        subject.registerObserver(this);
    }
    
    @Override
    public void update() {
        int state = subject.getState();
        System.out.println("State: " + state);
    }
    
    public void cleanup() {
        subject.removeObserver(this);
    }
}
```


***

