# 📚 Composite Pattern - Comprehensive Revision Notes


***

## 🎯 Pattern Summary

**Definition:** Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions uniformly.

**Category:** Structural Pattern

**Problem Solved:** Need to work with tree structures where individual objects and groups should be treated the same way.

**Key Insight:** Both Leaf and Composite implement the same interface, allowing uniform treatment through polymorphism and recursion.

***

## 🏗️ Structure \& Participants

### 1. Component (Interface/Abstract Class)

- Declares common interface for Leaf and Composite
- Defines operations like `getPrice()`, `display()`
- May include default `add()/remove()` that throw exceptions


### 2. Leaf

- Represents individual objects with no children
- Implements Component operations
- `add()/remove()` throw `UnsupportedOperationException`


### 3. Composite

- Represents containers that hold Leaf or other Composites
- Implements Component operations **recursively** (delegates to children)
- Manages children with `add()/remove()` methods
- **HAS-A** relationship: `List<Component> children`


### 4. Client

- Works through Component interface
- Doesn't need to distinguish between Leaf and Composite

***

## 💻 Code Templates

### Template 1: Basic Structure

```java
// Component Interface
public interface Component {
    void operation();
    
    // Default implementations for tree operations
    default void add(Component component) {
        throw new UnsupportedOperationException(
            "Cannot add to " + this.getClass().getSimpleName()
        );
    }
    
    default void remove(Component component) {
        throw new UnsupportedOperationException(
            "Cannot remove from " + this.getClass().getSimpleName()
        );
    }
    
    default List<Component> getChildren() {
        throw new UnsupportedOperationException(
            this.getClass().getSimpleName() + " has no children"
        );
    }
}

// Leaf Implementation
@Data
@AllArgsConstructor
public class Leaf implements Component {
    private String name;
    private int value;
    
    @Override
    public void operation() {
        System.out.println("Leaf: " + name + " = " + value);
    }
    
    // add/remove inherited from interface - throw exceptions
}

// Composite Implementation
@Getter
@RequiredArgsConstructor
public class Composite implements Component {
    private final String name;
    private final List<Component> children = new ArrayList<>();
    
    @Override
    public void operation() {
        System.out.println("Composite: " + name);
        // Recursively call operation on all children
        children.forEach(Component::operation);
    }
    
    @Override
    public void add(Component component) {
        if (component == null) {
            throw new IllegalArgumentException("Cannot add null");
        }
        children.add(component);
    }
    
    @Override
    public void remove(Component component) {
        children.remove(component);
    }
    
    @Override
    public List<Component> getChildren() {
        return new ArrayList<>(children);  // Defensive copy
    }
}
```


***

### Template 2: With Type Differentiation (Enum-based)

```java
public enum ComponentType {
    GROUP, CATEGORY, CONTAINER
}

@Getter
@RequiredArgsConstructor
public class TypedComposite implements Component {
    private final String name;
    private final ComponentType type;
    private final List<Component> children = new ArrayList<>();
    
    @Override
    public void operation() {
        String icon = switch (type) {
            case GROUP -> "📦";
            case CATEGORY -> "📁";
            case CONTAINER -> "🗂️";
        };
        System.out.println(icon + " " + name);
        children.forEach(Component::operation);
    }
    
    // add/remove same as Template 1
}
```


***

### Template 3: File System Example

```java
public interface FileSystemComponent {
    long getSize();
    void display(String indent);
    
    default void add(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot add to file");
    }
    
    default void remove(FileSystemComponent component) {
        throw new UnsupportedOperationException("Cannot remove from file");
    }
}

@Data
@AllArgsConstructor
public class File implements FileSystemComponent {
    private String name;
    private long sizeKB;
    
    @Override
    public long getSize() {
        return sizeKB;
    }
    
    @Override
    public void display(String indent) {
        System.out.println(indent + "📄 " + name + " (" + sizeKB + "KB)");
    }
}

@Getter
@RequiredArgsConstructor
public class Folder implements FileSystemComponent {
    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();
    
    @Override
    public long getSize() {
        return children.stream()
                      .mapToLong(FileSystemComponent::getSize)
                      .sum();
    }
    
    @Override
    public void display(String indent) {
        System.out.println(indent + "📁 " + name + " (" + getSize() + "KB)");
        children.forEach(child -> child.display(indent + "  "));
    }
    
    @Override
    public void add(FileSystemComponent component) {
        if (component == null) {
            throw new IllegalArgumentException("Cannot add null component");
        }
        children.add(component);
    }
    
    @Override
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }
}

// Usage
Folder root = new Folder("Documents");
root.add(new File("resume.pdf", 200));
root.add(new File("photo.jpg", 500));

Folder backup = new Folder("Backup");
backup.add(new File("backup.zip", 1000));

root.add(backup);  // Folder in folder!

System.out.println("Total: " + root.getSize() + "KB");  // 1700KB
root.display("");
```


***

## 🔑 Key Concepts \& Relationships

### IS-A Relationships

```
Leaf IS-A Component ✅
Composite IS-A Component ✅
```


### HAS-A Relationships

```
Composite HAS-A List<Component> ✅
```


### Recursion Pattern

```java
// How recursion works in Composite:

composite.getSize()
  ├─> child1.getSize()  // If child1 is Leaf → returns value
  ├─> child2.getSize()  // If child2 is Composite → recursively calls
  │     ├─> grandchild1.getSize()
  │     └─> grandchild2.getSize()
  └─> child3.getSize()
  
// Sum all results
```


***

## ⚠️ Common Mistakes \& Solutions

### Mistake 1: Wrong Collection Type in Composite

```java
❌ private List<Leaf> children;  // TOO SPECIFIC!
✅ private List<Component> children;  // Correct - can hold Leaf OR Composite
```


### Mistake 2: Forgetting Null Checks

```java
❌ public void add(Component c) {
    children.add(c);  // NPE if c is null
}

✅ public void add(Component c) {
    if (c == null) throw new IllegalArgumentException("Null not allowed");
    children.add(c);
}
```


### Mistake 3: Circular

```java
❌ Composite folder1 = new Composite("A");
   Composite folder2 = new Composite("B");
   folder1.add(folder2);
   folder2.add(folder1);  // INFINITE LOOP!
   folder1.operation();  // StackOverflowError

✅ Validate before adding:
   public void add(Component c) {
       if (c == this) throw new IllegalArgumentException("Self-reference");
       // Additional check: traverse parents to prevent cycles
       children.add(c);
   }
```


### Mistake 4: Exposing Mutable Collections

```java
❌ public List<Component> getChildren() {
    return children;  // Direct reference - client can modify!
}

✅ public List<Component> getChildren() {
    return new ArrayList<>(children);  // Defensive copy
}

// OR use unmodifiable:
✅ return Collections.unmodifiableList(children);
```


### Mistake 5: Not Using Recursion Properly

```java
❌ public void display() {
    System.out.println(name);
    children.forEach(c -> System.out.println(c));  // Only prints one level!
}

✅ public void display(String indent) {
    System.out.println(indent + name);
    children.forEach(c -> c.display(indent + "  "));  // Recursion!
}
```


***

## 🆚 Pattern Comparisons

| Aspect | Composite | Decorator | Adapter | Proxy |
| :-- | :-- | :-- | :-- | :-- |
| **Purpose** | Tree structures, uniform treatment | Add behavior dynamically | Convert interfaces | Control access |
| **Relationship** | One-to-many (parent-children) | One-to-one (wrapper chain) | One-to-one | One-to-one |
| **Structure** | Tree hierarchy | Linear chain | Interface conversion | Access control |
| **Focus** | Aggregation \& composition | Behavior enhancement | Interface compatibility | Access management |
| **Recursion** | Yes (traverse children) | No (delegate to wrapped) | No | No |
| **Example** | File/Folder system | Coffee with milk/sugar | Payment gateway integration | Virtual proxy, cache |

### Composite vs Decorator (Detailed)

**Similarities:**

- Both use composition
- Both implement same interface as wrapped objects
- Both use IS-A + HAS-A

**Differences:**


| Composite | Decorator |
| :-- | :-- |
| Manages **multiple** children | Wraps **single** object |
| Focus: **structure** (what contains what) | Focus: **behavior** (what features added) |
| Recursion to aggregate results | Chain delegation to enhance |
| `List<Component>` children | `Component wrapped` (singular) |
| Example: Folder with 10 files | Example: Milk decorator wrapping Coffee |


***

## 🎯 When to Use Composite Pattern

### ✅ Use When:

1. **Tree Structure Exists**
    - File systems (files in folders in folders)
    - Organization charts (employees in departments in divisions)
    - GUI hierarchies (buttons in panels in windows)
2. **Uniform Treatment Needed**
    - Same operations on individual items and groups
    - Client shouldn't distinguish between leaf and composite
    - Example: `delete(item)` works on file OR entire folder
3. **Part-Whole Hierarchies**
    - Objects have clear parent-child relationships
    - Whole is sum of parts (total size = sum of file sizes)
4. **Recursive Operations Natural**
    - Calculate total (sum of all children)
    - Display hierarchy (print self, then children)
    - Search (check self, then search children)

### ❌ Don't Use When:

1. **Flat Structure (No Nesting)**
    - Simple list of products in cart
    - No parent-child relationships
    - Use: Simple `List<Product>`
2. **Type Safety Critical**
    - Need compile-time guarantees
    - Can't afford runtime exceptions
    - Example: Banking - SavingsAccount should NEVER have sub-accounts
3. **Different Operations on Leaf vs Composite**
    - Leaf and Composite need fundamentally different methods
    - Forcing uniform interface creates meaningless methods
    - Better: Separate interfaces or visitor pattern
4. **Shallow Hierarchy (Always 1 Level)**
    - Parent always has direct children, no nesting
    - Example: Category with Products (never Category → Category → Products)
    - Use: Direct composition without pattern overhead
5. **Need to Restrict Child Types**
    - Specific composites should only accept specific children

```
- Example: `<ul>` only contains `<li>`, not any element
```

    - Composite allows ANY child type (too permissive)

***

## 🧠 Interview Questions \& Answers

### Q1: Explain Composite pattern in 2 sentences.

**A:** Composite pattern composes objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects and compositions uniformly through a common interface.

### Q2: What's the difference between Composite and Decorator?

**A:** Composite manages tree structures with multiple children (one-to-many) focusing on aggregation, while Decorator wraps a single object (one-to-one) focusing on adding behavior dynamically. Composite uses recursion to traverse children; Decorator chains wrappers linearly.

### Q3: Why do Leaf nodes have add/remove methods if they throw exceptions?

**A:** To achieve uniform treatment. The pattern sacrifices type safety (compile-time checks) for simplicity - clients can call the same methods on any component without checking types. The trade-off is runtime exceptions for invalid operations.

### Q4: How does recursion work in Composite pattern?

**A:** When a Composite's method is called (e.g., `getSize()`), it iterates through its children and calls the same method on each. If a child is a Leaf, it returns a value. If a child is another Composite, it recursively calls the method on ITS children, creating multi-level recursion until all leaves are reached.

### Q5: What's a major risk with Composite pattern?

**A:** Circular references causing infinite recursion and StackOverflowError. Example: Folder A contains Folder B, and Folder B contains Folder A. Calling any recursive operation creates an infinite loop. Solution: Validate before adding to prevent self-references and cycles.

### Q6: Can a Composite be empty (no children)?

**A:** Yes. An empty Composite typically returns 0 or null for aggregate operations (e.g., empty folder has 0 size). This is valid and allows composites to start empty and have children added dynamically.

### Q7: Should Composite pattern use ArrayList or LinkedList?

**A:** Usually `ArrayList`. Most operations are iteration (traverse all children) where ArrayList performs well. LinkedList is better for frequent add/remove in the middle, which is rare in Composite usage. Use `List<Component>` interface to allow flexibility.

### Q8: Thread safety in Composite?

**A:** Basic Composite is NOT thread-safe. If multiple threads add/remove children concurrently, use:

- `Collections.synchronizedList()` wrapper
- `CopyOnWriteArrayList` for read-heavy scenarios
- External synchronization (lock on Composite)


### Q9: Real-world examples of Composite?

**A:**

- File systems (files and folders)
- GUI frameworks (Swing's JComponent hierarchy, Android Views)
- Organization charts (employees and departments)
- Document structures (paragraphs, sections, chapters)
- Graphics (shapes and groups in Adobe Illustrator)


### Q10: Performance considerations?

**A:**

- Recursion depth: Deep trees may cause StackOverflowError
- Memory: Each Composite holds a list (overhead)
- Iteration: O(n) where n = all nodes in subtree
- Optimization: Cache aggregate values if tree doesn't change often

***

## 🎨 Decision Tree: When to Use Composite

```
Do you have a tree/hierarchical structure?
│
├─ YES → Do parts and whole need same operations?
│   │
│   ├─ YES → Do clients benefit from NOT knowing Leaf vs Composite?
│   │   │
│   │   ├─ YES → ✅ USE COMPOSITE PATTERN
│   │   │
│   │   └─ NO → Consider Visitor or separate interfaces
│   │
│   └─ NO → Different operations → Don't use Composite
│
└─ NO → Flat structure → Simple List/Array is enough
```


***

## ✅ Best Practices

1. **Use Interface or Abstract Class for Component**
    - Interface: When components don't share implementation
    - Abstract Class: When components share common fields/methods
2. **Default Methods for Tree Operations**

```java
default void add(Component c) {
    throw new UnsupportedOperationException();
}
```

Leaf inherits exception; Composite overrides with real implementation.
3. **Final Fields for Immutable Properties**

```java
private final String name;  // Can't change after creation
private final List<Component> children = new ArrayList<>();  // Reference final, content mutable
```

4. **Null Safety**
    - Always validate `component != null` in add()
    - Initialize children list in declaration or constructor
5. **Defensive Copying for getChildren()**

```java
return new ArrayList<>(children);  // Prevents external modification
```

6. **Use Streams for Aggregate Operations**

```java
return children.stream().mapToDouble(Component::getValue).sum();
```

7. **Indent/Prefix for Hierarchical Display**

```java
void display(String indent) {
    System.out.println(indent + name);
    children.forEach(c -> c.display(indent + "  "));
}
```

8. **Type Differentiation with Enums** (Optional)
    - Use enum to show different icons/behavior
    - Avoids creating multiple identical Composite classes
9. **Prevent Circular References**
    - Validate in add() method
    - Traverse up parent chain to detect cycles (advanced)
10. **Document Recursion Depth Limits**
    - Mention max depth if applicable
    - Consider iterative solutions for very deep trees

***

## 🚀 Quick Reference

**Intent:** Tree structures with uniform treatment

**Structure:** Component ← Leaf, Composite (HAS-A List<Component>)

**Key Methods:**

- `operation()` - Leaf: direct, Composite: recursive
- `add(Component)` - Leaf: exception, Composite: add to list
- `remove(Component)` - Leaf: exception, Composite: remove from list

**Recursion:** Composite delegates operations to children

**Trade-off:** Simplicity (uniform) vs Type Safety (compile-time checks)

**Common Use:** File systems, GUI, org charts, graphics

**Avoid When:** Flat structure, type safety critical, different operations needed

***

## 📌 Copy-Paste Template for Quick Use

```java
// Minimal Composite Pattern Template

public interface Component {
    void operation();
    default void add(Component c) { throw new UnsupportedOperationException(); }
    default void remove(Component c) { throw new UnsupportedOperationException(); }
}

@Data
@AllArgsConstructor
class Leaf implements Component {
    private String name;
    public void operation() { System.out.println("Leaf: " + name); }
}

@Getter
@RequiredArgsConstructor
class Composite implements Component {
    private final String name;
    private final List<Component> children = new ArrayList<>();
    
    public void operation() {
        System.out.println("Composite: " + name);
        children.forEach(Component::operation);
    }
    
    public void add(Component c) { children.add(c); }
    public void remove(Component c) { children.remove(c); }
}
```


***

