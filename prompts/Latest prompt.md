
# 📋 Detailed Prompt for New Chat - Design Pattern Learning


***

```
I'm a Java backend developer preparing for SDE roles. I'm learning Design Patterns systematically and have completed 8 out of 22 patterns so far. I need your help to continue learning in the same structured way.

## My Learning Progress (8/22 Patterns Completed)

**Creational Patterns (4/4 - 100% Complete):**
✅ Singleton
✅ Builder  
✅ Factory (Factory Method + Abstract Factory)
✅ Prototype

**Structural Patterns (4/7 - 57% Complete):**
✅ Adapter
✅ Decorator
✅ Facade
✅ Proxy

**Remaining Patterns to Learn:**
- Structural: Composite, Bridge, Flyweight
- Behavioral: Strategy, Observer, Command, Template Method, Iterator, State, Visitor, Mediator, Memento, Chain of Responsibility, Interpreter

## My Learning Style & Preferences

### Teaching Methodology (Follow This Exact Flow):
For each pattern, follow this sequence:

1. **Introduction** (5-10 minutes)
   - What is the pattern?
   - Real-world analogies
   - When to use vs when NOT to use
   - Pattern comparison (vs similar patterns like Adapter/Decorator/Facade/Proxy)

2. **Design Questions** (Before Implementation)
   - Ask 4-5 conceptual questions to test understanding
   - Let me answer them
   - Discuss my answers with detailed explanations

3. **Implementation Problem** (Hands-on)
   - Give me a real-world problem to implement
   - I'll code it in IntelliJ and share the code
   - Review my code with questions (help me discover issues, don't just tell me)
   - Discuss fixes and improvements

4. **Quiz** (After Implementation)
   - 10 questions covering: core concepts, implementation, design decisions, real-world scenarios
   - Let me answer all questions
   - Review with detailed explanations and score

5. **Revision Notes** (Comprehensive Summary)
   - Complete pattern summary with examples
   - Code templates
   - Common mistakes & solutions
   - Pattern comparisons
   - Interview Q&A
   - Best practices

### Content Preferences:
- **Concise replies** - Avoid bloated explanations, keep responses focused
- **No large tables** unless I explicitly request them
- **Allow explanations and examples** - These are helpful
- **Detailed revision notes** only when I ask for them explicitly
- **Interactive approach** - Ask questions to help me discover concepts
- **Step-by-step problem solving** - Don't give solutions immediately, guide me

### Code Review Style:
- Point out issues by asking questions, not direct corrections
- Help me discover problems myself
- Explain WHY something is wrong, not just WHAT is wrong
- Use visual examples and analogies
- Show before/after comparisons

### What Works Well for Me:
✅ Problem-based learning (implement real scenarios)
✅ Quiz after each pattern (tests understanding)
✅ Revision notes for quick reference
✅ Design questions before implementation
✅ Interactive discussions when confused
✅ Code examples in Java with Lombok annotations
✅ Comparison tables for similar patterns

### What I DON'T Want:
❌ Long chat bloat with repeated explanations
❌ Large tables unless requested
❌ Summaries or conclusions (unnecessary repetition)
❌ Being told solutions directly (let me discover)

## Technical Background

- **Language:** Java (with Lombok)
- **IDE:** IntelliJ IDEA
- **Experience:** Working software developer
- **Focus:** Low-Level Design (LLD) and System Design
- **Goal:** SDE interview preparation

## Key Learnings from Completed Patterns

### Important Concepts I've Mastered:

**Adapter Pattern:**
- Converts incompatible interfaces to work together
- Interface-based composition for flexibility
- One adapter per incompatible system
- Different from Decorator (changes interface vs adds features)

**Decorator Pattern:**
- Adds behavior dynamically without modifying structure
- IS-A + HAS-A with same interface
- Can stack infinitely (milk + sugar + cream)
- Base decorator reduces code duplication
- Order of decoration matters
- Different from Proxy (adds features vs controls access)

**Facade Pattern:**
- Simplifies complex subsystems with unified interface
- Reduces coupling (client → facade, not → all subsystems)
- Multiple focused facades better than one god object
- Rollback on failure is critical
- Different from Adapter and Decorator

**Proxy Pattern:**
- Controls access to an object
- Types: Virtual (lazy load), Protection (access control), Logging (audit), Cache
- Virtual Proxy uses CONCRETE type (creates object)
- Other proxies use INTERFACE type (wrap existing)
- Virtual Proxy is CREATOR, others are WRAPPERS
- Can chain different proxy types
- Different from Decorator (controls when/how vs adds what)

## Request for Next Pattern

Please teach me the next pattern in the sequence. Start with an introduction and follow the exact methodology outlined above:
1. Introduction with comparisons
2. Design questions (let me answer)
3. Implementation problem (I'll code)
4. Quiz (10 questions)
5. Revision notes (when I ask)

After each pattern is complete, I'll ask for the next one. Continue this cycle until all 22 patterns are learned.

Use this exact teaching flow that has worked well so far. Keep responses concise, interactive, and focused on problem-solving.

Let's continue with the next structural pattern!
```


***

## How to Use This Prompt

1. **Copy the entire prompt above** (everything inside the code block)
2. **Paste it into a new chat** with the AI
3. **The AI will start teaching** the next pattern (likely Composite)
4. **Follow the same flow:** Introduction → Questions → Implementation → Quiz → Notes
5. **After each pattern**, just say "next pattern" to continue

***

## Tips for New Chat

- **Keep it focused:** Don't let the chat get bloated like this one
- **One pattern at a time:** Complete fully before moving to next
- **Ask for clarification** when confused (like you did with Proxy concrete vs interface)
- **Share code** for review after implementing
- **Request notes explicitly** when you want revision summary

***

**This prompt captures:**
✅ Your exact learning style
✅ Your progress (8/22 patterns)
✅ Teaching methodology that works for you
✅ Your preferences (concise, interactive, problem-based)
✅ Key learnings from completed patterns
✅ Technical background

**Ready to use in new chat!** 🚀

Start fresh, learn the remaining 14 patterns the same way! 💪

**Good luck with your SDE preparation!** 🎯

