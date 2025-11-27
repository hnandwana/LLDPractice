***

# **BUILDER PATTERN - REVISION NOTES**


***

## **1. WHAT IS BUILDER PATTERN?**

**Definition:** A creational design pattern that lets you construct complex objects **step by step**, separating the construction process from the object's representation.

**Purpose:**

- Avoid telescoping constructors (too many parameters)
- Create immutable objects with many optional fields
- Provide readable, fluent API for object creation
- Support different representations of the same construction process

**Category:** Creational Design Pattern

***

## **2. THE PROBLEM (Without Builder)**

### **Telescoping Constructor Anti-Pattern:**

```java
public class Computer {
    public Computer(String cpu, String ram) { }
    public Computer(String cpu, String ram, String storage) { }
    public Computer(String cpu, String ram, String storage, String gpu) { }
    public Computer(String cpu, String ram, String storage, String gpu, boolean bluetooth) { }
    // ❌ Too many constructors!
}

// Usage - confusing!
Computer c = new Computer("i7", "16GB", "512GB", "RTX", true, true, false);
// What does each parameter mean? 😵
```


***

## **3. THE SOLUTION (With Builder)**

### **Basic Structure:**

```java
public class Product {
    // Required fields
    private final String requiredField1;
    private final String requiredField2;
    
    // Optional fields
    private final String optionalField1;
    private final boolean optionalField2;
    
    // Private constructor
    private Product(Builder builder) {
        this.requiredField1 = builder.requiredField1;
        this.requiredField2 = builder.requiredField2;
        this.optionalField1 = builder.optionalField1;
        this.optionalField2 = builder.optionalField2;
    }
    
    // Static inner Builder class
    public static class Builder {
        // Required fields
        private final String requiredField1;
        private final String requiredField2;
        
        // Optional fields with defaults
        private String optionalField1 = "default";
        private boolean optionalField2 = false;
        
        // Constructor with required fields only
        public Builder(String requiredField1, String requiredField2) {
            this.requiredField1 = requiredField1;
            this.requiredField2 = requiredField2;
        }
        
        // Methods for optional fields (return this for chaining)
        public Builder optionalField1(String value) {
            this.optionalField1 = value;
            return this;
        }
        
        public Builder optionalField2(boolean value) {
            this.optionalField2 = value;
            return this;
        }
        
        // Build method
        public Product build() {
            return new Product(this);
        }
    }
}

// Usage - much clearer!
Product product = new Product.Builder("required1", "required2")
                      .optionalField1("custom")
                      .optionalField2(true)
                      .build();
```


***

## **4. CORE COMPONENTS**

1. **Product Class** - The complex object being built
2. **Private Constructor** - Takes Builder as parameter
3. **Static Inner Builder Class** - Constructs the Product
4. **Required Fields** - Set in Builder constructor
5. **Optional Fields** - Set via methods with defaults
6. **Method Chaining** - Methods return `this`
7. **build() Method** - Creates and returns the Product

***

## **5. KEY CONCEPTS**

### **A. Immutability**

```java
public class Email {
    private final String to;        // final = immutable
    private final String subject;   // final = immutable
    private final List<String> cc;  // final reference
    
    private Email(EmailBuilder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.cc = List.copyOf(builder.cc);  // Immutable copy!
    }
    
    // Only getters, NO setters!
    public String getTo() { return to; }
}
```

**Why immutable?**

- Thread-safe (no synchronization needed)
- Safe to share across code
- Predictable behavior

***

### **B. Method Chaining (Fluent Interface)**

```java
public Builder header(String key, String value) {
    this.headers.put(key, value);
    return this;  // ← Returns Builder for chaining
}

// Usage:
request.header("A", "1")      // Returns Builder
       .header("B", "2")      // Returns Builder
       .timeout(60)           // Returns Builder
       .build();              // Returns Product
```


***

### **C. Collections Handling**

**Lists:**

```java
public class EmailBuilder {
    private List<String> cc = new ArrayList<>();  // Mutable in Builder
    
    public Builder addCC(String email) {
        this.cc.add(email);
        return this;
    }
    
    public Builder cc(List<String> cc) {
        if (cc != null) {
            this.cc = new ArrayList<>(cc);  // Copy
        }
        return this;
    }
}

// In Product constructor:
this.cc = List.copyOf(builder.cc);  // Immutable!
```

**Maps:**

```java
public class HttpRequestBuilder {
    private Map<String, String> headers = new HashMap<>();  // Mutable
    
    public Builder addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }
    
    public Builder headers(Map<String, String> headers) {
        if (headers != null) {
            this.headers.putAll(headers);  // Merge!
        }
        return this;
    }
}

// In Product constructor:
this.headers = Map.copyOf(builder.headers);  // Immutable!
```


***

### **D. Validation Strategies**

**1. Validate Required Fields in Builder Constructor:**

```java
public Builder(String url, HttpMethod method) {
    if (url == null || url.isEmpty()) {
        throw new IllegalArgumentException("URL required");
    }
    if (method == null) {
        throw new IllegalArgumentException("Method required");
    }
    this.url = url;
    this.method = method;
}
```

**2. Validate Values in Setter Methods (Fail Fast):**

```java
public Builder timeout(int timeout) {
    if (timeout <= 0) {
        throw new IllegalArgumentException("Timeout must be positive");
    }
    this.timeout = timeout;
    return this;
}
```

**3. Complex Validation in build() Method:**

```java
public Product build() {
    // Validate combinations
    if (method == POST && body == null) {
        throw new IllegalStateException("POST requires body");
    }
    return new Product(this);
}
```


***

## **6. IMPLEMENTATION PATTERNS**

### **Pattern A: Simple Builder (Basic)**

```java
public class Pizza {
    private final String size;
    private final String crust;
    
    private Pizza(Builder builder) {
        this.size = builder.size;
        this.crust = builder.crust;
    }
    
    public static class Builder {
        private String size = "Medium";
        private String crust = "Thin";
        
        public Builder size(String size) {
            this.size = size;
            return this;
        }
        
        public Builder crust(String crust) {
            this.crust = crust;
            return this;
        }
        
        public Pizza build() {
            return new Pizza(this);
        }
    }
}
```


***

### **Pattern B: Builder with Required Fields**

```java
public class Email {
    private final String to;      // Required
    private final String subject; // Required
    private final String body;    // Optional
    
    private Email(EmailBuilder builder) { /* ... */ }
    
    public static class EmailBuilder {
        private final String to;      // Required in constructor
        private final String subject; // Required in constructor
        private String body = "";     // Optional with default
        
        public EmailBuilder(String to, String subject) {
            this.to = to;
            this.subject = subject;
        }
        
        public EmailBuilder body(String body) {
            this.body = body;
            return this;
        }
        
        public Email build() {
            return new Email(this);
        }
    }
}
```


***

### **Pattern C: Builder with Collections**

```java
public class HttpRequest {
    private final Map<String, String> headers;
    
    private HttpRequest(Builder builder) {
        this.headers = Map.copyOf(builder.headers);  // Immutable!
    }
    
    public static class Builder {
        private Map<String, String> headers = new HashMap<>();
        
        public Builder addHeader(String key, String value) {
            if (key != null && value != null) {
                this.headers.put(key, value);
            }
            return this;
        }
        
        public Builder headers(Map<String, String> headers) {
            if (headers != null) {
                this.headers.putAll(headers);  // Merge
            }
            return this;
        }
        
        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }
}
```


***

## **7. WHEN TO USE BUILDER PATTERN**

### **Use Builder When:**

✅ Object has **4+ parameters** (especially with many optional)
✅ Need **immutable objects** (thread-safety, safety)
✅ Want **readable, self-documenting code**
✅ Construction is **multi-step or complex**
✅ Need **different representations** of same object
✅ Have **telescoping constructor problem**

### **Don't Use Builder When:**

❌ Object has only **1-2 simple fields**
❌ All fields are **required** (simple constructor is fine)
❌ Object needs to be **mutable**
❌ Simple constructors + setters are sufficient

***

## **8. REAL-WORLD EXAMPLES**

- **StringBuilder** / **StringBuffer** (Java)
- **HttpRequest.Builder** (OkHttp library)
- **AlertDialog.Builder** (Android)
- **ProcessBuilder** (Java)
- **SQL Query Builders** (Hibernate, jOOQ)
- **Email builders** (JavaMail)
- **Test data builders** (testing frameworks)

***

## **9. ADVANTAGES \& DISADVANTAGES**

### **Advantages ✅**

- **Readable Code** - Clear what each parameter means
- **Immutability** - Thread-safe, predictable
- **Flexibility** - Easy to add new optional fields
- **Validation** - Centralized validation logic
- **Default Values** - Easy to manage defaults
- **No Telescoping** - Avoids multiple constructors


### **Disadvantages ❌**

- **More Code** - Builder class adds boilerplate
- **Complexity** - Overkill for simple objects
- **Memory** - Builder object created before Product

***

## **10. COMPARISON WITH OTHER PATTERNS**

| **Aspect** | **Builder** | **Constructor** | **Factory** |
| :-- | :-- | :-- | :-- |
| **Purpose** | Complex object construction | Simple object creation | Object creation logic |
| **Parameters** | Many (especially optional) | Few | Varies |
| **Immutability** | Enforced | Can be enforced | Can be enforced |
| **Readability** | Excellent (fluent) | Poor (many params) | Good |
| **Flexibility** | High | Low | Medium |
| **Use Case** | Complex objects | Simple objects | Object type selection |


***

## **11. COMMON MISTAKES TO AVOID**

❌ **Not making Product immutable** (add setters)
❌ **Not using `final` fields** in Product
❌ **Not copying collections** (shallow copy issues)
❌ **Not validating required fields**
❌ **Forgetting to return `this`** in builder methods
❌ **Making Builder constructor public** when Product constructor is private
❌ **Not handling null in collection methods**

***

## **12. IMMUTABILITY HELPERS**

### **For Lists:**

```java
// In Builder (mutable)
private List<String> items = new ArrayList<>();

// In Product constructor (immutable)
this.items = List.copyOf(builder.items);
```


### **For Maps:**

```java
// In Builder (mutable)
private Map<String, String> headers = new HashMap<>();

// In Product constructor (immutable)
this.headers = Map.copyOf(builder.headers);
```


### **Important Notes:**

- `List.copyOf()` and `Map.copyOf()` create **immutable** collections
- They throw `NullPointerException` if source contains `null`
- They perform **shallow copy** (copies references, not objects)
- Safe for immutable types (String, Integer, etc.)

***

## **13. CODE TEMPLATE**

```java
public class Product {
    // Step 1: Define fields (final for immutability)
    private final String requiredField;
    private final String optionalField;
    private final List<String> listField;
    
    // Step 2: Private constructor taking Builder
    private Product(Builder builder) {
        this.requiredField = builder.requiredField;
        this.optionalField = builder.optionalField;
        this.listField = List.copyOf(builder.listField);
    }
    
    // Step 3: Getters only (no setters!)
    public String getRequiredField() { return requiredField; }
    public String getOptionalField() { return optionalField; }
    public List<String> getListField() { return listField; }
    
    // Step 4: Static inner Builder class
    public static class Builder {
        // Same fields as Product
        private final String requiredField;  // Required
        private String optionalField = "default";  // Optional
        private List<String> listField = new ArrayList<>();  // Collection
        
        // Constructor with required fields
        public Builder(String requiredField) {
            if (requiredField == null || requiredField.isEmpty()) {
                throw new IllegalArgumentException("Required field cannot be null");
            }
            this.requiredField = requiredField;
        }
        
        // Methods for optional fields (return this)
        public Builder optionalField(String value) {
            this.optionalField = value;
            return this;
        }
        
        public Builder addListItem(String item) {
            if (item != null) {
                this.listField.add(item);
            }
            return this;
        }
        
        public Builder listField(List<String> list) {
            if (list != null) {
                this.listField = new ArrayList<>(list);
            }
            return this;
        }
        
        // Build method
        public Product build() {
            // Optional: Additional validation
            return new Product(this);
        }
    }
}
```


***

## **14. INTERVIEW TIPS**

### **Common Questions:**

**Q: Why use Builder instead of constructor with many parameters?**
> "Builder pattern improves readability and handles optional parameters elegantly. With constructors, you'd need telescoping constructors which are hard to read and maintain."

**Q: Why is the Builder class static and inner?**
> "Static inner class can access the private constructor of the outer class while remaining instantiable without an instance of the outer class. This allows Builder to create Product objects while keeping Product's constructor private."

**Q: How does Builder ensure immutability?**
> "By using `final` fields, private constructor, no setters, and defensive copying of collections using `List.copyOf()` and `Map.copyOf()`."

**Q: When would you NOT use Builder pattern?**
> "For simple objects with 1-2 required fields and no optional fields, a simple constructor is sufficient. Builder adds unnecessary complexity in such cases."

**Q: What's the difference between Builder and Factory?**
> "Builder focuses on constructing a single complex object step-by-step with many optional parameters. Factory focuses on creating different types/variants of objects based on input parameters."

***

## **15. KEY TAKEAWAYS**

1. **Builder Pattern = Step-by-step construction of complex objects**
2. **Best for objects with many optional parameters**
3. **Enforces immutability through design**
4. **Static inner class accesses private constructor**
5. **Method chaining creates fluent, readable API**
6. **Required fields in constructor, optional in methods**
7. **Defensive copying for collections (List.copyOf, Map.copyOf)**
8. **Validation in constructor (required), methods (fail-fast), or build() (complex)**
9. **Real-world use: HttpClient, StringBuilder, test builders**
10. **Trade-off: More code but better readability and safety**

***

