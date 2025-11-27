# LLD Practice 2025

This project is a collection of common design patterns implemented in Java. It serves as a practical guide and a reference for understanding and applying these patterns in software development.

## Implemented Design Patterns

The project currently includes implementations of the following design patterns:

### Creational Patterns

These patterns deal with object creation mechanisms, trying to create objects in a manner suitable to the situation.

*   **Builder:** Separates the construction of a complex object from its representation, so that the same construction process can create different representations.
*   **Factory:** Defines an interface for creating an object, but lets subclasses alter the type of objects that will be created.
*   **Prototype:** Specifies the kinds of objects to create using a prototypical instance, and creates new objects by copying this prototype.
*   **Singleton:** Ensures a class has only one instance, and provides a global point of access to it.

### Structural Patterns

These patterns ease the design by identifying a simple way to realize relationships between entities.

*   **Adapter:** Allows the interface of an existing class to be used as another interface. It is often used to make existing classes work with others without modifying their source code.
*   **Bridge:** Decouples an abstraction from its implementation so that the two can vary independently.
*   **Composite:** Composes objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.
*   **Decorator:** Attaches additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.
*   **Facade:** Provides a unified interface to a set of interfaces in a subsystem. Facade defines a higher-level interface that makes the subsystem easier to use.
*   **Proxy:** Provides a surrogate or placeholder for another object to control access to it.

## How to Use

Each design pattern is implemented in its own package. You can browse the source code to understand how each pattern works.