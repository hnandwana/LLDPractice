# 📚 Flyweight Pattern - Comprehensive Revision Notes


***

## 🎯 Pattern Summary

**Definition:** Minimize memory usage by sharing as much data as possible among similar objects. Use sharing to support large numbers of fine-grained objects efficiently.

**Category:** Structural Pattern

**Problem Solved:** Too many objects consuming excessive memory when most of their state can be shared. Prevents memory exhaustion when creating thousands or millions of similar objects.

**Key Insight:** Split object state into **intrinsic** (shared, immutable, stored in flyweight) and **extrinsic** (unique, context-dependent, stored externally or passed as parameters). Create few flyweight objects and reuse them with different extrinsic state.

**Alternative Names:** Cache pattern (in some contexts)

**Core Principle:** "Share what you can, pass what you must"

***

## 🏗️ Structure \& Participants

### 1. Flyweight (Interface/Abstract Class)

- Declares methods that accept extrinsic state as parameters
- Defines operations that flyweights can perform
- Example: `ParticleFlyweight`, `Character`, `TreeType`


### 2. Concrete Flyweight

- Implements Flyweight interface
- Stores intrinsic state (shared, immutable)
- **Must be immutable** - all fields final, no setters
- Example: `ParticleType`, `CharacterGlyph`, `TreeModel`


### 3. Flyweight Factory

- Creates and manages pool of flyweight objects
- Ensures flyweights are shared properly (uses cache/pool)
- Returns existing flyweight if already created
- Tracks statistics (optional but useful)
- Example: `ParticleFactory`, `CharacterFactory`, `TreeFactory`


### 4. Client

- Maintains or computes extrinsic state
- Stores reference to flyweight
- Passes extrinsic state to flyweight methods
- Example: `Particle`, `TextDocument`, `Tree`

***

## 💻 Code Templates

### Template 1: Basic Flyweight Structure

```java
// FLYWEIGHT INTERFACE
public interface Flyweight {
    void operation(String extrinsicState);  // Extrinsic passed as parameter
}

// CONCRETE FLYWEIGHT - Stores intrinsic state
@Getter
@AllArgsConstructor
public class ConcreteFlyweight implements Flyweight {
    // INTRINSIC STATE - Shared, immutable
    private final String sharedData;
    private final int sharedValue;
    
    @Override
    public void operation(String extrinsicState) {
        System.out.println("Intrinsic: " + sharedData + 
                         " | Extrinsic: " + extrinsicState);
    }
}

// FLYWEIGHT FACTORY - Manages pool
public class FlyweightFactory {
    private final Map<String, Flyweight> flyweights = new HashMap<>();
    
    public Flyweight getFlyweight(String key) {
        Flyweight flyweight = flyweights.get(key);
        
        if (flyweight == null) {
            // Create new flyweight only if doesn't exist
            flyweight = new ConcreteFlyweight(key, 100);
            flyweights.put(key, flyweight);
            System.out.println("Created new flyweight: " + key);
        } else {
            System.out.println("Reusing flyweight: " + key);
        }
        
        return flyweight;
    }
    
    public int getFlyweightCount() {
        return flyweights.size();
    }
}

// CLIENT - Stores extrinsic state
@AllArgsConstructor
public class Client {
    private final Flyweight flyweight;  // Reference to shared object
    private final String uniqueData;     // Extrinsic state
    
    public void execute() {
        flyweight.operation(uniqueData);  // Pass extrinsic state
    }
}

// USAGE
public class FlyweightDemo {
    public static void main(String[] args) {
        FlyweightFactory factory = new FlyweightFactory();
        
        // Create multiple clients sharing flyweights
        Flyweight fw1 = factory.getFlyweight("TypeA");  // Creates new
        Client c1 = new Client(fw1, "Data1");
        
        Flyweight fw2 = factory.getFlyweight("TypeA");  // Reuses existing
        Client c2 = new Client(fw2, "Data2");
        
        Flyweight fw3 = factory.getFlyweight("TypeB");  // Creates new
        Client c3 = new Client(fw3, "Data3");
        
        c1.execute();  // TypeA with Data1
        c2.execute();  // TypeA with Data2 (same flyweight!)
        c3.execute();  // TypeB with Data3
        
        System.out.println("Total flyweights: " + factory.getFlyweightCount());  // 2
    }
}
```


***

### Template 2: Text Editor Character Example

```java
// FLYWEIGHT INTERFACE
public interface CharacterFlyweight {
    void render(int row, int column);
}

// CONCRETE FLYWEIGHT
@Getter
@AllArgsConstructor
public class Character implements CharacterFlyweight {
    // INTRINSIC STATE - Same for all instances of this character
    private final char symbol;
    private final String fontFamily;
    private final int fontSize;
    private final String color;
    
    @Override
    public void render(int row, int column) {
        System.out.println("'" + symbol + "' at (" + row + "," + column + 
                         ") | Font: " + fontFamily + " " + fontSize + 
                         "pt | Color: " + color);
    }
}

// FLYWEIGHT FACTORY
public class CharacterFactory {
    private final Map<String, CharacterFlyweight> characters = new HashMap<>();
    
    public CharacterFlyweight getCharacter(char symbol, String font, 
                                          int size, String color) {
        String key = symbol + "-" + font + "-" + size + "-" + color;
        
        CharacterFlyweight character = characters.get(key);
        if (character == null) {
            character = new Character(symbol, font, size, color);
            characters.put(key, character);
        }
        
        return character;
    }
    
    public int getCharacterCount() {
        return characters.size();
    }
}

// CLIENT - Text Document
public class TextDocument {
    private final List<CharacterFlyweight> characters = new ArrayList<>();
    private final List<Integer> rows = new ArrayList<>();
    private final List<Integer> columns = new ArrayList<>();
    private final CharacterFactory factory = new CharacterFactory();
    
    public void insertCharacter(char symbol, String font, int size, 
                               String color, int row, int column) {
        CharacterFlyweight character = factory.getCharacter(symbol, font, size, color);
        characters.add(character);
        rows.add(row);
        columns.add(column);
    }
    
    public void render() {
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).render(rows.get(i), columns.get(i));
        }
    }
    
    public void printStatistics() {
        System.out.println("Total characters in document: " + characters.size());
        System.out.println("Unique character types: " + factory.getCharacterCount());
        System.out.println("Memory saved by sharing: " + 
            (characters.size() - factory.getCharacterCount()) + " objects");
    }
}

// USAGE
public class TextEditorDemo {
    public static void main(String[] args) {
        TextDocument doc = new TextDocument();
        
        // Type "HELLO" - each letter reuses flyweight
        doc.insertCharacter('H', "Arial", 12, "Black", 0, 0);
        doc.insertCharacter('E', "Arial", 12, "Black", 0, 1);
        doc.insertCharacter('L', "Arial", 12, "Black", 0, 2);
        doc.insertCharacter('L', "Arial", 12, "Black", 0, 3);  // Reuses 'L'
        doc.insertCharacter('O', "Arial", 12, "Black", 0, 4);
        
        doc.render();
        doc.printStatistics();
        // Output:
        // Total characters: 5
        // Unique types: 4 (H, E, L, O)
        // Memory saved: 1 object (second 'L' reused)
    }
}
```


***

### Template 3: Game Tree Rendering Example

```java
// FLYWEIGHT
@Getter
@AllArgsConstructor
public class TreeType {
    // INTRINSIC STATE
    private final String name;
    private final String texture;
    private final String color;
    
    public void render(int x, int y, int age, int health) {
        System.out.println(name + " tree at (" + x + "," + y + 
                         ") | Texture: " + texture + " | Color: " + color +
                         " | Age: " + age + " | Health: " + health + "%");
    }
}

// FLYWEIGHT FACTORY
public class TreeFactory {
    private final Map<String, TreeType> treeTypes = new HashMap<>();
    
    public TreeType getTreeType(String name, String texture, String color) {
        String key = name + "-" + texture + "-" + color;
        
        TreeType type = treeTypes.get(key);
        if (type == null) {
            type = new TreeType(name, texture, color);
            treeTypes.put(key, type);
            System.out.println("Creating new tree type: " + name);
        }
        
        return type;
    }
    
    public void printMemoryStats(int totalTrees) {
        int intrinsicBytes = 100;  // Texture, model, color data
        int extrinsicBytes = 16;   // Position, age, health
        
        long withoutFlyweight = totalTrees * (intrinsicBytes + extrinsicBytes);
        long withFlyweight = (treeTypes.size() * intrinsicBytes) + 
                            (totalTrees * extrinsicBytes);
        long saved = withoutFlyweight - withFlyweight;
        
        System.out.println("\n=== Memory Statistics ===");
        System.out.println("Total trees: " + totalTrees);
        System.out.println("Tree types: " + treeTypes.size());
        System.out.println("Without Flyweight: " + (withoutFlyweight / 1024) + " KB");
        System.out.println("With Flyweight: " + (withFlyweight / 1024) + " KB");
        System.out.println("Saved: " + (saved / 1024) + " KB (" + 
                         (saved * 100 / withoutFlyweight) + "%)");
    }
}

// CLIENT - Individual tree
@Getter
@AllArgsConstructor
public class Tree {
    // EXTRINSIC STATE
    private final int x;
    private final int y;
    private final int age;
    private final int health;
    
    // REFERENCE TO FLYWEIGHT
    private final TreeType type;
    
    public void render() {
        type.render(x, y, age, health);
    }
}

// FOREST (Collection of trees)
public class Forest {
    private final List<Tree> trees = new ArrayList<>();
    private final TreeFactory factory = new TreeFactory();
    
    public void plantTree(int x, int y, int age, int health,
                         String name, String texture, String color) {
        TreeType type = factory.getTreeType(name, texture, color);
        Tree tree = new Tree(x, y, age, health, type);
        trees.add(tree);
    }
    
    public void render() {
        for (Tree tree : trees) {
            tree.render();
        }
    }
    
    public void printStats() {
        factory.printMemoryStats(trees.size());
    }
}

// USAGE
public class ForestDemo {
    public static void main(String[] args) {
        Forest forest = new Forest();
        
        // Plant 1 million oak trees
        for (int i = 0; i < 1000000; i++) {
            forest.plantTree(
                (int)(Math.random() * 10000),  // x
                (int)(Math.random() * 10000),  // y
                (int)(Math.random() * 100),    // age
                (int)(Math.random() * 100),    // health
                "Oak", "oak.png", "Brown"      // Same for all oaks
            );
        }
        
        // Plant 500,000 pine trees
        for (int i = 0; i < 500000; i++) {
            forest.plantTree(
                (int)(Math.random() * 10000), 
                (int)(Math.random() * 10000),
                (int)(Math.random() * 100),
                (int)(Math.random() * 100),
                "Pine", "pine.png", "Green"
            );
        }
        
        forest.printStats();
        // Output:
        // Total trees: 1,500,000
        // Tree types: 2 (Oak, Pine)
        // Without Flyweight: 165 MB
        // With Flyweight: 23 MB
        // Saved: 142 MB (86%)
    }
}
```


***

## 🔑 Key Concepts \& Relationships

### Intrinsic vs Extrinsic State

| Aspect | Intrinsic State | Extrinsic State |
| :-- | :-- | :-- |
| **Storage** | Inside flyweight object | Outside flyweight (client or parameter) |
| **Sharing** | Shared among many objects | Unique per context |
| **Mutability** | **Must be immutable** | Can be mutable |
| **Context** | Context-independent | Context-dependent |
| **Lifecycle** | Created once, lives forever | Changes frequently |
| **Examples** | Font, texture, color, model | Position, velocity, age, health |

### Mathematical Definition

For N objects with shared properties:

```
Traditional approach:
Memory = N × (Intrinsic + Extrinsic)

Flyweight approach:
Memory = (U × Intrinsic) + (N × Extrinsic)

Where:
N = Total number of objects
U = Unique intrinsic state combinations (U << N)
Savings = N × Intrinsic - U × Intrinsic
        = (N - U) × Intrinsic
```

**Example:**

- 100,000 characters
- 100 unique character types
- Intrinsic = 50 bytes, Extrinsic = 8 bytes

```
Without: 100,000 × 58 = 5,800,000 bytes
With: (100 × 50) + (100,000 × 8) = 805,000 bytes
Saved: 4,995,000 bytes (86% reduction)
```


***

## ⚠️ Common Mistakes \& Solutions

### Mistake 1: Storing Extrinsic State in Flyweight

```java
❌ // Wrong - Position is extrinsic!
@Data  // Has setters!
public class Character implements CharacterFlyweight {
    private final char symbol;  // Intrinsic
    private int row;            // ❌ Extrinsic in flyweight!
    private int column;         // ❌ Extrinsic in flyweight!
    
    public void render() {
        System.out.println(symbol + " at " + row + "," + column);
    }
}

// Problem: All clients sharing this flyweight see same position!
```

```java
✅ // Correct - Pass extrinsic as parameter
@Getter
@AllArgsConstructor
public class Character implements CharacterFlyweight {
    private final char symbol;  // Intrinsic only
    
    public void render(int row, int column) {  // Extrinsic passed in
        System.out.println(symbol + " at " + row + "," + column);
    }
}

// Client stores extrinsic state
public class CharacterInstance {
    private final CharacterFlyweight character;  // Flyweight reference
    private final int row;                       // Extrinsic
    private final int column;                    // Extrinsic
}
```


***

### Mistake 2: Mutable Flyweight

```java
❌ // Wrong - Has setters!
@Data  // Generates setters
public class TreeType {
    private String texture;  // Not final!
    private String color;    // Not final!
}

// Problem:
TreeType oak = factory.getTreeType("Oak");
oak.setColor("Red");  // Changes ALL oaks to red!
```

```java
✅ // Correct - Immutable
@Getter  // Only getters
@AllArgsConstructor
public class TreeType {
    private final String texture;  // Final
    private final String color;    // Final
    // No setters!
}
```


***

### Mistake 3: Not Using Factory (Creating Flyweights Directly)

```java
❌ // Wrong - Creates duplicate flyweights
TreeType oak1 = new TreeType("Oak", "oak.png", "Brown");
TreeType oak2 = new TreeType("Oak", "oak.png", "Brown");  // Duplicate!
// oak1 != oak2, no sharing!
```

```java
✅ // Correct - Factory ensures sharing
TreeFactory factory = new TreeFactory();
TreeType oak1 = factory.getTreeType("Oak", "oak.png", "Brown");
TreeType oak2 = factory.getTreeType("Oak", "oak.png", "Brown");
// oak1 == oak2, shared object!
```


***

### Mistake 4: Incorrect Key Generation

```java
❌ // Wrong - Objects as keys (equals/hashCode issues)
public class TreeType {
    private String name;
    // No equals/hashCode override
}

Map<TreeType, Integer> map = new HashMap<>();
TreeType oak1 = new TreeType("Oak");
TreeType oak2 = new TreeType("Oak");
map.put(oak1, 1);
map.get(oak2);  // Returns null! Different objects
```

```java
✅ // Correct - String key or proper equals/hashCode
private String createKey(String name, String texture, String color) {
    return name + "-" + texture + "-" + color;
}

Map<String, TreeType> flyweights = new HashMap<>();
String key1 = createKey("Oak", "oak.png", "Brown");
String key2 = createKey("Oak", "oak.png", "Brown");
// key1.equals(key2) → true, correct lookup!
```


***

### Mistake 5: Using Flyweight for Unique Objects

```java
❌ // Wrong - Every object has unique intrinsic state
for (int i = 0; i < 10000; i++) {
    String uniqueTexture = "texture_" + i + ".png";  // All different!
    TreeType type = factory.getTreeType("Tree" + i, uniqueTexture, "Color" + i);
}
// Result: 10,000 flyweights, no sharing, wasted effort
```

```java
✅ // Correct - Only use Flyweight when significant sharing
for (int i = 0; i < 10000; i++) {
    String treeType = (i % 5 == 0) ? "Oak" : "Pine";  // Only 2 types
    TreeType type = factory.getTreeType(treeType, ...);
}
// Result: 2 flyweights shared by 10,000 objects
```

**Rule:** Minimum reuse factor of 3x-5x, ideally 100x+

***

## 🆚 Pattern Comparisons

### Flyweight vs Singleton

| Aspect | Flyweight | Singleton |
| :-- | :-- | :-- |
| **Instances** | Multiple shared instances (pool) | ONE instance globally |
| **Purpose** | Memory optimization | Global access point |
| **Variation** | Different flyweights per intrinsic state | No variation |
| **Factory** | Manages pool of flyweights | Returns same instance |
| **Example** | 100 character types shared by 1M chars | One Logger for entire app |

```java
// Singleton - ONE instance
Logger logger1 = Logger.getInstance();
Logger logger2 = Logger.getInstance();
// logger1 == logger2 (always)

// Flyweight - MULTIPLE instances
CharacterFlyweight a = factory.getCharacter('a');
CharacterFlyweight b = factory.getCharacter('b');
// a != b (different intrinsic state)

CharacterFlyweight a2 = factory.getCharacter('a');
// a == a2 (same intrinsic state, shared)
```


***

### Flyweight vs Prototype

| Aspect | Flyweight | Prototype |
| :-- | :-- | :-- |
| **Intent** | Share objects to save memory | Clone objects to create new ones |
| **Creation** | Reuse existing objects | Create copies |
| **Sharing** | Yes, extensive sharing | No sharing |
| **Mutability** | Immutable (intrinsic) | Often mutable |
| **Example** | 1M particles share 4 types | Clone complex object to avoid reconstruction |


***

### Flyweight vs Object Pool

| Aspect | Flyweight | Object Pool |
| :-- | :-- | :-- |
| **Purpose** | Minimize memory via sharing | Reuse expensive-to-create objects |
| **State** | Immutable intrinsic state | Often reset between uses |
| **Concurrent Use** | Multiple clients use simultaneously | One client at a time |
| **Return** | Never returned to pool | Returned after use |
| **Example** | Character glyphs | Database connections |

```java
// Flyweight - Shared concurrently
CharacterFlyweight a = factory.getCharacter('a');
// Used by multiple positions simultaneously, never "returned"

// Object Pool - Exclusive use
Connection conn = pool.acquire();
// Use connection
pool.release(conn);  // Return for reuse
```


***

### Flyweight vs Proxy

| Aspect | Flyweight | Proxy |
| :-- | :-- | :-- |
| **Focus** | Memory optimization | Access control/lazy loading |
| **Sharing** | Essential | Not required |
| **Intrinsic/Extrinsic** | Yes | No |
| **Purpose** | Reduce object count | Control access to object |


***

## 🎯 When to Use Flyweight Pattern

### ✅ Use When:

1. **Massive Number of Objects**
    - Application creates thousands/millions of objects
    - Memory consumption is critical concern
    - Example: Text editor, game with particles/trees
2. **Significant Shared State**
    - Most object state can be made intrinsic (shared)
    - Shared state significantly larger than unique state
    - Example: 100 bytes shared, 8 bytes unique per object
3. **Objects Are Similar**
    - Many objects share same properties
    - Only small portion of state differs
    - Example: 1M characters, only 100 unique fonts
4. **Identity Doesn't Matter**
    - Application doesn't rely on object identity (==)
    - Conceptual identity via intrinsic state is sufficient
5. **Context-Independent Shared State**
    - Intrinsic state doesn't depend on context
    - Can be safely shared across contexts

**Minimum Requirements:**

- Reuse factor: At least 3-5x, ideally 100x+
- Intrinsic state >> Extrinsic state (in terms of memory)
- Many objects with similar properties

***

### ❌ Don't Use When:

1. **Few Objects**
    - Creating 10-100 objects
    - Memory not a concern
    - Complexity not worth it
2. **Unique State**
    - Each object has completely unique properties
    - No shared state to leverage
    - Example: User profiles (each unique)
3. **Mutable Shared State Needed**
    - Shared state needs to change
    - Managing mutable shared state is complex
    - Better to use separate objects
4. **Object Identity Required**
    - Application uses == comparisons
    - Relies on object uniqueness
    - Flyweight breaks identity semantics
5. **Simple State**
    - Objects are already lightweight
    - No significant memory savings possible
    - Example: Small objects with 2-3 primitives

***

## 🧠 Interview Questions \& Answers

### Q1: Explain Flyweight pattern in 2 sentences.

**A:** Flyweight minimizes memory usage by sharing intrinsic (common) state among many objects, while extrinsic (unique) state is stored externally or passed as parameters. It prevents memory exhaustion when creating thousands of similar objects by reusing a small pool of shared flyweight objects.

***

### Q2: What's the difference between intrinsic and extrinsic state?

**A:** Intrinsic state is shared, context-independent, immutable data stored inside the flyweight object (e.g., font, texture, color). Extrinsic state is unique, context-dependent data stored outside the flyweight or passed as method parameters (e.g., position, velocity). The key is that intrinsic state can be safely shared, while extrinsic state differs per context.

***

### Q3: Why must flyweight objects be immutable?

**A:** Because flyweights are shared by multiple contexts simultaneously. If one context modifies shared state, it would unexpectedly affect all other contexts using that flyweight, causing bugs. Immutability ensures thread-safety and prevents unintended side effects.

***

### Q4: How does FlyweightFactory ensure sharing?

**A:** The factory maintains a pool (typically HashMap) of flyweight objects keyed by intrinsic state. When a flyweight is requested, the factory checks if one with matching intrinsic state already exists. If yes, it returns the existing object; if no, it creates a new one, caches it, and returns it. This guarantees only one flyweight per unique intrinsic state combination.

***

### Q5: Calculate memory savings: 100,000 bullets, intrinsic=200 bytes, extrinsic=16 bytes, 1 bullet type.

**A:**

- Without Flyweight: 100,000 × (200+16) = 21,600,000 bytes = 20.6 MB
- With Flyweight: (1 × 200) + (100,000 × 16) = 1,600,200 bytes = 1.53 MB
- Saved: 20.6 - 1.53 = 19.07 MB (93% reduction)

***

### Q6: What's the difference between Flyweight and Singleton?

**A:** Singleton ensures exactly ONE instance globally for a class, providing a global access point. Flyweight creates MULTIPLE shared instances (one per unique intrinsic state) managed by a factory, focusing on memory optimization. Example: One Logger (Singleton) vs 100 character glyphs shared by 1M characters (Flyweight).

***

### Q7: When should you NOT use Flyweight?

**A:** Don't use Flyweight when there's no significant shared state (each object is unique), when creating few objects (memory not a concern), when object identity matters (using == comparisons), or when the complexity outweighs the benefits. Minimum reuse factor should be 3-5x per flyweight.

***

### Q8: Can flyweights be used in multithreaded environments?

**A:** Yes, because flyweights are immutable (intrinsic state never changes), they're inherently thread-safe. Multiple threads can safely share flyweight objects simultaneously without synchronization. However, the factory's pool access might need synchronization if multiple threads create flyweights concurrently.

***

### Q9: Real-world examples of Flyweight?

**A:**

- **String pool in Java** (`String.intern()`)
- **Text editors** (character glyphs shared across document)
- **Games** (particles, trees, bullets sharing models/textures)
- **GUI toolkits** (icons, cursors, fonts)
- **Graphics** (shapes sharing colors/styles)
- **Caching systems** (database query results)

***

### Q10: How to identify intrinsic vs extrinsic state in a problem?

**A:** Ask: "What properties are SAME for many objects?" (intrinsic) vs "What properties are UNIQUE per instance?" (extrinsic). Also: "What doesn't change?" (intrinsic) vs "What changes based on context?" (extrinsic). Example: For forest trees, texture/model are intrinsic (same per tree type), position/health are extrinsic (unique per tree).

***

## ✅ Best Practices

### 1. Always Make Flyweight Immutable

```java
✅ @Getter  // Only getters
   @AllArgsConstructor
   public class Flyweight {
       private final String data;  // Final fields
   }

❌ @Data  // Has setters - wrong!
   public class Flyweight {
       private String data;  // Not final
   }
```


***

### 2. Use Factory Pattern

```java
✅ // Factory ensures sharing
   Flyweight fw = factory.getFlyweight("key");

❌ // Direct creation breaks sharing
   Flyweight fw = new Flyweight("key");
```


***

### 3. Clear Key Generation

```java
✅ // Explicit, understandable key
   private String createKey(String name, String texture, String color) {
       return name + "-" + texture + "-" + color;
   }

❌ // Hash-based key (debugging nightmare)
   private int createKey(String... parts) {
       return Arrays.hashCode(parts);
   }
```


***

### 4. Store Extrinsic State in Client

```java
✅ // Client holds extrinsic state
   public class Client {
       private final Flyweight flyweight;  // Shared
       private final int x, y;             // Extrinsic
   }

❌ // Wrong - extrinsic in flyweight
   public class Flyweight {
       private int x, y;  // Should be in client!
   }
```


***

### 5. Validate Minimum Reuse Factor

```java
// Track and report reuse statistics
public void printStatistics() {
    int reuseCount = totalObjects / uniqueFlyweights;
    if (reuseCount < 3) {
        System.out.println("Warning: Low reuse factor (" + reuseCount + 
                         "x). Consider if Flyweight is worth the complexity.");
    }
}
```


***

### 6. Null Safety in Factory

```java
✅ public Flyweight getFlyweight(String key) {
       if (key == null) {
           throw new IllegalArgumentException("Key cannot be null");
       }
       // ... rest of logic
   }
```


***

### 7. Document State Classification

```java
/**
 * Flyweight storing intrinsic (shared) state.
 * Intrinsic: texture, color, size (same for all particles of this type)
 * Extrinsic: position, velocity (unique per particle, passed to render())
 */
@Getter
@AllArgsConstructor
public class ParticleType {
    private final String texture;  // Intrinsic
    private final String color;    // Intrinsic
    
    public void render(int x, int y) {  // Extrinsic as parameters
        // ...
    }
}
```


***

### 8. Memory Calculation Method

```java
public void calculateMemorySavings(int totalObjects) {
    int intrinsicBytes = 100;
    int extrinsicBytes = 50;
    
    long withoutFlyweight = totalObjects * (intrinsicBytes + extrinsicBytes);
    long withFlyweight = (flyweights.size() * intrinsicBytes) + 
                        (totalObjects * extrinsicBytes);
    long saved = withoutFlyweight - withFlyweight;
    
    System.out.println("Memory saved: " + (saved / 1024.0 / 1024.0) + 
                     " MB (" + (saved * 100 / withoutFlyweight) + "%)");
}
```


***

### 9. Thread-Safe Factory (If Needed)

```java
public class FlyweightFactory {
    private final ConcurrentHashMap<String, Flyweight> flyweights = 
        new ConcurrentHashMap<>();
    
    public Flyweight getFlyweight(String key) {
        // computeIfAbsent is atomic in ConcurrentHashMap
        return flyweights.computeIfAbsent(key, k -> createFlyweight(k));
    }
}
```


***

### 10. Use Enums for Fixed Types

```java
// If intrinsic state combinations are known and fixed
public enum ParticleType {
    FIRE("fire.png", "RED", 6),
    SMOKE("smoke.png", "GRAY", 10),
    EXPLOSION("explosion.png", "YELLOW", 8);
    
    private final String texture;
    private final String color;
    private final int size;
    
    ParticleType(String texture, String color, int size) {
        this.texture = texture;
        this.color = color;
        this.size = size;
    }
    
    // Enum instances are inherently singletons/flyweights
}
```


***

## 🚀 Quick Reference

**Intent:** Minimize memory by sharing common state among many objects

**Structure:**

- Flyweight interface (methods accept extrinsic state)
- Concrete Flyweight (stores immutable intrinsic state)
- Flyweight Factory (manages pool, ensures sharing)
- Client (stores extrinsic state, references flyweight)

**Key Terms:**

- **Intrinsic:** Shared, immutable, stored in flyweight
- **Extrinsic:** Unique, mutable, stored externally/passed as parameter

**When to Use:**

- Thousands+ similar objects
- Significant shared state
- Memory is critical
- Reuse factor 3x+ (ideally 100x+)

**When NOT to Use:**

- Few objects (<100)
- No shared state
- Object identity matters
- Mutable shared state needed

**Memory Formula:**

```
Savings = (N - U) × IntrinsicSize
Where N = total objects, U = unique flyweights
```

**Related Patterns:**

- Singleton (one instance vs pool)
- Object Pool (exclusive use vs concurrent sharing)
- Prototype (cloning vs sharing)

***

## 📌 Copy-Paste Template for Quick Use

```java
// FLYWEIGHT INTERFACE
public interface Flyweight {
    void operation(String extrinsicState);
}

// CONCRETE FLYWEIGHT
@Getter
@AllArgsConstructor
public class ConcreteFlyweight implements Flyweight {
    private final String intrinsicState;  // Immutable
    
    @Override
    public void operation(String extrinsicState) {
        System.out.println("Intrinsic: " + intrinsicState + 
                         " | Extrinsic: " + extrinsicState);
    }
}

// FLYWEIGHT FACTORY
public class FlyweightFactory {
    private final Map<String, Flyweight> pool = new HashMap<>();
    
    public Flyweight getFlyweight(String key) {
        return pool.computeIfAbsent(key, k -> new ConcreteFlyweight(k));
    }
    
    public int getPoolSize() {
        return pool.size();
    }
}

// CLIENT
@AllArgsConstructor
public class Client {
    private final Flyweight flyweight;
    private final String extrinsicState;
    
    public void execute() {
        flyweight.operation(extrinsicState);
    }
}

// USAGE
FlyweightFactory factory = new FlyweightFactory();
Flyweight fw1 = factory.getFlyweight("TypeA");
Flyweight fw2 = factory.getFlyweight("TypeA");  // Reuses fw1
Client c1 = new Client(fw1, "Data1");
Client c2 = new Client(fw2, "Data2");
c1.execute();
c2.execute();
```


***