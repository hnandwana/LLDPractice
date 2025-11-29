# 📚 Strategy Pattern - Comprehensive Revision Notes


***

## 🎯 Pattern Summary

**Definition:** Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

**Category:** Behavioral Pattern

**Problem Solved:** Eliminates complex conditional logic (if-else/switch) for algorithm selection. Allows adding new algorithms without modifying existing code. Enables runtime algorithm selection.

**Key Insight:** Instead of implementing multiple algorithms in one class with conditionals, extract each algorithm into separate classes implementing a common interface. Client can then select and switch algorithms at runtime.

**Alternative Names:** Policy Pattern

**Core Principle:** "Favor composition over inheritance" - Context HAS-A Strategy (delegates) rather than IS-A multiple algorithm variations (inheritance).

***

## 🏗️ Structure \& Participants

### 1. Strategy (Interface/Abstract Class)

- Declares interface common to all supported algorithms
- Context uses this interface to call algorithm
- Example: `PaymentStrategy`, `SortStrategy`, `ShippingStrategy`


### 2. Concrete Strategy

- Implements Strategy interface with specific algorithm
- Each encapsulates a different behavior/algorithm
- Example: `CreditCardStrategy`, `PayPalStrategy`, `BitcoinStrategy`


### 3. Context

- Maintains reference to Strategy object
- May define interface for Strategy to access its data
- Delegates algorithm execution to Strategy
- Allows switching strategies at runtime
- Example: `ShoppingCart`, `SortContext`, `ShippingCalculator`


### 4. Client

- Creates specific Strategy object
- Passes Strategy to Context (via constructor or setter)
- Can change Strategy at runtime
- Example: User selecting payment method at checkout

***

## 💻 Code Templates

### Template 1: Basic Strategy Pattern

```java
// STRATEGY INTERFACE
public interface Strategy {
    void execute();
}

// CONCRETE STRATEGIES
public class ConcreteStrategyA implements Strategy {
    @Override
    public void execute() {
        System.out.println("Algorithm A implementation");
    }
}

public class ConcreteStrategyB implements Strategy {
    @Override
    public void execute() {
        System.out.println("Algorithm B implementation");
    }
}

public class ConcreteStrategyC implements Strategy {
    @Override
    public void execute() {
        System.out.println("Algorithm C implementation");
    }
}

// CONTEXT
public class Context {
    private Strategy strategy;
    
    // Constructor injection
    public Context(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy cannot be null");
    }
    
    // Setter for runtime switching
    public void setStrategy(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Strategy cannot be null");
    }
    
    // Delegates to strategy
    public void executeStrategy() {
        strategy.execute();
    }
}

// CLIENT USAGE
public class Client {
    public static void main(String[] args) {
        // Create context with initial strategy
        Context context = new Context(new ConcreteStrategyA());
        context.executeStrategy();  // Uses Strategy A
        
        // Switch strategy at runtime
        context.setStrategy(new ConcreteStrategyB());
        context.executeStrategy();  // Uses Strategy B
        
        context.setStrategy(new ConcreteStrategyC());
        context.executeStrategy();  // Uses Strategy C
    }
}
```


***

### Template 2: Payment Processing Example

```java
// STRATEGY INTERFACE
public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentType();
}

// CONCRETE STRATEGIES
public class CreditCardStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final String cvv;
    private final String expiryDate;
    
    public CreditCardStrategy(String cardNumber, String cvv, String expiryDate) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing credit card payment of ₹" + amount);
        // Validate card
        if (!validateCard()) {
            System.out.println("Invalid card details");
            return false;
        }
        // Process payment
        System.out.println("Payment successful via Credit Card ending in " + 
                         cardNumber.substring(cardNumber.length() - 4));
        return true;
    }
    
    @Override
    public String getPaymentType() {
        return "Credit Card";
    }
    
    private boolean validateCard() {
        // Card validation logic
        return cardNumber != null && cvv != null && expiryDate != null;
    }
}

public class PayPalStrategy implements PaymentStrategy {
    private final String email;
    private final String password;
    
    public PayPalStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing PayPal payment of ₹" + amount);
        // Authenticate
        if (!authenticate()) {
            System.out.println("PayPal authentication failed");
            return false;
        }
        // Process payment
        System.out.println("Payment successful via PayPal (" + email + ")");
        return true;
    }
    
    @Override
    public String getPaymentType() {
        return "PayPal";
    }
    
    private boolean authenticate() {
        // PayPal authentication logic
        return email != null && password != null;
    }
}

public class UPIStrategy implements PaymentStrategy {
    private final String upiId;
    
    public UPIStrategy(String upiId) {
        this.upiId = upiId;
    }
    
    @Override
    public boolean pay(double amount) {
        System.out.println("Generating UPI payment request for ₹" + amount);
        System.out.println("Please approve payment on UPI app for: " + upiId);
        // Simulate payment approval
        System.out.println("Payment successful via UPI");
        return true;
    }
    
    @Override
    public String getPaymentType() {
        return "UPI";
    }
}

// CONTEXT
@Getter
public class ShoppingCart {
    private final List<Item> items = new ArrayList<>();
    private PaymentStrategy paymentStrategy;
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = Objects.requireNonNull(strategy, 
            "Payment strategy cannot be null");
    }
    
    public double calculateTotal() {
        return items.stream()
                   .mapToDouble(Item::getPrice)
                   .sum();
    }
    
    public boolean checkout() {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment method not selected");
        }
        
        double total = calculateTotal();
        System.out.println("\n=== Checkout ===");
        System.out.println("Total: ₹" + total);
        System.out.println("Payment method: " + paymentStrategy.getPaymentType());
        
        return paymentStrategy.pay(total);
    }
}

// ITEM CLASS
@Data
@AllArgsConstructor
public class Item {
    private String name;
    private double price;
}

// CLIENT USAGE
public class ECommerceDemo {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new Item("Laptop", 45000));
        cart.addItem(new Item("Mouse", 500));
        
        // Customer selects credit card
        cart.setPaymentStrategy(new CreditCardStrategy(
            "1234-5678-9012-3456", "123", "12/25"));
        cart.checkout();
        
        // Customer changes mind, switches to PayPal
        cart.setPaymentStrategy(new PayPalStrategy(
            "user@example.com", "password"));
        cart.checkout();
        
        // Try UPI
        cart.setPaymentStrategy(new UPIStrategy("user@paytm"));
        cart.checkout();
    }
}
```


***

### Template 3: Sorting Strategy Example

```java
// STRATEGY INTERFACE
public interface SortStrategy {
    void sort(int[] array);
    String getAlgorithmName();
}

// CONCRETE STRATEGIES
public class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Sorting using Bubble Sort");
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }
    
    @Override
    public String getAlgorithmName() {
        return "Bubble Sort";
    }
}

public class QuickSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Sorting using Quick Sort");
        quickSort(array, 0, array.length - 1);
    }
    
    @Override
    public String getAlgorithmName() {
        return "Quick Sort";
    }
    
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
    
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        
        return i + 1;
    }
}

public class MergeSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        System.out.println("Sorting using Merge Sort");
        mergeSort(array, 0, array.length - 1);
    }
    
    @Override
    public String getAlgorithmName() {
        return "Merge Sort";
    }
    
    private void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }
    
    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        
        int[] L = new int[n1];
        int[] R = new int[n2];
        
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);
        
        int i = 0, j = 0, k = left;
        
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
}

// CONTEXT
public class Sorter {
    private SortStrategy strategy;
    
    public Sorter(SortStrategy strategy) {
        this.strategy = strategy;
    }
    
    public void setStrategy(SortStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, 
            "Sort strategy cannot be null");
    }
    
    public void sort(int[] array) {
        if (strategy == null) {
            throw new IllegalStateException("Sort strategy not set");
        }
        
        long startTime = System.nanoTime();
        strategy.sort(array);
        long endTime = System.nanoTime();
        
        System.out.println("Algorithm: " + strategy.getAlgorithmName());
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");
        System.out.println("Sorted array: " + Arrays.toString(array));
    }
}

// SMART STRATEGY SELECTOR (Advanced)
public class SmartSorter extends Sorter {
    public SmartSorter() {
        super(new QuickSortStrategy());  // Default
    }
    
    @Override
    public void sort(int[] array) {
        // Select strategy based on array size
        if (array.length < 10) {
            setStrategy(new BubbleSortStrategy());  // Small arrays
        } else if (array.length < 100) {
            setStrategy(new QuickSortStrategy());   // Medium arrays
        } else {
            setStrategy(new MergeSortStrategy());   // Large arrays
        }
        
        super.sort(array);
    }
}

// CLIENT USAGE
public class SortingDemo {
    public static void main(String[] args) {
        int[] data1 = {64, 34, 25, 12, 22, 11, 90};
        int[] data2 = Arrays.copyOf(data1, data1.length);
        int[] data3 = Arrays.copyOf(data1, data1.length);
        
        // Test different strategies
        Sorter sorter = new Sorter(new BubbleSortStrategy());
        sorter.sort(data1);
        
        System.out.println("\n---");
        sorter.setStrategy(new QuickSortStrategy());
        sorter.sort(data2);
        
        System.out.println("\n---");
        sorter.setStrategy(new MergeSortStrategy());
        sorter.sort(data3);
        
        // Smart sorter (auto-selects strategy)
        System.out.println("\n=== Smart Sorter ===");
        SmartSorter smartSorter = new SmartSorter();
        int[] smallArray = {5, 2, 8, 1};
        smartSorter.sort(smallArray);
    }
}
```


***

### Template 4: Discount Strategy Example

```java
// STRATEGY INTERFACE
public interface DiscountStrategy {
    double applyDiscount(double originalPrice);
    String getDiscountDescription();
}

// CONCRETE STRATEGIES
public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice;
    }
    
    @Override
    public String getDiscountDescription() {
        return "No discount applied";
    }
}

public class NewCustomerDiscountStrategy implements DiscountStrategy {
    private static final double DISCOUNT_PERCENTAGE = 15.0;
    
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - DISCOUNT_PERCENTAGE / 100);
    }
    
    @Override
    public String getDiscountDescription() {
        return "New customer discount: " + DISCOUNT_PERCENTAGE + "% off";
    }
}

public class LoyaltyDiscountStrategy implements DiscountStrategy {
    private static final double DISCOUNT_PERCENTAGE = 20.0;
    
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - DISCOUNT_PERCENTAGE / 100);
    }
    
    @Override
    public String getDiscountDescription() {
        return "Loyalty discount: " + DISCOUNT_PERCENTAGE + "% off";
    }
}

public class SeasonalSaleStrategy implements DiscountStrategy {
    private static final double DISCOUNT_PERCENTAGE = 25.0;
    
    @Override
    public double applyDiscount(double originalPrice) {
        return originalPrice * (1 - DISCOUNT_PERCENTAGE / 100);
    }
    
    @Override
    public String getDiscountDescription() {
        return "Seasonal sale: " + DISCOUNT_PERCENTAGE + "% off";
    }
}

// CONTEXT
@Data
public class PricingContext {
    private DiscountStrategy discountStrategy;
    
    public PricingContext() {
        this.discountStrategy = new NoDiscountStrategy();  // Default
    }
    
    public PricingContext(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }
    
    public double calculateFinalPrice(double originalPrice) {
        if (discountStrategy == null) {
            discountStrategy = new NoDiscountStrategy();
        }
        
        double finalPrice = discountStrategy.applyDiscount(originalPrice);
        
        System.out.println("Original Price: ₹" + originalPrice);
        System.out.println(discountStrategy.getDiscountDescription());
        System.out.println("Final Price: ₹" + finalPrice);
        System.out.println("You saved: ₹" + (originalPrice - finalPrice));
        
        return finalPrice;
    }
}

// CLIENT USAGE
public class DiscountDemo {
    public static void main(String[] args) {
        double productPrice = 1000.0;
        
        System.out.println("=== New Customer ===");
        PricingContext pricing = new PricingContext(new NewCustomerDiscountStrategy());
        pricing.calculateFinalPrice(productPrice);
        
        System.out.println("\n=== Loyalty Customer ===");
        pricing.setDiscountStrategy(new LoyaltyDiscountStrategy());
        pricing.calculateFinalPrice(productPrice);
        
        System.out.println("\n=== Seasonal Sale ===");
        pricing.setDiscountStrategy(new SeasonalSaleStrategy());
        pricing.calculateFinalPrice(productPrice);
        
        System.out.println("\n=== No Discount ===");
        pricing.setDiscountStrategy(new NoDiscountStrategy());
        pricing.calculateFinalPrice(productPrice);
    }
}
```


***

## 🔑 Key Concepts \& Relationships

### Composition over Inheritance

**Without Strategy (Inheritance Explosion):**

```
ShoppingCart
  ├─ CreditCardShoppingCart
  ├─ PayPalShoppingCart
  ├─ BitcoinShoppingCart
  └─ UPIShoppingCart

Problem: Need 4 subclasses for 4 payment types
If add 2 more payments → 6 subclasses total
Hard to change payment at runtime
```

**With Strategy (Composition):**

```
ShoppingCart HAS-A PaymentStrategy
                    ├─ CreditCardStrategy
                    ├─ PayPalStrategy
                    ├─ BitcoinStrategy
                    └─ UPIStrategy

Solution: 1 context + 4 strategies
Add 2 more payments → Just 2 new strategy classes
Easy runtime switching: cart.setPaymentStrategy(...)
```


***

### Delegation Pattern

```java
// Context delegates to Strategy, doesn't implement itself
public class Context {
    private Strategy strategy;
    
    public void executeStrategy() {
        strategy.execute();  // Delegation - Context doesn't know details
    }
}
```

**Key:** Context is responsible for **using** strategy, not **implementing** algorithm.

***

### Strategy Selection Approaches

**1. Client Selects (Most Common):**

```java
// Client creates and injects strategy
PaymentStrategy strategy = new CreditCardStrategy();
cart.setPaymentStrategy(strategy);
```

**2. Factory Pattern:**

```java
public class PaymentStrategyFactory {
    public static PaymentStrategy getStrategy(String type) {
        return switch (type.toUpperCase()) {
            case "CARD" -> new CreditCardStrategy();
            case "PAYPAL" -> new PayPalStrategy();
            case "UPI" -> new UPIStrategy();
            default -> throw new IllegalArgumentException("Unknown payment type");
        };
    }
}

// Usage
cart.setPaymentStrategy(PaymentStrategyFactory.getStrategy("CARD"));
```

**3. Context Auto-Selects (Smart Context):**

```java
public class SmartSorter {
    public void sort(int[] array) {
        if (array.length < 10) {
            setStrategy(new BubbleSortStrategy());  // Auto-select
        } else {
            setStrategy(new QuickSortStrategy());
        }
        strategy.sort(array);
    }
}
```


***

## ⚠️ Common Mistakes \& Solutions

### Mistake 1: Not Using Strategy Interface

```java
❌ // Wrong - Each payment is separate with no common interface
public class CreditCardPayment {
    public void processCard(double amount) { ... }
}

public class PayPalPayment {
    public void handlePayPal(double amount) { ... }
}

// Client must know all types
if (paymentType.equals("CARD")) {
    new CreditCardPayment().processCard(amount);
} else if (paymentType.equals("PAYPAL")) {
    new PayPalPayment().handlePayPal(amount);
}
```

```java
✅ // Correct - Common interface
public interface PaymentStrategy {
    boolean pay(double amount);
}

public class CreditCardStrategy implements PaymentStrategy {
    public boolean pay(double amount) { ... }
}

public class PayPalStrategy implements PaymentStrategy {
    public boolean pay(double amount) { ... }
}

// Client uses interface
PaymentStrategy strategy = getStrategy(paymentType);
strategy.pay(amount);  // Polymorphism!
```


***

### Mistake 2: Strategy with State (Not Stateless)

```java
❌ // Wrong - Strategy holds state
public class SortStrategy {
    private int comparisonCount = 0;  // Shared state!
    
    public void sort(int[] array) {
        comparisonCount++;  // Modifying state
        // Sort logic
    }
}

// Problem: Can't reuse strategy instance safely
SortStrategy strategy = new SortStrategy();
sorter1.setStrategy(strategy);
sorter2.setStrategy(strategy);  // Both share same state!
```

```java
✅ // Correct - Stateless strategy
public class SortStrategy {
    // No instance fields - stateless
    
    public void sort(int[] array) {
        int localComparisons = 0;  // Local variable, not field
        // Sort logic
        return localComparisons;  // Return if needed
    }
}

// Can safely reuse
SortStrategy strategy = new SortStrategy();
sorter1.setStrategy(strategy);  // Safe
sorter2.setStrategy(strategy);  // Safe - no shared state
```


***

### Mistake 3: Context Implementing Algorithm

```java
❌ // Wrong - Context has algorithm logic
public class ShoppingCart {
    private String paymentType;
    
    public boolean checkout() {
        if (paymentType.equals("CARD")) {
            // 50 lines of card processing logic here
        } else if (paymentType.equals("PAYPAL")) {
            // 50 lines of PayPal logic here
        }
        // Violates Strategy pattern!
    }
}
```

```java
✅ // Correct - Context delegates
public class ShoppingCart {
    private PaymentStrategy paymentStrategy;
    
    public boolean checkout() {
        return paymentStrategy.pay(calculateTotal());  // Delegates!
    }
}
```


***

### Mistake 4: No Null Checks

```java
❌ // Wrong - No validation
public class Context {
    private Strategy strategy;
    
    public void execute() {
        strategy.execute();  // NullPointerException if strategy is null!
    }
}
```

```java
✅ // Correct - Validate strategy
public class Context {
    private Strategy strategy;
    
    public Context(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, 
            "Strategy cannot be null");
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, 
            "Strategy cannot be null");
    }
    
    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set");
        }
        strategy.execute();
    }
}
```


***

### Mistake 5: Strategy Knows About Context

```java
❌ // Wrong - Strategy depends on Context
public interface Strategy {
    void execute(Context context);  // Strategy knows Context!
}

public class ConcreteStrategy implements Strategy {
    public void execute(Context context) {
        context.getData();  // Tight coupling!
    }
}
```

```java
✅ // Correct - Strategy independent of Context
public interface Strategy {
    void execute(Data data);  // Strategy receives data, not Context
}

public class ConcreteStrategy implements Strategy {
    public void execute(Data data) {
        // Use data, doesn't know about Context
    }
}

// Context extracts and passes data
public class Context {
    private Strategy strategy;
    private Data data;
    
    public void execute() {
        strategy.execute(data);  // Pass data, not this
    }
}
```


***

### Mistake 6: Creating Strategy Constants Wrong Way

```java
❌ // Wrong - Public mutable
public class Strategies {
    public static SortStrategy BUBBLE = new BubbleSortStrategy();  // Can be reassigned!
}

// Client can do this
Strategies.BUBBLE = new QuickSortStrategy();  // Breaks system!
```

```java
✅ // Correct - Final constants
public class Strategies {
    public static final SortStrategy BUBBLE = new BubbleSortStrategy();
    public static final SortStrategy QUICK = new QuickSortStrategy();
    public static final SortStrategy MERGE = new MergeSortStrategy();
    
    private Strategies() {}  // Prevent instantiation
}

// Cannot reassign
// Strategies.BUBBLE = ...; // Compile error!
```


***

## 🆚 Pattern Comparisons

### Strategy vs State Pattern

**Most Important Comparison!**


| Aspect | Strategy | State |
| :-- | :-- | :-- |
| **Intent** | Select algorithm | Manage state transitions |
| **Who Changes** | Client sets strategy | Object changes own state |
| **Frequency** | Set once or occasionally | Changes frequently |
| **Awareness** | Strategies independent | States know other states |
| **Transitions** | No transitions | State → State transitions |
| **Example** | Payment method selection | Order status workflow |

**Code Comparison:**

**Strategy:**

```java
// Client controls strategy
PaymentStrategy strategy = new CreditCardStrategy();
cart.setPaymentStrategy(strategy);  // Client decides
cart.checkout();  // Uses chosen strategy
// Strategy doesn't change itself
```

**State:**

```java
// Object changes its own state
Order order = new Order();  // State: NEW
order.submit();  // Changes to SUBMITTED state internally
order.process();  // Changes to PROCESSING state internally
order.ship();  // Changes to SHIPPED state internally
// Object manages state machine
```

**Key Difference:** Strategy = client-driven choice. State = object-driven transitions.

***

### Strategy vs Template Method

| Aspect | Strategy | Template Method |
| :-- | :-- | :-- |
| **Mechanism** | Composition (HAS-A) | Inheritance (IS-A) |
| **Flexibility** | Runtime switching | Compile-time binding |
| **Variation** | Entire algorithm | Steps within algorithm |
| **Classes** | Many strategy classes | Few subclasses |
| **When** | Different algorithms | Same algorithm, different steps |

**Code Comparison:**

**Strategy:**

```java
// Different complete algorithms
interface SortStrategy {
    void sort(int[] array);  // Entire algorithm varies
}

class BubbleSort implements SortStrategy { ... }
class QuickSort implements SortStrategy { ... }
```

**Template Method:**

```java
// Same algorithm skeleton, different steps
abstract class DataProcessor {
    public final void process() {  // Template method (fixed)
        readData();      // Step 1
        processData();   // Step 2 - varies
        writeData();     // Step 3
    }
    
    protected abstract void processData();  // Hook - subclasses override
}

class CSVProcessor extends DataProcessor {
    protected void processData() { /* CSV processing */ }
}
```


***

### Strategy vs Bridge

| Aspect | Strategy | Bridge |
| :-- | :-- | :-- |
| **Type** | Behavioral | Structural |
| **Purpose** | Algorithm selection | Decouple abstraction/implementation |
| **Hierarchies** | One (strategies) | Two (abstraction + implementation) |
| **Focus** | Behavior variation | Structure separation |

**When to Use:**

- **Strategy:** Choose different algorithms at runtime
- **Bridge:** Separate what from how (prevent M×N explosion)

***

### Strategy vs Factory

| Aspect | Strategy | Factory |
| :-- | :-- | :-- |
| **Type** | Behavioral | Creational |
| **Purpose** | Algorithm selection/execution | Object creation |
| **Focus** | How objects behave | How objects are created |

**Often Used Together:**

```java
// Factory creates Strategy
PaymentStrategy strategy = PaymentFactory.createStrategy("CARD");
cart.setPaymentStrategy(strategy);  // Strategy executes
```


***

## 🎯 When to Use Strategy Pattern

### ✅ Use When:

1. **Multiple Algorithms for Same Task**
    - Different sorting algorithms
    - Multiple compression methods
    - Various payment processors
    - Different discount calculations
2. **Avoid Complex Conditionals**

```java
// Replace this mess
if (type == A) { /* 50 lines */ }
else if (type == B) { /* 50 lines */ }
else if (type == C) { /* 50 lines */ }

// With this
strategy.execute();
```

3. **Runtime Algorithm Selection**
    - User chooses payment at checkout
    - Admin selects compression based on file
    - System picks sort based on data size
4. **Open/Closed Principle Needed**
    - Add new payment methods without touching existing code
    - Extend with new strategies without modification
5. **Related Classes Differ Only in Behavior**
    - Same structure, different algorithm
    - Example: All payment processors process, but differently
6. **Algorithm Should Be Hidden from Client**
    - Client doesn't need to know implementation details
    - Just call `strategy.execute()`, don't worry how

***

### ❌ Don't Use When:

1. **Only 2-3 Algorithms, Never Change**
    - Simple if-else is clearer
    - Over-engineering
2. **Algorithms Too Different (No Common Interface)**
    - Payment vs Sorting vs Validation - can't make interchangeable
    - Different inputs/outputs
3. **Client Doesn't Care About Selection**
    - Fixed algorithm
    - Use direct implementation
4. **Performance Critical (Overhead Unacceptable)**
    - Strategy adds indirection (minimal overhead)
    - In 99.99% cases, negligible, but in extreme cases consider
5. **Simple One-Time Logic**
    - Lambda or inline implementation sufficient

```java
// For simple cases, lambda is enough
List<Integer> list = Arrays.asList(5, 2, 8);
list.sort((a, b) -> a - b);  // Lambda instead of Strategy class
```


***

## 🧠 Interview Questions \& Answers

### Q1: Explain Strategy pattern in 2 sentences.

**A:** Strategy pattern defines a family of algorithms, encapsulates each one in a separate class implementing a common interface, and makes them interchangeable. It allows selecting and switching algorithms at runtime without modifying the client code.

***

### Q2: What problem does Strategy solve?

**A:** Strategy eliminates complex conditional statements (if-else/switch) for algorithm selection, prevents tight coupling between algorithm and context, allows adding new algorithms without modifying existing code (Open/Closed Principle), and enables runtime algorithm switching.

***

### Q3: What's the difference between Strategy and State patterns?

**A:** Strategy pattern lets client choose which algorithm to use (client-driven, set once or rarely). State pattern allows object to change its behavior when internal state changes (object-driven, frequent transitions). Strategy strategies are independent; State states know about each other and manage transitions.

***

### Q4: Give real-world examples of Strategy pattern.

**A:**

- **Payment processing** - Credit card, PayPal, UPI, Bitcoin strategies
- **Sorting** - Bubble, Quick, Merge sort strategies
- **Compression** - ZIP, RAR, GZIP strategies
- **Routing** - Car, walking, bicycle, public transit strategies
- **Discount** - Seasonal, loyalty, new customer strategies
- **Validation** - Email, phone, password validation strategies

***

### Q5: How does Strategy support Open/Closed Principle?

**A:** To add new algorithm (e.g., ApplePayStrategy), you create new class implementing Strategy interface without modifying existing strategies or Context. System is open for extension (add strategies), closed for modification (don't change existing code).

***

### Q6: Should strategies be stateless or stateful?

**A:** Strategies should be **stateless** (no instance fields) so they can be safely reused and shared. If state is needed, pass it as method parameter or create new strategy instance per use. Stateless strategies can be singleton constants for efficiency.

***

### Q7: Where should validation logic go in Strategy pattern?

**A:** Validation belongs in the strategy itself since each strategy knows its own constraints. Example: OvernightShippingStrategy validates weight limit, FreeShippingStrategy validates order total. This keeps strategy self-contained and follows Single Responsibility Principle.

***

### Q8: Can Context change strategy during execution?

**A:** Yes! Context can change strategy at runtime using `setStrategy()`. This is key benefit - runtime flexibility. Example: User starts checkout with credit card, changes mind to PayPal - just call `cart.setPaymentStrategy(new PayPalStrategy())`.

***

### Q9: How does Strategy differ from Template Method?

**A:** Strategy uses composition (HAS-A) and allows runtime switching of entire algorithms. Template Method uses inheritance (IS-A), defines algorithm skeleton with compile-time steps, and subclasses override specific steps. Strategy = different algorithms; Template Method = same algorithm, different steps.

***

### Q10: What are the trade-offs of Strategy pattern?

**A:**
**Pros:** Eliminates conditionals, easy to extend, runtime flexibility, testable strategies
**Cons:** Increases number of classes (1 interface + N strategies), client must know available strategies, slight indirection overhead (negligible)

***

### Q11: How to optimize Strategy for stateless strategies?

**A:** Create singleton constants instead of new instances:

```java
public class Strategies {
    public static final SortStrategy BUBBLE = new BubbleSortStrategy();
    public static final SortStrategy QUICK = new QuickSortStrategy();
}
// Reuse: sorter.setStrategy(Strategies.QUICK);
```

More memory-efficient and faster than creating new instances.

***

### Q12: Can Strategy pattern be combined with Factory?

**A:** Yes! Factory creates appropriate strategy:

```java
public class StrategyFactory {
    public static PaymentStrategy create(String type) {
        return switch(type) {
            case "CARD" -> new CreditCardStrategy();
            case "PAYPAL" -> new PayPalStrategy();
            default -> throw new IllegalArgumentException();
        };
    }
}
```

Client doesn't need to know concrete strategy classes.

***

### Q13: How does Java 8 simplify Strategy?

**A:** For simple strategies, use lambda expressions:

```java
// Old way
strategy.setDiscount(new TenPercentDiscount());

// Java 8 way
strategy.setDiscount(price -> price * 0.9);  // Lambda as strategy

// Method reference
list.sort(Comparator.naturalOrder());  // Method ref as strategy
```

Eliminates need for separate strategy classes for simple algorithms.

***

### Q14: What's the relationship between Context and Strategy?

**A:** Context maintains reference to Strategy and delegates algorithm execution to it. Context doesn't implement algorithm - it just calls `strategy.execute()`. This is delegation pattern. Context is responsible for **using** strategy, not **implementing** algorithm.

***

### Q15: When would you use Strategy over simple if-else?

**A:** Use Strategy when:

- More than 3-4 algorithms
- Algorithms might change/extend frequently
- Algorithm logic is complex (not 1-2 lines)
- Need runtime switching
- Want testable algorithms independently

Use if-else when:

- Only 2-3 simple algorithms
- Logic is 1-2 lines
- Never changes
- No need for extensibility

***

## ✅ Best Practices

### 1. Make Strategies Stateless

```java
✅ // Stateless - can be reused
public class SortStrategy {
    public void sort(int[] array) {
        // No instance fields, uses only parameters
    }
}

❌ // Stateful - can't be safely reused
public class SortStrategy {
    private int counter;  // State!
    public void sort(int[] array) {
        counter++;  // Modifying shared state
    }
}
```


***

### 2. Use Null Checks

```java
public class Context {
    private Strategy strategy;
    
    public Context(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }
}
```


***

### 3. Strategy Interface Should Be Minimal

```java
✅ // Clean, focused interface
public interface PaymentStrategy {
    boolean pay(double amount);
}

❌ // Too many methods - God interface
public interface PaymentStrategy {
    boolean pay(double amount);
    void refund(double amount);
    void validateCard();
    void sendReceipt();
    void logTransaction();
    // Too many responsibilities!
}
```


***

### 4. Use Constants for Stateless Strategies

```java
public class Strategies {
    public static final PaymentStrategy CARD = new CreditCardStrategy();
    public static final PaymentStrategy PAYPAL = new PayPalStrategy();
    public static final PaymentStrategy UPI = new UPIStrategy();
    
    private Strategies() {}
}
```


***

### 5. Context Should Be Strategy-Agnostic

```java
✅ // Context doesn't know which strategy
public class Context {
    private Strategy strategy;
    
    public void execute() {
        strategy.execute();  // Doesn't care which strategy
    }
}

❌ // Context knows specific strategies
public class Context {
    private Strategy strategy;
    
    public void execute() {
        if (strategy instanceof ConcreteStrategyA) {
            // Special handling - breaks pattern!
        }
        strategy.execute();
    }
}
```


***

### 6. Document Strategy Selection Criteria

```java
/**
 * Selects sort strategy based on array size:
 * - Small (<10): Bubble Sort (simple)
 * - Medium (10-100): Quick Sort (fast average)
 * - Large (>100): Merge Sort (stable, O(n log n) worst case)
 */
public void sort(int[] array) {
    if (array.length < 10) {
        setStrategy(Strategies.BUBBLE);
    } else if (array.length < 100) {
        setStrategy(Strategies.QUICK);
    } else {
        setStrategy(Strategies.MERGE);
    }
    strategy.sort(array);
}
```


***

### 7. Use Builder for Complex Strategies

```java
public class CreditCardStrategy implements PaymentStrategy {
    private final String cardNumber;
    private final String cvv;
    private final String expiry;
    
    // Use Builder for readability
    public static class Builder {
        private String cardNumber;
        private String cvv;
        private String expiry;
        
        public Builder cardNumber(String num) {
            this.cardNumber = num;
            return this;
        }
        
        public Builder cvv(String cvv) {
            this.cvv = cvv;
            return this;
        }
        
        public Builder expiry(String exp) {
            this.expiry = exp;
            return this;
        }
        
        public CreditCardStrategy build() {
            return new CreditCardStrategy(cardNumber, cvv, expiry);
        }
    }
}

// Usage
PaymentStrategy strategy = new CreditCardStrategy.Builder()
    .cardNumber("1234-5678-9012-3456")
    .cvv("123")
    .expiry("12/25")
    .build();
```


***

### 8. Consistent Naming Convention

```java
// Strategy Interface: [Action]Strategy
public interface PaymentStrategy { }
public interface SortStrategy { }
public interface DiscountStrategy { }

// Concrete Strategies: [Type][Action]Strategy
public class CreditCardPaymentStrategy { }
public class QuickSortStrategy { }
public class SeasonalDiscountStrategy { }

// Context: [Domain]Context or [Domain]Manager
public class PaymentContext { }
public class SortContext { }
public class PricingContext { }
```


***

### 9. Throw Meaningful Exceptions

```java
public class Context {
    public void execute() {
        if (strategy == null) {
            throw new IllegalStateException(
                "Strategy not set. Call setStrategy() before execute()");
        }
        strategy.execute();
    }
}
```


***

### 10. Test Strategies Independently

```java
@Test
public void testCreditCardStrategy() {
    PaymentStrategy strategy = new CreditCardStrategy("1234", "123", "12/25");
    assertTrue(strategy.pay(100));
    // Test in isolation, no need for Context
}

@Test
public void testPayPalStrategy() {
    PaymentStrategy strategy = new PayPalStrategy("user@test.com", "pass");
    assertTrue(strategy.pay(100));
}
```


***

## 🚀 Quick Reference

**Intent:** Define family of algorithms, encapsulate, make interchangeable

**Structure:**

- Strategy interface (declares algorithm method)
- Concrete Strategies (implement algorithm)
- Context (maintains Strategy reference, delegates)
- Client (creates Strategy, passes to Context)

**Key Participants:**

- **Strategy** - Common interface
- **ConcreteStrategy** - Specific algorithm
- **Context** - Uses Strategy
- **Client** - Selects Strategy

**When to Use:**

- Multiple algorithms for same task
- Avoid complex if-else
- Runtime algorithm selection
- Open/Closed Principle needed

**When NOT to Use:**

- Few unchanging algorithms
- No common interface possible
- Simple one-time logic

**Benefits:**

- Eliminates conditionals
- Easy to extend (add strategies)
- Runtime flexibility
- Testable algorithms

**Trade-offs:**

- More classes (1 interface + N strategies)
- Client must know strategies
- Slight indirection

**Related Patterns:**

- State (object changes itself vs client selects)
- Template Method (inheritance vs composition)
- Bridge (structural vs behavioral)
- Factory (creates strategies)

***

## 📌 Copy-Paste Template for Quick Use

```java
// STRATEGY INTERFACE
public interface Strategy {
    void execute();
}

// CONCRETE STRATEGY
public class ConcreteStrategyA implements Strategy {
    @Override
    public void execute() {
        System.out.println("Algorithm A");
    }
}

// CONTEXT
public class Context {
    private Strategy strategy;
    
    public Context(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }
    
    public void setStrategy(Strategy strategy) {
        this.strategy = Objects.requireNonNull(strategy);
    }
    
    public void executeStrategy() {
        strategy.execute();
    }
}

// CLIENT
Context context = new Context(new ConcreteStrategyA());
context.executeStrategy();
context.setStrategy(new ConcreteStrategyB());
context.executeStrategy();
```


***
