***

# **SINGLETON PATTERN - REVISION NOTES**


***

## **1. WHAT IS SINGLETON PATTERN?**

**Definition:** Ensures a class has **only ONE instance** and provides a global point of access to it.

**Purpose:**

- Control object creation for shared resources
- Ensure single instance across application
- Provide global access point

**Category:** Creational Design Pattern

***

## **2. CORE COMPONENTS**

1. **Private Constructor** - Prevents external instantiation
2. **Static Instance Variable** - Holds the single instance
3. **Static getInstance() Method** - Returns the instance

***

## **3. SINGLETON IMPLEMENTATIONS**

### **A. Basic Singleton (NOT Thread-Safe) ❌**

```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Pros:** Simple, lazy loading
**Cons:** NOT thread-safe
**Use:** Only in single-threaded apps

***

### **B. Eager Initialization ✅**

```java
public class Singleton {
    private static final Singleton instance = new Singleton();
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        return instance;
    }
}
```

**Pros:** Simple, thread-safe (JVM handles it)
**Cons:** NOT lazy, instance created even if never used
**Use:** When instance is always needed and lightweight

***

### **C. Synchronized Method ✅**

```java
public class Singleton {
    private static Singleton instance;
    
    private Singleton() {}
    
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Pros:** Thread-safe, lazy loading
**Cons:** Slow (every call is synchronized)
**Use:** When getInstance() is not called frequently

***

### **D. Double-Checked Locking (DCL) ✅✅**

```java
public class Singleton {
    private static volatile Singleton instance;
    
    private Singleton() {}
    
    public static Singleton getInstance() {
        if (instance == null) {                  // Check 1
            synchronized (Singleton.class) {     // Lock
                if (instance == null) {          // Check 2
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**Pros:** Thread-safe, lazy, high performance
**Cons:** Complex, requires `volatile`
**Use:** Production code with frequent getInstance() calls
**⭐ Most common in interviews!**

**Why `volatile`?** Prevents instruction reordering, ensures visibility across threads

***

### **E. Bill Pugh (Initialization-on-demand) ✅✅**

```java
public class Singleton {
    private Singleton() {}
    
    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

**Pros:** Thread-safe, lazy, no synchronization overhead
**Cons:** None (best approach for Java)
**Use:** Recommended for most cases
**⭐ Clean and performant!**

**How it works:** Inner class loads only when getInstance() is called; JVM handles synchronization

***

### **F. Enum Singleton ✅✅**

```java
public enum Singleton {
    INSTANCE;
    
    public void doSomething() {
        // methods here
    }
}

// Usage: Singleton.INSTANCE.doSomething();
```

**Pros:** Simplest, reflection-proof, serialization-proof
**Cons:** NOT lazy, less flexible
**Use:** When you need best protection
**⭐ Joshua Bloch's recommendation (Effective Java)**

***

## **4. COMPARISON TABLE**

| **Approach** | **Thread-Safe?** | **Lazy?** | **Performance** | **Reflection-Safe?** |
| :-- | :-- | :-- | :-- | :-- |
| Basic | ❌ | ✅ | Fast | ❌ |
| Eager | ✅ | ❌ | Fast | ❌ |
| Synchronized Method | ✅ | ✅ | Slow | ❌ |
| **Double-Checked** | ✅ | ✅ | **Fast** | ❌ |
| **Bill Pugh** | ✅ | ✅ | **Fast** | ❌ |
| **Enum** | ✅ | ❌ | Fast | **✅** |


***

## **5. WHEN TO USE SINGLETON?**

### **Common Use Cases:**

- Logger classes
- Configuration settings
- Database connection pools
- Cache managers
- Thread pools
- File system managers
- Print spoolers


### **Real-World Examples:**

- `java.lang.Runtime`
- Spring Framework's beans (default scope)
- Logging frameworks (Log4j, SLF4J)

***

## **6. BREAKING SINGLETON**

### **A. Reflection Attack**

```java
Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
constructor.setAccessible(true);
Singleton s2 = constructor.newInstance();  // Creates another instance!
```

**Prevention:**

```java
private Singleton() {
    if (instance != null) {
        throw new RuntimeException("Use getInstance()!");
    }
}
```

**Best Prevention:** Use Enum Singleton (reflection-proof by design)

***

### **B. Serialization/Deserialization**

```java
// Serialize
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("file.ser"));
oos.writeObject(instance);

// Deserialize creates NEW instance!
ObjectInputStream ois = new ObjectInputStream(new FileInputStream("file.ser"));
Singleton s2 = (Singleton) ois.readObject();  // Different instance!
```

**Prevention:**

```java
protected Object readResolve() {
    return getInstance();  // Return existing instance
}
```


***

## **7. THREAD SAFETY EXPLAINED**

### **Where Synchronization Happens:**

| **Approach** | **Synchronization Location** |
| :-- | :-- |
| Synchronized Method | Entire getInstance() method |
| DCL | Only creation block |
| Bill Pugh | JVM's class loading (automatic) |
| Eager/Enum | JVM's class initialization (automatic) |

**Key Point:** Bill Pugh delegates synchronization to JVM's class loading mechanism - inner class loads only when referenced, and JVM guarantees thread-safe loading.

***

## **8. DECISION FLOWCHART**

```
Choose Singleton Implementation:

Need best protection (reflection/serialization)?
├─ YES → Use Enum Singleton ✅
└─ NO → Need lazy loading?
    ├─ YES → Use Bill Pugh or DCL ✅
    │   └─ Bill Pugh is cleaner, DCL is more common in interviews
    └─ NO → Use Eager Initialization ✅
```


***

## **9. INTERVIEW TIPS**

### **Common Questions:**

**Q: Why is Singleton considered an anti-pattern sometimes?**

- Global state can make testing difficult
- Hidden dependencies
- Can violate Single Responsibility Principle
- Tight coupling

**Q: How do you make Singleton thread-safe?**

- Synchronized method
- Double-checked locking with `volatile`
- Bill Pugh (JVM handles it)
- Enum

**Q: What's the difference between Eager and Lazy initialization?**

- Eager: Instance created at class loading (before any call)
- Lazy: Instance created only when getInstance() is called

**Q: Why use `volatile` in DCL?**

- Prevents instruction reordering
- Ensures memory visibility across threads
- Without it, threads might see partially constructed object

***

## **10. CODE TEMPLATE (Bill Pugh - Recommended)**

```java
public class Singleton {
    // Private constructor
    private Singleton() {
        // Initialization code
    }
    
    // Static inner class (lazy loaded)
    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    // Public access point
    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
    
    // Business methods
    public void doSomething() {
        // Implementation
    }
}
```


***

## **11. REAL IMPLEMENTATION (Your Logger)**

```java
public class Logger {
    private static final DateTimeFormatter formatter = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private Logger() {}
    
    private static class SingletonLoggerHelper {
        private static final Logger loggerInstance = new Logger();
    }
    
    public static Logger getInstance() {
        return SingletonLoggerHelper.loggerInstance;
    }
    
    public void info(String message) {
        log(LogLevel.INFO.name(), message);
    }
    
    public void error(String message) {
        log(LogLevel.ERROR.name(), message);
    }
    
    public void warning(String message) {
        log(LogLevel.WARNING.name(), message);
    }
    
    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[" + timestamp + "] [" + level + "] " + message);
    }
}
```


***

## **12. KEY TAKEAWAYS**

1. **Singleton = One instance per JVM**
2. **Best implementations: Bill Pugh, DCL, Enum**
3. **Thread safety crucial in multi-threaded apps**
4. **`volatile` needed in DCL for memory visibility**
5. **Enum is reflection-proof and serialization-safe**
6. **Bill Pugh uses JVM's class loading for synchronization**
7. **Use when you need controlled access to shared resource**

***

## **13. COMMON MISTAKES TO AVOID**

❌ Forgetting `volatile` in DCL
❌ Using basic Singleton in multi-threaded apps
❌ Not handling reflection attacks when security matters
❌ Overusing Singleton (creates hidden dependencies)
❌ Not considering serialization issues

***
