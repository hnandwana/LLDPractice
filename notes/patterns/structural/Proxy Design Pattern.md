# Proxy Pattern - Revision Notes 📝


***

## Overview

**Proxy Pattern** is a structural design pattern that provides a **substitute or placeholder** for another object. The proxy **controls access** to the original object, allowing you to perform something either **before or after** the request reaches the original object.

**Think of it as:** A security guard or receptionist—you don't access the person/resource directly. The proxy intercepts, may check credentials, log the visit, and then allows (or denies) access.

***

## The Problem Proxy Solves

### Without Proxy (Direct Access ❌)

```java
// Every image loads immediately - expensive!
Image img1 = new HighResImage("photo1.jpg");  // Loads (2 seconds)
Image img2 = new HighResImage("photo2.jpg");  // Loads (2 seconds)
Image img3 = new HighResImage("photo3.jpg");  // Loads (2 seconds)
// 6 seconds wasted even if we never view them!

// No access control
Document doc = new RealDocument("secret.pdf");
doc.delete();  // Anyone can delete! Dangerous!

// No logging
bankAccount.withdraw(1000);  // No audit trail
```

**Problems:**

- ❌ Expensive objects created even when not needed
- ❌ No access control or security
- ❌ No logging or monitoring
- ❌ Can't add behavior without modifying original class


### With Proxy (Controlled Access ✅)

```java
// Virtual Proxy: Lazy loading
Image img = new ProxyImage("photo.jpg");  // Fast! Not loaded yet
img.display();  // Loads NOW (only when needed)

// Protection Proxy: Access control
Document doc = new ProtectionProxy(realDoc, "VIEWER");
doc.view();  // ✅ Allowed
doc.delete();  // ❌ SecurityException!

// Logging Proxy: Audit trail
BankAccount account = new LoggingProxy(realAccount, "user123");
account.withdraw(1000);  // Logs: [2025-11-25 21:15] user123 withdrew $1000
```

**Benefits:**

- ✅ Control when objects are created (lazy loading)
- ✅ Control who can access what (security)
- ✅ Track all operations (audit trail)
- ✅ Add behavior without changing original class

***

## When to Use Proxy Pattern

✅ **Use Proxy When:**

1. **Lazy initialization** - Expensive object creation should be delayed
2. **Access control** - Need to check permissions before allowing operations
3. **Logging/auditing** - Track all method calls for security/debugging
4. **Caching** - Store results to avoid repeated expensive operations
5. **Remote objects** - Represent object in different address space (RPC, web services)
6. **Resource management** - Control access to limited resources
7. **Smart reference** - Add actions when object is accessed (reference counting, locking)

❌ **Don't Use When:**

- Want to add features (use Decorator)
- Want to simplify interface (use Facade)
- Want to convert interface (use Adapter)
- Object is cheap and simple to create

***

## Types of Proxies

### 1. Virtual Proxy (Lazy Loading) 🐌

**Purpose:** Delay expensive object creation until actually needed.

**Use when:** Objects are expensive to create (large files, database connections, images)

```java
interface Image {
    void display();
}

class RealImage implements Image {
    private String filename;
    
    public RealImage(String filename) {
        loadFromDisk(filename);  // Expensive! (2 seconds)
    }
    
    private void loadFromDisk(String filename) {
        System.out.println("Loading: " + filename);
        try { Thread.sleep(2000); } catch (Exception e) {}
    }
    
    public void display() {
        System.out.println("Displaying: " + filename);
    }
}

class ProxyImage implements Image {
    private RealImage realImage;  // null initially!
    private String filename;
    
    public ProxyImage(String filename) {
        this.filename = filename;  // Just store, don't load!
    }
    
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);  // Load on demand!
        }
        realImage.display();
    }
}

// Usage
Image img = new ProxyImage("photo.jpg");  // Fast! (0 seconds)
// ... do other things ...
img.display();  // NOW it loads (2 seconds)
```

**Benefits:**

- ✅ Fast startup (delayed loading)
- ✅ Memory efficient (load only what's needed)
- ✅ Improved performance

***

### 2. Protection Proxy (Access Control) 🔒

**Purpose:** Control access based on permissions/roles.

**Use when:** Need authentication, authorization, role-based access control

```java
interface Document {
    void view();
    void edit(String content);
    void delete();
}

class RealDocument implements Document {
    private String content;
    
    public void view() {
        System.out.println("Viewing: " + content);
    }
    
    public void edit(String newContent) {
        this.content = newContent;
    }
    
    public void delete() {
        this.content = null;
    }
}

class ProtectionProxy implements Document {
    private Document document;
    private String userRole;  // ADMIN, EDITOR, VIEWER
    
    public ProtectionProxy(Document document, String userRole) {
        this.document = document;
        this.userRole = userRole;
    }
    
    public void view() {
        // All roles can view
        document.view();
    }
    
    public void edit(String content) {
        if (userRole.equals("ADMIN") || userRole.equals("EDITOR")) {
            document.edit(content);  // ✅ Allowed
        } else {
            throw new SecurityException("Access denied!");  // ❌ Blocked
        }
    }
    
    public void delete() {
        if (userRole.equals("ADMIN")) {
            document.delete();  // ✅ Allowed
        } else {
            throw new SecurityException("Only ADMIN can delete!");  // ❌ Blocked
        }
    }
}

// Usage
Document adminDoc = new ProtectionProxy(new RealDocument(), "ADMIN");
adminDoc.delete();  // ✅ Works

Document viewerDoc = new ProtectionProxy(new RealDocument(), "VIEWER");
viewerDoc.delete();  // ❌ SecurityException!
```

**Access Matrix:**


| Operation | ADMIN | EDITOR | VIEWER |
| :-- | :-- | :-- | :-- |
| view() | ✅ | ✅ | ✅ |
| edit() | ✅ | ✅ | ❌ |
| delete() | ✅ | ❌ | ❌ |


***

### 3. Logging Proxy (Audit Trail) 📝

**Purpose:** Log all method calls for debugging/auditing.

**Use when:** Need audit trails, security monitoring, debugging

```java
class LoggingProxy implements Document {
    private Document document;
    private String userId;
    
    public LoggingProxy(Document document, String userId) {
        this.document = document;
        this.userId = userId;
    }
    
    public void view() {
        log("VIEW");
        document.view();
    }
    
    public void edit(String content) {
        log("EDIT");
        document.edit(content);
    }
    
    public void delete() {
        log("DELETE");
        document.delete();
    }
    
    private void log(String operation) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println("[" + timestamp + "] User: " + userId + " Operation: " + operation);
    }
}

// Usage
Document doc = new LoggingProxy(new RealDocument(), "user123");
doc.view();    // Logs: [2025-11-25T21:15:30] User: user123 Operation: VIEW
doc.edit("x"); // Logs: [2025-11-25T21:15:31] User: user123 Operation: EDIT
```


***

### 4. Cache Proxy 💾

**Purpose:** Cache results to avoid repeated expensive operations.

**Use when:** Expensive operations (database queries, API calls, computations)

```java
class CacheProxy implements DataService {
    private DataService service;
    private Map<String, CacheEntry> cache = new HashMap<>();
    private long ttl = 5 * 60 * 1000;  // 5 minutes
    
    static class CacheEntry {
        String data;
        long timestamp;
        
        public CacheEntry(String data) {
            this.data = data;
            this.timestamp = System.currentTimeMillis();
        }
        
        public boolean isExpired(long ttl) {
            return System.currentTimeMillis() - timestamp > ttl;
        }
    }
    
    public String getData(String key) {
        // Check cache
        if (cache.containsKey(key)) {
            CacheEntry entry = cache.get(key);
            if (!entry.isExpired(ttl)) {
                System.out.println("Cache HIT: " + key);
                return entry.data;  // Return cached
            } else {
                cache.remove(key);  // Remove expired
            }
        }
        
        // Fetch from real service
        System.out.println("Cache MISS: " + key);
        String data = service.getData(key);
        cache.put(key, new CacheEntry(data));
        return data;
    }
    
    public void invalidate(String key) {
        cache.remove(key);  // Manual invalidation
    }
}
```


***

### 5. Remote Proxy 🌐

**Purpose:** Represent object in different address space (different JVM, server).

**Use when:** Distributed systems, microservices, web services

```java
class RemoteServiceProxy implements Service {
    private String serverUrl;
    private HttpClient client;
    
    public RemoteServiceProxy(String serverUrl) {
        this.serverUrl = serverUrl;
        this.client = HttpClient.newHttpClient();
    }
    
    public String getData(String key) {
        // Make HTTP call to remote server
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(serverUrl + "/data?key=" + key))
            .build();
        
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }
}

// Client thinks it's calling local object, but it's remote!
Service service = new RemoteServiceProxy("https://api.example.com");
String data = service.getData("user:123");  // HTTP call behind the scenes
```


***

## Proxy Pattern Structure

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │ uses
       ↓
┌──────────────┐
│   Subject    │  ← Interface
│  (Document)  │
│              │
│ + operation()│
└──────┬───────┘
       │
   ┌───┴────────┐
   │            │
┌──▼─────────┐ ┌▼────────────┐
│   Proxy    │ │ RealSubject │
│            │ │             │
│ - subject  │ │             │
│ + operation│ │ + operation │
└────────────┘ └─────────────┘
```

**Key Components:**

1. **Subject (Interface):** Defines common interface for Real and Proxy
2. **RealSubject:** The actual object doing the work
3. **Proxy:** Controls access to RealSubject
4. **Client:** Uses Subject interface (doesn't know if Real or Proxy)

***

## Core Implementation Patterns

### Pattern 1: Virtual Proxy (Creator)

```java
// Proxy CREATES the real object
public class VirtualProxy implements Subject {
    private RealSubject realSubject;  // Concrete type!
    private String initData;
    
    public VirtualProxy(String initData) {
        this.initData = initData;  // Just store, don't create yet
    }
    
    public void operation() {
        if (realSubject == null) {
            realSubject = new RealSubject(initData);  // Create on demand
        }
        realSubject.operation();
    }
}
```

**Key:** Uses **concrete type** (RealSubject) because it creates the object.

***

### Pattern 2: Wrapper Proxy (Logging, Protection)

```java
// Proxy WRAPS an existing object
public class WrapperProxy implements Subject {
    private Subject subject;  // Interface type!
    
    public WrapperProxy(Subject subject) {
        this.subject = subject;  // Receives from outside
    }
    
    public void operation() {
        // Do something before
        System.out.println("Before operation");
        
        // Delegate
        subject.operation();
        
        // Do something after
        System.out.println("After operation");
    }
}
```

**Key:** Uses **interface type** (Subject) because it wraps any implementation.

***

### Pattern 3: Chaining Proxies

```java
// Multiple proxies can wrap each other
Subject obj = new RealSubject();
obj = new VirtualProxy("data");      // Lazy loading
obj = new ProtectionProxy(obj, "ADMIN");  // Access control
obj = new LoggingProxy(obj, "user123");   // Logging

obj.operation();
// Flow: Logging → Protection → Virtual → Real
```


***

## Key Characteristic: Virtual Proxy Uses Concrete, Others Use Interface

### Why Virtual Proxy Uses RealSubject (Concrete)

```java
class VirtualDocumentProxy implements Document {
    private RealDocument document;  // Concrete! Not Document interface
    private String filename;
    
    public void view() {
        if (document == null) {
            document = new RealDocument(filename);  // CREATES it!
        }
        document.view();
    }
}
```

**Reason:** Virtual proxy **creates** the object, so it needs to know exact type to instantiate.

### Why Other Proxies Use Subject (Interface)

```java
class LoggingProxy implements Document {
    private Document document;  // Interface! Not RealDocument
    private String userId;
    
    public LoggingProxy(Document document) {  // Receives from outside
        this.document = document;
    }
    
    public void view() {
        log("VIEW");
        document.view();  // Just delegates
    }
}
```

**Reason:** Other proxies **wrap** existing objects, don't care about concrete type.

***

## Pattern Comparisons

### Proxy vs Decorator

| Aspect | Proxy | Decorator |
| :-- | :-- | :-- |
| **Purpose** | Control access | Add features |
| **When behavior added** | Before/after access | Dynamically wrap |
| **Typical use** | Lazy load, security, logging | Add milk to coffee |
| **Stacking** | Usually single proxy | Multiple decorators |
| **Focus** | Access control \& lifecycle | Feature enhancement |

**Example:**

```java
// Proxy: Controls WHEN/HOW to access
Image proxy = new ProxyImage("photo.jpg");  // Lazy load
proxy.display();  // Loads on demand

// Decorator: Adds WHAT features
Coffee coffee = new SimpleCoffee();
coffee = new MilkDecorator(coffee);    // Add milk
coffee = new SugarDecorator(coffee);   // Add sugar
```


***

### Proxy vs Facade

| Aspect | Proxy | Facade |
| :-- | :-- | :-- |
| **Purpose** | Control one object | Simplify many subsystems |
| **Number of objects** | Single subject | Multiple subsystems |
| **Interface** | Same as subject | New simplified interface |
| **Transparency** | Client may not know | Client knows it's facade |


***

### Proxy vs Adapter

| Aspect | Proxy | Adapter |
| :-- | :-- | :-- |
| **Purpose** | Control access | Convert interface |
| **Interface** | Same as subject | Different interfaces |
| **Object creation** | May control creation | Wraps existing incompatible object |


***

## Common Mistakes \& Solutions

### ❌ Mistake 1: Wrapper Proxy Using Concrete Type

```java
// Bad - Can't chain proxies!
class LoggingProxy implements Document {
    private RealDocument document;  // ❌ Concrete type
    
    public LoggingProxy(RealDocument document) {
        this.document = document;
    }
}

// Can't do this:
Document doc = new VirtualDocumentProxy("file.pdf");
doc = new LoggingProxy(doc);  // ❌ Type error! VirtualProxy is not RealDocument
```

**Solution:** Use interface

```java
// Good - Can chain!
class LoggingProxy implements Document {
    private Document document;  // ✅ Interface
    
    public LoggingProxy(Document document) {
        this.document = document;
    }
}

// Now works:
Document doc = new VirtualDocumentProxy("file.pdf");
doc = new LoggingProxy(doc);  // ✅ Works!
```


***

### ❌ Mistake 2: Forgetting to Delegate

```java
// Bad - Only logs, doesn't actually view!
class LoggingProxy implements Document {
    public void view() {
        System.out.println("Logging view");
        // ❌ Forgot to call document.view()!
    }
}
```

**Solution:** Always delegate

```java
// Good - Logs AND views
class LoggingProxy implements Document {
    public void view() {
        System.out.println("Logging view");
        document.view();  // ✅ Delegate!
    }
}
```


***

### ❌ Mistake 3: Virtual Proxy Loading on All Methods

```java
// Bad - Loads even for metadata!
class VirtualProxy implements Document {
    public String getMetadata() {
        if (document == null) {
            document = new RealDocument(filename);  // ❌ Unnecessary load!
        }
        return document.getMetadata();
    }
}
```

**Solution:** Load only when content needed

```java
// Good - Metadata doesn't need loading
class VirtualProxy implements Document {
    public String getMetadata() {
        return "Metadata: " + filename;  // ✅ No load needed!
    }
    
    public void view() {
        if (document == null) {
            document = new RealDocument(filename);  // Load here
        }
        document.view();
    }
}
```


***

### ❌ Mistake 4: No Cache Invalidation

```java
// Bad - Cache never expires!
class CacheProxy {
    private Map<String, String> cache = new HashMap<>();
    
    public String getData(String key) {
        if (cache.containsKey(key)) {
            return cache.get(key);  // ❌ Might be stale!
        }
        // ...
    }
}
```

**Solution:** Add TTL or manual invalidation

```java
// Good - Cache with expiry
class CacheProxy {
    private Map<String, CacheEntry> cache = new HashMap<>();
    
    static class CacheEntry {
        String data;
        long timestamp;
        boolean isExpired(long ttl) {
            return System.currentTimeMillis() - timestamp > ttl;
        }
    }
    
    public String getData(String key) {
        if (cache.containsKey(key) && !cache.get(key).isExpired(ttl)) {
            return cache.get(key).data;  // ✅ Fresh cache
        }
        // ...
    }
    
    public void invalidate(String key) {
        cache.remove(key);  // Manual invalidation
    }
}
```


***

## Testing Proxies

### Testing Virtual Proxy

```java
@Test
public void testLazyLoading() {
    // Verify object not created in constructor
    long start = System.currentTimeMillis();
    Image image = new ProxyImage("photo.jpg");
    long constructorTime = System.currentTimeMillis() - start;
    
    assertTrue(constructorTime < 100);  // Fast construction
    
    // Verify object created on first access
    start = System.currentTimeMillis();
    image.display();  // Should load now
    long displayTime = System.currentTimeMillis() - start;
    
    assertTrue(displayTime >= 2000);  // Took time to load
}
```


### Testing Protection Proxy

```java
@Test
public void testAccessControl() {
    Document doc = new RealDocument();
    Document viewerDoc = new ProtectionProxy(doc, "VIEWER");
    
    // Viewer can view
    viewerDoc.view();  // Should work
    
    // Viewer cannot delete
    assertThrows(SecurityException.class, () -> {
        viewerDoc.delete();  // Should throw
    });
}
```


### Testing Logging Proxy

```java
@Test
public void testLogging() {
    // Redirect System.out to capture logs
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));
    
    Document doc = new LoggingProxy(new RealDocument(), "user123");
    doc.view();
    
    String log = outContent.toString();
    assertTrue(log.contains("user123"));
    assertTrue(log.contains("VIEW"));
}
```


***

## Interview Questions \& Answers

**Q: What is Proxy Pattern?**
A: Structural pattern that provides a substitute for another object to control access to it.

**Q: Types of proxies?**
A: Virtual (lazy loading), Protection (access control), Cache (caching), Logging (audit), Remote (distributed systems).

**Q: Proxy vs Decorator?**
A: Proxy controls access/lifecycle, Decorator adds features. Proxy usually single layer, Decorator stacks multiple.

**Q: Why Virtual Proxy uses concrete type?**
A: Because it creates the object internally, needs to know exact type to instantiate (`new RealSubject()`).

**Q: Why other proxies use interface?**
A: They wrap existing objects, don't care about concrete type. Enables chaining different proxies.

**Q: Can proxies be chained?**
A: Yes! Logging → Protection → Virtual → Real. Each proxy wraps the next.

**Q: When NOT to use Proxy?**
A: When want to add features (use Decorator), simplify interface (use Facade), or object is cheap to create.

***

## Best Practices ✅

1. **Use interface** for wrapped object (except Virtual Proxy)
2. **Virtual Proxy uses concrete** type (creates object)
3. **Always delegate** after control logic
4. **Same interface** as subject (enables polymorphism)
5. **Cache with TTL** or manual invalidation
6. **Log meaningful info** (timestamp, user, operation, result)
7. **Check permissions** before delegation (Protection Proxy)
8. **Load on demand** for expensive methods only (Virtual Proxy)
9. **Chain proxies** for multiple concerns (logging + security + caching)
10. **Test with mocks** for unit testing proxy logic

***

## Quick Reference Cheat Sheet

**One-Line Summary:** Proxy controls access to an object by providing a substitute.

**When to use:** Lazy loading, access control, logging, caching, remote access

**Structure:**

```java
class Proxy implements Subject {
    private Subject subject;  // Interface (or concrete for Virtual)
    
    public void operation() {
        // Control logic (check, log, cache, etc.)
        subject.operation();  // Delegate
    }
}
```

**Key Principle:** Same interface as subject, controls access

**Common Use Cases:** Image lazy loading, document security, API caching, audit trails

***

## Summary

**Proxy Pattern controls access to objects.**

**Key Points:**

1. Same interface as subject (polymorphism)
2. Virtual Proxy: lazy loading with concrete type
3. Protection Proxy: access control with interface type
4. Logging Proxy: audit trail with interface type
5. Cache Proxy: performance optimization
6. Can chain multiple proxies
7. Controls WHEN/HOW to access, not WHAT features to add

**You've mastered:** Document management with Virtual (lazy load), Protection (RBAC), and Logging (audit) proxies! 🎉

***

